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
}
