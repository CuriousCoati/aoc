package de.breyer.aoc.y2021;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_25")
public class D25 extends AbstractAocPuzzle {

    private final int width = 139;
    private final int height = 137;
    private char[][] map = new char[height][width];

    @Override
    protected void partOne() {
        processInput();
        simulate();
    }

    @Override
    protected void partTwo() {

    }

    private void processInput() {
        int y = 0;
        for (String line : lines) {
            for (int x = 0; x < width; x++) {
                map[y][x] = line.charAt(x);
            }
            y++;
        }
    }

    private void simulate() {

        int step = 0;
        boolean changes;

        do {
            changes = false;

            char[][] newMap = new char[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == '>') {
                        int next = x + 1 == width ? 0 : x + 1;
                        if (map[y][next] == '.') {
                            newMap[y][x] = '.';
                            newMap[y][next] = '>';
                            changes = true;
                        } else {
                            newMap[y][x] = map[y][x];
                        }
                    } else if (map[y][x] != '.' || newMap[y][x] == '\u0000') {
                        newMap[y][x] = map[y][x];
                    }
                }
            }
            map = newMap;

            newMap = new char[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == 'v') {
                        int next = y + 1 == height ? 0 : y + 1;
                        if (map[next][x] == '.') {
                            newMap[y][x] = '.';
                            newMap[next][x] = 'v';
                            changes = true;
                        } else {
                            newMap[y][x] = map[y][x];
                        }
                    } else if (map[y][x] != '.' || newMap[y][x] == '\u0000') {
                        newMap[y][x] = map[y][x];
                    }
                }
            }
            map = newMap;

            step++;
        } while(changes);

        System.out.println("step: " + step);
    }

}
