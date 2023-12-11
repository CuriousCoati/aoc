package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.utils.MathUtil;

@AocPuzzle("2023_11")
public class D11 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var galaxies = parseInput(2);
        var sum = calcAndSumDistances(galaxies);
        System.out.println("sum of distances: " + sum);
    }

    private List<Point2D> parseInput(int expansion) {
        var galaxies = new ArrayList<Point2D>();

        var expansionMap = new HashMap<String, Integer>();

        int expandedY = 0;
        for (int y = 0; y < lines.size(); y++) {
            if (!lines.get(y).contains("#")) {
                expandedY += expansion - 1;
            } else {
                for (int x = 0; x < lines.get(y).length(); x++) {
                    if ('#' == lines.get(y).charAt(x)) {
                        expansionMap.put(y + "_" + x, expandedY);
                    }
                }
            }

            expandedY++;
        }

        int expandedX = 0;
        for (int x = 0; x < lines.get(0).length(); x++) {
            boolean noGalaxies = true;
            for (int y = 0; y < lines.size(); y++) {
                char c = lines.get(y).charAt(x);
                if ('#' == c) {
                    noGalaxies = false;
                    galaxies.add(new Point2D(expandedX, expansionMap.get(y + "_" + x)));
                }
            }

            if (noGalaxies) {
                expandedX += expansion - 1;
            }
            expandedX++;
        }

        return galaxies;
    }

    private long calcAndSumDistances(List<Point2D> galaxies) {
        var sum = 0L;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += MathUtil.manhattenDistance(galaxies.get(i), galaxies.get(j));
            }
        }
        return sum;
    }

    protected void partTwo() {
        var galaxies = parseInput(1000000);
        var sum = calcAndSumDistances(galaxies);
        System.out.println("sum of distances: " + sum);
    }

}
