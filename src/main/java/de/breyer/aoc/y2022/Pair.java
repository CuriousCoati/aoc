package de.breyer.aoc.y2022;

public class Pair<T> {

    private T first;
    private T second;

    public Pair(T first, T second) {
        setFirst(first);
        setSecond(second);
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
