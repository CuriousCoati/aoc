package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

public class Cave {

    private final String name;
    private final List<Cave> neighbours = new ArrayList<>();
    private final boolean big;

    public Cave(String name) {
        this.name = name;
        this.big = name.toUpperCase().equals(name);
    }

    public String getName() {
        return name;
    }

    public boolean isBig() {
        return big;
    }

    public List<Cave> getNeighbours() {
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cave cave = (Cave) o;
        return Objects.equals(name, cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public void addNeighbour(Cave cave) {
        neighbours.add(cave);
    }
}
