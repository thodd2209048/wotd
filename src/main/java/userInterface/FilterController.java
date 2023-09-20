package userInterface;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FilterController {
    @FXML
    private TextField fxInputWordLength;
    @FXML
    private TextField fxIncludeLetters;
    @FXML
    private TextField excludeLetters;
    @FXML
    private TextField letter1Include;
    @FXML
    private TextField letter2Include;
    @FXML
    private TextField letter3Include;
    @FXML
    private TextField letter5Include;
    @FXML
    private TextField letter4Include;
    @FXML
    private TextField letter6Include;
    @FXML
    private TextField letter7Include;
    @FXML
    private TextField letter8Include;
    @FXML
    private TextField letter1Exclude;
    @FXML
    private TextField letter2Exclude;
    @FXML
    private TextField letter3Exclude;
    @FXML
    private TextField letter4Exclude;
    @FXML
    private TextField letter5Exclude;
    @FXML
    private TextField letter6Exclude;
    @FXML
    private TextField letter7Exclude;
    @FXML
    private TextField letter8Exclude;

    private Integer wordLength;
    private List<String> includeLetterList = new ArrayList<>();

    public void onChangeWordLength() {
        fxInputWordLength.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isInvalidWordLength(newValue)) {
                fxInputWordLength.setText(oldValue);
            } else {
                wordLength = Integer.parseInt(newValue);
            }
        });
    }
    public void onChangeIncludeLetters(KeyEvent event){
        String inputText = event.getText();
        System.out.println(inputText);
        if(inputText.length()!=1 || isInvalidIncludeLetter(inputText)){
            event.consume();
            deleteLastLetter(fxIncludeLetters);
        }
        includeLetterList = stringtoCharList(fxIncludeLetters.getText());
        System.out.println(includeLetterList);
    }

    private void deleteLastLetter(TextField textField) {
        String currentText = textField.getText();
        int caretPosition = textField.getCaretPosition();
        String oldText = currentText.substring(0, caretPosition - 1) + currentText.substring(caretPosition);
        textField.setText(oldText);
        textField.positionCaret(caretPosition - 1);
    }

    private List<String> stringtoCharList(String text) {
        List<String> charList = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            charList.add(String.valueOf(text.charAt(i)));
        }
        return charList;
    }

    private boolean isInvalidIncludeLetter(String newLetter) {
        if (!isLetter(newLetter)) return true;
        if (includeLetterList.contains(newLetter)) return true;
        return false;
    }

    private boolean isLetter(String letter) {
        System.out.println(letter.matches("[a-zA-Z]"));
        return letter.matches("[a-zA-Z]");
    }

    private boolean isInvalidWordLength(String wordLength) {
        return !wordLength.matches("[3-8]");
    }


}
