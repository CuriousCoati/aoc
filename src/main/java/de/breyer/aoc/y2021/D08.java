package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_08")
public class D08 extends AbstractAocPuzzle {

    private final List<String[]> signalsPerDisplay = new ArrayList<>();
    private final List<String[]> outputValuesperDisplay = new ArrayList<>();

    @Override
    protected void partOne() {
        parseInput();
        countSimpleNumbersInOutputValues();
    }

    @Override
    protected void partTwo() {
        sumOutputValues();
    }

    private void parseInput() {
        for (String line : lines) {
            String[] splitLine = line.split(" \\| ");
            signalsPerDisplay.add(splitLine[0].split(" "));
            outputValuesperDisplay.add(splitLine[1].split(" "));
        }
    }

    private void countSimpleNumbersInOutputValues() {
        int count = 0;
        for (String[] outputValueForDisplay : outputValuesperDisplay) {
            for (String outputValue : outputValueForDisplay) {
                if (outputValue.length() == 2 || outputValue.length() == 3 || outputValue.length() == 4 || outputValue.length() == 7) {
                    count++;
                }
            }
        }
        System.out.println("simple numbers in output values: " + count);
    }

    private void sumOutputValues() {
        int sum = 0;
        for (int idx = 0; idx < outputValuesperDisplay.size(); idx++) {
            String[] decodedSignals = decodeSignals(signalsPerDisplay.get(idx));
            String[] outputValues = outputValuesperDisplay.get(idx);

            StringBuilder number = new StringBuilder();

            for (String digit : outputValues) {
                for (int i = 0; i < decodedSignals.length; i++) {
                    String signalDigit = decodedSignals[i];
                    if (digit.length() == signalDigit.length() && signalDigit.length() == countMatchingSegments(digit, signalDigit)) {
                        number.append(i);
                    }
                }
            }

            sum += Integer.parseInt(number.toString());
        }
        System.out.println("sum of output values: " + sum);
    }

    private String[] decodeSignals(String[] signals) {
        String[] digits = new String[10];

        for (String signal: signals) {
            if (signal.length() == 2) {
                digits[1] = signal;
            } else if (signal.length() == 3) {
                digits[7] = signal;
            } else if (signal.length() == 4) {
                digits[4] = signal;
            } else if (signal.length() == 7) {
                digits[8] = signal;
            }
        }

        for (String signal: signals) {
            if (signal.length() == 5) {
                if (3 == countMatchingSegments(signal, digits[7])) {
                    digits[3] = signal;
                } else {
                    int matchingSegments = countMatchingSegments(signal, digits[4]);
                    if (2 == matchingSegments) {
                        digits[2] = signal;
                    } else if (3 == matchingSegments) {
                        digits[5] = signal;
                    }
                }
            } else if (signal.length() == 6) {
                if (4 == countMatchingSegments(signal, digits[4])) {
                    digits[9] = signal;
                } else if (3 == countMatchingSegments(signal, digits[7])) {
                    digits[0] = signal;
                } else {
                    digits[6] = signal;
                }
            }
        }
        return digits;
    }

    private int countMatchingSegments(String input, String number) {
        int matchingSegments = 0;
        for (char segment: number.toCharArray()) {
            if (input.contains("" + segment)) {
                matchingSegments++;
            }
        }
        return matchingSegments;
    }
}
