package de.breyer.aoc.y2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_11")
public class D11 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        List<Monkey> monkeys = convertInput();
        simulate(monkeys, 20, null);
        monkeys.sort(Comparator.comparingLong(Monkey::getInspections));
        long monkeyBusiness = monkeys.get(monkeys.size() - 1).getInspections() * monkeys.get(monkeys.size() - 2).getInspections();
        System.out.println("monkey business: " + monkeyBusiness);
    }

    private List<Monkey> convertInput() {
        List<Monkey> monkeys = new ArrayList<>();
        Monkey currentMonkey = new Monkey();
        for (String line : lines) {
            String[] split = line.split(": ");
            if (split[0].startsWith("Monkey")) {
                currentMonkey = new Monkey();
                monkeys.add(currentMonkey);
            } else if (split[0].contains("Starting items")) {
                String[] itemSplit = split[1].split(", ");
                for (String item : itemSplit) {
                    currentMonkey.addItem(BigInteger.valueOf(Long.parseLong(item)));
                }
            } else if (split[0].contains("Operation")) {
                String[] operationSplit = split[1].split(" = ");
                String[] calcSplit = operationSplit[1].split(" ");

                boolean firstIsInput = "old".equals(calcSplit[0]);
                boolean secondIsInput = "old".equals(calcSplit[2]);
                boolean bothAreInput = firstIsInput && secondIsInput;

                if ("*".equals(calcSplit[1])) {
                    if (bothAreInput) {
                        currentMonkey.setOperation(old -> old.multiply(old));
                    } else if (firstIsInput) {
                        currentMonkey.setOperation(old -> old.multiply(BigInteger.valueOf(Long.parseLong(calcSplit[2]))));
                    } else if (secondIsInput) {
                        currentMonkey.setOperation(old -> BigInteger.valueOf(Long.parseLong(calcSplit[0])).multiply(old));
                    }
                } else if ("+".equals(calcSplit[1])) {
                    if (bothAreInput) {
                        currentMonkey.setOperation(old -> old.add(old));
                    } else if (firstIsInput) {
                        currentMonkey.setOperation(old -> old.add(BigInteger.valueOf(Long.parseLong(calcSplit[2]))));
                    } else if (secondIsInput) {
                        currentMonkey.setOperation(old -> BigInteger.valueOf(Long.parseLong(calcSplit[0])).add(old));
                    }
                }
            } else if (split[0].contains("Test")) {
                String[] testSplit = split[1].split(" ");
                currentMonkey.setTest(BigInteger.valueOf(Long.parseLong(testSplit[2])));
            } else if (split[0].contains("If true")) {
                String[] trueSplit = split[1].split(" ");
                currentMonkey.setMonkeyCaseTrue(Integer.parseInt(trueSplit[3]));
            } else if (split[0].contains("If false")) {
                String[] falseSplit = split[1].split(" ");
                currentMonkey.setMonkeyCaseFalse(Integer.parseInt(falseSplit[3]));
            }
        }
        return monkeys;
    }

    private void simulate(List<Monkey> monkeys, int rounds, BigInteger leastCommonMultiple) {
        for (int i = 0; i < rounds; i++) {
            monkeys.forEach(monkey -> monkey.turn(monkeys, leastCommonMultiple));
        }
    }

    @Override
    protected void partTwo() {
        List<Monkey> monkeys = convertInput();
        BigInteger leastCommonMultiple = monkeys.stream().map(Monkey::getTest).reduce(BigInteger.ONE, BigInteger::multiply);
        simulate(monkeys, 10000, leastCommonMultiple);
        monkeys.sort(Comparator.comparingLong(Monkey::getInspections));
        long monkeyBusiness = monkeys.get(monkeys.size() - 1).getInspections() * monkeys.get(monkeys.size() - 2).getInspections();
        System.out.println("monkey business: " + monkeyBusiness);
    }

}
