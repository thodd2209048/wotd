package logic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class Service {
    private static final WordDAO wordDAO = new WordDAOImpl();

    public void addWordsFromFolder(String folderName) throws IOException, SQLException {
        Path path = Paths.get(folderName);
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path file : stream) {
            if (Files.isRegularFile(file)) {
                Stream<String> lines = Files.lines(file);
                addWordsFromLines(lines);
                Files.move(file, path.resolve("old-resource").resolve(file.getFileName()));
            }
        }
    }

    public void addWordsFromArticles(List<Article> articles) throws SQLException, IOException {
        for (Article article : articles
        ) {
            Stream<String> lines = Stream.of(article.getContent());
            addWordsFromLines(lines);
            wordDAO.addArticleToDB(article);
        }
    }

    public List<Article> crawlAndRetrieveArticles(Integer maxLevel, Integer maxNumberOfArticles, String url) throws SQLException, IOException {
        List<Article> visited = wordDAO.getArticlesFromDB();
        List<Article> newArticles = new ArrayList<>();
        crawl(1, maxLevel,maxNumberOfArticles, url, visited, newArticles);
        addWordsFromArticles(newArticles);
        return newArticles;
    }


    public void addWordsFromLines(Stream<String> lines) throws IOException, SQLException {
        Set<String> wordSet = new HashSet<>();
        lines.flatMap(line -> Stream.of(line.split("[\\s+,:’'‘“().\\[\\]{}?!`~*@#$%^&\\/\\-\"]")))
                .filter(word -> word.matches(".*[a-zA-Z].*") && !word.matches(".*\\d.*") && !word.contains("'"))
                .filter(word -> word.length() < 9 && word.length() > 2)
                .map(String::toLowerCase)
                .forEach(wordSet::add);
        List<Word> wordList = wordSet.stream()
                .map(w -> new Word(w, w.length()))
                .toList();
        wordDAO.insertWords(wordList);
    }

    public StringListPuzzle getWords(Integer wordSize) throws SQLException {
        return wordDAO.getWords(wordSize);
    }

    public BigInteger sizeOfDB() throws SQLException {
        return wordDAO.sizeOfDB();
    }

    public void cleanDB() throws SQLException {
        wordDAO.cleanDB();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return wordDAO.getConnection();
    }

    public void closeConnection() throws SQLException {
        wordDAO.close();
    }

    public List<Article> getArticlesFromDB() throws SQLException {
        return wordDAO.getArticlesFromDB();
    }


    public static void crawl(Integer level, Integer maxLevel, Integer maxNumberOfArticles,String url, List<Article> visited, List<Article> newArticles) {
        if (level > maxLevel || newArticles.size() > maxNumberOfArticles) {
            return;
        }

        Document doc = fetchAndSaveArticle(url, visited, newArticles);

        if (doc != null) {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String nextLink = link.absUrl("href");
                if(nextLink.contains("https://www.binance.com/en/blog")){
                    crawl(level + 1, maxLevel,maxNumberOfArticles, nextLink, visited, newArticles);
                }
            }
        }
    }

    public static Document fetchAndSaveArticle(String url, List<Article> visited, List<Article> newArticles) {
        try {
            org.jsoup.Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                String title = doc.title();
                if (url.contains("https://www.binance.com/en/blog")
                        && visited.stream().noneMatch(v -> v.getTitle().equals(title))
                        && newArticles.stream().noneMatch(v -> v.getTitle().equals(title)))  {
                    Elements spans = doc.getElementsByClass("richtext-text");
                    String content = spans.text();
                    newArticles.add(new Article(title, url, content, ZonedDateTime.now(), ZonedDateTime.now()));
                    System.out.println("Article added: " + title + " // Link: " + url);
                }
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Article fetchFromLink(String url) throws SQLException {
        List<Article> visited = wordDAO.getArticlesFromDB();
        try {
            org.jsoup.Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                String title = doc.title();
                if (visited.stream().noneMatch(v -> v.getTitle().equals(title)))  {
                    Elements spans = doc.getElementsByClass("richtext-text");
                    String content = spans.text();
                    Stream<String> lines = Stream.of(content);
                    addWordsFromLines(lines);

                    Article newArticle = new Article(title, url, content, ZonedDateTime.now(), ZonedDateTime.now());
                    wordDAO.addArticleToDB(newArticle);

                    System.out.println("Article added: " + title + " // Link: " + url);
                    return newArticle;
                }
                return null;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
