package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_05")
public class D05 extends AbstractAocPuzzle {

    private final List<OrderRule> orderRules = new ArrayList<>();
    private final List<List<Integer>> updates = new ArrayList<>();
    private final List<List<Integer>> incorrectUpdates = new ArrayList<>();

    @Override
    protected void partOne() {
        parseInput();
        var sum = findAndSumValidOrders();
        System.out.println("sum: " + sum);
    }

    private void parseInput() {
        orderRules.clear();
        updates.clear();

        var rules = true;

        for (var line : lines) {
            if (line.isEmpty()) {
                rules = false;
            } else if (rules) {
                var split = line.split("\\|");
                orderRules.add(new OrderRule(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            } else {
                var split = line.split(",");
                updates.add(Arrays.stream(split).map(Integer::parseInt).toList());
            }
        }
    }

    private long findAndSumValidOrders() {
        incorrectUpdates.clear();
        var sum = 0L;

        for (var update : updates) {

            var valid = true;

            for (var rule : orderRules) {
                var firstIndex = update.indexOf(rule.first);
                var lastIndex = update.indexOf(rule.second);

                if (-1 != firstIndex && -1 != lastIndex && lastIndex < firstIndex) {
                    valid = false;
                    incorrectUpdates.add(update);
                    break;
                }

            }

            if (valid) {
                sum += update.get((int) Math.floor(update.size() / 2d));
            }
        }

        return sum;
    }

    @Override
    protected void partTwo() {
        var sum = orderAndSumIncorrectUpdates();
        System.out.println("sum: " + sum);
    }

    private long orderAndSumIncorrectUpdates() {
        var sum = 0L;

        for (var update : incorrectUpdates) {
            var copy = new ArrayList<>(update);

            var reorderd = false;

            do {
                reorderd = false;
                for (var rule : orderRules) {
                    var firstIndex = copy.indexOf(rule.first);
                    var lastIndex = copy.indexOf(rule.second);

                    if (-1 != firstIndex && -1 != lastIndex && lastIndex < firstIndex) {
                        var obj = copy.remove(firstIndex);
                        copy.add(lastIndex, obj);
                        reorderd = true;
                    }
                }
            } while (reorderd);

            sum += copy.get((int) Math.floor(update.size() / 2d));
        }

        return sum;
    }

    private record OrderRule(
            Integer first,
            Integer second
    ) {

    }

}
