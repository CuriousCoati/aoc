package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_20")
public class D20 extends AbstractAocPuzzle {

    private static final boolean TEST = false;
    private static final int STEPS = 50;

    private char[] imageEnhancementAlgorithm;
    private List<char[]> inputImage = new ArrayList<>();
    private List<char[]> outputImage;


    @Override
    protected void partOne() {
        processInput();
        calculateOutputImage(2);
        countLitPixels();
    }

    @Override
    protected void partTwo() {
        calculateOutputImage(48);
        countLitPixels();
    }

    private void processInput() {
        boolean first = true;
        for (String line : lines) {
            if (first) {
                imageEnhancementAlgorithm = line.toCharArray();
                first = false;
            } else if (!line.isEmpty()) {
                inputImage.add(line.toCharArray());
            }
        }
    }

    private void calculateOutputImage(int steps) {
        for (int step = 0; step < steps; step++) {
            int newHeight = inputImage.size() + 2;
            int newWidth = inputImage.get(0).length + 2;

            outputImage = new ArrayList<>();

            for (int y = 0; y < newHeight; y++) {
                char[] row = new char[newWidth];
                outputImage.add(row);

                for (int x = 0; x < newWidth; x++) {
                    int number = getNumberForNeighbours(x, y, step);
                    char outputValue = imageEnhancementAlgorithm[number];
                    row[x] = outputValue;
                }
            }
            inputImage = outputImage;
        }
    }

    private int getNumberForNeighbours(int x, int y, int step) {
        StringBuilder builder = new StringBuilder();

        for (int i = y - 2; i <= y; i++) {
            for (int j = x -2; j <= x; j++) {
                if (i < 0 || i >= inputImage.size() || j < 0 || j >= inputImage.get(0).length) {
                    if (TEST) {
                        builder.append('0');
                    } else {
                        builder.append((step & 1) == 0 ? '0' : '1');
                    }
                } else {
                    char character = inputImage.get(i)[j];
                    builder.append(character == '#' ? '1' : '0');
                }
            }
        }
        return Integer.parseInt(builder.toString(), 2);
    }

    private void countLitPixels() {
        long litPixels = 0;
        for (char[] row : outputImage) {
            for (char character: row) {
                if (character == '#') {
                    litPixels++;
                }
            }
        }
        System.out.println("after " + STEPS + " enhancements " + litPixels + " pixels are lit");
    }
}
