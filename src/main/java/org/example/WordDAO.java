package org.example;

import java.sql.SQLException;
import java.util.List;

public interface WordDAO {
    public void insertWords(List<Word> wordList) throws SQLException;
    public List<Word> getWords(Integer wordSize) throws SQLException;
}
