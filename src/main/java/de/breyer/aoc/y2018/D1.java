package de.breyer.aoc.y2018;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

import java.util.ArrayList;
import java.util.List;

@AocPuzzle("2018_01")
public class D1 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var numbers = parseInput();
        var result = calcResult(numbers);
        System.out.println("resulting frequency: " + result);
    }

    private List<Long> parseInput() {
        return lines.stream().map(Long::parseLong).toList();
    }

    private long calcResult(List<Long> numbers) {
        return numbers.stream().mapToLong(Long::longValue).sum();
    }

    @Override
    protected void partTwo() {
        var numbers = parseInput();
        var result = findFirstReoccuringFrequency(numbers);
        System.out.println("first frequency reached twice: " + result);
    }

    private long findFirstReoccuringFrequency(List<Long> numbers) {
        boolean found = false;
        long result = 0;
        int index = 0;
        List<Long> cache = new ArrayList<>();

        do {
            result += numbers.get(index);

            if (cache.contains(result)) {
                found = true;
            } else {
                cache.add(result);
                index = index == numbers.size() - 1 ? 0 : index + 1;
            }

        } while(!found);

        return result;
    }
}
