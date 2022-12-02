package de.breyer.aoc.y2022;

public enum GameResult {
    WIN,
    DRAW,
    LOSE;

    public static GameResult convert(char character) {
        return switch (character) {
            case 'X' -> LOSE;
            case 'Y' -> DRAW;
            case 'Z' -> WIN;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }
}
