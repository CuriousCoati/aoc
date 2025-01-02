package de.breyer.aoc.y2024;

import java.util.regex.Pattern;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_03")
public class D03 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var sum = analyzeCorruptedMemoryAndRunUncorruptedMultiplyCommands();
        System.out.println("sum of multiplications: " + sum);
    }

    private int analyzeCorruptedMemoryAndRunUncorruptedMultiplyCommands() {
        var regex = "mul\\((\\d+),(\\d+)\\)";
        var pattern = Pattern.compile(regex);
        var sum = 0;

        for (var line : lines) {
            var matcher = pattern.matcher(line);

            while (matcher.find()) {
                sum += executeCommand(matcher.group());
            }
        }

        return sum;
    }

    private int executeCommand(String command) {
        var split = command.split(",");

        var n1 = Integer.parseInt(split[0].substring(4));
        var n2 = Integer.parseInt(split[1].substring(0, split[1].length() - 1));

        return n1 * n2;
    }

    @Override
    protected void partTwo() {
        var sum = analyzeCorruptedMemoryAndRunUncorruptedCommands();
        System.out.println("sum of multiplications: " + sum);
    }

    private int analyzeCorruptedMemoryAndRunUncorruptedCommands() {
        var regex = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";
        var pattern = Pattern.compile(regex);
        var sum = 0;
        var calculate = true;

        for (var line : lines) {
            var matcher = pattern.matcher(line);

            while (matcher.find()) {
                var find = matcher.group();

                if (find.startsWith("don't")) {
                    calculate = false;
                } else if (find.startsWith("do")) {
                    calculate = true;
                } else if (calculate) {
                    sum += executeCommand(matcher.group());
                }
            }
        }

        return sum;
    }
}
