package de.breyer.aoc.y2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.utils.MathUtil;
import lombok.RequiredArgsConstructor;

@AocPuzzle("2023_20")
public class D20 extends AbstractAocPuzzle {

    private HashMap<String, Module> modules;
    private HashMap<String, List<Integer>> loopDetector;
    private long lowPulses;
    private long highPulses;
    private int countButtonPresses;

    @Override
    protected void partOne() {
        init();
        parseInput();
        initConjunctions();
        sendPulses(1000);
        System.out.println("low pulses: " + lowPulses);
        System.out.println("high pulses: " + highPulses);
        System.out.println("result: " + (lowPulses * highPulses));
    }

    private void init() {
        countButtonPresses = 0;
        lowPulses = 0L;
        highPulses = 0L;

        loopDetector = new HashMap<>();
        loopDetector.put("tr", new ArrayList<>());
        loopDetector.put("xm", new ArrayList<>());
        loopDetector.put("dr", new ArrayList<>());
        loopDetector.put("nh", new ArrayList<>());
    }

    private void parseInput() {
        modules = new HashMap<>();

        for (var line : lines) {
            var split = line.split(" -> ");

            var type = split[0].charAt(0);
            var name = type == 'b' ? split[0] : split[0].substring(1);
            var module = new Module(name, type);
            modules.put(name, module);

            module.targets.addAll(Arrays.asList(split[1].split(", ")));
        }
    }

    private void initConjunctions() {
        modules.values().forEach(m ->
                m.targets.forEach(t -> {
                    var tModule = modules.get(t);
                    if (null != tModule) {
                        tModule.conjunctionMemory.put(m.name, false);
                    }
                })
        );
    }

    private void sendPulses(int buttonPresses) {
        for (int i = 0; i < buttonPresses; i++) {
            countButtonPresses++;
            pressButton();

            if (loopDetector.values().stream().allMatch(l -> l.size() >= 3)) {
                break;
            }
        }
    }

    private void pressButton() {
        var queue = new ArrayDeque<PulseSignal>();
        queue.add(new PulseSignal("button", false, "broadcaster"));

        do {
            var current = queue.poll();
            var pulse = current.pulse();

            if (pulse) {
                highPulses++;
            } else {
                lowPulses++;
            }

            if (current.receiver.equals("dh") && pulse) {
                loopDetector.get(current.sender()).add(countButtonPresses);
            }

            var module = modules.get(current.receiver());
            if (null != module) {
                queue.addAll(module.send(current.sender, pulse));
            }
        } while (!queue.isEmpty());
    }

    @Override
    protected void partTwo() {
        sendPulses(Integer.MAX_VALUE);

        var loops = new ArrayList<Integer>();

        for (var key : List.of("tr", "xm", "dr", "nh")) {
            var list = loopDetector.get(key);
            if (list.get(1) - list.get(0) == list.get(2) - list.get(1)) {
                loops.add(list.get(1) - list.get(0));
            } else {
                throw new RuntimeException("no loop found");
            }
        }

        var lcm = MathUtil.calculateLCM(loops);

        System.out.println("button presses to trigger rx: " + lcm);
    }

    private record PulseSignal(String sender, boolean pulse, String receiver) {

    }

    @RequiredArgsConstructor
    private static class Module {

        private final String name;
        private final char type;
        private final List<String> targets = new ArrayList<>();
        private boolean flipFlopState = false;
        private final HashMap<String, Boolean> conjunctionMemory = new HashMap<>();

        public List<PulseSignal> send(String sender, boolean pulse) {
            return switch (type) {
                case 'b' -> broadCastLogic(pulse);
                case '%' -> flipFlopLogic(pulse);
                case '&' -> conjunctionLogic(sender, pulse);
                default -> throw new IllegalArgumentException("Unknown type: " + type);
            };
        }

        private List<PulseSignal> broadCastLogic(boolean pulse) {
            var result = new ArrayList<PulseSignal>();
            targets.forEach(target -> result.add(new PulseSignal(name, pulse, target)));
            return result;
        }

        private List<PulseSignal> flipFlopLogic(boolean pulse) {
            var result = new ArrayList<PulseSignal>();
            if (!pulse) {
                flipFlopState = !flipFlopState;
                targets.forEach(target -> result.add(new PulseSignal(name, flipFlopState, target)));
            }
            return result;
        }

        private List<PulseSignal> conjunctionLogic(String sender, boolean pulse) {
            var result = new ArrayList<PulseSignal>();
            conjunctionMemory.put(sender, pulse);
            boolean newPulse = conjunctionMemory.values().stream().anyMatch(Boolean.FALSE::equals);
            targets.forEach(target -> result.add(new PulseSignal(name, newPulse, target)));
            return result;
        }

    }

}
