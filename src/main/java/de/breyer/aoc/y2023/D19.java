package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_19")
public class D19 extends AbstractAocPuzzle {

    private HashMap<String, List<Rule>> workflows;
    private List<Part> parts;
    private List<Part> acceptedParts;
    private List<Range[]> acceptedRanges;

    @Override
    protected void partOne() {
        parseInput();
        simplifyWorkflows();
        inspectParts();
        var sum = acceptedParts.stream().mapToLong(Part::sum).sum();
        System.out.println("sum of accepted parts: " + sum);
    }

    private void parseInput() {
        workflows = new HashMap<>();
        parts = new ArrayList<>();

        boolean partsInputReached = false;

        for (var line : lines) {
            if (line.isEmpty()) {
                partsInputReached = true;
            } else {
                if (!partsInputReached) {
                    var name = line.substring(0, line.indexOf("{"));
                    var rules = new ArrayList<Rule>();
                    workflows.put(name, rules);

                    var split = line.substring(line.indexOf("{") + 1, line.length() - 1).split(",");
                    Arrays.stream(split).map(this::parseRule).forEach(rules::add);
                } else {
                    var split = line.substring(1, line.length() - 1).split(",");
                    var x = Integer.parseInt(split[0].substring(2));
                    var m = Integer.parseInt(split[1].substring(2));
                    var a = Integer.parseInt(split[2].substring(2));
                    var s = Integer.parseInt(split[3].substring(2));
                    parts.add(new Part(x, m, a, s));
                }
            }
        }
    }

    private void simplifyWorkflows() {
        var replaced = false;

        do {
            replaced = false;
            var replaceableWorkflows = new HashMap<String, String>();

            for (var workflow : workflows.entrySet()) {

                var replaceable = true;
                var lastTarget = workflow.getValue().get(0).target;

                for (int i = 1; i < workflow.getValue().size(); i++) {
                    if (!lastTarget.equals(workflow.getValue().get(i).target)) {
                        replaceable = false;
                        break;
                    }
                }

                if (replaceable) {
                    replaceableWorkflows.put(workflow.getKey(), lastTarget);
                }
            }

            if (!replaceableWorkflows.isEmpty()) {
                for (var ruleList : workflows.values()) {
                    ruleList.forEach(r -> r.replace(replaceableWorkflows));
                }
                replaceableWorkflows.keySet().forEach(workflows::remove);

                replaced = true;
            }
        } while (replaced);

    }

    private Rule parseRule(String ruleString) {
        if (ruleString.contains(":")) {
            var attribute = ruleString.charAt(0);
            var operator = ruleString.charAt(1);
            var number = Integer.parseInt(ruleString.substring(2, ruleString.indexOf(':')));
            var target = ruleString.substring(ruleString.indexOf(':') + 1);

            return new Rule(attribute, operator, number, target);
        } else {
            return new Rule(ruleString);
        }
    }

    private void inspectParts() {
        acceptedParts = new ArrayList<>();

        for (var part : parts) {

            var workflow = "in";
            do {
                var rules = workflows.get(workflow);
                workflow = null;

                for (var rule : rules) {
                    if (rule.check(part)) {
                        if ("R".equals(rule.target)) {
                            break;
                        } else if ("A".equals(rule.target)) {
                            acceptedParts.add(part);
                            break;
                        } else {
                            workflow = rule.target;
                            break;
                        }
                    }
                }
            } while (workflow != null);

        }
    }

    @Override
    protected void partTwo() {
        findDistinctCombinations();
        var combinations = acceptedRanges.stream().mapToLong(this::countCombinations).sum();
        System.out.println("number of combinations: " + combinations);
    }

    private void findDistinctCombinations() {
        acceptedRanges = new ArrayList<>();
        var ranges = new Range[]{new Range(1, 4000), new Range(1, 4000), new Range(1, 4000), new Range(1, 4000)};
        calcCombinationsInWorkflow(ranges, "in");
    }

    private void calcCombinationsInWorkflow(Range[] currentRanges, String workflowId) {
        var rules = workflows.get(workflowId);

        if (Arrays.stream(currentRanges).anyMatch(r -> !r.valid())) {
            return;
        }

        if (workflowId.equals("R")) {
            return;
        } else if (workflowId.equals("A")) {
            acceptedRanges.add(currentRanges);
            return;
        }

        for (var rule : rules) {
            if (rule.defaultRule) {
                calcCombinationsInWorkflow(currentRanges, rule.target);
                break;
            }

            var number = rule.number;

            var index = switch (rule.attribute) {
                case 'x' -> 0;
                case 'm' -> 1;
                case 'a' -> 2;
                case 's' -> 3;
                default -> throw new IllegalArgumentException("Unknown attribute: " + rule.attribute);
            };

            if ((rule.operator == '<' && currentRanges[index].bottom > number) ||
                    (rule.operator == '>' && currentRanges[index].top < number)) {
                continue;
            }

            var upperRanges = new Range[4];
            var lowerRanges = new Range[4];
            for (int i = 0; i < 4; i++) {
                if (index == i) {
                    upperRanges[i] = new Range(rule.operator == '<' ? number : number + 1, currentRanges[i].top);
                    lowerRanges[i] = new Range(currentRanges[i].bottom, rule.operator == '>' ? number : number - 1);
                } else {
                    upperRanges[i] = new Range(currentRanges[i].bottom, currentRanges[i].top);
                    lowerRanges[i] = new Range(currentRanges[i].bottom, currentRanges[i].top);
                }
            }

            if (rule.operator == '>') {
                calcCombinationsInWorkflow(upperRanges, rule.target);
                currentRanges = lowerRanges;
            } else {
                calcCombinationsInWorkflow(lowerRanges, rule.target);
                currentRanges = upperRanges;
            }

        }

    }

    private long countCombinations(Range[] ranges) {
        return ranges[0].count() * ranges[1].count() * ranges[2].count() * ranges[3].count();
    }

    private static class Rule {

        private final boolean defaultRule;
        private char attribute;
        private char operator;
        private int number;
        private String target;

        public Rule(char attribute, char operator, int number, String target) {
            this.defaultRule = false;
            this.attribute = attribute;
            this.operator = operator;
            this.number = number;
            this.target = target;
        }

        public Rule(String target) {
            this.defaultRule = true;
            this.target = target;
        }

        public boolean check(Part part) {
            if (defaultRule) {
                return true;
            }

            var value = switch (attribute) {
                case 'x' -> part.x;
                case 'm' -> part.m;
                case 'a' -> part.a;
                case 's' -> part.s;
                default -> 0;
            };

            if (operator == '>') {
                return value > number;
            } else {
                return value < number;
            }
        }

        public void replace(HashMap<String, String> replaceableWorkflows) {
            var replacement = replaceableWorkflows.get(target);
            if (null != replacement) {
                target = replacement;
            }
        }
    }

    private record Part(int x, int m, int a, int s) {

        public long sum() {
            return x + m + a + s;
        }

    }

    private record Range(int bottom, int top) {

        public boolean valid() {
            return bottom <= top;
        }

        public long count() {
            return top - bottom + 1;
        }
    }

}
