package de.breyer.aoc.y2021;

import java.math.BigInteger;

public class Pair {

    private int x = 0;
    private int y = 0;
    private boolean xFilled = false;
    private Pair xPair;
    private Pair yPair;
    private Pair parent;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
        xFilled = true;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isXFilled() {
        return xFilled;
    }

    public Pair getXPair() {
        return xPair;
    }

    public void setXPair(Pair pair) {
        this.xPair = pair;
        xFilled = true;
    }

    public Pair getYPair() {
        return yPair;
    }

    public void setYPair(Pair pair) {
        this.yPair = pair;
    }

    public Pair getParent() {
        return parent;
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    public void print() {
        System.out.print("[");
        if (null == xPair) {
            System.out.print(x);
        } else {
            xPair.print();
        }
        System.out.print(",");
        if (null == yPair) {
            System.out.print(y);
        } else {
            yPair.print();
        }
        System.out.print("]");
    }

    public BigInteger getMagnitude() {
        BigInteger magnitude = BigInteger.ZERO;
        if (null == xPair) {
            magnitude = magnitude.add(BigInteger.valueOf(x).multiply(BigInteger.valueOf(3)));
        } else {
            magnitude = magnitude.add(xPair.getMagnitude().multiply(BigInteger.valueOf(3)));
        }

        if (null == yPair) {
            magnitude = magnitude.add(BigInteger.valueOf(y).multiply(BigInteger.valueOf(2)));
        } else {
            magnitude = magnitude.add(yPair.getMagnitude().multiply(BigInteger.valueOf(2)));
        }
        return magnitude;
    }
}
