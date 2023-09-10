package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Service service = new Service();
        Controller controller = new Controller(service);
        controller.addWordsFromFolder("resource");


//        StringListPuzzle stringListPuzzle = controller.getWords(5);
//        StringListPuzzle newStringListPuzzle = stringListPuzzle.includeFilter(List.of("e", "a"));
//        StringListPuzzle newStringListPuzzle2 = newStringListPuzzle.excludeFilter(List.of("t", "av"));
//        System.out.println(newStringListPuzzle2);
//        StringListPuzzle newStringListPuzzle3 = newStringListPuzzle2.singleLetterFilter(3, "e", null);
//        System.out.println(newStringListPuzzle3);
    }
}