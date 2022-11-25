package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_07")
public class D07 extends AbstractAocPuzzle {

    private final List<Integer> crabSubmarines = new ArrayList<>();
    private final List<Integer> triangularNumbers = new ArrayList<>();
    private int median;
    private int mean;

    @Override
    protected void partOne() {
        parseInput();
        calculateMedian();
        calculateFuelByMedian();
    }

    @Override
    protected void partTwo() {
        calculateMean();
        calculateFuelByMean();
    }

    private void parseInput() {
        String[] split = lines.get(0).split(",");
        Arrays.stream(split).forEach(number -> crabSubmarines.add(Integer.parseInt(number)));
    }

    private void calculateMedian() {
        Collections.sort(crabSubmarines);
        int size = crabSubmarines.size();
        if (crabSubmarines.size() % 2 == 0) {
            median = (crabSubmarines.get(size / 2) + crabSubmarines.get(size / 2 - 1)) / 2;
        } else {
            median = crabSubmarines.get(size / 2);
        }
        System.out.println("median: " + median);
    }

    private void calculateMean() {
        int sum = 0;
        for (int crabSubmarine : crabSubmarines) {
            sum += crabSubmarine;
        }
        mean = Math.round(sum / crabSubmarines.size());
        System.out.println("mean: " + mean);
    }

    private void calculateFuelByMedian() {
        long fuelNeeded = 0;
        for (int crabSubmarine : crabSubmarines) {
            if (crabSubmarine < median) {
                fuelNeeded += median - crabSubmarine;
            } else if (crabSubmarine > median) {
                fuelNeeded += crabSubmarine - median;
            }
        }
        System.out.println("Fuel needed by median: " + fuelNeeded);
    }

    private void calculateFuelByMean() {
        long fuelNeeded = 0;
        for (int crabSubmarine : crabSubmarines) {
            if (crabSubmarine < mean) {
                fuelNeeded += getTriangularNumber(mean - crabSubmarine);
            } else if (crabSubmarine > mean) {
                fuelNeeded += getTriangularNumber(crabSubmarine - mean);
            }
        }
        System.out.println("Fuel needed by mean: " + fuelNeeded);
    }

    public int getTriangularNumber(int naturalNumber) {
        if (naturalNumber > triangularNumbers.size()) {
            int triangular = 0;
            if (!triangularNumbers.isEmpty()) {
                triangular = triangularNumbers.get(triangularNumbers.size() - 1);
            }
            for (int i = triangularNumbers.size() + 1; i <= naturalNumber; i++) {
                triangular += i;
                triangularNumbers.add(triangular);
            }
        }
        return triangularNumbers.get(naturalNumber - 1);
    }
}
