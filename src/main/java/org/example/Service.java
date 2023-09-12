package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class Service {
    private final WordDAO wordDAO = new WordDAOImpl();

    public void addNewWord(Path path) throws IOException, SQLException {

        Set<String> wordSet = new HashSet<>();
        Stream<String> lines = Files.lines(path);
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

    public void addWordsFromFolder(String folderName) throws IOException, SQLException {
        Path path = Paths.get(folderName);
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path entry : stream) {
            if (Files.isRegularFile(entry)) {
                addNewWord(entry);
                Files.move(entry, path.resolve("old-resource").resolve(entry.getFileName()));
            }
        }
    }

    public BigInteger sizeOfDB() throws SQLException{
        return wordDAO.sizeOfDB();
    }

    public void cleanDB() throws SQLException {
        wordDAO.cleanDB();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        return wordDAO.getConnection();
    }

    public void closeConnection() throws SQLException{
        wordDAO.close();
    }

}
