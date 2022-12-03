package de.breyer.aoc;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ListUtil {

    public static <T> Stream<List<T>> subGroups(List<T> list, int size) {
        return IntStream.range(0, list.size() / size).mapToObj(start -> list.subList(size * start, size * start + size));
    }

}
