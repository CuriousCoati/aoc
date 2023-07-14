package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_14")
public class D14 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var reindeerList = parseInput();
        var states = simulate(2503, reindeerList);
        printStates(states);
    }

    private List<Reindeer> parseInput() {
        List<Reindeer> reindeerList = new ArrayList<>();

        for (var line : lines) {
            var split = line.split(" ");
            reindeerList.add(new Reindeer(split[0], Integer.parseInt(split[3]), Integer.parseInt(split[6]), Integer.parseInt(split[13])));
        }

        return reindeerList;
    }

    private List<RaceState> simulate(int seconds, List<Reindeer> reindeerList) {
        List<RaceState> states = new ArrayList<>();
        for (var reindeer : reindeerList) {
            RaceState raceState = new RaceState(reindeer);
            states.add(raceState);

            int remainingSeconds = seconds;
            do {
                final int usedSeconds = Math.min(remainingSeconds, reindeer.getFlyTime());
                remainingSeconds -= usedSeconds;
                raceState.addDistance(usedSeconds * reindeer.getFlySpeed());

                remainingSeconds -= reindeer.getRestTime();
            } while (remainingSeconds > 0);
        }

        return states;
    }

    private void printStates(Collection<RaceState> states) {
        states.forEach(standing -> System.out.println(standing.getReindeer().getName() + " " + standing.getDistance() + " " + standing.getPoints()));
    }

    @Override
    protected void partTwo() {
        var reindeerList = parseInput();
        var states = simulateWithPoints(2503, reindeerList);
        printStates(states);
    }

    private Collection<RaceState> simulateWithPoints(int seconds, List<Reindeer> reindeerList) {
        Map<Reindeer, RaceState> states = new HashMap<>();

        for (int i = 0; i < seconds; i++) {
            reindeerList.forEach(reindeer -> {
                var state = states.getOrDefault(reindeer, new RaceState(reindeer));
                states.putIfAbsent(reindeer, state);

                if (state.getRemainingFlyTime() > 0) {
                    state.addDistance(reindeer.getFlySpeed());
                    state.reduceFlyTime();

                    if (state.getRemainingFlyTime() == 0) {
                        state.resetRestTime();
                    }
                } else {
                    state.reduceRestTime();

                    if (state.getRemainingRestTime() == 0) {
                        state.resetFlyTime();
                    }
                }
            });

            Integer maxDistance = states.values().stream().map(RaceState::getDistance).max(Integer::compare).orElseThrow();
            states.values().forEach(s -> {
                if (s.getDistance() == maxDistance) {
                    s.addPoint();
                }
            });
        }

        return states.values();
    }

}
