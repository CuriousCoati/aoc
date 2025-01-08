package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2024_10")
public class D10 extends AbstractAocPuzzle {

    private final List<Point2D> startPoints = new ArrayList<>();

    @Override
    protected void partOne() {
        var map = parseInput(lines);
        var trailheadScoreSum = measureTrailheads(map, false);
        System.out.println("sum of trailhead scores: " + trailheadScoreSum);
    }

    private int[][] parseInput(List<String> input) {
        var map = new int[input.size()][input.get(0).length()];
        startPoints.clear();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(0).length(); x++) {
                var c = input.get(y).charAt(x);
                map[y][x] = Integer.parseInt("" + c);
                if ('0' == c) {
                    startPoints.add(new Point2D(x, y));
                }
            }
        }

        return map;
    }

    private long measureTrailheads(int[][] map, boolean measureRating) {
        var scoreSum = 0L;

        for (var startPoint : startPoints) {
            scoreSum += measure(map, startPoint, 0, new ArrayList<>(), measureRating);
        }

        return scoreSum;
    }

    private long measure(int[][] map, Point2D currentPoint, int currentHeight, List<Point2D> reachedEndPoints, boolean measureRating) {
        if (currentHeight == 9 && (measureRating || !reachedEndPoints.contains(currentPoint))) {
            reachedEndPoints.add(currentPoint);
            return 1;
        }

        var score = 0L;

        for (var direction : Direction.values()) {
            var nextX = direction.getXExpression().apply(currentPoint.getX(), 1);
            var nextY = direction.getYExpression().apply(currentPoint.getY(), 1);

            if (nextY >= 0 && nextX >= 0 && nextY < map.length && nextX < map[0].length && map[nextY][nextX] == currentHeight + 1) {
                score += measure(map, new Point2D(nextX, nextY), currentHeight + 1, reachedEndPoints, measureRating);
            }
        }

        return score;

    }

    @Override
    protected void partTwo() {
        var map = parseInput(lines);
        var trailheadScoreSum = measureTrailheads(map, true);
        System.out.println("sum of trailhead scores: " + trailheadScoreSum);
    }

}
