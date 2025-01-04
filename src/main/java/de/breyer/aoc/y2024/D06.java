package de.breyer.aoc.y2024;

import java.util.HashSet;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2024_06")
public class D06 extends AbstractAocPuzzle {

    private char[][] map;
    private Point2D initialGuardPosition;
    private Direction initialDirection;
    private final HashSet<Point2D> visitedLocations = new HashSet<>();

    @Override
    protected void partOne() {
        parseInput();
        moveGuardUntilMapLeft();
        System.out.println("guard visited " + visitedLocations.size() + " positions");
    }

    private void parseInput() {
        var input = lines;

        map = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                map[y][x] = input.get(y).charAt(x);
                if (map[y][x] == '^') {
                    initialGuardPosition = new Point2D(x, y);
                    initialDirection = Direction.UP;
                }
            }
        }
    }

    private void moveGuardUntilMapLeft() {
        var move = true;
        var guardPosition = initialGuardPosition;
        var direction = initialDirection;

        visitedLocations.clear();
        visitedLocations.add(guardPosition);

        do {
            var x = direction.getXExpression().apply(guardPosition.getX(), 1);
            var y = direction.getYExpression().apply(guardPosition.getY(), 1);

            if (x < 0 || y < 0 || y >= map.length || x >= map[0].length) {
                move = false;
            } else if (map[y][x] == '#') {
                direction = nextDirection(direction);
            } else {
                guardPosition = new Point2D(x, y);
                visitedLocations.add(guardPosition);
            }

        } while (move);
    }

    private Direction nextDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            case RIGHT -> Direction.DOWN;
        };
    }

    @Override
    protected void partTwo() {
        var loopPositions = checkForLoopPositions();
        System.out.println(loopPositions + " possible locations for loops");
    }

    private long checkForLoopPositions() {
        var loopPositions = 0L;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == '^' || map[y][x] == '#') {
                    continue;
                }

                map[y][x] = '#';
                if (moveGuardUntilMapLeftOrLoopDetected()) {
                    loopPositions++;
                }
                map[y][x] = '.';
            }
        }

        return loopPositions;
    }

    private boolean moveGuardUntilMapLeftOrLoopDetected() {
        var move = true;
        var guardPosition = initialGuardPosition;
        var direction = initialDirection;

        var visitedLocations = new HashSet<String>();

        do {
            var x = direction.getXExpression().apply(guardPosition.getX(), 1);
            var y = direction.getYExpression().apply(guardPosition.getY(), 1);

            if (x < 0 || y < 0 || y >= map.length || x >= map[0].length) {
                move = false;
            } else if (map[y][x] == '#') {
                direction = nextDirection(direction);
            } else {
                guardPosition = new Point2D(x, y);
                var key = String.format("%s-%s-%s", x, y, direction);

                if (visitedLocations.contains(key)) {
                    return true;
                }

                visitedLocations.add(key);
            }

        } while (move);

        return false;
    }

}
