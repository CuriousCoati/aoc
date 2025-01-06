package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.utils.VectorUtil;

@AocPuzzle("2024_08")
public class D08 extends AbstractAocPuzzle {

    private final Map<Character, List<Point2D>> antennas = new HashMap<>();
    private int maxY;
    private int maxX;
    private boolean withResonantHarmonicEffect = false;

    @Override
    protected void partOne() {
        parseInput();
        withResonantHarmonicEffect = false;
        var antinodeLocations = calculateAntinodes();
        System.out.println("there are " + antinodeLocations + " unique antinode locations");
    }

    private void parseInput() {
        var input = lines;

        antennas.clear();
        maxY = input.size();
        maxX = input.get(0).length();

        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var c = input.get(y).charAt(x);
                if (c != '.') {
                    if (!antennas.containsKey(c)) {
                        antennas.put(c, new ArrayList<>());
                    }
                    antennas.get(c).add(new Point2D(x, y));
                }
            }
        }
    }

    private long calculateAntinodes() {
        var antinodeLocations = new HashSet<Point2D>();

        for (var entry : antennas.entrySet()) {

            if (entry.getValue().size() == 1) {
                continue;
            }

            for (int i = 0; i < entry.getValue().size() - 1; i++) {
                var firstAntenna = entry.getValue().get(i);
                if (withResonantHarmonicEffect) {
                    antinodeLocations.add(firstAntenna);
                }

                for (int j = i + 1; j < entry.getValue().size(); j++) {
                    var secondAntenna = entry.getValue().get(j);
                    if (withResonantHarmonicEffect) {
                        antinodeLocations.add(secondAntenna);
                    }

                    var vectorBetween = VectorUtil.vectorBetween(firstAntenna, secondAntenna);

                    var currentLocation = firstAntenna;
                    var isInbound = true;
                    do {
                        currentLocation = VectorUtil.subtract(currentLocation, vectorBetween);
                        isInbound = isInbound(currentLocation);
                        if (isInbound) {
                            antinodeLocations.add(currentLocation);
                        }
                    } while (withResonantHarmonicEffect && isInbound);

                    currentLocation = secondAntenna;
                    do {
                        currentLocation = VectorUtil.add(currentLocation, vectorBetween);
                        isInbound = isInbound(currentLocation);
                        if (isInbound) {
                            antinodeLocations.add(currentLocation);
                        }
                    } while (withResonantHarmonicEffect && isInbound);
                }
            }
        }

        return antinodeLocations.size();
    }

    private boolean isInbound(Point2D location) {
        return location.getX() >= 0 &&
                location.getX() < maxX &&
                location.getY() >= 0 &&
                location.getY() < maxY;
    }

    @Override
    protected void partTwo() {
        withResonantHarmonicEffect = true;
        var antinodeLocations = calculateAntinodes();
        System.out.println("there are " + antinodeLocations + " unique antinode locations");
    }

}
