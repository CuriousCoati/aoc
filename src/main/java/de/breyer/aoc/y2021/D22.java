package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_22")
public class D22 extends AbstractAocPuzzle {

    private  List<Area> areas;

    @Override
    protected void partOne() {
        convertInput(true);
        countActivatedCubes();
    }

    @Override
    protected void partTwo() {
        convertInput(false);
        countActivatedCubesBetterVersion();
    }

    private void convertInput(boolean initialization) {
        areas = new ArrayList<>();
        for (String line : lines) {
            String[] splitArr = line.split(" ");
            boolean state = splitArr[0].equals("on");

            splitArr = splitArr[1].split(",");
            int xStart = Integer.parseInt(splitArr[0].substring(splitArr[0].indexOf("=") + 1, splitArr[0].indexOf("..")));
            int xEnd = Integer.parseInt(splitArr[0].substring(splitArr[0].indexOf("..") + 2));
            int yStart = Integer.parseInt(splitArr[1].substring(splitArr[1].indexOf("=") + 1, splitArr[1].indexOf("..")));
            int yEnd = Integer.parseInt(splitArr[1].substring(splitArr[1].indexOf("..") + 2));
            int zStart = Integer.parseInt(splitArr[2].substring(splitArr[2].indexOf("=") + 1, splitArr[2].indexOf("..")));
            int zEnd = Integer.parseInt(splitArr[2].substring(splitArr[2].indexOf("..") + 2));

            Area area = new Area(xStart, xEnd, yStart, yEnd, zStart, zEnd, state);
            if (!initialization || initializationRelevant(area)) {
                areas.add(area);
            }
        }
    }

    private boolean initializationRelevant(Area area) {
        return initializationValid(area.getXStart()) && initializationValid(area.getXEnd()) &&
                initializationValid(area.getYStart()) && initializationValid(area.getYEnd()) &&
                initializationValid(area.getZStart()) && initializationValid(area.getZEnd());
    }

    private boolean initializationValid(int value) {
        return value >= -50 && value <= 50;
    }

    private void countActivatedCubes() {
        Set<Point3D> points = new HashSet<>();

        for (Area area : areas) {
            List<Point3D> newPoints = area.toPoints();

            if (area.isActivate()) {
                points.addAll(newPoints);
            } else {
                newPoints.forEach(points::remove);
            }
        }

        System.out.println("activated cubes: " + points.size());
    }

    private void countActivatedCubesBetterVersion() {
        List<Area> placedAreas = new ArrayList<>();
        for (Area area : areas) {
            List<Area> todo = new ArrayList<>();
            if (area.isActivate()) {
                todo.add(area);
            }
            for (Area placedArea : placedAreas) {
                Optional<Area> overlappingArea = placedArea.getOverlappingArea(area, !placedArea.isActivate());
                overlappingArea.ifPresent(todo::add);
            }
            placedAreas.addAll(todo);
        }

        System.out.println("activated cubes: " + placedAreas.stream().mapToLong(Area::getSize).sum());
    }
}
