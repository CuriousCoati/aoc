package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_08_sequential")
public class D08Sequential extends AbstractAocPuzzle {

    private static final int SIZE = 99;

    private final int[][] trees = new int[SIZE][SIZE];

    @Override
    protected void partOne() {
        parseInput();
        long start = System.currentTimeMillis();
        Set<String> visibleTrees = findVisibleTrees();
        long end = System.currentTimeMillis();
        System.out.println("visible trees sequential: " + visibleTrees.size() + " (" + (end - start) + "ms)");
    }

    private void parseInput() {
        int x = 0, y = 0;
        for (String line : lines) {
            for (char character : line.toCharArray()) {
                trees[y][x] = Integer.parseInt("" + character);
                x++;
            }
            y++;
            x = 0;
        }
    }

    private Set<String> findVisibleTrees() {
        Set<String> uniqueTrees = new HashSet<>();

        uniqueTrees.addAll(collectVisibleTreesInDirection((i, j) -> i, (i, j) -> j));
        uniqueTrees.addAll(collectVisibleTreesInDirection((i, j) -> i, (i, j) -> SIZE - 1 - j));
        uniqueTrees.addAll(collectVisibleTreesInDirection((i, j) -> j, (i, j) -> i));
        uniqueTrees.addAll(collectVisibleTreesInDirection((i, j) -> SIZE - 1 - j, (i, j) -> i));

        return uniqueTrees;
    }

    private List<String> collectVisibleTreesInDirection(BiFunction<Integer, Integer, Integer> yExpression,
            BiFunction<Integer, Integer, Integer> xExpression) {
        int highest = -1;
        List<String> visibleTrees = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int y = yExpression.apply(i, j);
                int x = xExpression.apply(i, j);
                int current = trees[y][x];

                if (current > highest) {
                    highest = current;
                    visibleTrees.add(y + "_" + x);
                }

                if (9 == current) {
                    break;
                }
            }
            highest = -1;
        }

        return visibleTrees;
    }

    @Override
    protected void partTwo() {
        long start = System.currentTimeMillis();
        int highestScenicScore = rateTrees();
        long end = System.currentTimeMillis();
        System.out.println("highest scenic score sequential: " + highestScenicScore + " (" + (end - start) + "ms)");
    }

    private int rateTrees() {
        int highestScenicScore = 0;

        for (int currentY = 0; currentY < SIZE; currentY++) {
            for (int currentX = 0; currentX < SIZE; currentX++) {
                int up = countInDirection(currentY, currentX, (y) -> y - 1, (x) -> x);
                int right = countInDirection(currentY, currentX, (y) -> y, (x) -> x + 1);
                int down = countInDirection(currentY, currentX, (y) -> y + 1, (x) -> x);
                int left = countInDirection(currentY, currentX, (y) -> y, (x) -> x - 1);

                int treeScore = up * right * down * left;
                if (treeScore > highestScenicScore) {
                    highestScenicScore = treeScore;
                }
            }
        }

        return highestScenicScore;
    }

    private int countInDirection(int y, int x, Function<Integer, Integer> yExpression, Function<Integer, Integer> xExpression) {
        int height = trees[y][x];
        int visibleTrees = 0;

        boolean stop = false;
        do {
            y = yExpression.apply(y);
            x = xExpression.apply(x);

            if (y < 0 || y >= SIZE || x < 0 || x >= SIZE) {
                stop = true;
            } else {
                visibleTrees++;
                int nextHeight = trees[y][x];
                if (nextHeight >= height) {
                    stop = true;
                }
            }
        } while (!stop);

        return visibleTrees;
    }

}
