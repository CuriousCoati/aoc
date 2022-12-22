package de.breyer.aoc.y2022;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_22")
public class D22 extends AbstractAocPuzzle {

    private static final char OPEN = '.';
    private static final char BLOCKED = '#';

    private char[][] map;
    private Point2D position;
    private String instructions;
    private Direction direction;
    private Cube cube;

    @Override
    protected void partOne() {
        init();
        parseInput();
        followInstructions(this::move, () -> direction, newDirection -> direction = newDirection);
        System.out.println("final password: " + calcPassword(direction, position));
    }

    private void init() {
        instructions = lines.get(lines.size() - 1);
        position = null;
        direction = Direction.RIGHT;
    }

    private void parseInput() {
        List<String> mapLines = lines.subList(0, lines.size() - 2);

        int maxX = mapLines.stream().map(String::length).max(Integer::compareTo).orElse(0);
        map = new char[mapLines.size()][maxX];

        for (int y = 0; y < mapLines.size(); y++) {
            String line = mapLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                if (character == OPEN || character == BLOCKED) {
                    map[y][x] = character;
                    if (null == position && y == 0 && character == OPEN) {
                        position = new Point2D(x, y);
                    }
                }
            }
        }
    }

    private void followInstructions(Consumer<Integer> move, Supplier<Direction> directionSupplier, Consumer<Direction> directionSetter) {
        StringBuilder builder = new StringBuilder();
        for (char character : instructions.toCharArray()) {
            if (character == 'R' || character == 'L') {
                move.accept(Integer.parseInt(builder.toString()));
                builder.setLength(0);
                directionSetter.accept(Direction.turn(directionSupplier.get(), character));
            } else {
                builder.append(character);
            }
        }
        move.accept(Integer.parseInt(builder.toString()));
    }

    private void move(int distance) {
        for (int i = 0; i < distance; i++) {
            int nextX = direction.getXExpression().apply(position.getX(), 1);
            int nextY = direction.getYExpression().apply(position.getY(), 1);

            if (nextX < 0 || nextX >= map[0].length || nextY < 0 || nextY >= map.length) {
                Optional<Point2D> foundPosition = findOppositePosition();
                if (foundPosition.isPresent()) {
                    position = foundPosition.get();
                } else {
                    break;
                }
            } else {
                char mapValue = map[nextY][nextX];
                if (BLOCKED == mapValue) {
                    break;
                } else if (OPEN == mapValue) {
                    position = new Point2D(nextX, nextY);
                } else {
                    Optional<Point2D> foundPosition = findOppositePosition();
                    if (foundPosition.isPresent()) {
                        position = foundPosition.get();
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private Optional<Point2D> findOppositePosition() {
        Point2D searchPosition = switch (direction) {
            case UP -> new Point2D(position.getX(), map.length - 1);
            case RIGHT -> new Point2D(0, position.getY());
            case DOWN -> new Point2D(position.getX(), 0);
            case LEFT -> new Point2D(map[0].length - 1, position.getY());
        };

        Optional<Point2D> result = Optional.empty();
        boolean finished = false;

        do {
            char mapValue = map[searchPosition.getY()][searchPosition.getX()];

            if (mapValue == BLOCKED) {
                finished = true;
            } else if (mapValue == OPEN) {
                result = Optional.of(searchPosition);
                finished = true;
            } else {
                int nextX = direction.getXExpression().apply(searchPosition.getX(), 1);
                int nextY = direction.getYExpression().apply(searchPosition.getY(), 1);
                searchPosition = new Point2D(nextX, nextY);
            }
        } while (!finished);

        return result;
    }

    private long calcPassword(Direction direction, Point2D position) {
        int directionValue = switch (direction) {
            case UP -> 3;
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
        };
        int finalX = position.getX() + 1;
        int finalY = position.getY() + 1;

        return 1000L * finalY + 4L * finalX + directionValue;
    }

    protected void partTwo() {
        convertToCube();
        followInstructions(cube::move, cube::getDirection, cube::setDirection);
        System.out.println("final password: " + calcPassword(cube.getDirection(), cube.getAbsolutePosition()));
    }

    private void convertToCube() {
        cube = new Cube();
        for (int x = 50; x < 100; x++) {
            for (int y = 0; y < 50; y++) {
                cube.fillPosition(map[y][x], 0, x % 50, y % 50);
            }
        }
        for (int x = 100; x < 150; x++) {
            for (int y = 0; y < 50; y++) {
                cube.fillPosition(map[y][x], 1, x % 50, y % 50);
            }
        }
        for (int x = 50; x < 100; x++) {
            for (int y = 50; y < 100; y++) {
                cube.fillPosition(map[y][x], 2, x % 50, y % 50);
            }
        }
        for (int x = 50; x < 100; x++) {
            for (int y = 100; y < 150; y++) {
                cube.fillPosition(map[y][x], 3, x % 50, y % 50);
            }
        }
        for (int x = 0; x < 50; x++) {
            for (int y = 100; y < 150; y++) {
                cube.fillPosition(map[y][x], 4, x % 50, y % 50);
            }
        }
        for (int x = 0; x < 50; x++) {
            for (int y = 150; y < 200; y++) {
                cube.fillPosition(map[y][x], 5, x % 50, y % 50);
            }
        }
    }
}
