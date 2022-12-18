package de.breyer.aoc.y2022;

public class Pair<T, S> {

    private T first;
    private S second;

    public Pair(T first, S second) {
        setFirst(first);
        setSecond(second);
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }
}
