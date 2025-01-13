package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;
import lombok.extern.slf4j.Slf4j;

@AocPuzzle("2024_15")
@Slf4j
public class D15 extends AbstractAocPuzzle {

    private List<char[]> map;
    private List<char[]> scaledMap;
    private Point2D robotPosition;
    private String moveSequence;

    @Override
    protected void partOne() {
        parseInput(lines);
        moveRobot();
        var sum = calcSumOfGpsCoordinates();
        System.out.println("sum of all boxes GPS coordinates: " + sum);
    }

    private void parseInput(List<String> input) {
        map = new ArrayList<>();
        var mapParse = true;
        var sb = new StringBuilder();

        for (int y = 0; y < input.size(); y++) {
            var line = input.get(y);

            if (line.isEmpty()) {
                mapParse = false;
                continue;
            }

            if (mapParse) {
                var row = new char[line.length()];
                map.add(row);
                for (int x = 0; x < line.length(); x++) {
                    var c = line.charAt(x);
                    row[x] = c;
                    if (c == '@') {
                        robotPosition = new Point2D(x, y);
                    }
                }
            } else {
                sb.append(line);
            }
        }

        moveSequence = sb.toString();
    }

    private void moveRobot() {
        for (var move : moveSequence.toCharArray()) {
            robotPosition = moveIfPossible(robotPosition, mapToDirection(move));
        }
    }

    private Direction mapToDirection(char move) {
        if ('<' == move) {
            return Direction.LEFT;
        } else if ('v' == move) {
            return Direction.DOWN;
        } else if ('>' == move) {
            return Direction.RIGHT;
        } else {
            return Direction.UP;
        }
    }

    private Point2D moveIfPossible(Point2D point, Direction direction) {
        var nextX = direction.getXExpression().apply(point.getX(), 1);
        var nextY = direction.getYExpression().apply(point.getY(), 1);

        if (map.get(nextY)[nextX] == '#') {
            return point;
        } else if (map.get(nextY)[nextX] == '.') {
            map.get(nextY)[nextX] = map.get(point.getY())[point.getX()];
            map.get(point.getY())[point.getX()] = '.';
            return new Point2D(nextX, nextY);
        } else if (map.get(nextY)[nextX] == 'O') {
            var result = moveIfPossible(new Point2D(nextX, nextY), direction);
            if (result.getX() != nextX || result.getY() != nextY) {
                map.get(nextY)[nextX] = map.get(point.getY())[point.getX()];
                map.get(point.getY())[point.getX()] = '.';
                return new Point2D(nextX, nextY);
            } else {
                return point;
            }
        }

        throw new IllegalStateException("should not reach here");
    }

    private long calcSumOfGpsCoordinates() {
        var sum = 0L;

        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).length; x++) {
                if ('O' == map.get(y)[x]) {
                    sum += 100L * y + x;
                }
            }
        }

        return sum;
    }

    @Override
    protected void partTwo() {
        parseInput(lines);
        scaleMap();
        moveRobotInScaledWarehouse();
        var sum = calcSumOfScaledGpsCoordinates();
        System.out.println("sum of all boxes GPS coordinates: " + sum);
    }

    private void scaleMap() {
        scaledMap = new ArrayList<>();
        robotPosition = new Point2D(robotPosition.getX() * 2, robotPosition.getY());

        for (var row : map) {
            var scaledRow = new char[row.length * 2];
            scaledMap.add(scaledRow);
            var i = 0;

            for (var c : row) {
                if ('#' == c) {
                    scaledRow[i] = '#';
                    scaledRow[i + 1] = '#';
                } else if ('O' == c) {
                    scaledRow[i] = '[';
                    scaledRow[i + 1] = ']';
                } else if ('.' == c) {
                    scaledRow[i] = '.';
                    scaledRow[i + 1] = '.';
                } else if ('@' == c) {
                    scaledRow[i] = '@';
                    scaledRow[i + 1] = '.';
                }
                i += 2;
            }
        }
    }

    private void moveRobotInScaledWarehouse() {
        for (var move : moveSequence.toCharArray()) {
            moveInScaledIfPossible(mapToDirection(move));
        }
    }

    private void moveInScaledIfPossible(Direction direction) {
        var nextX = direction.getXExpression().apply(robotPosition.getX(), 1);
        var nextY = direction.getYExpression().apply(robotPosition.getY(), 1);
        var nextChar = scaledMap.get(nextY)[nextX];

        if (nextChar == '.') {
            scaledMap.get(nextY)[nextX] = '@';
            scaledMap.get(robotPosition.getY())[robotPosition.getX()] = '.';
            robotPosition = new Point2D(nextX, nextY);
        } else if (nextChar == '[' || nextChar == ']') {
            Point2D leftPart, rightPart;
            if (nextChar == '[') {
                leftPart = new Point2D(nextX, nextY);
                rightPart = new Point2D(nextX + 1, nextY);
            } else {
                leftPart = new Point2D(nextX - 1, nextY);
                rightPart = new Point2D(nextX, nextY);
            }

            var moved = moveBoxIfPossible(leftPart, rightPart, direction, false);
            if (moved) {
                scaledMap.get(nextY)[nextX] = '@';
                scaledMap.get(robotPosition.getY())[robotPosition.getX()] = '.';
                robotPosition = new Point2D(nextX, nextY);
            }
        }

    }

    private boolean moveBoxIfPossible(Point2D leftPart, Point2D rightPart, Direction direction, boolean justCheck) {
        if (direction == Direction.LEFT) {
            var tile = scaledMap.get(leftPart.getY())[leftPart.getX() - 1];

            if ('#' == tile) {
                return false;
            } else if (']' == tile) {
                var leftPartNextBox = new Point2D(leftPart.getX() - 2, leftPart.getY());
                var rightPartNextBox = new Point2D(leftPart.getX() - 1, leftPart.getY());
                var moved = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                if (moved) {
                    scaledMap.get(leftPart.getY())[leftPart.getX() - 1] = '[';
                    scaledMap.get(leftPart.getY())[leftPart.getX()] = ']';
                    scaledMap.get(leftPart.getY())[rightPart.getX()] = '.';
                    return true;
                } else {
                    return false;
                }
            } else {
                scaledMap.get(leftPart.getY())[leftPart.getX() - 1] = '[';
                scaledMap.get(leftPart.getY())[leftPart.getX()] = ']';
                scaledMap.get(leftPart.getY())[rightPart.getX()] = '.';
                return true;
            }
        }

        if (direction == Direction.RIGHT) {
            var tile = scaledMap.get(rightPart.getY())[rightPart.getX() + 1];

            if ('#' == tile) {
                return false;
            } else if ('[' == tile) {
                var leftPartNextBox = new Point2D(rightPart.getX() + 1, rightPart.getY());
                var rightPartNextBox = new Point2D(rightPart.getX() + 2, rightPart.getY());
                var moved = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                if (moved) {
                    scaledMap.get(rightPart.getY())[rightPart.getX() + 1] = ']';
                    scaledMap.get(rightPart.getY())[rightPart.getX()] = '[';
                    scaledMap.get(rightPart.getY())[leftPart.getX()] = '.';
                    return true;
                } else {
                    return false;
                }
            } else {
                scaledMap.get(rightPart.getY())[rightPart.getX() + 1] = ']';
                scaledMap.get(rightPart.getY())[rightPart.getX()] = '[';
                scaledMap.get(rightPart.getY())[leftPart.getX()] = '.';
                return true;
            }
        }

        if (direction == Direction.UP) {
            var tileLeft = scaledMap.get(leftPart.getY() - 1)[leftPart.getX()];
            var tileRight = scaledMap.get(rightPart.getY() - 1)[rightPart.getX()];

            if ('#' == tileLeft || '#' == tileRight) {
                return false;
            } else if ('.' == tileLeft && '.' == tileRight) {
                if (!justCheck) {
                    scaledMap.get(leftPart.getY() - 1)[leftPart.getX()] = '[';
                    scaledMap.get(rightPart.getY() - 1)[rightPart.getX()] = ']';
                    scaledMap.get(leftPart.getY())[leftPart.getX()] = '.';
                    scaledMap.get(rightPart.getY())[rightPart.getX()] = '.';
                }
                return true;
            } else {
                var leftCanMove = true;
                if (tileLeft == ']') {
                    var leftPartNextBox = new Point2D(leftPart.getX() - 1, leftPart.getY() - 1);
                    var rightPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() - 1);

                    leftCanMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                }

                var rightCanMove = true;
                if (tileRight == '[') {
                    var leftPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() - 1);
                    var rightPartNextBox = new Point2D(rightPart.getX() + 1, rightPart.getY() - 1);

                    rightCanMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                }

                if (tileLeft == '[' && tileRight == ']') {
                    var leftPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() - 1);
                    var rightPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() - 1);

                    var canMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                    leftCanMove = canMove;
                    rightCanMove = canMove;
                }

                if (!justCheck && leftCanMove && rightCanMove) {
                    if (tileLeft == ']') {
                        var leftPartNextBox = new Point2D(leftPart.getX() - 1, leftPart.getY() - 1);
                        var rightPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() - 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    if (tileRight == '[') {
                        var leftPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() - 1);
                        var rightPartNextBox = new Point2D(rightPart.getX() + 1, rightPart.getY() - 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    if (tileLeft == '[' && tileRight == ']') {
                        var leftPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() - 1);
                        var rightPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() - 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    scaledMap.get(leftPart.getY() - 1)[leftPart.getX()] = '[';
                    scaledMap.get(rightPart.getY() - 1)[rightPart.getX()] = ']';
                    scaledMap.get(leftPart.getY())[leftPart.getX()] = '.';
                    scaledMap.get(rightPart.getY())[rightPart.getX()] = '.';
                }

                return leftCanMove && rightCanMove;
            }
        }

        if (direction == Direction.DOWN) {
            var tileLeft = scaledMap.get(leftPart.getY() + 1)[leftPart.getX()];
            var tileRight = scaledMap.get(rightPart.getY() + 1)[rightPart.getX()];

            if ('#' == tileLeft || '#' == tileRight) {
                return false;
            } else if ('.' == tileLeft && '.' == tileRight) {
                if (!justCheck) {
                    scaledMap.get(leftPart.getY() + 1)[leftPart.getX()] = '[';
                    scaledMap.get(rightPart.getY() + 1)[rightPart.getX()] = ']';
                    scaledMap.get(leftPart.getY())[leftPart.getX()] = '.';
                    scaledMap.get(rightPart.getY())[rightPart.getX()] = '.';
                }
                return true;
            } else {
                var leftCanMove = true;
                if (tileLeft == ']') {
                    var leftPartNextBox = new Point2D(leftPart.getX() - 1, leftPart.getY() + 1);
                    var rightPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() + 1);

                    leftCanMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                }

                var rightCanMove = true;
                if (tileRight == '[') {
                    var leftPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() + 1);
                    var rightPartNextBox = new Point2D(rightPart.getX() + 1, rightPart.getY() + 1);

                    rightCanMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                }

                if (tileLeft == '[' && tileRight == ']') {
                    var leftPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() + 1);
                    var rightPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() + 1);

                    var canMove = moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, true);
                    leftCanMove = canMove;
                    rightCanMove = canMove;
                }

                if (!justCheck && leftCanMove && rightCanMove) {
                    if (tileLeft == ']') {
                        var leftPartNextBox = new Point2D(leftPart.getX() - 1, leftPart.getY() + 1);
                        var rightPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() + 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    if (tileRight == '[') {
                        var leftPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() + 1);
                        var rightPartNextBox = new Point2D(rightPart.getX() + 1, rightPart.getY() + 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    if (tileLeft == '[' && tileRight == ']') {
                        var leftPartNextBox = new Point2D(leftPart.getX(), leftPart.getY() + 1);
                        var rightPartNextBox = new Point2D(rightPart.getX(), rightPart.getY() + 1);
                        moveBoxIfPossible(leftPartNextBox, rightPartNextBox, direction, false);
                    }

                    scaledMap.get(leftPart.getY() + 1)[leftPart.getX()] = '[';
                    scaledMap.get(rightPart.getY() + 1)[rightPart.getX()] = ']';
                    scaledMap.get(leftPart.getY())[leftPart.getX()] = '.';
                    scaledMap.get(rightPart.getY())[rightPart.getX()] = '.';
                }

                return leftCanMove && rightCanMove;
            }
        }

        throw new IllegalStateException("should not reach here");
    }

    private long calcSumOfScaledGpsCoordinates() {
        var sum = 0L;

        for (int y = 0; y < scaledMap.size(); y++) {
            for (int x = 0; x < scaledMap.get(y).length; x++) {
                if ('[' == scaledMap.get(y)[x]) {
                    sum += 100L * y + x;
                }
            }
        }

        return sum;
    }

}
