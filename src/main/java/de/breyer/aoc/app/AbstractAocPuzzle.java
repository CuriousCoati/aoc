package de.breyer.aoc.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAocPuzzle {

    protected List<String> lines;

    protected AbstractAocPuzzle() {
        lines = new ArrayList<>();
    }

    public void run() {
        readInputs();
        System.out.println();
        System.out.println("Part One: ");
        var start = System.currentTimeMillis();
        partOne();
        var end = System.currentTimeMillis();
        System.out.println("time: " + (end - start) + " ms");
        System.out.println();
        System.out.println("Part Two: ");
        start = System.currentTimeMillis();
        partTwo();
        end = System.currentTimeMillis();
        System.out.println("time: " + (end - start) + " ms");
    }

    private void readInputs() {
        String name = getClass().getAnnotation(AocPuzzle.class).value().replace('_', '/');
        readInput(name + "/input");
    }

    private void readInput(String resource) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (null != inputStream) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    lines = reader.lines().toList();
                }
            }
        } catch (IOException e) {
            System.out.println("could not read input");
        }
    }

    protected abstract void partOne();

    protected abstract void partTwo();

}
