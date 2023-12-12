package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import lombok.Data;
import lombok.Getter;

@AocPuzzle("2023_12")
public class D12 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var records = parseInput(false);
        var sum = records.stream().mapToLong(this::countArrangements).sum();
        System.out.println("sum of possible arrangements: " + sum);
    }

    private List<ConditionRecord> parseInput(boolean unfold) {
        var records = new ArrayList<ConditionRecord>();

        for (var line : lines) {
            var split = line.split(" ");
            var report = unfold ? String.join("?", split[0], split[0], split[0], split[0], split[0]) : split[0];

            var record = new ConditionRecord(StringUtils.strip(report).replaceAll("\\.+", "."));
            records.add(record);

            var groupString = unfold ? String.join(",", split[1], split[1], split[1], split[1], split[1]) : split[1];

            for (var group : groupString.split(",")) {
                record.getGroups().add(Integer.parseInt(group));
            }
        }

        return records;
    }

    private long countArrangements(ConditionRecord record) {
        return calcArrangements(new SearchState(record), record.getReport(), new HashMap<>());
    }

    private long calcArrangements(SearchState state, String line, Map<String, Long> cache) {
        if (cache.containsKey(state.getKey())) {
            return cache.get(state.getKey());
        }

        if (state.getIndex() >= line.length()) {
            var result = state.getRemainingGroups().isEmpty() || (state.getRemainingGroups().size() == 1
                    && state.getRemainingGroups().get(0) == state.getIndexInGroup()) ? 1L : 0L;
            cache.put(state.getKey(), result);
            return result;
        }

        if (state.getRemainingGroups().isEmpty()) {
            var result = line.indexOf("#", state.getIndex()) == -1 ? 1L : 0L;
            cache.put(state.getKey(), result);
            return result;
        }

        var count = 0L;
        var c = line.charAt(state.getIndex());

        if ('.' == c) {
            count = handleNoSpring(state, line, cache);
        } else if ('#' == c) {
            count = handleSpring(state, line, cache);
        } else if ('?' == c) {
            count = handleSpring(state, line, cache);
            count += handleNoSpring(state, line, cache);
        }

        cache.put(state.getKey(), count);

        return count;
    }

    private long handleSpring(SearchState state, String line, Map<String, Long> cache) {
        if (state.getIndexInGroup() < state.getRemainingGroups().get(0)) {
            return calcArrangements(new SearchState(state, true, false), line, cache);
        }
        return 0L;
    }

    private long handleNoSpring(SearchState state, String line, Map<String, Long> cache) {
        if (state.getIndexInGroup() == 0) {
            return calcArrangements(new SearchState(state, false, false), line, cache);
        } else if (state.getIndexInGroup() == state.getRemainingGroups().get(0)) {
            return calcArrangements(new SearchState(state, false, true), line, cache);
        }
        return 0L;
    }

    protected void partTwo() {
        var records = parseInput(true);
        var sum = records.stream().mapToLong(this::countArrangements).sum();
        System.out.println("sum of possible arrangements: " + sum);
    }

    @Data
    private static class ConditionRecord {

        private final String report;
        private final List<Integer> groups = new ArrayList<>();
    }

    private static class SearchState {

        @Getter
        private final int index;
        @Getter
        private final int indexInGroup;
        @Getter
        private final List<Integer> remainingGroups = new ArrayList<>();

        public SearchState(ConditionRecord record) {
            this.index = 0;
            this.indexInGroup = 0;
            this.remainingGroups.addAll(record.getGroups());
        }

        public SearchState(SearchState state, boolean increaseIndexInGroup, boolean removeGroup) {
            this.index = state.getIndex() + 1;
            this.indexInGroup = increaseIndexInGroup ? state.getIndexInGroup() + 1 : 0;
            if (removeGroup) {
                state.getRemainingGroups().remove(0);
            }
            this.remainingGroups.addAll(state.getRemainingGroups());
        }

        public String getKey() {
            var groupString = remainingGroups.stream().map(String::valueOf).collect(Collectors.joining(","));
            return String.format("%d-%d-%s", index, indexInGroup, groupString);
        }

    }

}
