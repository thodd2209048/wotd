package org.example;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface WordDAO {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
    public void close() throws SQLException;
    public void cleanDB() throws SQLException ;
    public void insertWords(List<Word> wordList) throws SQLException;
    public StringListPuzzle getWords(Integer wordSize) throws SQLException;
    public BigInteger sizeOfDB() throws SQLException;
    public void addArticleToDB(Article article) throws SQLException;
    public List<Article> getArticlesFromDB() throws SQLException;
}
