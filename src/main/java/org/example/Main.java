package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.getConnection();

//        controller.addWordsFromFolder("resource");
//        System.out.println(controller.sizeOfDB());
//
//        controller.cleanDB();
//
//        controller.closeConnection();


        StringListPuzzle stringListPuzzle = controller.getWords(7);
        printFiveElements(stringListPuzzle.getInputList());
        StringListPuzzle result = stringListPuzzle.includeFilter(List.of("e", "f", "o"))
                .excludeFilter(List.of("c", "s", "a", "p", "t", "r", "g", "y"))
                .singleLetterFilter(1, null, List.of("f"))
                .singleLetterFilter(2, null, List.of("o"))
//                .singleLetterFilter(3, null, List.of("l"))

                .singleLetterFilter(4, null, List.of("e"))
                .singleLetterFilter(5, null, List.of("e"))
//                .singleLetterFilter(6, "s", null)
//                .singleLetterFilter(7, null, List.of("t"))
                ;
        System.out.println(result);
    }

    public static void printFiveElements(List<String> inputList) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(inputList.size());
            System.out.println(inputList.get(randomIndex));
        }
    }
}