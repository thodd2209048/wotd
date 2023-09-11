package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

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

    public void closeConnection() throws SQLException {
        service.closeConnection();
    }

    public BigInteger sizeOfDB() throws SQLException {
        return service.sizeOfDB();
    }
}
