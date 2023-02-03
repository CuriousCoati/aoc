package de.breyer.aoc.y2015;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_08")
public class D08 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        long charactersInString = countCharactersInString();
        long charactersInMemory = countCharactersInMemory();
        System.out.printf("%s - %s = %s%n", charactersInString, charactersInMemory, charactersInString - charactersInMemory);
    }

    private long countCharactersInString() {
        return lines.stream().mapToLong(String::length).sum();
    }

    private long countCharactersInMemory() {
        long count = 0;
        for (String line : lines) {

            boolean escapeSequence = false;
            boolean hexEscape = false;
            int hexCount = 0;

            for (char character : line.substring(1, line.length() - 1).toCharArray()) {
                if (hexEscape) {
                    if (hexCount == 1) {
                        hexCount = 0;
                        hexEscape = false;
                        escapeSequence = false;
                        count++;
                    } else {
                        hexCount++;
                    }
                } else if (escapeSequence) {
                    if (character == '\\' || character == '"') {
                        count++;
                        escapeSequence = false;
                    } else if (character == 'x') {
                        hexEscape = true;
                    } else {
                        count += 2;
                        escapeSequence = false;
                    }
                } else {
                    if (character == '\\') {
                        escapeSequence = true;
                    } else {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    protected void partTwo() {
        long charactersToEncode = countCharactersToEncode();
        long charactersInString = countCharactersInString();
        System.out.printf("%s - %s = %s%n", charactersToEncode, charactersInString, charactersToEncode - charactersInString);

    }

    private long countCharactersToEncode() {
        long count = 0;
        for (String line : lines) {
            count += 2;
            for (char character : line.toCharArray()) {
                if (character == '"' || character == '\\') {
                    count += 2;
                } else {
                    count++;
                }
            }
        }
        return count;
    }

}
