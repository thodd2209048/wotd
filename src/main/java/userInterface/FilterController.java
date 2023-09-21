package userInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import logic.Controller;
import logic.Service;
import logic.StringListPuzzle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static userInterface.UIUtilities.*;


public class FilterController {
    private Service service;
    @FXML
    private TextField fxInputWordLength;
    @FXML
    private TextField fxIncludeLetters;
    @FXML
    private TextField fxExcludeLetters;
    @FXML
    private TextField fxLetter1Correct;
    @FXML
    private TextField fxLetter2Correct;
    @FXML
    private TextField fxLetter3Correct;
    @FXML
    private TextField fxLetter5Correct;
    @FXML
    private TextField fxLetter4Correct;
    @FXML
    private TextField fxLetter6Correct;
    @FXML
    private TextField fxLetter7Correct;
    @FXML
    private TextField fxLetter8Correct;
    @FXML
    private TextField fxLetter1Exclude;
    @FXML
    private TextField fxLetter2Exclude;
    @FXML
    private TextField fxLetter3Exclude;
    @FXML
    private TextField fxLetter4Exclude;
    @FXML
    private TextField fxLetter5Exclude;
    @FXML
    private TextField fxLetter6Exclude;
    @FXML
    private TextField fxLetter7Exclude;
    @FXML
    private TextField fxLetter8Exclude;
    @FXML
    private Label fxResult;

    private Integer wordLength;
    private List<String> includeLetterList = new ArrayList<>();
    private List<String> excludeLetterList = new ArrayList<>();
    private HashMap<TextField, String> textFieldLetterHashMap = new HashMap<>();
    private String letter1;
    private String letter2;
    private String letter3;
    private String letter4;
    private String letter5;
    private String letter6;
    private String letter7;
    private String letter8;
    private List<String> excludeLetterList1 = new ArrayList<>();
    private List<String> excludeLetterList2 = new ArrayList<>();
    private List<String> excludeLetterList3 = new ArrayList<>();
    private List<String> excludeLetterList4 = new ArrayList<>();
    private List<String> excludeLetterList5 = new ArrayList<>();
    private List<String> excludeLetterList6 = new ArrayList<>();
    private List<String> excludeLetterList7 = new ArrayList<>();
    private List<String> excludeLetterList8 = new ArrayList<>();

    public void predict() throws SQLException, ClassNotFoundException {
        Connection connection = service.getConnection();

        String predictResult = "";
        StringListPuzzle stringListPuzzle = service.getWords(wordLength);
        predictResult += printFiveElements(stringListPuzzle.getInputList());
        stringListPuzzle = stringListPuzzle.includeExcludeFilter(includeLetterList,
                        excludeLetterList)
                .singleLetterFilter(1, letter1, excludeLetterList1)
                .singleLetterFilter(2, letter2, excludeLetterList2)
                .singleLetterFilter(3, letter3, excludeLetterList3)
                .singleLetterFilter(4, letter4, excludeLetterList4)
                .singleLetterFilter(5, letter5, excludeLetterList5)
                .singleLetterFilter(6, letter6, excludeLetterList6)
                .singleLetterFilter(7, letter7, excludeLetterList7)
                .singleLetterFilter(8, letter8, excludeLetterList8)
        ;
        predictResult += "\nPredict: " + stringListPuzzle.getInputList();
        fxResult.setText(predictResult);
//        service.closeConnection();
    }

    private String printFiveElements(List<String> inputList) {
        String output = "5 random words: ";

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(inputList.size());
            output += inputList.get(randomIndex) + ", ";
        }
        output += "\n-----";
        return output;
    }

    public void onChangeWordLength(KeyEvent event) {
        String inputText = event.getCharacter();

        if (inputText.length() != 1 || isInvalidWordLength(inputText)) {
            event.consume();
            fxInputWordLength.setText(null);
        }
        if (fxInputWordLength.getText() != null) {
            wordLength = Integer.valueOf(fxInputWordLength.getText());
        }
    }

    public void onChangeIncludeLetters(KeyEvent event) {
        String inputText = event.getCharacter();
        if (inputText.isEmpty()) {
            includeLetterList = null;
        } else if (inputText.length() > 1 || isInvalidIncludeLetter(inputText)) {
            event.consume();
            deleteLastLetter(fxIncludeLetters);
        }
        includeLetterList = stringtoCharList(fxIncludeLetters.getText());
    }

    public void onChangeExcludeLetters(KeyEvent event) {
        String inputText = event.getCharacter();
        if (inputText.isEmpty()) {
            includeLetterList = null;
        } else if (inputText.length() != 1 || isInvalidExcludeLetter(inputText)) {
            event.consume();
            deleteLastLetter(fxExcludeLetters);
        }
        excludeLetterList = stringtoCharList(fxExcludeLetters.getText());
    }

    public void onChangeCorrectLetter(KeyEvent event) {
        String inputText = event.getCharacter();
        TextField textField = (TextField) event.getSource();
        textField.setText(inputText);
        if (inputText.length() != 1 || isInvalidLetterCorrect(inputText)) {
            event.consume();
            textField.setText(null);
        }
        if (textField.getText() != null) {
            String correctLetter = textField.getText();
            String target = textFieldLetterHashMap.get(textField);
            switch (target) {
                case ("letter1"):
                    letter1 = textField.getText();
                    break;
                case ("letter2"):
                    letter2 = textField.getText();
                    break;
                case ("letter3"):
                    letter3 = textField.getText();
                    break;
                case ("letter4"):
                    letter4 = textField.getText();
                    break;
                case ("letter5"):
                    letter5 = textField.getText();
                    break;
                case ("letter6"):
                    letter6 = textField.getText();
                    break;
                case ("letter7"):
                    letter7 = textField.getText();
                    break;
                case ("letter8"):
                    letter8 = textField.getText();
                    break;
            }
        }
    }

    public void onChangExcludeLetter(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String inputText = event.getCharacter();
        if (inputText.length() != 1 || !isLetter(inputText)) {
            event.consume();
            deleteLastLetter(textField);
        }
        List<String> excludeList = stringtoCharList(textField.getText());
        String target = textFieldLetterHashMap.get(textField);
        switch (target) {
            case ("letter1"):
                excludeLetterList1 = excludeList;
                break;
            case ("letter2"):
                excludeLetterList2 = excludeList;
                break;
            case ("letter3"):
                excludeLetterList3 = excludeList;
                break;
            case ("letter4"):
                excludeLetterList4 = excludeList;
                break;
            case ("letter5"):
                excludeLetterList5 = excludeList;
                break;
            case ("letter6"):
                excludeLetterList6 = excludeList;
                break;
            case ("letter7"):
                excludeLetterList7 = excludeList;
                break;
            case ("letter8"):
                excludeLetterList8 = excludeList;
                break;
        }
    }

    private boolean isInvalidLetterCorrect(String letter) {
        if (!isLetter(letter)) return true;
        if (excludeLetterList.contains(letter)) return true;
        return false;
    }

    private boolean isInvalidExcludeLetter(String letter) {
        if (!isLetter(letter)) return true;
        if (includeLetterList != null && includeLetterList.contains(letter)) return true;
        if (excludeLetterList != null && excludeLetterList.contains(letter)) return true;
        return false;
    }

    private boolean isInvalidIncludeLetter(String letter) {
        if (!isLetter(letter)) return true;
        if (includeLetterList != null && includeLetterList.contains(letter)) return true;
        return false;
    }

    private boolean isInvalidWordLength(String wordLength) {
        return !wordLength.matches("[3-8]");
    }

    @FXML
    public void initialize() {
        service = new Service();
        textFieldLetterHashMap.put(fxLetter1Correct, "letter1");
        textFieldLetterHashMap.put(fxLetter2Correct, "letter2");
        textFieldLetterHashMap.put(fxLetter3Correct, "letter3");
        textFieldLetterHashMap.put(fxLetter4Correct, "letter4");
        textFieldLetterHashMap.put(fxLetter5Correct, "letter5");
        textFieldLetterHashMap.put(fxLetter6Correct, "letter6");
        textFieldLetterHashMap.put(fxLetter7Correct, "letter7");
        textFieldLetterHashMap.put(fxLetter8Correct, "letter8");
        textFieldLetterHashMap.put(fxLetter1Exclude, "letter1");
        textFieldLetterHashMap.put(fxLetter2Exclude, "letter2");
        textFieldLetterHashMap.put(fxLetter3Exclude, "letter3");
        textFieldLetterHashMap.put(fxLetter4Exclude, "letter4");
        textFieldLetterHashMap.put(fxLetter5Exclude, "letter5");
        textFieldLetterHashMap.put(fxLetter6Exclude, "letter6");
        textFieldLetterHashMap.put(fxLetter7Exclude, "letter7");
        textFieldLetterHashMap.put(fxLetter8Exclude, "letter8");

    }

}
