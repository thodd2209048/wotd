package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.getConnection();

        controller.addWordsFromFolder("resource");
        System.out.println(controller.sizeOfDB());

        controller.cleanDB();
//



        StringListPuzzle stringListPuzzle = controller.getWords(7);
        printFiveElements(stringListPuzzle.getInputList());
        stringListPuzzle = stringListPuzzle.includeExcludeFilter(List.of("a", "i", "l","d"), List.of("f", "u", "r", "e", "g","t"))
                .singleLetterFilter(1, "d", null)
                .singleLetterFilter(2, "i", null)
                .singleLetterFilter(3, null, List.of("i"))
                .singleLetterFilter(4, null, List.of("l"))
////                .singleLetterFilter(5, null, List.of("e"))
                .singleLetterFilter(6, "a", null)
                .singleLetterFilter(7, null, List.of("l"))
                ;
        System.out.println("Predict: " + stringListPuzzle.getInputList());



        controller.closeConnection();
    }

    public static void printFiveElements(List<String> inputList) {
        String output = "5 random words: ";

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(inputList.size());
            output += inputList.get(randomIndex) +", ";
        }
        System.out.println(output);
        System.out.println("-----");
    }
}