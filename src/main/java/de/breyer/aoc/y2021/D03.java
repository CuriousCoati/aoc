package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_03")
public class D03 extends AbstractAocPuzzle {

    private final StringBuilder gammaRateString = new StringBuilder();
    private final StringBuilder epsilonRateString = new StringBuilder();

    @Override
    protected void partOne() {
        evaluateInput();
        convertBitsToDecimal();
    }

    private void evaluateInput() {
        for (int i = 0; i < 12; i++) {

            int countZeros = 0;
            int countOnes = 0;

            for (int j = 0; j < lines.size(); j++) {
                char bit = lines.get(j).charAt(i);
                if ('0' == bit) {
                    countZeros++;
                } else if ('1' == bit) {
                    countOnes++;
                }
            }

            if (countZeros > countOnes) {
                gammaRateString.append('0');
                epsilonRateString.append('1');
            } else {
                gammaRateString.append('1');
                epsilonRateString.append('0');
            }

        }
    }

    private void convertBitsToDecimal() {
        int gammaRate = Integer.parseInt(gammaRateString.toString(), 2);
        int epsilonRate = Integer.parseInt(epsilonRateString.toString(), 2);

        System.out.println("gamma rate: " + gammaRate);
        System.out.println("epsilon rate: " + epsilonRate);
        System.out.println("power consumption: " + (gammaRate * epsilonRate));
    }

    @Override
    protected void partTwo() {
        evaluateLifesupport();
    }

    private void evaluateLifesupport() {
        String oxygenGeneratorRatingString = compareLinesByBit(true, lines, 0);
        String co2ScrubberRatingString = compareLinesByBit(false, lines, 0);

        int oxygenGeneratorRating = Integer.parseInt(oxygenGeneratorRatingString, 2);
        int co2ScrubberRating = Integer.parseInt(co2ScrubberRatingString, 2);

        System.out.println("oxygen generator rating: " + oxygenGeneratorRating);
        System.out.println("co2 scrubber rating: " + co2ScrubberRating);
        System.out.println("life support rating: " + (oxygenGeneratorRating * co2ScrubberRating));
    }

    private String compareLinesByBit(boolean mostCommon, List<String> lines, int index) {
        List<String> linesWithOne = new ArrayList<>();
        List<String> linesWithZero = new ArrayList<>();
        int countZeros = 0, countOnes = 0;

        for (String line: lines) {
            char bit = line.charAt(index);
            if (bit == '0') {
                countZeros++;
                linesWithOne.add(line);
            } else if (bit == '1') {
                countOnes++;
                linesWithZero.add(line);
            }
        }

        List<String> linesToKeep;
        if (mostCommon) {
            if (countOnes > countZeros || countOnes == countZeros) {
                linesToKeep = linesWithOne;
            } else {
                linesToKeep = linesWithZero;
            }
        } else {
            if (countZeros < countOnes || countOnes == countZeros) {
                linesToKeep = linesWithZero;
            } else {
                linesToKeep = linesWithOne;
            }
        }

        if (linesToKeep.size() > 1) {
            return compareLinesByBit(mostCommon, linesToKeep, index+1);
        } else if (linesToKeep.size() == 1) {
            return linesToKeep.get(0);
        } else {
            throw new RuntimeException("error no lines to keep");
        }
    }

}
