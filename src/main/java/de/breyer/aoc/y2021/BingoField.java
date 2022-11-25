package de.breyer.aoc.y2021;

public class BingoField {

    private final int number;
    private boolean marked;

    public BingoField(int number) {
       this.number = number;
       this.marked = false;
    }

    public void mark() {
        this.marked = true;
    }

    public boolean checkNumber(int number) {
        return this.number == number;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getNumber() {
        return number;
    }
}
