package de.breyer.aoc.y2022;

import java.util.function.BiFunction;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_04")
public class D04 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        int fullyContained = countByComparison(
                (firstRange, secondRange) -> firstRange.containsRange(secondRange) || secondRange.containsRange(firstRange));
        System.out.println("fully contained: " + fullyContained);
    }

    private int countByComparison(BiFunction<Range, Range, Boolean> expression) {
        int count = 0;
        for (String line : lines) {
            String[] pairs = line.split(",");
            Range firstRange = new Range(pairs[0].split("-"));
            Range secondRange = new Range(pairs[1].split("-"));

            if (expression.apply(firstRange, secondRange)) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected void partTwo() {
        int overlapping = countByComparison(Range::overlaps);
        System.out.println("overlapping: " + overlapping);
    }

}
