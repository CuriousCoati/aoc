package de.breyer.aoc.y2024;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_04")
public class D04 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var grid = parseInput();
        var count = wordSearchXmas(grid);
        System.out.println("found " + count + " xmas instances");
    }

    private char[][] parseInput() {
        var input = lines;

        var grid = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }

        return grid;
    }

    private int wordSearchXmas(char[][] grid) {
        var count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                var c = grid[y][x];

                if (c == 'X') {
                    count += isXmas(grid, x, y);
                }
            }
        }

        return count;
    }

    private int isXmas(char[][] grid, int x, int y) {
        var count = 0;

        // up
        if (y - 3 >= 0 && grid[y - 1][x] == 'M' && grid[y - 2][x] == 'A' && grid[y - 3][x] == 'S') {
            count++;
        }

        // up-right
        if (y - 3 >= 0 && x + 3 < grid[y].length && grid[y - 1][x + 1] == 'M' && grid[y - 2][x + 2] == 'A' && grid[y - 3][x + 3] == 'S') {
            count++;
        }

        // right
        if (x + 3 < grid[y].length && grid[y][x + 1] == 'M' && grid[y][x + 2] == 'A' && grid[y][x + 3] == 'S') {
            count++;
        }

        // down-right
        if (y + 3 < grid.length && x + 3 < grid[y].length && grid[y + 1][x + 1] == 'M' && grid[y + 2][x + 2] == 'A' && grid[y + 3][x + 3] == 'S') {
            count++;
        }

        // down
        if (y + 3 < grid.length && grid[y + 1][x] == 'M' && grid[y + 2][x] == 'A' && grid[y + 3][x] == 'S') {
            count++;
        }

        // down-left
        if (y + 3 < grid.length && x - 3 >= 0 && grid[y + 1][x - 1] == 'M' && grid[y + 2][x - 2] == 'A' && grid[y + 3][x - 3] == 'S') {
            count++;
        }

        // left
        if (x - 3 >= 0 && grid[y][x - 1] == 'M' && grid[y][x - 2] == 'A' && grid[y][x - 3] == 'S') {
            count++;
        }

        // up-left
        if (y - 3 >= 0 && x - 3 >= 0 && grid[y - 1][x - 1] == 'M' && grid[y - 2][x - 2] == 'A' && grid[y - 3][x - 3] == 'S') {
            count++;
        }

        return count;
    }

    @Override
    protected void partTwo() {
        var grid = parseInput();
        var count = wordSearchMasAsX(grid);
        System.out.println("found " + count + " x-mas instances");
    }

    private int wordSearchMasAsX(char[][] grid) {
        var count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                var c = grid[y][x];

                if (c == 'A') {
                    count += isMasAsX(grid, x, y);
                }
            }
        }

        return count;
    }

    private int isMasAsX(char[][] grid, int x, int y) {
        var count = 0;

        if (y - 1 >= 0 && y + 1 < grid.length && x - 1 >= 0 && x + 1 < grid[y].length) {

            if (grid[y - 1][x + 1] == 'M' && grid[y + 1][x - 1] == 'S' && grid[y + 1][x + 1] == 'M' && grid[y - 1][x - 1] == 'S') {
                count++;
            }

            if (grid[y - 1][x + 1] == 'M' && grid[y + 1][x - 1] == 'S' && grid[y - 1][x - 1] == 'M' && grid[y + 1][x + 1] == 'S') {
                count++;
            }

            if (grid[y + 1][x - 1] == 'M' && grid[y - 1][x + 1] == 'S' && grid[y - 1][x - 1] == 'M' && grid[y + 1][x + 1] == 'S') {
                count++;
            }

            if (grid[y + 1][x - 1] == 'M' && grid[y - 1][x + 1] == 'S' && grid[y + 1][x + 1] == 'M' && grid[y - 1][x - 1] == 'S') {
                count++;
            }

        }

        return count;
    }
}
