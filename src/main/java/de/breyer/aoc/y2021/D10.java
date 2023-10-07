package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_10")
public class D10 extends AbstractAocPuzzle {

    private final Map<Character, Integer> scorePerCharacter = new HashMap<>();
    private final Map<Character, Character> charPairs = new HashMap<>();
    private final Map<Character, Integer> completionScores = new HashMap<>();

    @Override
    protected void partOne() {
        initScores();
        initCharPairs();
        initCompletionScore();
        removeCorruptedLines();
    }

    @Override
    protected void partTwo() {
        calculateMiddleScoreOfIncompleteLines();
    }

    private void initScores() {
        scorePerCharacter.put(')', 3);
        scorePerCharacter.put(']', 57);
        scorePerCharacter.put('}', 1197);
        scorePerCharacter.put('>', 25137);
    }

    private void initCharPairs() {
        charPairs.put('(', ')');
        charPairs.put('[', ']');
        charPairs.put('{', '}');
        charPairs.put('<', '>');
    }

    private void initCompletionScore() {
        completionScores.put(')', 1);
        completionScores.put(']', 2);
        completionScores.put('}', 3);
        completionScores.put('>', 4);
    }

    private void removeCorruptedLines() {
        int errorScore = 0;
        final List<String> corruptedLines = new ArrayList<>();

        for (String line : lines) {
            List<Character> openingChars = new ArrayList<>();
            for (char character: line.toCharArray()) {
                if (character == '(' || character == '[' || character == '{' || character == '<') {
                    openingChars.add(character);
                } else if (character == charPairs.get(openingChars.get(openingChars.size() -1))) {
                    openingChars.remove(openingChars.size() -1);
                } else {
                    errorScore += scorePerCharacter.get(character);
                    corruptedLines.add(line);
                    break;
                }
            }
        }

        lines.removeAll(corruptedLines);
        System.out.println("error score: " + errorScore);
    }

    private void calculateMiddleScoreOfIncompleteLines() {
        List<Long> lineScores = new ArrayList<>();

        for (String line : lines) {
            List<Character> openingChars = new ArrayList<>();

            for (char character: line.toCharArray()) {
                if (character == '(' || character == '[' || character == '{' || character == '<') {
                    openingChars.add(character);
                } else if (character == charPairs.get(openingChars.get(openingChars.size() -1))) {
                    openingChars.remove(openingChars.size() -1);
                } else {
                    throw new RuntimeException("There shouldn't be anymore corrupted lines");
                }
            }

            long lineScore = 0;

            for (int idx = openingChars.size() - 1; idx >= 0; idx--) {
                char openingChar = openingChars.get(idx);
                char closingChar = charPairs.get(openingChar);
                lineScore = lineScore * 5L + completionScores.get(closingChar);
            }

            lineScores.add(lineScore);
        }

        Collections.sort(lineScores);
        System.out.println("middle score of incomplete lines: " + (lineScores.get(lineScores.size() / 2)));
    }
}
