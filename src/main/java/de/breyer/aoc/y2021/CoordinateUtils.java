package de.breyer.aoc.y2021;

public class CoordinateUtils {

    public static double calculateDistance(Coordinate3D a, Coordinate3D b) {
        long xDiff = b.getX() - a.getX();
        long yDiff = b.getY() - a.getY();
        long zDiff = b.getZ() - a.getZ();

        double xPow = Math.pow(xDiff, 2);
        double yPow = Math.pow(yDiff, 2);
        double zPow = Math.pow(zDiff, 2);

        return Math.sqrt(xPow + yPow + zPow);
    }

    public static Coordinate3D addCoordinates(Coordinate3D a, Coordinate3D b) {
        long x = a.getX() + b.getX();
        long y = a.getY() + b.getY();
        long z = a.getZ() + b.getZ();
        return new Coordinate3D(x, y, z);
    }

    public static Coordinate3D substractCoordinates(Coordinate3D a, Coordinate3D b) {
        long x = a.getX() - b.getX();
        long y = a.getY() - b.getY();
        long z = a.getZ() - b.getZ();
        return new Coordinate3D(x, y, z);
    }

    public static Coordinate3D rotateCoordinate(Coordinate3D coordinate, Rotation rotation) {
        coordinate = spinX(coordinate, rotation.getRotationX());
        coordinate = spinY(coordinate, rotation.getRotationY());
        coordinate = spinZ(coordinate, rotation.getRotationZ());
        return coordinate;
    }

    private static Coordinate3D spinX(Coordinate3D coordinate, double angle) {
        double phi = Math.toRadians(angle);
        long tempX = coordinate.getX();
        long tempY = Math.round(Math.cos(phi) * coordinate.getY() - Math.sin(phi) * coordinate.getZ());
        long tempZ = Math.round(Math.sin(phi) * coordinate.getY() + Math.cos(phi) * coordinate.getZ());
        return new Coordinate3D(tempX, tempY, tempZ);
    }

    private static Coordinate3D spinY(Coordinate3D coordinate, double angle) {
        double phi = Math.toRadians(angle);
        long tempX = Math.round(Math.cos(phi) * coordinate.getX() + Math.sin(phi) * coordinate.getZ());
        long tempY = coordinate.getY();
        long tempZ = Math.round((-Math.sin(phi)) * coordinate.getX() + Math.cos(phi) * coordinate.getZ());
        return new Coordinate3D(tempX, tempY, tempZ);
    }

    private static Coordinate3D spinZ(Coordinate3D coordinate, double angle) {
        double phi = Math.toRadians(angle);
        long tempX = Math.round(Math.cos(phi) * coordinate.getX() - Math.sin(phi) * coordinate.getY());
        long tempY = Math.round(Math.sin(phi) * coordinate.getX() + Math.cos(phi) * coordinate.getY());
        long tempZ = coordinate.getZ();
        return new Coordinate3D(tempX, tempY, tempZ);
    }

    public static long calcManhattenDistance(Coordinate3D a, Coordinate3D b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ());
    }

}
