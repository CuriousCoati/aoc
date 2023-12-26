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
    private static final int BRUTE_FORCE_RANGE = 300;

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
        System.out.println("sum of position to hit all hailstones: " + (position.x() + position.y() + position.z()));
    }

    public Point3D findPositionToHitAllHailstones(List<Hailstone> hailstones) {
        var a = hailstones.get(0);
        var b = hailstones.get(1);

        for (var vx = -BRUTE_FORCE_RANGE; vx <= BRUTE_FORCE_RANGE; vx++) {
            for (var vy = -BRUTE_FORCE_RANGE; vy <= BRUTE_FORCE_RANGE; vy++) {

                if (0 == vx || 0 == vy) {
                    continue;
                }

                var cartesianProductVelocity = (a.velocity.x() - vx) * (b.velocity.y() - vy) - (a.velocity.y() - vy) * (b.velocity.x() - vx);
                if (0 == b.velocity.x() - vx || 0 == cartesianProductVelocity) {
                    continue;
                }

                var r = ((b.position.x() - a.position.x()) * (b.velocity.y() - vy) - (b.position.y() - a.position.y()) * (b.velocity.x() - vx))
                        / (cartesianProductVelocity);

                var x = a.position.x() + a.velocity.x() * r - vx * r;
                var y = a.position.y() + a.velocity.y() * r - vy * r;

                for (int vz = -BRUTE_FORCE_RANGE; vz <= BRUTE_FORCE_RANGE; vz++) {

                    if (0 == vz) {
                        continue;
                    }

                    var z = a.position.z() + a.velocity.z() * r - vz * r;
                    var stone = new Hailstone(new Point3D(x, y, z), new Point3D(vx, vy, vz));

                    var hitsSomeOtherStones = true;
                    for (int i = 1; i < 5; i++) {
                        var h = hailstones.get(i);
                        long u;
                        if (h.velocity.x() != vx) {
                            u = (x - h.position.x()) / (h.velocity.x() - vx);
                        } else if (h.velocity.y() != vy) {
                            u = (y - h.position.y()) / (h.velocity.y() - vy);
                        } else if (h.velocity.z() != vz) {
                            u = (z - h.position.z()) / (h.velocity.z() - vz);
                        } else {
                            throw new RuntimeException("velocities are equal");
                        }

                        if ((x + u * vx != h.position.x() + u * h.velocity.x()) || (y + u * vy != h.position.y() + u * h.velocity.y()) || (z + u * vz
                                != h.position.z() + u * h.velocity.z())) {
                            hitsSomeOtherStones = false;
                            break;
                        }
                    }

                    if (hitsSomeOtherStones) {
                        return new Point3D(x, y, z);
                    }
                }
            }
        }

        throw new RuntimeException("no position found to hit all hailstones");
    }

    private record Hailstone(Point3D position, Point3D velocity) {

    }

}
