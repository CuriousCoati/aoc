package de.breyer.aoc.y2021;

public class Dice {

    private long rolls = 0;
    private int value = 1;

    public long getRolls() {
        return rolls;
    }

    public int rollDie() {
        rolls++;
        int result = value;
        value++;
        if (value > 100) {
            value = 1;
        }
        return result;
    }

}
