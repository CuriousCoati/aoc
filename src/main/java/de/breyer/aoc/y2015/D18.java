package de.breyer.aoc.y2015;

import java.util.function.Consumer;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_18")
public class D18 extends AbstractAocPuzzle {

    private static final int STEPS = 100;

    @Override
    protected void partOne() {
        var grid = readInput();
        grid = animate(grid, null);
        int turnedOn = countTurnedOn(grid);
        System.out.println("turned on: " + turnedOn);
    }

    private boolean[][] readInput() {
        var grid = new boolean[lines.size()][lines.get(0).length()];

        for (int x = 0; x < lines.size(); x++) {
            for (int y = 0; y < lines.get(0).length(); y++) {
                grid[x][y] = lines.get(x).charAt(y) == '#';
            }
        }

        return grid;
    }

    private boolean[][] animate(boolean[][] grid, Consumer<boolean[][]> postStepCalcuationAction) {
        boolean[][] result = grid;
        for (int step = 0; step < STEPS; step++) {
            var nextGrid = new boolean[grid.length][grid[0].length];

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    nextGrid[x][y] = nextState(x, y, result);
                }
            }

            if (null != postStepCalcuationAction) {
                postStepCalcuationAction.accept(nextGrid);
            }
            result = nextGrid;
        }
        return result;
    }

    private boolean nextState(int x, int y, boolean[][] grid) {
        int neighboursOn = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((x == i && y == j) || i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
                    continue;
                }

                if (grid[i][j]) {
                    neighboursOn++;
                }
            }
        }

        return (grid[x][y] && neighboursOn >= 2 && neighboursOn <= 3) || (!grid[x][y] && neighboursOn == 3);
    }

    private int countTurnedOn(boolean[][] grid) {
        int turnedOn = 0;
        for (boolean[] booleans : grid) {
            for (int y = 0; y < grid[0].length; y++) {
                if (booleans[y]) {
                    turnedOn++;
                }
            }
        }
        return turnedOn;
    }

    @Override
    protected void partTwo() {
        var grid = readInput();
        lightCorners(grid);
        grid = animate(grid, this::lightCorners);
        int turnedOn = countTurnedOn(grid);
        System.out.println("turned on: " + turnedOn);
    }

    private void lightCorners(boolean[][] grid) {
        grid[0][0] = true;
        grid[0][grid[0].length - 1] = true;
        grid[grid.length - 1][0] = true;
        grid[grid.length - 1][grid[0].length - 1] = true;
    }

}
