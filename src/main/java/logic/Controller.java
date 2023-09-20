package logic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Data
@AllArgsConstructor
public class Controller {
    private final Service service = new Service();

    public StringListPuzzle getWords(Integer wordSize) throws SQLException {
        return service.getWords(wordSize);
    }

    public void addWordsFromFolder(String folderName) throws SQLException, IOException {
        service.addWordsFromFolder(folderName);
    }

    public List<Article> crawlAndRetrieveArticles(Integer maxLevel, Integer maxNumberOfArticles, String url) throws SQLException, IOException {
        return service.crawlAndRetrieveArticles(maxLevel, maxNumberOfArticles, url);
    }



    public void cleanDB() throws SQLException {
        service.cleanDB();
    }
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        return service.getConnection();
    }

    public void closeConnection() throws SQLException {
        service.closeConnection();
    }

    public BigInteger sizeOfDB() throws SQLException {
        return service.sizeOfDB();
    }

    public void addWordsFromArticles(List<Article> articles) throws SQLException, IOException{
        service.addWordsFromArticles(articles);
    }

    public Article fetchFromLink(String url) throws SQLException{
        return service.fetchFromLink(url);
    }
}
