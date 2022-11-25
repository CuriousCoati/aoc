package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_09")
public class D09 extends AbstractAocPuzzle {

    private final int[][] heightMap = new int[100][100];

    @Override
    protected void partOne() {
        prepareInputs();
        calculateSumOfRiskLevels();
    }

    @Override
    protected void partTwo() {
        productOfBiggestBasins();
    }

    private void prepareInputs() {
        int x = 0;
        int y = 0;

        for (String line : lines) {
            for (char character : line.toCharArray()) {
                heightMap[y][x] = Integer.parseInt("" + character);
                x++;
            }
            x = 0;
            y++;
        }
    }

    private void calculateSumOfRiskLevels() {
        int sumLowPoints = 0;

        for (int y = 0; y < heightMap.length; y++) {
            int[] row = heightMap[y];
            for(int x = 0; x < row.length; x++) {
                int point = row[x];
                boolean lowerThanNeighbours = true;

                if (y > 0) {
                    lowerThanNeighbours = point < heightMap[y-1][x];
                }

                if (lowerThanNeighbours && x > 0) {
                    lowerThanNeighbours = point < heightMap[y][x-1];
                }

                if (lowerThanNeighbours && y < 99) {
                    lowerThanNeighbours = point < heightMap[y+1][x];
                }

                if (lowerThanNeighbours && x < 99) {
                    lowerThanNeighbours = point < heightMap[y][x+1];
                }

                if (lowerThanNeighbours) {
                    sumLowPoints += (point + 1);
                }
            }
        }

        System.out.println("sum of risk levels: " + sumLowPoints);
    }

    private void productOfBiggestBasins() {
        final List<String> checked = new ArrayList<>();
        final List<Integer> basinSizes = new ArrayList<>();

        for (int y = 0; y < heightMap.length; y++) {
            int[] row = heightMap[y];
            for(int x = 0; x < row.length; x++) {
                int basinSize = checkPoint(x, y, 0, checked);
                if (basinSize > 0) {
                    basinSizes.add(basinSize);
                }
            }
        }

        Collections.sort(basinSizes);

        int productOfBiggestBasins = basinSizes.get(basinSizes.size() - 3) * basinSizes.get(basinSizes.size() - 2) * basinSizes.get(basinSizes.size() - 1);

        System.out.println("product Of biggest basins: " + productOfBiggestBasins);
    }

    private String getLocationString(int x, int y) {
        return x + ";" + y;
    }

    private int checkPoint(int x, int y, int basinSize, List<String> checked) {
        int point = heightMap[y][x];
        if (9 != point && !checked.contains(getLocationString(x, y))) {
            basinSize++;
            checked.add(getLocationString(x, y));

            if (x > 0) {
                basinSize = checkPoint(x-1, y, basinSize, checked);
            }

            if (y > 0) {
                basinSize = checkPoint(x, y-1, basinSize, checked);
            }

            if (x < 99) {
                basinSize = checkPoint(x+1, y, basinSize, checked);
            }

            if (y < 99) {
                basinSize = checkPoint(x, y+1, basinSize, checked);
            }
        }

        return basinSize;
    }
}
