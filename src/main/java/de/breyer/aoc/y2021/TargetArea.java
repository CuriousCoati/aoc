package de.breyer.aoc.y2021;

public class TargetArea {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public TargetArea(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public boolean isInside(Probe probe) {
        return minX <= probe.getX() && maxX >= probe.getX() && minY <= probe.getY() && maxY >= probe.getY();
    }

    public boolean missed(Probe probe) {
        return maxX < probe.getX() || minY > probe.getY();
    }
}
