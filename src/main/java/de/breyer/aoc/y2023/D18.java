package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.LongCoordinate2D;
import de.breyer.aoc.y2022.Direction;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2023_18")
public class D18 extends AbstractAocPuzzle {

    private Map<LongCoordinate2D, Pair<Direction, Direction>> corners;
    private List<Border> borders;
    private long minY;
    private long maxY;

    @Override
    protected void partOne() {
        var instructions = parseInput();
        dig(instructions);
        var size = calcSize();
        System.out.println("the lagoon can hold '" + size + "' cubic meters of lava");
    }

    private List<DigInstruction> parseInput() {
        var instructions = new ArrayList<DigInstruction>();

        for (var line : lines) {
            var split = line.split(" ");

            var direction = Direction.valueOf(split[0].charAt(0));
            var length = Integer.parseInt(split[1]);

            instructions.add(new DigInstruction(direction, length));
        }

        return instructions;
    }

    private void dig(List<DigInstruction> instructions) {
        corners = new HashMap<>();
        borders = new ArrayList<>();
        minY = Long.MAX_VALUE;
        maxY = Long.MIN_VALUE;

        var current = new LongCoordinate2D(0, 0);
        var lastDirection = Direction.UP;

        for (var instruction : instructions) {
            corners.put(current, new Pair<>(lastDirection, instruction.direction()));
            var next = calcNextPoint(instruction.direction(), current, instruction.length());
            minY = Math.min(minY, current.y());
            maxY = Math.max(maxY, current.y());
            lastDirection = instruction.direction();
            borders.add(new Border(current, next));
            current = next;
        }
    }

    private LongCoordinate2D calcNextPoint(Direction direction, LongCoordinate2D point, int length) {
        return switch (direction) {
            case RIGHT -> new LongCoordinate2D(point.x() + length, point.y());
            case LEFT -> new LongCoordinate2D(point.x() - length, point.y());
            case UP -> new LongCoordinate2D(point.x(), point.y() - length);
            case DOWN -> new LongCoordinate2D(point.x(), point.y() + length);
        };
    }

    private long calcSize() {
        var count = 0L;
        var inside = false;
        Pair<Direction, Direction> corner = null;

        for (var y = minY; y <= maxY; y++) {
            long finalY = y;
            var xList = borders.stream().filter(b -> b.isRelevant(finalY)).flatMapToLong(Border::getXList).distinct().sorted().toArray();
            var lastX = Long.MIN_VALUE;

            for (var x : xList) {
                var point = new LongCoordinate2D(x, y);
                count++;

                if (inside || corner != null) {
                    count += x - lastX - 1;
                }

                if (corners.containsKey(point)) {
                    if (corner == null) {
                        corner = corners.get(point);
                    } else {
                        if (inclusiveCorners(corner, corners.get(point))) {
                            inside = !inside;
                        }
                        corner = null;
                    }
                } else if (null == corner) {
                    inside = !inside;
                }

                lastX = x;
            }

        }

        return count;
    }

    private boolean inclusiveCorners(Pair<Direction, Direction> corner1, Pair<Direction, Direction> corner2) {
        return (corner1.getFirst() == corner2.getFirst() || corner1.getFirst() == corner2.getSecond()) &&
                (corner1.getSecond() == corner2.getFirst() || corner1.getSecond() == corner2.getSecond());
    }

    @Override
    protected void partTwo() {
        var instructions = parseInputTwo();
        dig(instructions);
        var size = calcSize();
        System.out.println("the lagoon can hold '" + size + "' cubic meters of lava");
    }

    private List<DigInstruction> parseInputTwo() {
        var instructions = new ArrayList<DigInstruction>();

        for (var line : lines) {
            var split = line.split(" ");

            var length = Integer.parseInt(split[2].substring(2, split[2].length() - 2), 16);
            var direction = parseDirection(split[2].charAt(split[2].length() - 2));

            instructions.add(new DigInstruction(direction, length));
        }

        return instructions;
    }

    private Direction parseDirection(char c) {
        return switch (c) {
            case '0' -> Direction.RIGHT;
            case '1' -> Direction.DOWN;
            case '2' -> Direction.LEFT;
            case '3' -> Direction.UP;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private record DigInstruction(Direction direction, int length) {

    }

    private record Border(LongCoordinate2D start, LongCoordinate2D end) {

        public boolean isRelevant(long y) {
            return start.y() <= y && y <= end.y() || start.y() >= y && y >= end.y();
        }

        public LongStream getXList() {
            return LongStream.of(start.x(), end.x());
        }

    }

}
