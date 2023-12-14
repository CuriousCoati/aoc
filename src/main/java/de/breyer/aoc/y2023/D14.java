package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.Collections;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2023_14")
public class D14 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var platform = parseInput();
        tilt(platform, Direction.UP);
        var totalLoad = calcTotalLoad(platform);
        System.out.println("totalLoad: " + totalLoad);
    }

    private char[][] parseInput() {
        var platform = new char[lines.size()][lines.get(0).length()];

        for (var y = 0; y < lines.size(); y++) {
            platform[y] = lines.get(y).toCharArray();
        }

        return platform;
    }

    private void tilt(char[][] platform, Direction direction) {
        var y = direction == Direction.UP ? 1 : direction == Direction.DOWN ? platform.length - 2 : 0;

        do {
            var x = direction == Direction.LEFT ? 1 : direction == Direction.RIGHT ? platform[0].length - 2 : 0;
            do {

                if (platform[y][x] == 'O') {
                    var newX = x;
                    var newY = y;
                    var nextX = direction.getXExpression().apply(x, 1);
                    var nextY = direction.getYExpression().apply(y, 1);

                    do {
                        if (platform[nextY][nextX] == '.') {
                            newY = nextY;
                            newX = nextX;
                        } else {
                            break;
                        }
                        nextX = direction.getXExpression().apply(nextX, 1);
                        nextY = direction.getYExpression().apply(nextY, 1);
                    } while (nextY >= 0 && nextY < platform.length && nextX >= 0 && nextX < platform[0].length);

                    if (newY != y) {
                        platform[newY][x] = 'O';
                        platform[y][x] = '.';
                    } else if (newX != x) {
                        platform[y][newX] = 'O';
                        platform[y][x] = '.';
                    }
                }

                if (direction == Direction.RIGHT) {
                    x--;
                } else {
                    x++;
                }
            } while (x >= 0 && x < platform[y].length);

            if (direction == Direction.DOWN) {
                y--;
            } else {
                y++;
            }
        } while (y >= 0 && y < platform.length);
    }

    private long calcTotalLoad(char[][] platform) {
        var totalLoad = 0L;

        for (var y = 0; y < platform.length; y++) {
            for (var c : platform[y]) {
                if (c == 'O') {
                    totalLoad += platform.length - y;
                }
            }
        }

        return totalLoad;
    }

    protected void partTwo() {
        var platform = parseInput();
        var totalLoad = spin(platform, 1000000000);
        System.out.println("totalLoad: " + totalLoad);
    }

    private long spin(char[][] platform, int times) {
        var loads = new ArrayList<Long>();
        var remainingIterations = 0;
        var offset = 0;
        var repetitionLength = 0;

        for (var i = 0; i < times; i++) {
            tilt(platform, Direction.UP);
            tilt(platform, Direction.LEFT);
            tilt(platform, Direction.DOWN);
            tilt(platform, Direction.RIGHT);

            var totalLoad = calcTotalLoad(platform);

            if (loads.size() > 4 && Collections.frequency(loads, totalLoad) == 2) {
                var firstIndex = loads.indexOf(totalLoad);
                var lastIndex = loads.lastIndexOf(totalLoad);

                if (lastIndex - firstIndex == loads.size() - lastIndex) {
                    repetitionLength = lastIndex - firstIndex;
                    remainingIterations = times - i - 1;
                    offset = firstIndex;
                    break;
                }
            }

            loads.add(totalLoad);

        }

        return loads.get(offset + remainingIterations % repetitionLength);
    }

}
