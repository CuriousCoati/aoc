package de.breyer.aoc.utils;

import de.breyer.aoc.data.Point2D;

public class MathUtil {

    public static int manhattenDistance(Point2D first, Point2D second) {
        return manhattenDistance(first.getX(), first.getY(), second.getX(), second.getY());
    }

    public static int manhattenDistance(int xFirst, int yFirst, int xSecond, int ySecond) {
        return Math.abs(xFirst - xSecond) + Math.abs(yFirst - ySecond);
    }

}
