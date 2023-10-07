package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_13")
public class D13 extends AbstractAocPuzzle {

    private final boolean[][] transparentPaper = new boolean[1311][900];
    private final List<String> folds = new ArrayList<>();
    private int width = 1311;
    private int height = 900;

    @Override
    protected void partOne() {
        readInput();
        fold(0, 1);
        countDots();
    }

    @Override
    protected void partTwo() {
        fold(1, folds.size());
        printCode();
    }

    private void readInput() {
        for (String line : lines) {
            if (line.startsWith("fold along")) {
                folds.add(line.substring(11));
            } else if (line.length() != 0) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                transparentPaper[x][y] = true;
            }
        }
    }

    private void fold(int start, int end) {
        for (String fold : folds.subList(start, end)) {
            String[] split = fold.split("=");
            boolean horizontal = split[0].equals("x");
            int position = Integer.parseInt(split[1]);
            int maxFoldDimension = horizontal ? width : height;
            int maxOtherDimension = horizontal ? height : width;

            for (int i = position; i < maxFoldDimension; i++) {
                for (int j = 0; j < maxOtherDimension; j++) {
                    int x = horizontal ? i : j;
                    int y = horizontal ? j : i;
                    boolean value = transparentPaper[x][y];

                    if (value) {
                        transparentPaper[x][y] = false;

                        int diff = i - position;
                        int posAfterFold = position - diff;

                        int x2 = horizontal ? posAfterFold : j;
                        int y2 = horizontal ? j : posAfterFold;
                        transparentPaper[x2][y2] = true;
                    }
                }
            }

            if (horizontal) {
                width = position;
            } else {
                height = position;
            }
        }
    }

    private void countDots() {
        int dotCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (transparentPaper[x][y]) {
                    dotCount++;
                }
            }
        }
        System.out.println("dot count " + dotCount);
    }

    private void printCode() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean value = transparentPaper[x][y];
                System.out.print(value ? "#" : ".");
            }
            System.out.println();
        }
    }
}
