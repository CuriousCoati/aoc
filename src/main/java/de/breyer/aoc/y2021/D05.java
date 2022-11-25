package de.breyer.aoc.y2021;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_05")
public class D05 extends AbstractAocPuzzle {

    private int[][] map;

    @Override
    protected void partOne() {
        map = new int[1000][1000];
        markOnMap(true);
        countOverlapping();
    }

    @Override
    protected void partTwo() {
        map = new int[1000][1000];
        markOnMap(false);
        countOverlapping();

    }

    private void markOnMap(boolean straight) {
        for (String lineString : lines) {
            Line line = new Line(lineString);
            if (!straight || line.isStraight()) {
                line.markPointsOnMap(map);
            }
        }
    }

    private void countOverlapping() {
        int countOverlapping = 0;
        for (int x = 0; x < 999; x++) {
            for (int y = 0; y < 999; y++) {
                if (map[x][y] > 1) {
                    countOverlapping++;
                }
            }
        }
        System.out.println("overlapping points: " + countOverlapping);
    }
}
