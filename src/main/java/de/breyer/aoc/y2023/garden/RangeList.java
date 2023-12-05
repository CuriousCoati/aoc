package de.breyer.aoc.y2023.garden;

import java.util.ArrayList;
import java.util.List;

public class RangeList {

    private final List<Range> ranges = new ArrayList<>();

    public void add(Range range) {
        ranges.add(range);
    }

    public long map(long input) {
        var mappingResult = -1L;

        for (var range : ranges) {
            mappingResult = range.map(input);
            if (-1L != mappingResult) {
                break;
            }
        }

        return mappingResult;
    }
}
