package de.breyer.aoc.y2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_18")
public class D18 extends AbstractAocPuzzle {

    private final List<Pair> pairs = new ArrayList<>();
    private boolean actionOccured;
    private Pair result;

    @Override
    protected void partOne() {
        processLines();
        result = pairs.get(0);
        for (int i = 1; i < pairs.size(); i++) {
            result = addPairs(result, pairs.get(i));
            processPair(result);
        }
        result.print();
        System.out.println();
        System.out.println("magnitude: " + result.getMagnitude());
    }

    @Override
    protected void partTwo() {
        BigInteger maxMagnitude = null;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < pairs.size(); j++) {
                if (i != j) {
                    pairs.clear();
                    processLines();
                    Pair pair = addPairs(pairs.get(i), pairs.get(j));
                    processPair(pair);
                    BigInteger magnitude = pair.getMagnitude();
                    if (null == maxMagnitude || maxMagnitude.compareTo(magnitude) < 0) {
                        maxMagnitude = magnitude;
                        result = pair;
                    }
                }
            }
        }
        result.print();
        System.out.println();
        System.out.println("magnitude: " + maxMagnitude);
    }

    private void processLines() {
        Stack<Pair> pairStack = new Stack<>();
        for (String line : lines) {
            for (char character : line.toCharArray()) {
                if ('[' == character) {
                    pairStack.push(new Pair());
                } else if (']' == character) {
                    Pair pair = pairStack.pop();
                    if (pairStack.isEmpty()) {
                        this.pairs.add(pair);
                    } else {
                        Pair parentPair = pairStack.peek();
                        if (!parentPair.isXFilled()) {
                            parentPair.setXPair(pair);
                        } else {
                            parentPair.setYPair(pair);
                        }
                        pair.setParent(parentPair);
                    }
                } else if (',' != character) {
                    Pair pair = pairStack.peek();
                    int value = Integer.parseInt("" + character);
                    if (!pair.isXFilled()) {
                        pair.setX(value);
                    } else {
                        pair.setY(value);
                    }
                }
            }
        }
    }

    private Pair addPairs(Pair summand1, Pair summand2) {
        Pair sum = new Pair();
        sum.setXPair(summand1);
        summand1.setParent(sum);
        sum.setYPair(summand2);
        summand2.setParent(sum);
        return sum;
    }

    private void processPair(Pair pair) {
        do {
            actionOccured = false;
            evaluatePairForExplode(pair, 0);
            if (!actionOccured) {
                evaluatePairForSplit(pair, 0);
            }
        } while(actionOccured);
    }

    private boolean evaluatePairForExplode(Pair pair, int level) {
        boolean exploded = false;
        if (null != pair.getXPair()) {
            if (evaluatePairForExplode(pair.getXPair(), level + 1)) {
                pair.setX(0);
                pair.setXPair(null);
            }

        }
        if (null != pair.getYPair()) {
            if (evaluatePairForExplode(pair.getYPair(), level + 1)) {
                pair.setY(0);
                pair.setYPair(null);
            }
        }
        if (null == pair.getXPair() && null == pair.getYPair() && level > 3) {
            explode(pair);
            actionOccured = true;
            exploded = true;
        }
        return exploded;
    }

    private void evaluatePairForSplit(Pair pair, int level) {
        if (pair.getX() >= 10) {
            Pair splitPair = produceSplitPair(pair.getX());
            pair.setX(0);
            pair.setXPair(splitPair);
            splitPair.setParent(pair);
            actionOccured = true;
            return;
        }
        if (null != pair.getXPair()) {
            evaluatePairForSplit(pair.getXPair(), level + 1);
            if (actionOccured) {
                return;
            }
        }
        if (pair.getY() >= 10) {
            Pair splitPair = produceSplitPair(pair.getY());
            pair.setY(0);
            pair.setYPair(splitPair);
            splitPair.setParent(pair);
            actionOccured = true;
            return;
        }
        if (null != pair.getYPair()) {
            evaluatePairForSplit(pair.getYPair(), level + 1);
        }
    }

    private void explode(Pair pair) {
        Pair currentPair = pair;
        boolean goUp = true;
        boolean valueSet = false;
        do {
            if (goUp) {
                Pair parent = currentPair.getParent();
                if (currentPair == parent.getYPair()) {
                    if (null == parent.getXPair()) {
                        parent.setX(parent.getX() + pair.getX());
                        valueSet = true;
                    } else {
                        currentPair = parent.getXPair();
                        goUp = false;
                    }
                } else {
                    currentPair = parent;
                }
            } else {
                if (null == currentPair.getYPair()) {
                    currentPair.setY(currentPair.getY() + pair.getX());
                    valueSet = true;
                } else {
                    currentPair = currentPair.getYPair();
                }
            }
        } while (null != currentPair.getParent() && !valueSet);

        currentPair = pair;
        goUp = true;
        valueSet = false;
        do {
            if (goUp) {
                Pair parent = currentPair.getParent();
                if (currentPair == parent.getXPair()) {
                    if (null == parent.getYPair()) {
                        parent.setY(parent.getY() + pair.getY());
                        valueSet = true;
                    } else {
                        currentPair = parent.getYPair();
                        goUp = false;
                    }
                } else {
                    currentPair = parent;
                }
            } else {
                if (null == currentPair.getXPair()) {
                    currentPair.setX(currentPair.getX() + pair.getY());
                    valueSet = true;
                } else {
                    currentPair = currentPair.getXPair();
                }
            }
        } while (null != currentPair.getParent() && !valueSet);
    }

    private Pair produceSplitPair(int value) {
        Pair pair = new Pair();
        pair.setX((int) Math.floor(value / 2d));
        pair.setY((int) Math.ceil(value / 2d));
        return pair;
    }
}
