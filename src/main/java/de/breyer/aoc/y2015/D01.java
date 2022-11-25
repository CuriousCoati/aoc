package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_01")
public class D01 extends AbstractAocPuzzle {

    private long floor = 0;
    private List<Integer> entersBasement = new ArrayList<>();

    @Override
    protected void partOne() {
        evaluateInstructions(lines.get(0));
        System.out.println("Floor: " + floor);
    }

    private void evaluateInstructions(String line) {
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (character == '(') {
                floor++;
            } else {
                if (floor == 0) {
                    entersBasement.add(i + 1);
                }
                floor--;
            }
        }
    }

    @Override
    protected void partTwo() {
        System.out.println("First Time Basement: " + entersBasement.get(0));
    }
}
