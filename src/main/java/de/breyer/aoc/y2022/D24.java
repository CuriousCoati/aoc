package de.breyer.aoc.y2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_24")
public class D24 extends AbstractAocPuzzle {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private Point2D startPosition;
    private Point2D endPosition;
    private List<Blizzard> blizzards;
    private int minutesPartOne;

    @Override
    protected void partOne() {
        parseInput();
        minutesPartOne = searchPath(startPosition, endPosition);
        System.out.println("minutes to reach other side: " + minutesPartOne);
    }

    private void parseInput() {
        blizzards = new ArrayList<>();
        minX = 1;
        minY = 1;
        maxX = lines.get(0).length() - 2;
        maxY = lines.size() - 2;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                if ('<' == character) {
                    Point2D position = new Point2D(x, y);
                    blizzards.add(new Blizzard(Direction.LEFT, position));
                } else if ('>' == character) {
                    Point2D position = new Point2D(x, y);
                    blizzards.add(new Blizzard(Direction.RIGHT, position));
                } else if ('v' == character) {
                    Point2D position = new Point2D(x, y);
                    blizzards.add(new Blizzard(Direction.DOWN, position));
                } else if ('^' == character) {
                    Point2D position = new Point2D(x, y);
                    blizzards.add(new Blizzard(Direction.UP, position));
                } else if ('.' == character) {
                    if (y == 0) {
                        startPosition = new Point2D(x, y);
                    } else if (y == lines.size() - 1) {
                        endPosition = new Point2D(x, y);
                    }
                }

            }
        }
    }

    private int searchPath(Point2D start, Point2D end) {
        Queue<Point2D> movesInMinute = new ArrayDeque<>();
        movesInMinute.add(start);
        boolean goalReached = false;
        int minute = 0;

        do {
            updateBlizzards();
            Set<Point2D> movesInNextMinute = new HashSet<>();
            do {
                Point2D position = movesInMinute.poll();
                // wait
                if (isNotBlocked(position)) {
                    movesInNextMinute.add(position);
                }

                for (Direction direction : Direction.values()) {
                    int nextX = direction.getXExpression().apply(position.getX(), 1);
                    int nextY = direction.getYExpression().apply(position.getY(), 1);

                    if (end.getX() == nextX && end.getY() == nextY) {
                        goalReached = true;
                        break;
                    }

                    if (nextX >= minX && nextX <= maxX && nextY >= minY && nextY <= maxY) {
                        Point2D nextPosition = new Point2D(nextX, nextY);
                        if (isNotBlocked(nextPosition)) {
                            movesInNextMinute.add(nextPosition);
                        }
                    }
                }

            } while (!movesInMinute.isEmpty());
            movesInMinute.addAll(movesInNextMinute);
            minute++;
        } while (!goalReached);

        return minute;
    }

    private void updateBlizzards() {
        blizzards.forEach(Blizzard::moveBlizzard);
    }

    private boolean isNotBlocked(Point2D position) {
        return blizzards.stream().noneMatch(blizzard -> blizzard.getPosition().equals(position));
    }

    @Override
    protected void partTwo() {
        int minutesBackToStart = searchPath(endPosition, startPosition);
        int minutesAgainToEnd = searchPath(startPosition, endPosition);
        System.out.println("total minutes: " + (minutesPartOne + minutesBackToStart + minutesAgainToEnd));
    }

    private class Blizzard {

        private Point2D position;
        private final Direction direction;

        public Point2D getPosition() {
            return position;
        }

        public Blizzard(Direction direction, Point2D position) {
            this.direction = direction;
            this.position = position;
        }

        public void moveBlizzard() {
            int nextX = direction.getXExpression().apply(position.getX(), 1);
            int nextY = direction.getYExpression().apply(position.getY(), 1);

            if (nextX < minX) {
                nextX = maxX;
            } else if (nextX > maxX) {
                nextX = minX;
            } else if (nextY < minY) {
                nextY = maxY;
            } else if (nextY > maxY) {
                nextY = minY;
            }

            position = new Point2D(nextX, nextY);
        }
    }

}
