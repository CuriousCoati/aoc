package de.breyer.aoc.y2015;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_10")
public class D10 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        String sequence = lines.get(0);
        for (int i = 0; i < 40; i++) {
            sequence = processSequence(sequence);
        }
        System.out.println("final sequence: " + sequence.length());
    }

    private String processSequence(String currentSequence) {
        StringBuilder builder = new StringBuilder();

        char currentDigit = 'x';
        int count = 0;

        for (char character : currentSequence.toCharArray()) {
            if (currentDigit != character) {
                if ('x' != currentDigit) {
                    builder.append(count).append(currentDigit);
                }
                count = 1;
                currentDigit = character;
            } else {
                count++;
            }
        }
        builder.append(count).append(currentDigit);

        return builder.toString();
    }

    @Override
    protected void partTwo() {
        String sequence = lines.get(0);
        for (int i = 0; i < 50; i++) {
            sequence = processSequence(sequence);
        }
        System.out.println("final sequence length: " + sequence.length());
    }

}
