package de.breyer.aoc.y2022;

import java.util.function.BiFunction;

public enum Direction {

    UP((x, i) -> x, (y, i) -> y - i),
    RIGHT((x, i) -> x + i, (y, i) -> y),
    DOWN((x, i) -> x, (y, i) -> y + i),
    LEFT((x, i) -> x - i, (y, i) -> y);


    public static Direction valueOf(char character) {
        return switch (character) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    public static Direction turn(Direction currentDirection, char rotation) {
        return switch (currentDirection) {
            case UP -> 'R' == rotation ? Direction.RIGHT : Direction.LEFT;
            case RIGHT -> 'R' == rotation ? Direction.DOWN : Direction.UP;
            case DOWN -> 'R' == rotation ? Direction.LEFT : Direction.RIGHT;
            case LEFT -> 'R' == rotation ? Direction.UP : Direction.DOWN;
        };
    }

    public static boolean isOpposite(Direction currentDirection, Direction otherDirection) {
        return getOpposite(currentDirection) == otherDirection;
    }

    public static Direction getOpposite(Direction currentDirection) {
        return switch (currentDirection) {
            case UP -> Direction.DOWN;
            case RIGHT -> Direction.LEFT;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
        };
    }

    private final BiFunction<Integer, Integer, Integer> xExpression;
    private final BiFunction<Integer, Integer, Integer> yExpression;

    public BiFunction<Integer, Integer, Integer> getXExpression() {
        return xExpression;
    }

    public BiFunction<Integer, Integer, Integer> getYExpression() {
        return yExpression;
    }

    Direction(BiFunction<Integer, Integer, Integer> xExpression, BiFunction<Integer, Integer, Integer> yExpression) {
        this.xExpression = xExpression;
        this.yExpression = yExpression;
    }

    @Override
    public String toString() {
        return switch (this) {
            case UP -> "U";
            case RIGHT -> "R";
            case DOWN -> "D";
            case LEFT -> "L";
        };
    }
}
