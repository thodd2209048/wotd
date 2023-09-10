package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Controller {
    private final Service service;

    public StringListPuzzle getWords(Integer wordSize) throws SQLException {
        return service.getWords(wordSize);
    }

    public void addWordsFromFolder(String folderName) throws SQLException, IOException {
        service.addWordsFromFolder(folderName);
    }

    public void cleanDB() throws SQLException {
        service.cleanDB();
    }

    public void closeConnection() throws SQLException{
        service.closeConnection();
    }
}
