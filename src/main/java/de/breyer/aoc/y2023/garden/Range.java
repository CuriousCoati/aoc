package de.breyer.aoc.y2023.garden;

public record Range(long destinationStart, long sourceStart, long length) {

    public long map(long sourceValue) {
        if (sourceValue >= sourceStart && sourceValue < sourceStart + length) {
            return destinationStart + sourceValue - sourceStart;
        }
        return -1;
    }
}
