package de.breyer.aoc.y2022;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_14")
public class D14 extends AbstractAocPuzzle {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 200;
    private static final char ROCK = '#';
    private static final char SAND = 'o';

    private int highestY = 0;

    @Override
    protected void partOne() {
        char[][] cave = readInput();
        int sandAtRest = simulateSand(cave);
        System.out.println(sandAtRest + " units of sand come to rest");
    }

    private char[][] readInput() {
        char[][] cave = new char[HEIGHT][WIDTH];

        for (String line : lines) {
            String[] split = line.split(" -> ");
            Point2D currentPoint = null;

            for (String coordinate : split) {
                String[] coordinateSplit = coordinate.split(",");
                Point2D newPoint = new Point2D(Integer.parseInt(coordinateSplit[0]), Integer.parseInt(coordinateSplit[1]));

                if (null != currentPoint) {
                    drawLine(cave, currentPoint, newPoint);
                }
                currentPoint = newPoint;
                if (currentPoint.getY() > highestY) {
                    highestY = currentPoint.getY();
                }
            }
        }

        return cave;
    }

    private void drawLine(char[][] cave, Point2D startPoint, Point2D endPoint) {
        Direction direction;
        int distance;
        if (startPoint.getX() < endPoint.getX()) {
            direction = Direction.RIGHT;
            distance = endPoint.getX() - startPoint.getX();
        } else if (startPoint.getX() > endPoint.getX()) {
            direction = Direction.LEFT;
            distance = startPoint.getX() - endPoint.getX();
        } else if (startPoint.getY() < endPoint.getY()) {
            direction = Direction.DOWN;
            distance = endPoint.getY() - startPoint.getY();
        } else if (startPoint.getY() > endPoint.getY()) {
            direction = Direction.UP;
            distance = startPoint.getY() - endPoint.getY();
        } else {
            throw new IllegalStateException("unexpected result: " + startPoint + " -> " + endPoint);
        }

        cave[startPoint.getY()][startPoint.getX()] = ROCK;
        for (int i = 1; i < distance; i++) {
            int x = direction.getXExpression().apply(startPoint.getX(), i);
            int y = direction.getYExpression().apply(startPoint.getY(), i);
            cave[y][x] = ROCK;
        }
        cave[endPoint.getY()][endPoint.getX()] = ROCK;
    }

    private int simulateSand(char[][] cave) {
        int atRest = 0;
        boolean finished = false;
        Point2D positionSand = null;

        do {
            if (null == positionSand) {
                positionSand = new Point2D(500, 0);
            }

            Point2D newPosition = moveSand(cave, positionSand);

            if (null == newPosition) {
                atRest++;
                cave[positionSand.getY()][positionSand.getX()] = SAND;
                if (positionSand.getX() == 500 && positionSand.getY() == 0) {
                    finished = true;
                } else {
                    positionSand = null;
                }
            } else if (newPosition.getY() == HEIGHT - 1) {
                finished = true;
            } else {
                positionSand = newPosition;
            }
        } while (!finished);

        return atRest;
    }

    private Point2D moveSand(char[][] cave, Point2D positionSand) {
        char charDown = cave[positionSand.getY() + 1][positionSand.getX()];
        char charDownLeft = cave[positionSand.getY() + 1][positionSand.getX() - 1];
        char charDownRight = cave[positionSand.getY() + 1][positionSand.getX() + 1];

        if (charDown != ROCK && charDown != SAND) {
            return new Point2D(positionSand.getX(), positionSand.getY() + 1);
        } else if (charDownLeft != ROCK && charDownLeft != SAND) {
            return new Point2D(positionSand.getX() - 1, positionSand.getY() + 1);
        } else if (charDownRight != ROCK && charDownRight != SAND) {
            return new Point2D(positionSand.getX() + 1, positionSand.getY() + 1);
        } else {
            return null;
        }
    }

    @Override
    protected void partTwo() {
        char[][] cave = readInput();
        drawLine(cave, new Point2D(0, highestY + 2), new Point2D(WIDTH - 1, highestY + 2));
        int sandAtRest = simulateSand(cave);
        System.out.println(sandAtRest + " units of sand come to rest");
    }

}
