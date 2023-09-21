package userInterface;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class UIUtilities {
    public static boolean isLetter(String letter) {
        return letter.matches("[a-zA-Z]") && letter.length() == 1;
    }

    public static List<String> stringtoCharList(String text) {
        if(text == null || text.isEmpty()){
            return null;
        }
        List<String> charList = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            charList.add(String.valueOf(text.charAt(i)));
        }
        return charList;
    }

    public static void deleteLastLetter(TextField textField) {
        String currentText = textField.getText();
        int caretPosition = textField.getCaretPosition();

        if(caretPosition == 0){
            textField.setText(null);
            return;
        }

        String oldText = currentText.substring(0, caretPosition - 1) + currentText.substring(caretPosition);
        textField.setText(oldText);
        textField.positionCaret(caretPosition - 1);
    }
}
