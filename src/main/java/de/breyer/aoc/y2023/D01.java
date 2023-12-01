package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_01")
public class D01 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var numbers = parseLines();
        var sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("sum of calibration values: " + sum);
    }

    private List<Integer> parseLines() {
        var numbers = new ArrayList<Integer>();

        for (var line : lines) {
            char firstDigit = 'x', lastDigit = 'x';
            var chars = line.toCharArray();

            for (char character : chars) {
                if (Character.isDigit(character)) {
                    firstDigit = character;
                    break;
                }
            }

            for (int i = chars.length - 1; i >= 0; i--) {
                if (Character.isDigit(chars[i])) {
                    lastDigit = chars[i];
                    break;
                }
            }

            if (firstDigit != 'x' && lastDigit != 'x') {
                numbers.add(Integer.parseInt(firstDigit + "" + lastDigit));
            }
        }

        return numbers;
    }

    @Override
    protected void partTwo() {
        var numbers = parseLinesEnhanced();
        var sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("sum of calibration values: " + sum);
    }

    private List<Integer> parseLinesEnhanced() {
        var numberStrings = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        var numbers = new ArrayList<Integer>();

        for (var line : lines) {
            char firstDigit = 'x', lastDigit = 'x';
            int indexFirstDigit = Integer.MAX_VALUE, indexLastDigit = Integer.MIN_VALUE;
            var chars = line.toCharArray();

            for (int i = 0; i < lines.size(); i++) {
                if (Character.isDigit(chars[i])) {
                    firstDigit = chars[i];
                    indexFirstDigit = i;
                    break;
                }
            }

            for (int i = chars.length - 1; i >= 0; i--) {
                if (Character.isDigit(chars[i])) {
                    lastDigit = chars[i];
                    indexLastDigit = i;
                    break;
                }
            }

            var smallestStringNumberIndex = Integer.MAX_VALUE;
            var highestStringNumberIndex = Integer.MIN_VALUE;
            var firstStringNumber = -1;
            var lastStringNumber = -1;

            for (int i = 0; i < numberStrings.length; i++) {
                var number = numberStrings[i];
                var index = line.indexOf(number);
                if (index >= 0 && index < smallestStringNumberIndex) {
                    smallestStringNumberIndex = index;
                    firstStringNumber = i + 1;
                }

                index = line.lastIndexOf(number);
                if (index >= 0 && index > highestStringNumberIndex) {
                    highestStringNumberIndex = index;
                    lastStringNumber = i + 1;
                }
            }

            var builder = new StringBuilder();

            if (smallestStringNumberIndex < indexFirstDigit) {
                builder.append(firstStringNumber);
            } else {
                builder.append(firstDigit);
            }

            if (highestStringNumberIndex > indexLastDigit) {
                builder.append(lastStringNumber);
            } else {
                builder.append(lastDigit);
            }

            numbers.add(Integer.parseInt(builder.toString()));
        }

        return numbers;
    }

}
