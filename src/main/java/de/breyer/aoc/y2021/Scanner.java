package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private final List<Beacon> beacons = new ArrayList<>();
    private Coordinate3D position;
    private Rotation rotation;

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public Coordinate3D getPosition() {
        return position;
    }

    public void setPosition(Coordinate3D position) {
        this.position = position;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void addBeacon(Beacon beacon) {
        beacons.add(beacon);
    }

    public void calcAbsoulteBeaconPositions() {
        for (Beacon beacon : beacons) {
            Coordinate3D rotatedPosition = CoordinateUtils.rotateCoordinate(beacon.getRelativePosition(), rotation);
            beacon.setAbsolutePosition(CoordinateUtils.addCoordinates(position, rotatedPosition));
        }
    }
}
