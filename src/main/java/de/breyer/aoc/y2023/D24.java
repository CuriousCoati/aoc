package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2021.Point3D;

@AocPuzzle("2023_24")
public class D24 extends AbstractAocPuzzle {

    private static final long MIN_TEST_AREA = 200000000000000L;
    private static final long MAX_TEST_AREA = 400000000000000L;

    @Override
    protected void partOne() {
        var hailstones = parseInput();
        var count = countIntersections(hailstones);
        System.out.println(count + " hailstones intersect within the test area");
    }

    private int countIntersections(List<Hailstone> hailstones) {
        var count = 0;

        for (int i = 0; i < hailstones.size() - 1; i++) {
            for (int j = i; j < hailstones.size(); j++) {
                if (doIntersect(hailstones.get(i), hailstones.get(j))) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean doIntersect(Hailstone a, Hailstone b) {
        var cartesianProductVelocity = a.velocity.x() * b.velocity.y() - a.velocity.y() * b.velocity.x();
        if (0 == cartesianProductVelocity) {
            return false;
        }

        var r = (double) ((b.position.x() - a.position.x()) * b.velocity.y() - (b.position.y() - a.position.y()) * b.velocity.x())
                / (cartesianProductVelocity);

        var s = (double) ((b.position.x() - a.position.x()) * a.velocity.y() - (b.position.y() - a.position.y()) * a.velocity.x())
                / (cartesianProductVelocity);

        var x = a.position.x() + r * a.velocity.x();
        var y = a.position.y() + r * a.velocity.y();

        return r > 0 && s > 0 && x >= MIN_TEST_AREA && x <= MAX_TEST_AREA && y >= MIN_TEST_AREA && y <= MAX_TEST_AREA;
    }

    private List<Hailstone> parseInput() {
        var hailstones = new ArrayList<Hailstone>();

        for (var line : lines) {
            var split = line.split("@");
            var pos = split[0].split(",");
            var vel = split[1].split(",");

            hailstones.add(new Hailstone(
                    new Point3D(Long.parseLong(pos[0].trim()), Long.parseLong(pos[1].trim()), Long.parseLong(pos[2].trim())),
                    new Point3D(Long.parseLong(vel[0].trim()), Long.parseLong(vel[1].trim()), Long.parseLong(vel[2].trim()))
            ));
        }

        return hailstones;
    }

    @Override
    protected void partTwo() {
        var hailstones = parseInput();
        var position = findPositionToHitAllHailstones(hailstones);
        System.out.println((position.x() * position.y() + position.z()) + " sum of initial position");
    }

    private Point3D findPositionToHitAllHailstones(List<Hailstone> hailstones) {
        var averagePosition = calculateAveragePoint(hailstones.stream().map(Hailstone::position).toList());
        var averageVelocity = calculateAveragePoint(hailstones.stream().map(Hailstone::velocity).toList());

        var k = 2;
        var throwPosition = averagePosition.subtract(averageVelocity.multiply(k));

        return new Point3D(0, 0, 0);
    }

    private Point3D calculateAveragePoint(List<Point3D> points) {
        Point3D sum = new Point3D(0, 0, 0);
        for (var point : points) {
            sum = sum.add(point);
        }
        return sum.divide(points.size());
    }

    private record Hailstone(Point3D position, Point3D velocity) {

    }

}
