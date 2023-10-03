package logic;

import userInterface.WotdController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Service service = new Service();
        service.getConnection();

        service.fetchFromLink(
                "https://academy.binance.com/en/glossary/defi");
        System.out.println(service.sizeOfDB());

        service.cleanDB();
//



//        StringListPuzzle stringListPuzzle = controller.getWords(8);
//        printFiveElements(stringListPuzzle.getInputList());
//        stringListPuzzle = stringListPuzzle.includeExcludeFilter(List.of("u", "n","t","i"),
//                        List.of("f","c","o"))
////                .singleLetterFilter(1, null, List.of("c"))
//                .singleLetterFilter(2, null, List.of("u"))
////                .singleLetterFilter(3, null, List.of("n"))
////                .singleLetterFilter(4, null, List.of("p"))
//                .singleLetterFilter(5, null, List.of("t"))
//                .singleLetterFilter(6, null, List.of("i"))
////                .singleLetterFilter(7, null, List.of("t"))
////                .singleLetterFilter(8, null, List.of("t"))
//                ;
//        System.out.println("Predict: " + stringListPuzzle.getInputList());



        service.closeConnection();
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