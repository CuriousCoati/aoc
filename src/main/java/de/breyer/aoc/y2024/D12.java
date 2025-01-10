package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.utils.InputUtil;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2024_12")
public class D12 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var map = InputUtil.inputToCharMap(lines);
        var regions = createRegions(map);
        var totalPrice = calcTotalPriceByPerimeter(regions);
        System.out.println("total price: " + totalPrice);
    }

    private List<List<Point2D>> createRegions(char[][] map) {
        var regions = new ArrayList<List<Point2D>>();
        var pointToRegionMap = new HashMap<String, List<Point2D>>();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {

                var c = map[y][x];
                List<Point2D> region = null;

                if (x - 1 >= 0 && map[y][x - 1] == c) {
                    region = pointToRegionMap.get(calcKey(x - 1, y));
                }

                if (y - 1 >= 0 && map[y - 1][x] == c) {
                    if (null == region) {
                        region = pointToRegionMap.get(calcKey(x, y - 1));
                    } else {
                        var region2 = pointToRegionMap.get(calcKey(x, y - 1));
                        if (region2 != region) {
                            region2.addAll(region);
                            regions.remove(region);
                            region = region2;

                            for (var merged : region) {
                                pointToRegionMap.put(calcKey(merged.getX(), merged.getY()), region);
                            }
                        }
                    }
                }

                if (region == null) {
                    region = new ArrayList<>();
                    regions.add(region);
                }

                region.add(new Point2D(x, y));
                pointToRegionMap.put(calcKey(x, y), region);
            }
        }

        return regions;
    }

    private String calcKey(int x, int y) {
        return String.format("%s-%s", x, y);
    }

    private long calcTotalPriceByPerimeter(List<List<Point2D>> regions) {
        var totalPrice = 0L;

        for (var region : regions) {
            var area = region.size();

            var perimeter = 0L;

            for (var point : region) {
                for (var direction : Direction.values()) {
                    var nextY = direction.getYExpression().apply(point.getY(), 1);
                    var nextX = direction.getXExpression().apply(point.getX(), 1);
                    var nextPoint = new Point2D(nextX, nextY);
                    if (!region.contains(nextPoint)) {
                        perimeter++;
                    }

                }
            }

            totalPrice += area * perimeter;
        }

        return totalPrice;
    }

    @Override
    protected void partTwo() {
        var map = InputUtil.inputToCharMap(lines);
        var regions = createRegions(map);
        var totalPrice = calcTotalPriceBySides(regions);
        System.out.println("total price: " + totalPrice);
    }

    private long calcTotalPriceBySides(List<List<Point2D>> regions) {
        var totalPrice = 0L;

        for (var region : regions) {
            var area = region.size();

            var sides = 0L;
            var counted = new ArrayList<String>();

            for (var point : region) {
                for (var direction : Direction.values()) {
                    var nextY = direction.getYExpression().apply(point.getY(), 1);
                    var nextX = direction.getXExpression().apply(point.getX(), 1);
                    var nextPoint = new Point2D(nextX, nextY);
                    if (!region.contains(nextPoint)) {
                        if (direction == Direction.UP || direction == Direction.DOWN) {
                            var key = String.format("H-%s-%s/%s", point.getX(), nextY, point.getY());
                            var keyLeft = String.format("H-%s-%s/%s", point.getX() - 1, nextY, point.getY());
                            if (!counted.contains(keyLeft)) {
                                sides++;
                            }
                            counted.add(key);
                        } else {
                            var key = String.format("V-%s-%s/%s", point.getY(), nextX, point.getX());
                            var keyUp = String.format("V-%s-%s/%s", point.getY() - 1, nextX, point.getX());
                            if (!counted.contains(keyUp)) {
                                sides++;
                            }
                            counted.add(key);
                        }
                    }

                }
            }

            totalPrice += area * sides;
        }

        return totalPrice;
    }

}
