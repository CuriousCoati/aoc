package de.breyer.aoc.utils;

import de.breyer.aoc.data.Point2D;

public class MathUtil {

    public static int manhattenDistance(Point2D first, Point2D second) {
        return Math.abs(first.getX() - second.getX()) + Math.abs(first.getY() - second.getY());
    }

}
