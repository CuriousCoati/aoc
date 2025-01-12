package de.breyer.aoc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Point2D {

    private final int x;
    private final int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point2D point2D = (Point2D) o;
        return x == point2D.x && y == point2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point2D{x=" + x + ", y=" + y + '}';
    }

    public boolean isNeighbour(Point2D other) {
        for (int x = this.x - 1; x <= this.x + 1; x++) {
            for (int y = this.y - 1; y <= this.y + 1; y++) {
                if (x == other.x && y == other.y && (x != this.x || y != this.y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Point2D> getNeighbourCoordinates(boolean withDiagonals) {
        var neighbourCoordinates = new ArrayList<Point2D>();
        for (int x = this.x - 1; x <= this.x + 1; x++) {
            for (int y = this.y - 1; y <= this.y + 1; y++) {
                if ((withDiagonals && (x != this.x || y != this.y)) || (!withDiagonals && (x != this.x ^ y != this.y))) {
                    neighbourCoordinates.add(new Point2D(x, y));
                }
            }
        }
        return neighbourCoordinates;
    }
}
