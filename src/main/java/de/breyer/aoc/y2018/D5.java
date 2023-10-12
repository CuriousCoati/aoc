package de.breyer.aoc.y2018;

import java.util.stream.Collectors;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2018_05")
public class D5 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var result = reducePolymer(lines.get(0));
        System.out.println("remaining units: " + result.length());
    }

    private String reducePolymer(String startPolymer) {
        var polymer = startPolymer.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        int index = 0;

        do {
            char cOne = polymer.get(index);
            char cTwo = polymer.get(index + 1);

            if (cOne != cTwo && Character.toLowerCase(cOne) == Character.toLowerCase(cTwo)) {
                polymer.remove(index);
                polymer.remove(index);

                if (index > 0) {
                    index--;
                }
            } else {
                index++;
            }
        } while (index < polymer.size() - 1);

        return polymer.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    protected void partTwo() {
        var result = removeMostDisturbingUnitType();
        System.out.println("remaining units: " + result.length());
    }

    private String removeMostDisturbingUnitType() {
        String bestPolymer = null;

        for (char c = 'a'; c <= 'z'; c++) {
            var polymerWithoutUnitType = lines.get(0)
                    .replace("" + c, "")
                    .replace("" + Character.toUpperCase(c), "");
            var reducedPolymer = reducePolymer(polymerWithoutUnitType);

            if (null == bestPolymer || reducedPolymer.length() < bestPolymer.length()) {
                bestPolymer = reducedPolymer;
            }
        }

        return bestPolymer;
    }
}