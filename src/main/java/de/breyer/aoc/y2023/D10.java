package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2023_10")
public class D10 extends AbstractAocPuzzle {

    // in my input the start point is a J and I follow the north pipe to calc the loop
    private static final char START_REPLACEMENT = 'J';
    private static final Direction START_LOOP_DIRECTION = Direction.UP;

    private Point2D start;
    private Character[][] pipeMaze;
    private List<Point2D> loop;

    @Override
    protected void partOne() {
        parseInput();
        findLoop();
        System.out.println("steps to farthest point: " + (loop.size() / 2));
    }

    private void parseInput() {
        pipeMaze = new Character[lines.size()][lines.get(0).length()];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                var c = lines.get(y).charAt(x);
                pipeMaze[y][x] = c;

                if ('S' == c) {
                    start = new Point2D(x, y);
                }
            }
        }
    }

    private void findLoop() {
        var finished = false;
        loop = new ArrayList<>();
        loop.add(start);

        var direction = START_LOOP_DIRECTION;
        var x = direction.getXExpression().apply(start.getX(), 1);
        var y = direction.getYExpression().apply(start.getY(), 1);
        var currentPoint = new Point2D(x, y);

        do {
            loop.add(currentPoint);
            var currentChar = pipeMaze[currentPoint.getY()][currentPoint.getX()];

            if ('S' == currentChar) {
                finished = true;
            } else if ('|' == currentChar) {
                if (Direction.UP == direction) {
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() - 1);
                } else {
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() + 1);
                }
            } else if ('-' == currentChar) {
                if (Direction.RIGHT == direction) {
                    currentPoint = new Point2D(currentPoint.getX() + 1, currentPoint.getY());
                } else {
                    currentPoint = new Point2D(currentPoint.getX() - 1, currentPoint.getY());
                }
            } else if ('L' == currentChar) {
                if (Direction.LEFT == direction) {
                    direction = Direction.UP;
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() - 1);
                } else {
                    direction = Direction.RIGHT;
                    currentPoint = new Point2D(currentPoint.getX() + 1, currentPoint.getY());
                }
            } else if ('J' == currentChar) {
                if (Direction.DOWN == direction) {
                    direction = Direction.LEFT;
                    currentPoint = new Point2D(currentPoint.getX() - 1, currentPoint.getY());
                } else {
                    direction = Direction.UP;
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() - 1);
                }
            } else if ('7' == currentChar) {
                if (Direction.RIGHT == direction) {
                    direction = Direction.DOWN;
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() + 1);
                } else {
                    direction = Direction.LEFT;
                    currentPoint = new Point2D(currentPoint.getX() - 1, currentPoint.getY());
                }
            } else if ('F' == currentChar) {
                if (Direction.UP == direction) {
                    direction = Direction.RIGHT;
                    currentPoint = new Point2D(currentPoint.getX() + 1, currentPoint.getY());
                } else {
                    direction = Direction.DOWN;
                    currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() + 1);
                }
            }
        } while (!finished);
    }

    protected void partTwo() {
        pipeMaze[start.getY()][start.getX()] = START_REPLACEMENT;
        var count = countTilesNotEnclosed();
        System.out.println((pipeMaze.length * pipeMaze[0].length - count) + " ground tiles are enclosed by the loop");
    }

    private int countTilesNotEnclosed() {
        var count = 0;

        var visitedCoordinates = new HashSet<Point2D>();
        var stack = new Stack<SearchState>();
        stack.push(new SearchState(new Point2D(0, 0), null));

        do {
            var state = stack.pop();
            var currentCoordinate = state.point();
            var side = state.side();
            visitedCoordinates.add(currentCoordinate);
            var currentTile = pipeMaze[currentCoordinate.getY()][currentCoordinate.getX()];

            count++;

            for (var direction : Direction.values()) {
                var x = direction.getXExpression().apply(currentCoordinate.getX(), 1);
                var y = direction.getYExpression().apply(currentCoordinate.getY(), 1);
                var neighbour = new Point2D(x, y);

                // check if bounds
                if (x < 0 || x >= pipeMaze[0].length || y < 0 || y >= pipeMaze.length) {
                    continue;
                }

                // ignore already visited or planned coordinates
                if (visitedCoordinates.contains(neighbour) || stack.contains(new SearchState(neighbour, null))) {
                    continue;
                }

                Direction nextSide = side;
                // filter moves that are not allowed
                if (null != side) {
                    switch (direction) {
                        case UP -> {
                            if ('-' == currentTile && side == Direction.DOWN) {
                                continue;
                            }
                            if ('F' == currentTile && (side == Direction.RIGHT || side == Direction.DOWN)) {
                                continue;
                            }
                            if ('7' == currentTile && (side == Direction.LEFT || side == Direction.DOWN)) {
                                continue;
                            }

                            // translate side at crossings
                            if ('J' == currentTile && Direction.DOWN == side) {
                                nextSide = Direction.RIGHT;
                            }
                            if ('J' == currentTile && Direction.UP == side) {
                                nextSide = Direction.LEFT;
                            }
                            if ('L' == currentTile && Direction.DOWN == side) {
                                nextSide = Direction.LEFT;
                            }
                            if ('L' == currentTile && Direction.UP == side) {
                                nextSide = Direction.RIGHT;
                            }
                            if ('F' == currentTile || '7' == currentTile) {
                                nextSide = null;
                            }
                        }
                        case DOWN -> {
                            if ('-' == currentTile && side == Direction.UP) {
                                continue;
                            }
                            if ('L' == currentTile && (side == Direction.RIGHT || side == Direction.UP)) {
                                continue;
                            }
                            if ('J' == currentTile && (side == Direction.LEFT || side == Direction.UP)) {
                                continue;
                            }

                            // translate side at crossings
                            if ('7' == currentTile && Direction.UP == side) {
                                nextSide = Direction.RIGHT;
                            }
                            if ('7' == currentTile && Direction.DOWN == side) {
                                nextSide = Direction.LEFT;
                            }
                            if ('F' == currentTile && Direction.UP == side) {
                                nextSide = Direction.LEFT;
                            }
                            if ('F' == currentTile && Direction.DOWN == side) {
                                nextSide = Direction.RIGHT;
                            }
                            if ('L' == currentTile || 'J' == currentTile) {
                                nextSide = null;
                            }
                        }
                        case LEFT -> {
                            if ('|' == currentTile && side == Direction.RIGHT) {
                                continue;
                            }
                            if ('L' == currentTile && (side == Direction.RIGHT || side == Direction.UP)) {
                                continue;
                            }
                            if ('F' == currentTile && (side == Direction.RIGHT || side == Direction.DOWN)) {
                                continue;
                            }

                            // translate side at crossings
                            if ('7' == currentTile && Direction.RIGHT == side) {
                                nextSide = Direction.UP;
                            }
                            if ('7' == currentTile && Direction.LEFT == side) {
                                nextSide = Direction.DOWN;
                            }
                            if ('J' == currentTile && Direction.RIGHT == side) {
                                nextSide = Direction.DOWN;
                            }
                            if ('J' == currentTile && Direction.LEFT == side) {
                                nextSide = Direction.UP;
                            }
                            if ('L' == currentTile || 'F' == currentTile) {
                                nextSide = null;
                            }
                        }
                        case RIGHT -> {
                            if ('|' == currentTile && side == Direction.LEFT) {
                                continue;
                            }
                            if ('7' == currentTile && (side == Direction.LEFT || side == Direction.DOWN)) {
                                continue;
                            }
                            if ('J' == currentTile && (side == Direction.LEFT || side == Direction.UP)) {
                                continue;
                            }

                            // translate side at crossings
                            if ('L' == currentTile && Direction.RIGHT == side) {
                                nextSide = Direction.UP;
                            }
                            if ('L' == currentTile && Direction.LEFT == side) {
                                nextSide = Direction.DOWN;
                            }
                            if ('F' == currentTile && Direction.RIGHT == side) {
                                nextSide = Direction.DOWN;
                            }
                            if ('F' == currentTile && Direction.LEFT == side) {
                                nextSide = Direction.UP;
                            }
                            if ('7' == currentTile || 'J' == currentTile) {
                                nextSide = null;
                            }
                        }
                    }
                }

                var isLoop = loop.contains(neighbour);
                if (!isLoop) {
                    stack.push(new SearchState(neighbour, null));
                } else {
                    var loopTile = pipeMaze[neighbour.getY()][neighbour.getX()];
                    switch (direction) {
                        case UP -> {
                            if ('-' == loopTile || 'J' == loopTile || 'L' == loopTile) {
                                nextSide = Direction.DOWN;
                            }
                        }
                        case DOWN -> {
                            if ('-' == loopTile || '7' == loopTile || 'F' == loopTile) {
                                nextSide = Direction.UP;
                            }
                        }
                        case LEFT -> {
                            if ('|' == loopTile || 'J' == loopTile || '7' == loopTile) {
                                nextSide = Direction.RIGHT;
                            }
                        }
                        case RIGHT -> {
                            if ('|' == loopTile || 'L' == loopTile || 'F' == loopTile) {
                                nextSide = Direction.LEFT;
                            }
                        }
                    }
                    stack.push(new SearchState(neighbour, nextSide));
                }
            }
        } while (!stack.isEmpty());

        return count;
    }

    private record SearchState(Point2D point, Direction side) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SearchState that = (SearchState) o;
            return Objects.equals(point, that.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(point);
        }
    }

}
