package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_01")
public class D01 extends AbstractAocPuzzle {

    private List<Integer> leftList;
    private List<Integer> rightList;

    @Override
    protected void partOne() {
        parseLines();
        var totalDistance = calcTotalDistance();
        System.out.println("Total distance: " + totalDistance);
    }

    private void parseLines() {
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();

        for (var line : lines) {
            var split = line.split(" {3}");
            leftList.add(Integer.parseInt(split[0]));
            rightList.add(Integer.parseInt(split[1]));
        }
    }

    private long calcTotalDistance() {
        Collections.sort(leftList);
        Collections.sort(rightList);

        var totalDistance = 0L;

        for (int i = 0; i < leftList.size(); i++) {
            var left = leftList.get(i);
            var right = rightList.get(i);

            totalDistance += Math.max(left, right) - Math.min(left, right);
        }

        return totalDistance;
    }

    @Override
    protected void partTwo() {
        var similarityScore = calcSimilarityScore();
        System.out.println("Similarity Score: " + similarityScore);
    }

    private long calcSimilarityScore() {
        var similarityScore = 0L;

        for (var number : leftList) {
            var count = Collections.frequency(rightList, number);
            similarityScore += (long) number * count;
        }

        return similarityScore;
    }

}
