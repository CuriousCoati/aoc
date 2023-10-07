package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_02")
public class D02 extends AbstractAocPuzzle {

    private final List<Present> presents = new ArrayList<>();

    @Override
    protected void partOne() {
        convertInput();
        calculateNeededPaper();
    }

    private void convertInput() {
        for (String line : lines) {
            String[] split = line.split("x");
            presents.add(new Present(split[0], split[1], split[2]));
        }
    }

    private void calculateNeededPaper() {
        int totalPaper = 0;
        for (Present present : presents) {
            totalPaper += present.getTotalSurface() + present.getSmallestSide();
        }
        System.out.println("wrapping paper needed: " + totalPaper);
    }

    @Override
    protected void partTwo() {
        calculateNeededRibbon();
    }

    private void calculateNeededRibbon() {
        int totalRibbon = 0;
        for (Present present : presents) {
            totalRibbon += present.getSmallestPerimeter() + present.getVolume();
        }
        System.out.println("ribbon needed: " + totalRibbon);
    }
}
