package de.breyer.aoc.y2022;

public class Range {

    private final int start;
    private final int end;

    public Range(String[] values) {
        start = Integer.parseInt(values[0]);
        end = Integer.parseInt(values[1]);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean containsRange(Range range) {
        return start <= range.getStart() && end >= range.getEnd();
    }

    public Boolean overlaps(Range secondRange) {
        return start <= secondRange.getEnd() && end >= secondRange.getStart();
    }
}
