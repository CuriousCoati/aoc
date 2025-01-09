package de.breyer.aoc.y2024;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_11")
public class D11 extends AbstractAocPuzzle {

    private Map<Long, Long> stones;

    @Override
    protected void partOne() {
        parseInput(lines);
        blink(25);
        System.out.println("there are " + stones.values().stream().mapToLong(Long::longValue).sum() + " stones");
    }

    private void parseInput(List<String> input) {
        stones = new HashMap<>();
        var split = input.get(0).split(" ");
        for (var element : split) {
            var key = Long.parseLong(element);
            stones.put(key, stones.getOrDefault(key, 0L) + 1);
        }
    }

    private void blink(int times) {
        for (int i = 0; i < times; i++) {
            var next = new HashMap<Long, Long>();
            for (var entry : stones.entrySet()) {
                var stoneAsString = Long.toString(entry.getKey());
                if (entry.getKey() == 0) {
                    var newKey = 1L;
                    next.put(newKey, next.getOrDefault(newKey, 0L) + entry.getValue());
                } else if (stoneAsString.length() % 2 == 0) {
                    var firstHalf = Long.parseLong(stoneAsString.substring(0, stoneAsString.length() / 2));
                    next.put(firstHalf, next.getOrDefault(firstHalf, 0L) + entry.getValue());

                    var secondHalf = Long.parseLong(stoneAsString.substring(stoneAsString.length() / 2));
                    next.put(secondHalf, next.getOrDefault(secondHalf, 0L) + entry.getValue());
                } else {
                    {
                        var newKey = entry.getKey() * 2024;
                        next.put(newKey, next.getOrDefault(newKey, 0L) + entry.getValue());
                    }
                }
            }
            stones = next;
        }
    }

    @Override
    protected void partTwo() {
        blink(50);
        System.out.println("there are " + stones.values().stream().mapToLong(Long::longValue).sum() + " stones");
    }

}
