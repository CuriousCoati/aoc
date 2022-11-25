package de.breyer.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAocPuzzle {

    protected final List<String> lines;

    protected AbstractAocPuzzle() {
        lines = new ArrayList<>();
    }

    public void run() {
        readInputs();
        System.out.println();
        System.out.println("Part One: ");
        partOne();
        System.out.println();
        System.out.println("Part Two: ");
        partTwo();
    }

    private void readInputs() {
        String name = getClass().getAnnotation(AocPuzzle.class).value().replace('_', '/');
        readInput(lines, name + "/input");
    }

    private void readInput(List<String> list, String resource) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (null != inputStream) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        list.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("could not read input");
        }
    }

    protected abstract void partOne();

    protected abstract void partTwo();

}
