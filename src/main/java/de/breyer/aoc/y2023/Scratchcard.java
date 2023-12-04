package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Scratchcard {

    private final int number;
    private final List<Integer> winningNumbers = new ArrayList<>();
    private final List<Integer> actualNumbers = new ArrayList<>();

    public long calcPoints() {
        return (long) Math.pow(2, getCountOfMatchingNumbers() - 1);
    }

    public int getCountOfMatchingNumbers() {
        int count = 0;

        for (var number : actualNumbers) {
            if (winningNumbers.contains(number)) {
                count++;
            }
        }

        return count;
    }
}
