package de.breyer.aoc.utils;

import java.util.List;
import de.breyer.aoc.data.Point2D;

public class MathUtil {

    public static int manhattenDistance(Point2D first, Point2D second) {
        return manhattenDistance(first.getX(), first.getY(), second.getX(), second.getY());
    }

    public static int manhattenDistance(int xFirst, int yFirst, int xSecond, int ySecond) {
        return Math.abs(xFirst - xSecond) + Math.abs(yFirst - ySecond);
    }

    private static long calculateGCD(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return calculateGCD(b, a % b);
        }
    }

    public static long calculateLCM(List<Integer> numbers) {
        var lcm = 1L;
        for (var number : numbers) {
            lcm = lcm * number / calculateGCD(lcm, number);
        }
        return lcm;
    }

}
