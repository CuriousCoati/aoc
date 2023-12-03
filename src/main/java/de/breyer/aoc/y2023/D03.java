package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2023.machinepart.Number;
import de.breyer.aoc.y2023.machinepart.Symbol;

@AocPuzzle("2023_03")
public class D03 extends AbstractAocPuzzle {

    private final List<Number> numbers = new ArrayList<>();
    private final List<Symbol> symbols = new ArrayList<>();

    @Override
    protected void partOne() {
        init();
        parseInput();
        var partNumberSum = numbers.stream().filter(number -> number.isPartNumber(symbols)).mapToInt(Number::getNumber).sum();
        System.out.println("sum of part numbers: " + partNumberSum);
    }

    private void init() {
        numbers.clear();
        symbols.clear();
    }

    private void parseInput() {
        for (int y = 0; y < lines.size(); y++) {
            var line = lines.get(y);
            var numberBuilder = new StringBuilder();

            for (int x = 0; x < line.length(); x++) {
                var character = line.charAt(x);
                if (Character.isDigit(character)) {
                    numberBuilder.append(character);
                } else {
                    if (numberBuilder.length() != 0) {
                        var number = new Number(Integer.parseInt(numberBuilder.toString()));
                        numbers.add(number);

                        for (int i = 0; i < numberBuilder.length(); i++) {
                            number.getCoordinates().add(new Point2D(x - i - 1, y));
                        }
                        numberBuilder.setLength(0);
                    }

                    if ('.' != character) {
                        symbols.add(new Symbol(character, new Point2D(x, y)));
                    }
                }
            }

            if (numberBuilder.length() != 0) {
                var number = new Number(Integer.parseInt(numberBuilder.toString()));
                numbers.add(number);

                for (int i = 0; i < numberBuilder.length(); i++) {
                    number.getCoordinates().add(new Point2D(line.length() - i - 1, y));
                }
                numberBuilder.setLength(0);
            }
        }
    }

    @Override
    protected void partTwo() {
        var sum = symbols.stream().mapToLong(s -> s.calcGearRatio(numbers)).sum();
        System.out.println("sum of gear ratios : " + sum);
    }

}
