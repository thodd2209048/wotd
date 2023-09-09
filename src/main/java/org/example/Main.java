package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Service service = new Service();
        Controller controller = new Controller(service);
//        controller.addNewWord("How To Grow Your Crypto Portfolio with Recurring Buy.txt");
        StringListPuzzle stringListPuzzle = controller.getWords(5);
        StringListPuzzle newStringListPuzzle = stringListPuzzle.includeFilter(List.of("e", "a"));
        StringListPuzzle newStringListPuzzle2 = newStringListPuzzle.excludeFilter(List.of("t", "av"));
        System.out.println(newStringListPuzzle2);
    }
}