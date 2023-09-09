package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@Data
@AllArgsConstructor
public class Controller {
    private final Service service;

    public void addNewWord(String fileName) throws IOException, SQLException {
        Path path = Paths.get("resource/" + fileName);
        service.addNewWord(path);
    }

    public StringListPuzzle getWords(Integer wordSize) throws SQLException {
        return service.getWords(wordSize);
    }

}
