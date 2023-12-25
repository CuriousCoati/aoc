package de.breyer.aoc.y2021;

import java.util.Objects;


public record Point3D(long x, long y, long z) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point3D point = (Point3D) o;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public Point3D add(Point3D other) {
        return new Point3D(x + other.x(), y + other.y(), z + other.z());
    }

    public Point3D subtract(Point3D other) {
        return new Point3D(x - other.x(), y - other.y(), z - other.z());
    }

    public Point3D multiply(int multiplier) {
        return new Point3D(x * multiplier, y * multiplier, z * multiplier);
    }

    public Point3D divide(int divider) {
        return new Point3D(x / divider, y / divider, z / divider);
    }
}
