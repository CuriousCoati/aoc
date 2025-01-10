package de.breyer.aoc.utils;

import java.util.List;

public class InputUtil {

    public static char[][] inputToCharMap(List<String> input) {
        var map = new char[input.size()][input.get(0).length()];

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(0).length(); x++) {
                map[y][x] = input.get(y).charAt(x);
            }
        }

        return map;
    }

}
