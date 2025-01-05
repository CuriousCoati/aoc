package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AocPuzzle("2024_07")
public class D07 extends AbstractAocPuzzle {

    private boolean withConcatenation;

    @Override
    protected void partOne() {
        var equations = parseInput();
        withConcatenation = false;
        var calibrationResult = calculateCalibrationResult(equations);
        System.out.println("total calibration result: " + calibrationResult);
    }

    private List<CalibrationEquation> parseInput() {
        var equations = new ArrayList<CalibrationEquation>();

        for (var line : lines) {
            var split = line.split(" ");

            var equation = new CalibrationEquation(Long.parseLong(split[0].substring(0, split[0].length() - 1)));
            equations.add(equation);

            for (int i = 1; i < split.length; i++) {
                equation.addNumber(Integer.parseInt(split[i]));
            }

        }

        return equations;
    }

    private long calculateCalibrationResult(List<CalibrationEquation> equations) {
        var result = 0L;

        for (var equation : equations) {
            var results = calculateCombination(equation.getNumbers(), 1, equation.getNumbers().get(0));

            if (results.contains(equation.result)) {
                result += equation.result;
            }
        }

        return result;
    }

    private List<Long> calculateCombination(List<Integer> numbers, int index, long value) {
        if (index == numbers.size()) {
            return List.of(value);
        }

        var resultList = new ArrayList<Long>();

        resultList.addAll(calculateCombination(numbers, index + 1, value + numbers.get(index)));
        resultList.addAll(calculateCombination(numbers, index + 1, value * numbers.get(index)));
        if (withConcatenation) {
            resultList.addAll(calculateCombination(numbers, index + 1, Long.parseLong(value + "" + numbers.get(index))));
        }

        return resultList;
    }

    @Override
    protected void partTwo() {
        var equations = parseInput();
        withConcatenation = true;
        var calibrationResult = calculateCalibrationResult(equations);
        System.out.println("total calibration result: " + calibrationResult);
    }

    @Getter
    @RequiredArgsConstructor
    @ToString
    private static class CalibrationEquation {

        private final long result;
        private final List<Integer> numbers = new ArrayList<>();

        public void addNumber(int number) {
            numbers.add(number);
        }

    }

}
