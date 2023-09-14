package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class StringListPuzzle {
    private List<String> inputList;

    public StringListPuzzle includeExcludeFilter(List<String> include, List<String> exclude) {
        List<String> duplicate = exclude.stream().filter(e -> include.contains(e)).toList();
        if (!duplicate.isEmpty()) {
            throw new RuntimeException("Exclude list is invalid: " +duplicate);
        } else {
            return includeFilter(include).excludeFilter(exclude);
        }
    }

    public StringListPuzzle singleLetterFilter(Integer position, String correctLetter, List<String> incorrectLetters) {
        try {
            if (inputList.isEmpty()) {
                throw new RuntimeException("InputList is empty. The answer is not in the database.");
            }
            if (correctLetter != null && correctLetter.length() > 1) {
                throw new IllegalArgumentException(correctLetter + ": correctLetter is invalid input");
            }
            if (position < 1 || position > inputList.get(0).length()) {
                throw new IllegalArgumentException((position - 1) + ": position is invalid input");
            }
            List<String> newList = inputList.stream()
                    .filter(w -> {
                        if (correctLetter != null) {
                            return w.charAt(position - 1) == correctLetter.charAt(0);
                        }
                        return true;
                    })
                    .filter(w -> {
                        if (incorrectLetters != null) {

                            return incorrectLetters.stream().noneMatch(letter -> {
                                return w.charAt(position - 1) == letter.charAt(0);
                            });
                        }
                        return true;
                    })
                    .toList();

            return new StringListPuzzle(newList);
        } catch (RuntimeException e) {
            System.out.println("Exception in position " + position);
            e.printStackTrace();
        }
        return null;
    }

    public StringListPuzzle excludeFilter(List<String> exclude) {
        List<String> newList = new ArrayList<>(inputList);
        newList.removeIf(word -> exclude.stream().anyMatch(word::contains));
        return new StringListPuzzle(newList);
    }

    public StringListPuzzle includeFilter(List<String> include) {
        List<String> newList = inputList.stream()
                .filter(word -> include.stream().allMatch(word::contains))
                .toList();
        return new StringListPuzzle(newList);
    }
}
