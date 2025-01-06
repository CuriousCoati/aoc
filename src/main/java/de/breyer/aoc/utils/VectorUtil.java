package de.breyer.aoc.utils;

import de.breyer.aoc.data.Point2D;

public class VectorUtil {

    public static Point2D vectorBetween(Point2D a, Point2D b) {
        return new Point2D(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public static Point2D add(Point2D a, Point2D b) {
        return new Point2D(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Point2D subtract(Point2D a, Point2D b) {
        return new Point2D(a.getX() - b.getX(), a.getY() - b.getY());
    }

}
