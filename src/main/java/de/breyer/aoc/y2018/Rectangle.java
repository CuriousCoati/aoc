package de.breyer.aoc.y2018;

public record Rectangle(
        int id,
        int x,
        int y,
        int width,
        int height
) {

    public int endX() {
        return x + width - 1;
    }

    public int endY() {
        return y + height - 1;
    }

}
