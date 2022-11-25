package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Area {

    private final int xStart;
    private final int xEnd;
    private final int yStart;
    private final int yEnd;
    private final int zStart;
    private final int zEnd;
    private final boolean activate;

    public int getXStart() {
        return xStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public int getYStart() {
        return yStart;
    }

    public int getYEnd() {
        return yEnd;
    }

    public int getZStart() {
        return zStart;
    }

    public int getZEnd() {
        return zEnd;
    }

    public boolean isActivate() {
        return activate;
    }

    public Area(int xStart, int xEnd, int yStart, int yEnd, int zStart, int zEnd, boolean activate) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.zStart = zStart;
        this.zEnd = zEnd;
        this.activate = activate;
    }

    public long getSize() {
        return (xEnd - xStart + 1L) * (yEnd - yStart + 1L) * (zEnd - zStart + 1L) * (isActivate() ? 1L : -1L);
    }

    public List<Point3D> toPoints() {
        List<Point3D> points = new ArrayList<>();
        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                for (int z = zStart; z <= zEnd; z++) {
                    points.add(new Point3D(x, y, z));
                }
            }
        }
        return points;
    }

    public Optional<Area> getOverlappingArea(Area other, boolean activate) {
        int diffX = Math.min(this.xEnd, other.xEnd) - Math.max(this.xStart, other.xStart) + 1;
        int diffY = Math.min(this.yEnd, other.yEnd) - Math.max(this.yStart, other.yStart) +1;
        int diffZ = Math.min(this.zEnd, other.zEnd) - Math.max(this.zStart, other.zStart) +1;
        if (diffX > 0 && diffY > 0 && diffZ > 0) {
            return Optional.of(new Area(
                    Math.max(this.xStart, other.xStart), Math.min(this.xEnd, other.xEnd),
                    Math.max(this.yStart, other.yStart), Math.min(this.yEnd, other.yEnd),
                    Math.max(this.zStart, other.zStart), Math.min(this.zEnd, other.zEnd),
                    activate));
        } else {
            return Optional.empty();
        }
    }
}
