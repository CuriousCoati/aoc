package de.breyer.aoc.y2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Monkey {

    private static final BigInteger THREE = BigInteger.valueOf(3L);

    private final List<BigInteger> items = new ArrayList<>();
    private BigInteger test;
    private Function<BigInteger, BigInteger> operation;
    private int monkeyCaseTrue;
    private int monkeyCaseFalse;
    private long inspections = 0;

    public BigInteger getTest() {
        return test;
    }

    public void setTest(BigInteger test) {
        this.test = test;
    }

    public void setOperation(Function<BigInteger, BigInteger> operation) {
        this.operation = operation;
    }

    public void setMonkeyCaseTrue(int monkeyCaseTrue) {
        this.monkeyCaseTrue = monkeyCaseTrue;
    }

    public void setMonkeyCaseFalse(int monkeyCaseFalse) {
        this.monkeyCaseFalse = monkeyCaseFalse;
    }

    public long getInspections() {
        return inspections;
    }

    public void turn(List<Monkey> monkeys, BigInteger leastCommonMultiple) {
        for (BigInteger worryLevel : items) {
            inspections++;
            BigInteger newWorryLevel = operation.apply(worryLevel);
            if (null == leastCommonMultiple) {
                newWorryLevel = newWorryLevel.divide(THREE);
            } else {
                newWorryLevel = newWorryLevel.remainder(leastCommonMultiple);
            }

            int idx = newWorryLevel.remainder(test).equals(BigInteger.ZERO) ? monkeyCaseTrue : monkeyCaseFalse;
            monkeys.get(idx).addItem(newWorryLevel);
        }
        items.clear();
    }

    public void addItem(BigInteger item) {
        this.items.add(item);
    }
}
