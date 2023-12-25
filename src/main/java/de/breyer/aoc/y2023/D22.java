package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2021.Point3D;

@AocPuzzle("2023_22")
public class D22 extends AbstractAocPuzzle {

    private List<Brick> bricks;

    @Override
    protected void partOne() {
        parseInput();
        fall();
        var count = countSafelyRemovableBricks();
        System.out.println(count + " bricks could be safely disintegrated");
    }

    private void parseInput() {
        bricks = new ArrayList<Brick>();

        for (var line : lines) {
            var split = line.split("~");
            var startSplit = split[0].split(",");
            var endSplit = split[1].split(",");

            var start = new Point3D(Integer.parseInt(startSplit[0]), Integer.parseInt(startSplit[1]), Integer.parseInt(startSplit[2]));
            var end = new Point3D(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), Integer.parseInt(endSplit[2]));

            bricks.add(new Brick(start, end));
        }
    }

    private void fall() {
        var hasMoved = false;

        do {
            hasMoved = false;

            for (var brick : bricks) {
                if (brick.end.z() != 1 && brick.start.z() != 1) {
                    var newStart = new Point3D(brick.start.x(), brick.start.y(), brick.start.z() - 1);
                    var newEnd = new Point3D(brick.end.x(), brick.end.y(), brick.end.z() - 1);
                    var newBrick = new Brick(newStart, newEnd);

                    var canMove = true;

                    for (var otherBrick : bricks) {
                        if (otherBrick != brick && otherBrick.intersects(newBrick)) {
                            canMove = false;
                            break;
                        }
                    }

                    if (canMove) {
                        brick.start = newStart;
                        brick.end = newEnd;
                        hasMoved = true;
                    }
                }
            }

        } while (hasMoved);
    }

    private int countSafelyRemovableBricks() {
        for (var brick : bricks) {
            for (var other : bricks) {
                var newStart = new Point3D(brick.start.x(), brick.start.y(), brick.start.z() + 1);
                var newEnd = new Point3D(brick.end.x(), brick.end.y(), brick.end.z() + 1);
                var higherBrick = new Brick(newStart, newEnd);

                if (brick != other && other.intersects(higherBrick)) {
                    brick.supports.add(other);
                    other.supportedBy.add(brick);
                }
            }
        }

        var count = 0;

        for (var brick : bricks) {
            if (brick.supports.size() == 0 || brick.supports.stream().noneMatch(b -> b.supportedBy.size() == 1)) {
                count++;
            }
        }

        return count;
    }

    @Override
    protected void partTwo() {
        var sum = countCollapsingBricks();
        System.out.println(sum + " bricks would fall");
    }

    private int countCollapsingBricks() {
        var count = 0;

        for (var brick : bricks) {
            var removed = new ArrayList<>();
            removed.add(brick);

            var set = new LinkedHashSet<>(brick.supports);

            while (!set.isEmpty()) {
                var current = set.iterator().next();
                set.remove(current);
                if (removed.containsAll(current.supportedBy)) {
                    removed.add(current);
                    set.addAll(current.supports);
                }

            }

            count += removed.size() - 1;
        }

        return count;
    }

    private static class Brick {

        private Point3D start;
        private Point3D end;
        private final List<Brick> supports = new ArrayList<>();
        private final List<Brick> supportedBy = new ArrayList<>();

        public Brick(Point3D start, Point3D end) {
            this.start = start;
            this.end = end;
        }

        public boolean intersects(Brick otherBrick) {
            return otherBrick.start.x() <= end.x() && otherBrick.end.x() >= start.x() &&
                    otherBrick.start.y() <= end.y() && otherBrick.end.y() >= start.y() &&
                    otherBrick.start.z() <= end.z() && otherBrick.end.z() >= start.z();
        }
    }

}
