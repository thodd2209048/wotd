package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WordDAOImpl implements WordDAO {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/SolveWOTD",
                    "postgres",
                    "123"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
        statement.executeBatch();
        connection.commit();
        statement.close();
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
                " WHERE word ~ '[^a-zA-Z]' ");

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
}
