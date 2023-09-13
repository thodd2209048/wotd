package org.example;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Crawl {

    @Getter
    private static List<Article> articleList = new ArrayList<>();

    public static void crawler(Integer level, String url, List<String> visited) {
        if (level <= 5 && articleList.size() <= 50) {
            Document doc = readAnArticle(url, visited);

            if (doc != null) {
                for (Element link : doc.select("a[href]")
                ) {
                    String next_link = link.absUrl("href");
                    if (next_link.contains("https://www.binance.com/en/blog") && !visited.contains(next_link)) {
                        crawler(level++, next_link, visited);
                    }
                }
            }
        }
    }

    public static Document readAnArticle(String url, List<String> visited) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200 &&
                    articleList.stream()
                            .noneMatch(a -> a.getTitle().equals(doc.title()))) {

                visited.add(url);

                String title = doc.title();
                Elements spans = doc.getElementsByClass("richtext-text");
                String content = "";
                for (Element span : spans
                ) {
                    content += span.text() + " ";
                }
                articleList.add(new Article(title, url, content, ZonedDateTime.now(), ZonedDateTime.now(), false));
                System.out.println("Article added: " + title + "/ Link: " + url);
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}

