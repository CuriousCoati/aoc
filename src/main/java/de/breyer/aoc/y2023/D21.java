package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2023_21")
public class D21 extends AbstractAocPuzzle {

    private Point2D startPoint;
    private Map<Point2D, List<Point2D>> cache;

    @Override
    protected void partOne() {
        cache = new HashMap<>();
        var map = parseInput();
        var gardenPlots = countReachableGardenPlots(64, map);
        System.out.println(gardenPlots + " garden plots can be reached");
    }

    private char[][] parseInput() {
        var map = new char[lines.size()][lines.get(0).length()];

        for (var y = 0; y < lines.size(); y++) {
            map[y] = lines.get(y).toCharArray();
            var startIndex = lines.get(y).indexOf('S');
            if (-1 != startIndex) {
                map[y][startIndex] = '.';
                startPoint = new Point2D(startIndex, y);
            }
        }

        return map;
    }

    private int countReachableGardenPlots(int steps, char[][] map) {
        var firstBorderCrossing = 0;
        var list = new ArrayList<Integer>();

        var positions = new HashSet<Point2D>();
        positions.add(startPoint);

        for (int step = 0; step < steps; step++) {
            var nextPositions = new HashSet<Point2D>();

            for (var position : positions) {
                if (firstBorderCrossing == 0 && (position.getX() < 0 || position.getX() >= map[0].length
                        || position.getY() < 0 && position.getY() >= map.length)) {
                    firstBorderCrossing = step - 1;
                }

                var translatedPoint = translatePointToSmallMap(position, map);

                if (!cache.containsKey(translatedPoint)) {
                    fillCacheForPoint(translatedPoint, map);
                }

                cache.get(translatedPoint).stream().map(p -> translateToBigMap(position, translatedPoint, p, map)).forEach(nextPositions::add);
            }

            positions = nextPositions;

            if (0 != firstBorderCrossing && step % map.length == firstBorderCrossing + 1) {
                list.add(positions.size());
            }
        }

        return positions.size();
    }

    private void fillCacheForPoint(Point2D point, char[][] map) {
        var validNeighbours = new ArrayList<Point2D>();
        for (var neighbourPoint : point.getNeighbourCoordinates(false)) {
            var translatedNeighbourPoint = translatePointToSmallMap(neighbourPoint, map);

            if ('.' == map[translatedNeighbourPoint.getY()][translatedNeighbourPoint.getX()]) {
                validNeighbours.add(neighbourPoint);
            }
        }

        cache.put(point, validNeighbours);
    }

    private Point2D translateToBigMap(Point2D position, Point2D translatedPoint, Point2D p, char[][] map) {
        if (position.getX() >= 0 && position.getX() < map[0].length && position.getY() >= 0 && position.getY() < map.length) {
            return p;
        }

        int x = position.getX() - translatedPoint.getX() + p.getX();
        int y = position.getY() - translatedPoint.getY() + p.getY();

        return new Point2D(x, y);
    }

    private Point2D translatePointToSmallMap(Point2D p, char[][] map) {
        if (p.getX() >= 0 && p.getX() < map[0].length && p.getY() >= 0 && p.getY() < map.length) {
            return p;
        }

        var translatedX = p.getX() % map[0].length;
        if (translatedX < 0) {
            translatedX += map[0].length;
        }
        var translatedY = p.getY() % map.length;
        if (translatedY < 0) {
            translatedY += map.length;
        }
        return new Point2D(translatedX, translatedY);
    }

    @Override
    protected void partTwo() {
        var map = parseInput();
        var gardenPlots = countReachableGardenPlots(4 * map.length, map);
        System.out.println(gardenPlots + " garden plots can be reached");
    }

}
