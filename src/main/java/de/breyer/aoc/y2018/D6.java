package de.breyer.aoc.y2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.utils.MathUtil;

@AocPuzzle("2018_06")
public class D6 extends AbstractAocPuzzle {

    private static final int SIZE = 1000;

    @Override
    protected void partOne() {
        var coordinates = parseInput();
        var grid = fillGrid(coordinates);
        var largestFiniteArea = findLargestFiniteArea(grid);
        System.out.println("largest finite area: " + largestFiniteArea);
    }

    private List<Point2D> parseInput() {
        var coordinates = new ArrayList<Point2D>();

        for (var line : lines) {
            var split = line.split(", ");
            var coordinate = new Point2D(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            coordinates.add(coordinate);
        }

        return coordinates;
    }

    private Point2D[][] fillGrid(List<Point2D> coordinates) {
        var grid = new Point2D[SIZE][SIZE];

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {

                Point2D nearestCoordinate = null;
                int shortestDistance = 0;
                boolean sameDistance = false;

                for (var coordinate : coordinates) {
                    var distance = MathUtil.manhattenDistance(new Point2D(x, y), coordinate);

                    if (nearestCoordinate == null) {
                        nearestCoordinate = coordinate;
                        shortestDistance = distance;
                    } else {
                        if (distance < shortestDistance) {
                            nearestCoordinate = coordinate;
                            shortestDistance = distance;
                            sameDistance = false;
                        } else if (distance == shortestDistance) {
                            sameDistance = true;
                        }
                    }
                }

                if (!sameDistance) {
                    grid[y][x] = nearestCoordinate;
                }

            }
        }

        return grid;
    }

    private int findLargestFiniteArea(Point2D[][] grid) {
        var countMap = new HashMap<Point2D, Integer>();

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                var coordinate = grid[y][x];
                if (coordinate != null) {
                    countMap.put(coordinate, countMap.getOrDefault(coordinate, 0) + 1);
                }
            }
        }

        removeInfiniteCoordinates(grid, countMap);

        return countMap.values().stream().max(Integer::compareTo).orElseThrow();
    }

    private void removeInfiniteCoordinates(Point2D[][] grid, HashMap<Point2D, Integer> countMap) {
        for (int i = 0; i < SIZE; i++) {
            countMap.remove(grid[0][i]);
            countMap.remove(grid[i][0]);
            countMap.remove(grid[SIZE - 1][i]);
            countMap.remove(grid[i][SIZE - 1]);
        }
    }

    @Override
    protected void partTwo() {
        var coordinates = parseInput();
        var areaSize = calcAreaSizeWithTotalDistance(coordinates);
        System.out.println("size of safe area: " + areaSize);
    }

    private int calcAreaSizeWithTotalDistance(List<Point2D> coordinates) {
        var areaSize = 0;

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                var totalDistance = 0;
                for (var coordinate : coordinates) {
                    totalDistance += MathUtil.manhattenDistance(new Point2D(x, y), coordinate);
                }

                if (totalDistance < 10000) {
                    areaSize++;
                }
            }
        }

        return areaSize;
    }

}