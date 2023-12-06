package de.breyer.aoc.y2023.boatrace;

public record RaceRecord(long time, long distance) {

    public long countWaysToBeatRecord() {
        var counter = 0;

        for (int i = 1; i < time; i++) {
            var traveledDistance = i * (time - i);
            if (traveledDistance > distance) {
                counter++;
            }
        }

        return counter;
    }

}
