package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2022_21")
public class D21 extends AbstractAocPuzzle {

    private Map<String, Calculation> numbers;
    private List<Calculation> calculations;
    private List<String> humnPath;

    @Override
    protected void partOne() {
        parseInput();
        calculate();
        long rootsNumber = numbers.get("root").getValue();
        System.out.println("roots number: " + rootsNumber);
    }

    private void parseInput() {
        numbers = new HashMap<>();
        calculations = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" ");
            String name = split[0].replace(":", "");

            Calculation calculation;
            if (split.length == 2) {
                calculation = new Calculation(name, Long.parseLong(split[1]));
            } else {
                calculation = new Calculation(name, split[1], split[3], split[2]);
                calculations.add(calculation);
            }
            numbers.put(calculation.getName(), calculation);
        }
    }

    private void calculate() {
        humnPath = new ArrayList<>();
        String search = "humn";
        humnPath.add(search);

        do {
            List<Calculation> done = new ArrayList<>();

            for (Calculation calculation : calculations) {
                Calculation first = numbers.get(calculation.getFirst());
                Calculation second = numbers.get(calculation.getSecond());

                if (first.isSet() && second.isSet()) {
                    calculation.perform(first.getValue(), second.getValue());
                    done.add(calculation);

                    if (calculation.getFirst().equals(search) || calculation.getSecond().equals(search)) {
                        humnPath.add(calculation.getName());
                        search = calculation.getName();
                    }
                }
            }

            calculations.removeAll(done);
        } while (!calculations.isEmpty());
    }

    @Override
    protected void partTwo() {
        Long myNumber = null;

        for (int i = humnPath.size() - 1; i > 0; i--) {
            Calculation step = numbers.get(humnPath.get(i));
            String nextStep = humnPath.get(i - 1);

            String knowValue;
            if (step.getFirst().equals(nextStep)) {
                knowValue = step.getSecond();
            } else {
                knowValue = step.getFirst();
            }

            long value = numbers.get(knowValue).getValue();
            myNumber = step.reverseCalc(myNumber, value, knowValue);
        }

        System.out.println("my number: " + myNumber);
    }

}
