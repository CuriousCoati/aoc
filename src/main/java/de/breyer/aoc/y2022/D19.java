package de.breyer.aoc.y2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_19")
public class D19 extends AbstractAocPuzzle {

    private static final String INPUT_PATTERN = "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.";

    @Override
    protected void partOne() {
        List<Blueprint> blueprints = parseInput();
        blueprints.forEach(blueprint -> simulate(blueprint, 24));
        int sumQualityLevels = blueprints.stream().map(Blueprint::calcQualityLevel).reduce(0, Integer::sum);
        System.out.println("sum quality levels: " + sumQualityLevels);
    }

    private List<Blueprint> parseInput() {
        List<Blueprint> blueprints = new ArrayList<>();
        Pattern pattern = Pattern.compile(INPUT_PATTERN);

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int idx = Integer.parseInt(matcher.group(1));
                int orOreCost = Integer.parseInt(matcher.group(2));
                int crOreCost = Integer.parseInt(matcher.group(3));
                int obrOreCost = Integer.parseInt(matcher.group(4));
                int obrClayCost = Integer.parseInt(matcher.group(5));
                int grOreCost = Integer.parseInt(matcher.group(6));
                int grObsidianCost = Integer.parseInt(matcher.group(7));

                Blueprint blueprint = new Blueprint(idx, orOreCost, crOreCost, obrOreCost, obrClayCost, grOreCost, grObsidianCost);
                blueprints.add(blueprint);
            }
        }
        return blueprints;
    }

    private void simulate(Blueprint blueprint, int minutes) {
        Queue<MiningState> statesInMinute = new ArrayDeque<>();
        statesInMinute.add(new MiningState());

        int currentMaxGeodes = 0;

        for (int i = 0; i < minutes; i++) {
            Set<MiningState> statesInNextMinute = new HashSet<>();
            while (!statesInMinute.isEmpty()) {
                MiningState state = statesInMinute.poll();

                if (state.getPossibleGeodes(minutes, i) < currentMaxGeodes) {
                    continue;
                }

                boolean canBuildGeodeBot = state.canBuildGeodeRobot(blueprint);
                boolean canBuildObsidianBot = state.canBuildObsidianRobot(blueprint);
                boolean canBuildClayBot = state.canBuildClayRobot(blueprint);
                boolean canBuildOreBot = state.canBuildOreRobot(blueprint);

                state.increaseResources();

                if (i == minutes - 1) {
                    statesInNextMinute.add(state);
                } else if (i > minutes - 4) {
                    if (canBuildGeodeBot) {
                        statesInNextMinute.add(state.buildGeodeRobot(blueprint));
                    } else {
                        statesInNextMinute.add(state);
                    }
                } else if (i > minutes - 6) {
                    if (canBuildGeodeBot) {
                        statesInNextMinute.add(state.buildGeodeRobot(blueprint));
                    } else {
                        statesInNextMinute.add(state);

                        if (canBuildObsidianBot && state.moreObsidianBotsUseful(blueprint)) {
                            statesInNextMinute.add(state.buildObsidianRobot(blueprint));
                        }

                        if (canBuildOreBot && state.moreOreBotsUseful(blueprint)) {
                            statesInNextMinute.add(state.buildOreRobot(blueprint));
                        }
                    }
                } else {
                    if (canBuildGeodeBot) {
                        statesInNextMinute.add(state.buildGeodeRobot(blueprint));
                    } else {
                        statesInNextMinute.add(state);

                        if (canBuildObsidianBot && state.moreObsidianBotsUseful(blueprint)) {
                            statesInNextMinute.add(state.buildObsidianRobot(blueprint));
                        }

                        if (canBuildClayBot && state.moreClayBotsUseful(blueprint)) {
                            statesInNextMinute.add(state.buildClayRobot(blueprint));
                        }

                        if (canBuildOreBot && state.moreOreBotsUseful(blueprint)) {
                            statesInNextMinute.add(state.buildOreRobot(blueprint));
                        }
                    }
                }
            }

            currentMaxGeodes = statesInNextMinute.stream().map(MiningState::getGeodeCount).max(Integer::compareTo).orElse(0);
            statesInMinute.addAll(statesInNextMinute);
        }

        blueprint.setMaxGeodes(currentMaxGeodes);
    }

    @Override
    protected void partTwo() {
        List<Blueprint> blueprints = parseInput();
        blueprints = blueprints.subList(0, Math.min(blueprints.size(), 3));
        blueprints.forEach(blueprint -> simulate(blueprint, 32));
        int result = blueprints.stream().map(Blueprint::getMaxGeodes).reduce(1, (a, b) -> a * b);
        System.out.println("result: " + result);
    }

}
