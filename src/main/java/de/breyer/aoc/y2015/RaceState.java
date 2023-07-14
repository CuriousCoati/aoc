package de.breyer.aoc.y2015;

import lombok.Getter;

public class RaceState {

    @Getter
    private final Reindeer reindeer;
    @Getter
    private int distance;
    @Getter
    private int points;
    @Getter
    private int remainingFlyTime;
    @Getter
    private int remainingRestTime;

    public RaceState(Reindeer reindeer) {
        this.reindeer = reindeer;
        resetFlyTime();
    }

    public void addDistance(int gain) {
        distance += gain;
    }

    public void addPoint() {
        points++;
    }

    public void resetFlyTime() {
        remainingFlyTime = reindeer.getFlyTime();
    }

    public void reduceFlyTime() {
        remainingFlyTime--;
    }

    public void resetRestTime() {
        remainingRestTime = reindeer.getRestTime();
    }

    public void reduceRestTime() {
        remainingRestTime--;
    }
}
