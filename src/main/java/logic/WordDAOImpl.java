package logic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WordDAOImpl implements WordDAO {
    private static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/SolveWOTD";
    private static final String user = "postgres";
    private static final String password = "123";
    private static final ZoneId zoneIdSaiGon = ZoneId.of("Asia/Ho_Chi_Minh");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if(connection == null){
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void insertWords(List<Word> wordList) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                " INSERT INTO words (word, length) " +
                        " VALUES (?,?) " +
                        " ON CONFLICT (word) DO NOTHING;"
        );
        connection.setAutoCommit(false);
        for (Word word : wordList
        ) {
            statement.setString(1, word.getWord());
            statement.setInt(2, word.getWordSize());
            statement.addBatch();
        }
        int[] batchResult = statement.executeBatch();
        int successfulStatements = 0;

        for (int result : batchResult) {
            if (result == Statement.SUCCESS_NO_INFO || result > 0) {
                successfulStatements++;
            }
        }

        connection.commit();
        statement.close();
        System.out.println("Inserted " + successfulStatements + " row into the database.");
    }

    @Override
    public StringListPuzzle getWords(Integer wordSize) throws SQLException {
        StringListPuzzle stringListPuzzle = new StringListPuzzle(new ArrayList<>());
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM words WHERE length = ?"
        );
        statement.setInt(1, wordSize);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            String word = resultSet.getString("word");
            stringListPuzzle.getInputList().add(word);
        }
        statement.close();
        return stringListPuzzle;
    }

    public void cleanDB() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM words " +
                " WHERE word ~ '[^a-zA-Z]+' ");

        statement.close();
    }

    public BigInteger sizeOfDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT COUNT(*) FROM words;"
        );
        BigInteger dbSize = BigInteger.ZERO;
        if (resultSet.next()) {
            dbSize = BigInteger.valueOf(resultSet.getLong(1));
        }
        statement.close();
        return dbSize;
    }

    public void addArticleToDB(Article article) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO articles(title, url, created_at, updated_at) " +
                " VALUES(?, ?, now(), now()) " +
                " ON CONFLICT (title) DO NOTHING; ");

        connection.setAutoCommit(false);
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getUrl());
        statement.execute();
        connection.commit();
        statement.close();
    }

    public List<Article> getArticlesFromDB() throws SQLException {
        List<Article> result = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM articles"
        );
        while (resultSet.next()){
            String title = resultSet.getString("title");
            String url = resultSet.getString("url");
            ZonedDateTime createdAt = resultSet.getTimestamp("created_at").toInstant().atZone(zoneIdSaiGon);
            ZonedDateTime updatedAt = resultSet.getTimestamp("updated_at").toInstant().atZone(zoneIdSaiGon);
            result.add(new Article(title, url, createdAt, updatedAt));
        }
        statement.close();
        return result;
    }
}
