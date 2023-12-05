package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2023.garden.Range;
import de.breyer.aoc.y2023.garden.RangeList;

@AocPuzzle("2023_05")
public class D05 extends AbstractAocPuzzle {

    private final List<RangeList> ranges = new ArrayList<>();

    @Override
    protected void partOne() {
        init();
        var seedNumbers = parseInput();
        var minLocation = findLowestLocationByNumber(seedNumbers);
        System.out.println("lowest location: " + minLocation);
    }

    private void init() {
        ranges.clear();
    }

    public List<Long> parseInput() {
        var seeds = new ArrayList<Long>();

        RangeList rangeList = null;
        for (var line : lines) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("seeds:")) {
                var split = line.split(" ");
                for (int i = 1; i < split.length; i++) {
                    seeds.add(Long.parseLong(split[i]));
                }
            } else if (line.contains(" map:")) {
                rangeList = new RangeList();
                ranges.add(rangeList);
            } else {
                var split = line.split(" ");
                var range = new Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
                rangeList.add(range);
            }
        }

        return seeds;
    }

    private long findLowestLocationByNumber(List<Long> seedNumbers) {
        long lowestLocation = Long.MAX_VALUE;
        for (var seedNumber : seedNumbers) {
            var location = calcLocation(seedNumber);
            lowestLocation = Math.min(lowestLocation, location);
        }
        return lowestLocation;
    }

    private Long calcLocation(Long seedNumber) {
        var value = seedNumber;

        for (var rangeList : ranges) {
            var mappingResult = rangeList.map(value);

            if (-1L != mappingResult) {
                value = mappingResult;
            }
        }

        return value;
    }

    @Override
    protected void partTwo() {
        var seedRanges = parseSeedRanges();
        var minLocation = findLowestLocationByRange(seedRanges);
        System.out.println("lowest location: " + minLocation);
    }

    private List<Range> parseSeedRanges() {
        var seedRanges = new ArrayList<Range>();

        var split = lines.get(0).split(" ");
        for (int i = 1; i < split.length; i += 2) {
            var start = Long.parseLong(split[i]);
            var length = Long.parseLong(split[i + 1]);

            seedRanges.add(new Range(0, start, length));
        }

        return seedRanges;
    }

    private long findLowestLocationByRange(List<Range> seedRanges) {
        return seedRanges.parallelStream().mapToLong(range -> {
            long lowestLocation = Long.MAX_VALUE;
            for (int i = 0; i < range.length(); i++) {
                var seedNumber = range.sourceStart() + i;
                var location = calcLocation(seedNumber);
                lowestLocation = Math.min(lowestLocation, location);
            }
            return lowestLocation;
        }).min().orElse(Long.MAX_VALUE);
    }

}
