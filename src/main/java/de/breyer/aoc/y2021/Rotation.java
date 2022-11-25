package de.breyer.aoc.y2021;

public class Rotation {

    private final double rotationX;
    private final double rotationY;
    private final double rotationZ;

    public double getRotationX() {
        return rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    public double getRotationZ() {
        return rotationZ;
    }

    public Rotation(double rotationX, double rotationY, double rotationZ) {
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
    }
}
