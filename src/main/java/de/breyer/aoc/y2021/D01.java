package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_01")
public class D01 extends AbstractAocPuzzle {

    private final List<Integer> sums = new ArrayList<>();

    @Override
    protected void partOne() {
        evaluateInput();
        buildSums();
    }

    @Override
    protected void partTwo() {
        evaluateSums();
    }

    private void evaluateInput() {
        int countIncreased = 0;
        Integer previousNumber = null;

        for (String line : lines) {
            int currentNumber = Integer.parseInt(line);
            if (null != previousNumber && previousNumber < currentNumber) {
                countIncreased++;
            }
            previousNumber = currentNumber;
        }

        System.out.println("Count Increased (line based): " + countIncreased);
    }

    private void buildSums() {
        for (int idx = 2; idx < lines.size(); idx++) {
            int firstNumber = Integer.parseInt(lines.get(idx - 2));
            int secondNumber = Integer.parseInt(lines.get(idx - 1));
            int thirdNumber = Integer.parseInt(lines.get(idx));
            int sum =  firstNumber + secondNumber + thirdNumber;
            sums.add(sum);
        }
    }

    private void evaluateSums() {
        int countIncreased = 0;
        Integer previousSum = null;

        for (Integer sum : sums) {
            if (null != previousSum && previousSum < sum) {
                countIncreased++;
            }
            previousSum = sum;
        }

        System.out.println("Count Increased (sum based): " + countIncreased);
    }

}
