package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class StringListPuzzle {
    private List<String> inputList = new ArrayList<>();

    public StringListPuzzle excludeFilter(List<String> exclude) {
        List<String> newList = new ArrayList<>(inputList);
        newList.removeIf(word -> exclude.stream().anyMatch(e -> word.contains(e)));
        return new StringListPuzzle(newList);
    };

    public StringListPuzzle includeFilter(List<String> include){
        List<String> newList = inputList.stream()
                .filter(word -> include.stream().allMatch(i -> word.contains(i)))
                .toList();
        return new StringListPuzzle(newList);
    }

    public StringListPuzzle singleLetterFilter(Integer position, String correctLetter, List<String> incorrectLetters){
        Integer index = position - 1;
        if (correctLetter !=null && correctLetter.length() > 1) {
            throw new IllegalArgumentException(correctLetter + ": correctLetter is invalid input");
        }
        if (index + 1 >inputList.get(0).length() ){
            throw new IllegalArgumentException(index + ": index is invalid input");
        }
        List<String> newList = new ArrayList<>();

        if (correctLetter != null) {
            newList = inputList.stream()
                    .filter(w -> w.length() > index && w.charAt(index) == correctLetter.charAt(0))
                    .toList();
        } else {
            newList = inputList;
        }
        if (incorrectLetters !=null){
            newList.removeIf(w -> incorrectLetters.stream().anyMatch(letter -> w.charAt(index)==letter.charAt(0)));
        }
        return new StringListPuzzle(newList);
    }
}
