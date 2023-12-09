package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_09")
public class D09 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var sequences = parseInput();
        var extrapolationSum = sequences.stream().mapToLong(s -> extrapolate(s, true)).sum();
        System.out.println("sum of extrapolations: " + extrapolationSum);
    }

    private List<List<Long>> parseInput() {
        var sequences = new ArrayList<List<Long>>();

        for (var line : lines) {
            var sequence = new ArrayList<Long>();
            sequences.add(sequence);
            var split = line.split(" ");

            for (var numberString : split) {
                sequence.add(Long.parseLong(numberString));
            }
        }

        return sequences;
    }

    private long extrapolate(List<Long> sequence, boolean forward) {
        var differenceSequence = new ArrayList<Long>();
        var allZeros = true;

        for (var i = 0; i < sequence.size() - 1; i++) {
            var difference = sequence.get(i + 1) - sequence.get(i);
            differenceSequence.add(difference);

            if (0 != difference) {
                allZeros = false;
            }
        }

        if (allZeros) {
            return forward ? sequence.get(sequence.size() - 1) : sequence.get(0);
        } else {
            var nextDiff = extrapolate(differenceSequence, forward);
            return forward ? sequence.get(sequence.size() - 1) + nextDiff : sequence.get(0) - nextDiff;
        }
    }

    protected void partTwo() {
        var sequences = parseInput();
        var extrapolationSum = sequences.stream().mapToLong(s -> extrapolate(s, false)).sum();
        System.out.println("sum of extrapolations: " + extrapolationSum);
    }

}
