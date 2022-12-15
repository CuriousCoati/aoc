package de.breyer.aoc.y2022;

import de.breyer.aoc.MathUtil;
import de.breyer.aoc.data.Point2D;

public class Sensor {

    private final Point2D position;
    private final int distance;

    public Sensor(Point2D position, int distance) {
        this.position = position;
        this.distance = distance;
    }

    public boolean covers(Point2D point) {
        return MathUtil.manhattenDistance(position, point) <= distance;
    }

    public int canCoverInRowOfPoint(Point2D point) {
        int yDiff = Math.abs(position.getY() - point.getY());
        int remainingDistance = distance - yDiff;
        int maxX = position.getX() + remainingDistance;
        return maxX - point.getX();
    }
}
