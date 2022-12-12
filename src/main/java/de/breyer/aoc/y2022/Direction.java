package de.breyer.aoc.y2022;

import java.util.function.BiFunction;

public enum Direction {

    UP((x, i) -> x, (y, i) -> y - i),
    RIGHT((x, i) -> x + i, (y, i) -> y),
    DOWN((x, i) -> x, (y, i) -> y + i),
    LEFT((x, i) -> x - i, (y, i) -> y);

    private final BiFunction<Integer, Integer, Integer> xExpression;
    private final BiFunction<Integer, Integer, Integer> yExpression;

    Direction(BiFunction<Integer, Integer, Integer> xExpression, BiFunction<Integer, Integer, Integer> yExpression) {
        this.xExpression = xExpression;
        this.yExpression = yExpression;
    }

    public static Direction valueOf(char character) {
        return switch (character) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    public BiFunction<Integer, Integer, Integer> getXExpression() {
        return xExpression;
    }

    public BiFunction<Integer, Integer, Integer> getYExpression() {
        return yExpression;
    }
}
