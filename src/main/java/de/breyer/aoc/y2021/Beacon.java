package de.breyer.aoc.y2021;

import java.util.HashMap;
import java.util.Map;

public class Beacon {

    public Coordinate3D relativePosition;
    public Coordinate3D absolutePosition;
    private Map<Beacon, Double> distances = new HashMap<>();

    public Coordinate3D getRelativePosition() {
        return relativePosition;
    }

    public Coordinate3D getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Coordinate3D absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public Map<Beacon, Double> getDistances() {
        return distances;
    }

    public Beacon(Coordinate3D position) {
        this.relativePosition = position;
    }

    public void addDistance(Beacon otherBeacon, Double distance) {
        distances.put(otherBeacon, distance);
    }


}
