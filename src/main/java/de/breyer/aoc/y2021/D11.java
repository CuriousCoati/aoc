package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2021_11")
public class D11 extends AbstractAocPuzzle {

    private static final int STEP_COUNT = 1000;

    private final int[][] octopuses = new int[10][10];
    private int flashes = 0;
    private int step100 = 0;
    private int allFlashed = 0;

    @Override
    protected void partOne() {
        parseInput();
        startSimulation();
        System.out.println("after 100 steps there were " + step100 + " flashes");
    }

    @Override
    protected void partTwo() {
        System.out.println("all octopuses will flash after " + allFlashed + " steps");
    }

    private void parseInput() {
        int x = 0, y = 0;

        for (String line : lines) {
            for (char character : line.toCharArray()){
                octopuses[y][x] = Integer.parseInt("" + character);
                x++;
            }

            x = 0;
            y++;
        }
    }

    private void startSimulation() {
        for (int step = 0; step < STEP_COUNT; step++) {

            List<Point2D> flashedOctopuses = new ArrayList<>();

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    processOctopus(x, y, flashedOctopuses);
                }
            }

            flashes += flashedOctopuses.size();

            if (flashedOctopuses.size() == 100) {
                allFlashed = step + 1;
                break;
            }

            for (Point2D flashedOctopus : flashedOctopuses) {
                octopuses[flashedOctopus.getY()][flashedOctopus.getX()] = 0;
            }

            if (step + 1 == 100) {
                step100 = flashes;
            }
        }
    }

    private void processOctopus(int x, int y, List<Point2D> flashedOctopuses) {
        int value = octopuses[y][x];
        value++;

        octopuses[y][x] = value;
        if (value == 10) {
            flashedOctopuses.add(new Point2D(x, y));
            increaseNeighbours(x, y, flashedOctopuses);
        }
    }

    private void increaseNeighbours(int x, int y, List<Point2D> flashedOctopuses) {
        for (int y2 = y-1; y2 <= y+1; y2++) {
            if (y2 >= 0 && y2 <= 9) {
                for (int x2 = x-1; x2 <= x+1; x2++) {
                    if (x2 >= 0 && x2 <= 9 && (x2 != x || y2 != y)) {
                        processOctopus(x2, y2, flashedOctopuses);
                    }
                }
            }
        }
    }
}
