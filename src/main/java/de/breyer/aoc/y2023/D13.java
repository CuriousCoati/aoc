package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_13")
public class D13 extends AbstractAocPuzzle {

    private final Map<char[][], String> cache = new HashMap<>();
    private final List<char[][]> patterns = new ArrayList<>();

    @Override
    protected void partOne() {
        init();

        parseInput();
        var sum = patterns.stream().mapToLong(this::countLineOrColumns).sum();
        System.out.println("result: " + sum);
    }

    private void init() {
        cache.clear();
        patterns.clear();
    }

    private void parseInput() {
        var groups = new ArrayList<List<String>>();
        var currentGroup = new ArrayList<String>();
        groups.add(currentGroup);
        for (var line : lines) {
            if (line.isEmpty()) {
                currentGroup = new ArrayList<>();
                groups.add(currentGroup);
            } else {
                currentGroup.add(line);
            }
        }

        char[][] currentPattern;
        for (var group : groups) {
            currentPattern = new char[group.size()][group.get(0).length()];
            patterns.add(currentPattern);
            for (int i = 0; i < group.size(); i++) {
                currentPattern[i] = group.get(i).toCharArray();
            }
        }
    }

    private long countLineOrColumns(char[][] pattern) {
        var vertical = findVerticalReflectionLines(pattern);
        var horizontal = findHorizontalReflectionLines(pattern);

        if (vertical.isEmpty() && horizontal.isEmpty()) {
            throw new RuntimeException("no reflections found");
        } else if (vertical.size() == 1 && horizontal.isEmpty()) {
            cache.put(pattern, "v" + vertical.get(0));
            return vertical.get(0);
        } else if (horizontal.size() == 1 && vertical.isEmpty()) {
            cache.put(pattern, "h" + horizontal.get(0));
            return horizontal.get(0) * 100L;
        } else {
            throw new RuntimeException("unclear which reflection to use - v: " + vertical + " h: " + horizontal);
        }
    }

    private List<Integer> findVerticalReflectionLines(char[][] pattern) {
        var reflections = new ArrayList<Integer>();

        for (int i = 0; i < pattern[0].length - 1; i++) {
            var lower = i;
            var upper = i + 1;
            var reflection = true;

            do {
                for (char[] row : pattern) {
                    if (row[lower] != row[upper]) {
                        reflection = false;
                        break;
                    }
                }

                upper++;
                lower--;
            } while (reflection && lower >= 0 && upper < pattern[0].length);

            if (reflection) {
                reflections.add(i + 1);
            }

        }

        return reflections;
    }

    private List<Integer> findHorizontalReflectionLines(char[][] pattern) {
        var reflections = new ArrayList<Integer>();

        for (int i = 0; i < pattern.length - 1; i++) {
            var lower = i;
            var upper = i + 1;
            var reflection = true;

            do {
                for (int x = 0; x < pattern[0].length; x++) {
                    if (pattern[lower][x] != pattern[upper][x]) {
                        reflection = false;
                        break;
                    }
                }

                upper++;
                lower--;
            } while (reflection && lower >= 0 && upper < pattern.length);

            if (reflection) {
                reflections.add(i + 1);
            }

        }

        return reflections;
    }

    protected void partTwo() {
        var sum = patterns.stream().mapToLong(this::locateSmudgeAndCount).sum();
        System.out.println("result: " + sum);
    }

    private long locateSmudgeAndCount(char[][] pattern) {
        var cachedReflection = cache.get(pattern);

        for (int y = 0; y < pattern.length; y++) {
            for (int x = 0; x < pattern[0].length; x++) {
                invertChar(pattern, x, y);

                var vertical = findVerticalReflectionLines(pattern);
                var horizontal = findHorizontalReflectionLines(pattern);

                for (var v : vertical) {
                    if (!("v" + v).equals(cachedReflection)) {
                        return v;
                    }
                }

                for (var h : horizontal) {
                    if (!("h" + h).equals(cachedReflection)) {
                        return h * 100L;
                    }
                }

                invertChar(pattern, x, y);
            }
        }

        throw new RuntimeException("no smudge found");
    }

    private void invertChar(char[][] pattern, int x, int y) {
        var c = pattern[y][x];
        var inverted = c == '#' ? '.' : '#';
        pattern[y][x] = inverted;
    }

}
