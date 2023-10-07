package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_16")
public class D16 extends AbstractAocPuzzle {

    private AuntSue giftData;
    private List<AuntSue> auntList;
    private List<AuntSue> matchingAunts;

    @Override
    protected void partOne() {
        fillGiftData();
        parseInput();
        findMatchingAunts();
        printResult();
    }

    private void fillGiftData() {
        giftData = AuntSue.builder()
                .children(3)
                .cats(7)
                .samoyeds(2)
                .pomeranians(3)
                .akitas(0)
                .vizslas(0)
                .goldfish(5)
                .trees(3)
                .cars(2)
                .perfumes(1)
                .build();
    }

    private void parseInput() {
        auntList = new ArrayList<>();

        for (String line : lines) {
            var split = line.replace(":", "").replace(",", "").split(" ");
            var builder = AuntSue.builder();
            builder.number(Integer.parseInt(split[1]));

            for (int i = 2; i < split.length - 1; i += 2) {
                switch (split[i]) {
                    case "children" -> builder.children(Integer.parseInt(split[i + 1]));
                    case "cats" -> builder.cats(Integer.parseInt(split[i + 1]));
                    case "samoyeds" -> builder.samoyeds(Integer.parseInt(split[i + 1]));
                    case "pomeranians" -> builder.pomeranians(Integer.parseInt(split[i + 1]));
                    case "akitas" -> builder.akitas(Integer.parseInt(split[i + 1]));
                    case "vizslas" -> builder.vizslas(Integer.parseInt(split[i + 1]));
                    case "goldfish" -> builder.goldfish(Integer.parseInt(split[i + 1]));
                    case "trees" -> builder.trees(Integer.parseInt(split[i + 1]));
                    case "cars" -> builder.cars(Integer.parseInt(split[i + 1]));
                    case "perfumes" -> builder.perfumes(Integer.parseInt(split[i + 1]));
                    default -> throw new IllegalArgumentException("unknown information about aunt sue '" + split[i] + "'");
                }
            }

            auntList.add(builder.build());
        }
    }

    private void findMatchingAunts() {
        matchingAunts = auntList.stream().filter(giftData::match).toList();
    }

    private void printResult() {
        System.out.println("matching aunts:");
        matchingAunts.forEach(System.out::println);
    }

    @Override
    protected void partTwo() {
        findMatchingAuntsTwo();
        printResult();
    }

    private void findMatchingAuntsTwo() {
        matchingAunts = auntList.stream().filter(giftData::matchTwo).toList();
    }

}
