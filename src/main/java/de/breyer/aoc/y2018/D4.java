package de.breyer.aoc.y2018;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2018_04")
public class D4 extends AbstractAocPuzzle {

    private Map<String, Guard> guards = null;

    @Override
    protected void partOne() {
        parseInput();
        var guardMostAsleep = findGuardMostAsleep();
        System.out.println(guardMostAsleep);
    }

    private void parseInput() {
        if (null != guards) {
            return;
        }

        var sortedLines = sortInput();
        guards = new HashMap<String, Guard>();
        Guard currentGuard = null;
        int sleepStart = 0;

        for (var line : sortedLines) {
            if (line.contains("Guard")) {
                var id = line.split(" ")[3];
                currentGuard = guards.get(id);
                if (null == currentGuard) {
                    currentGuard = new Guard(id);
                    guards.put(id, currentGuard);
                }
            } else if (line.contains("falls asleep")) {
                sleepStart = parseDateTime(line).getMinute();
            } else if (line.contains("wakes up")) {
                currentGuard.addSleepTime(sleepStart, parseDateTime(line).getMinute());
            }
        }

    }

    private Guard findGuardMostAsleep() {
        Guard guardMostAsleep = null;
        for (var guard : guards.values()) {
            if (guardMostAsleep == null) {
                guardMostAsleep = guard;
            } else if (guardMostAsleep.getTotalSleepTime() < guard.getTotalSleepTime()) {
                guardMostAsleep = guard;
            }
        }
        return guardMostAsleep;
    }

    private List<String> sortInput() {
        var inputLines = new ArrayList<>(lines);
        inputLines.sort((a, b) -> {
            var dateA = parseDateTime(a);
            var dateB = parseDateTime(b);
            return dateA.compareTo(dateB);
        });
        return inputLines;
    }

    private LocalDateTime parseDateTime(String line) {
        return LocalDateTime.parse(line.substring(1, 17), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    protected void partTwo() {
        var guardMostAsleep = findGuardMostAsleepAtAnyMinute();
        System.out.println(guardMostAsleep);
    }

    private Guard findGuardMostAsleepAtAnyMinute() {
        Guard guardMostAsleep = null;
        for (var guard : guards.values()) {
            if (guardMostAsleep == null) {
                guardMostAsleep = guard;
            } else if (guardMostAsleep.daysSleptAtMinute(guardMostAsleep.mostAsleepMinute()) < guard.daysSleptAtMinute(guard.mostAsleepMinute())) {
                guardMostAsleep = guard;
            }
        }
        return guardMostAsleep;
    }

}
