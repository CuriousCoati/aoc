package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_12")
public class D12 extends AbstractAocPuzzle {

    private final Map<String, Cave> caves = new HashMap<>();
    private long pathCount;
    private boolean allowSmallCaveTwice;

    @Override
    protected void partOne() {
        processLines();
        calculatePaths(false);
    }

    @Override
    protected void partTwo() {
        calculatePaths(true);
    }

    private void processLines() {
        for (String line : lines) {
            String[] split = line.split("-");

            Cave caveOne = getOrCreateCave(split[0]);
            Cave caveTwo = getOrCreateCave(split[1]);

            caveOne.addNeighbour(caveTwo);
            caveTwo.addNeighbour(caveOne);
        }
    }

    private Cave getOrCreateCave(String name) {
        Cave cave = caves.get(name);
        if (null == cave) {
            cave = new Cave(name);
            caves.put(name, cave);
        }
        return cave;
    }

    private void calculatePaths(boolean allowSmallCaveTwice) {
        this.allowSmallCaveTwice = allowSmallCaveTwice;
        pathCount = 0;
        Cave start = caves.get("start");
        List<String> path = new ArrayList<>();
        path.add(start.toString());
        processNeighbours(start, path, false);
        System.out.println("found " + pathCount + " possible paths");
    }

    private void processNeighbours(Cave cave, List<String> path, boolean smallCaveVisitedTwice) {
        for (Cave neighbour : cave.getNeighbours()) {
            if (neighbour.getName().equals("end")) {
                path.add(neighbour.toString());
                pathCount++;
                path.remove(path.size() - 1);
            } else if (neighbour.getName().equals("start")) {
            } else if (neighbour.isBig() || (allowSmallCaveTwice && !smallCaveVisitedTwice) || !path.contains(neighbour.toString())) {
                boolean reset = false;
                if (!neighbour.isBig() && path.contains(neighbour.toString())) {
                    smallCaveVisitedTwice = true;
                    reset = true;
                }
                path.add(neighbour.toString());
                processNeighbours(neighbour, path, smallCaveVisitedTwice);
                path.remove(path.size() - 1);
                if (reset) {
                    smallCaveVisitedTwice = false;
                }
            }
        }
    }
}
