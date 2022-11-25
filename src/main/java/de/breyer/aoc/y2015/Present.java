package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Present {

    private final List<Integer> dimensions = new ArrayList<>();
    private final List<Integer> surfaces = new ArrayList<>();

    public Present(String length, String width, String height) {
        dimensions.add(parse(length));
        dimensions.add(parse(width));
        dimensions.add(parse(height));
        Collections.sort(dimensions);

        surfaces.add(dimensions.get(0) * dimensions.get(1));
        surfaces.add(dimensions.get(1) * dimensions.get(2));
        surfaces.add(dimensions.get(2) * dimensions.get(0));
        Collections.sort(surfaces);
    }

    private int parse(String value) {
        return Integer.parseInt(value);
    }

    public int getTotalSurface() {
        return 2 * surfaces.get(0) + 2 * surfaces.get(1) + 2 * surfaces.get(2);
    }

    public int getSmallestSide() {
        return surfaces.get(0);
    }

    public int getVolume() {
        return dimensions.get(0) * dimensions.get(1) * dimensions.get(2);
    }

    public int getSmallestPerimeter() {
        int smallestSide = dimensions.get(0);
        int mediumSide = dimensions.get(1);
        return smallestSide + smallestSide + mediumSide + mediumSide;
    }
}
