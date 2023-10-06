package de.breyer.aoc.y2015;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_25")
public class D25 extends AbstractAocPuzzle {

    private static final int SEARCHED_ROW = 2978;
    private static final int SEARCHED_COL = 3083;
    private static final int MAX_SIZE = 5000;
    private static final long START_VALUE = 20151125;
    private static final long MULTIPLICAND = 252533;
    private static final long DIVISOR = 33554393;

    private final long[][] grid = new long[MAX_SIZE * 2][MAX_SIZE * 2];

    @Override
    protected void partOne() {
        generateGrid();
        var code = grid[SEARCHED_ROW - 1][SEARCHED_COL - 1];
        System.out.println("Search code: " + code);
    }

    private void generateGrid() {
        int row = 0, col = 0, maxRow = 0;
        Long code = null;

        for (int i = 0; i < MAX_SIZE * MAX_SIZE; i++) {
            if (null == code) {
                code = START_VALUE;
            } else {
                code = code * MULTIPLICAND % DIVISOR;
            }
            grid[row][col] = code;

            if (row == 0) {
                maxRow++;
                row = maxRow;
                col = 0;
            } else {
                row -= 1;
                col++;
            }
        }
    }

    @Override
    protected void partTwo() {

    }

}
