package de.breyer.aoc.y2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2021.Point3D;

@AocPuzzle("2022_18")
public class D18 extends AbstractAocPuzzle {

    private Set<Point3D> points;

    @Override
    protected void partOne() {
        fillGrid();
        long count = countFreeSides();
        System.out.println(count + " sides are exposed");
    }

    private void fillGrid() {
        points = new HashSet<>();
        for (String line : lines) {
            String[] split = line.split(",");
            Point3D point = new Point3D(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            points.add(point);
        }
    }

    private long countFreeSides() {
        long sidesExposed = 0;
        for (Point3D point : points) {
            for (Point3D neighbour : getNeighbours(point)) {
                if (!points.contains(neighbour)) {
                    sidesExposed++;
                }
            }
        }
        return sidesExposed;
    }

    private List<Point3D> getNeighbours(Point3D point) {
        List<Point3D> neighbours = new ArrayList<>();
        neighbours.add(new Point3D(point.x() - 1, point.y(), point.z()));
        neighbours.add(new Point3D(point.x() + 1, point.y(), point.z()));
        neighbours.add(new Point3D(point.x(), point.y() - 1, point.z()));
        neighbours.add(new Point3D(point.x(), point.y() + 1, point.z()));
        neighbours.add(new Point3D(point.x(), point.y(), point.z() - 1));
        neighbours.add(new Point3D(point.x(), point.y(), point.z() + 1));
        return neighbours;
    }

    @Override
    protected void partTwo() {
        long count = countFreeSidesReachable();
        System.out.println(count + " sides are exposed");
    }

    private long countFreeSidesReachable() {
        Queue<Point3D> queue = new ArrayDeque<>();
        List<Point3D> checked = new ArrayList<>();
        queue.add(new Point3D(-1, -1, -1));
        long sidesExposed = 0;

        do {
            Point3D point = queue.poll();
            checked.add(point);
            List<Point3D> neighbours = getNeighbours(point);

            for (Point3D neighbour : neighbours) {
                if (neighbour.x() < -1 || neighbour.x() > 20 || neighbour.y() < -1 || neighbour.y() > 20 || neighbour.z() < -1
                        || neighbour.z() > 20) {
                    continue;
                }

                if (points.contains(neighbour)) {
                    sidesExposed++;
                } else if (!checked.contains(neighbour) && !queue.contains(neighbour)) {
                    queue.add(neighbour);
                }
            }
        } while (!queue.isEmpty());

        return sidesExposed;
    }

}
