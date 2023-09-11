package org.example;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface WordDAO {
    public void close() throws SQLException;
    public void cleanDB() throws SQLException ;
    public void insertWords(List<Word> wordList) throws SQLException;
    public StringListPuzzle getWords(Integer wordSize) throws SQLException;
    public BigInteger sizeOfDB() throws SQLException;
}
