package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2023_16")
public class D16 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var contraption = parseInput();
        var energizedTiles = energizeTiles(contraption, new Point2D(0, 0), Direction.RIGHT);
        System.out.println("energized tiles: " + energizedTiles);
    }

    private char[][] parseInput() {
        var contraption = new char[lines.size()][lines.get(0).length()];

        for (int y = 0; y < lines.size(); y++) {
            contraption[y] = lines.get(y).toCharArray();
        }

        return contraption;
    }

    private int energizeTiles(char[][] contraption, Point2D startPosition, Direction startDirection) {
        var calculated = new ArrayList<String>();
        var energizedTiles = new HashSet<Point2D>();
        var stack = new Stack<Pair<Point2D, Direction>>();
        stack.push(new Pair<>(startPosition, startDirection));

        do {
            var beam = stack.pop();
            var position = beam.getFirst();
            var direction = beam.getSecond();

            if (position.getX() < 0 || position.getY() < 0 || position.getX() >= contraption[0].length || position.getY() >= contraption.length) {
                continue;
            }

            var key = position.getX() + "," + position.getY() + "," + direction;
            if (calculated.contains(key)) {
                continue;
            } else {
                calculated.add(key);
            }

            var tile = contraption[position.getY()][position.getX()];

            energizedTiles.add(position);

            if ('/' == tile) {
                switch (direction) {
                    case RIGHT -> direction = Direction.UP;
                    case UP -> direction = Direction.RIGHT;
                    case LEFT -> direction = Direction.DOWN;
                    case DOWN -> direction = Direction.LEFT;
                }
            } else if ('\\' == tile) {
                switch (direction) {
                    case RIGHT -> direction = Direction.DOWN;
                    case UP -> direction = Direction.LEFT;
                    case LEFT -> direction = Direction.UP;
                    case DOWN -> direction = Direction.RIGHT;
                }
            } else if ('-' == tile) {
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    direction = Direction.LEFT;
                    var nextY = direction.getYExpression().apply(position.getY(), 1);
                    var nextX = direction.getXExpression().apply(position.getX(), 1);
                    stack.push(new Pair<>(new Point2D(nextX, nextY), direction));
                    direction = Direction.RIGHT;
                }
            } else if ('|' == tile) {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    direction = Direction.DOWN;
                    var nextY = direction.getYExpression().apply(position.getY(), 1);
                    var nextX = direction.getXExpression().apply(position.getX(), 1);
                    stack.push(new Pair<>(new Point2D(nextX, nextY), direction));
                    direction = Direction.UP;
                }
            }

            var nextY = direction.getYExpression().apply(position.getY(), 1);
            var nextX = direction.getXExpression().apply(position.getX(), 1);
            stack.push(new Pair<>(new Point2D(nextX, nextY), direction));

        } while (!stack.isEmpty());

        return energizedTiles.size();
    }

    @Override
    protected void partTwo() {
        var contraption = parseInput();
        var energizedTiles = energizeTilesWithBestStartingPosition(contraption);
        System.out.println("energized tiles: " + energizedTiles);
    }

    private int energizeTilesWithBestStartingPosition(char[][] contraption) {
        var startPositions = new ArrayList<Pair<Point2D, Direction>>();

        for (int y = 0; y < contraption.length; y++) {
            startPositions.add(new Pair<>(new Point2D(0, y), Direction.RIGHT));
            startPositions.add(new Pair<>(new Point2D(contraption[0].length - 1, y), Direction.LEFT));
        }
        for (int x = 0; x < contraption[0].length; x++) {
            startPositions.add(new Pair<>(new Point2D(x, 0), Direction.DOWN));
            startPositions.add(new Pair<>(new Point2D(x, contraption.length - 1), Direction.UP));
        }

        return startPositions.parallelStream().mapToInt(pair -> energizeTiles(contraption, pair.getFirst(), pair.getSecond())).max().orElse(-1);
    }

}
