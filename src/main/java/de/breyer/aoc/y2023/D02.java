package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2023.cubegame.Game;
import de.breyer.aoc.y2023.cubegame.Round;

@AocPuzzle("2023_02")
public class D02 extends AbstractAocPuzzle {

    private static final List<String> TEST_INPUT = new ArrayList<>();
    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;

    static {
        TEST_INPUT.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        TEST_INPUT.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
        TEST_INPUT.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
        TEST_INPUT.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
        TEST_INPUT.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
    }

    @Override
    protected void partOne() {
        var games = parseInput(TEST_INPUT);
        games = games.stream().filter(game -> game.isValid(MAX_RED, MAX_GREEN, MAX_BLUE)).toList();
        var sum = games.stream().mapToInt(Game::getId).sum();
        System.out.println("sum of valid game IDs for test input: " + sum);

        games = parseInput(lines);
        games = games.stream().filter(game -> game.isValid(MAX_RED, MAX_GREEN, MAX_BLUE)).toList();
        sum = games.stream().mapToInt(Game::getId).sum();
        System.out.println("sum of valid game IDs for real input: " + sum);
    }

    private List<Game> parseInput(List<String> lines) {
        var games = new ArrayList<Game>();

        lines.forEach(line -> {
            var split = line.split(":");
            var game = new Game(Integer.parseInt(split[0].substring(5)));
            games.add(game);

            split = split[1].split(";");

            for (var round : split) {
                var roundSplit = round.split(",");
                int red = 0, green = 0, blue = 0;
                for (var color : roundSplit) {
                    var colorSplit = color.trim().split(" ");
                    switch (colorSplit[1]) {
                        case "red" -> red = Integer.parseInt(colorSplit[0]);
                        case "green" -> green = Integer.parseInt(colorSplit[0]);
                        case "blue" -> blue = Integer.parseInt(colorSplit[0]);
                    }
                }
                game.getRounds().add(new Round(red, green, blue));
            }

        });

        return games;
    }

    @Override
    protected void partTwo() {
        var games = parseInput(TEST_INPUT);
        var powerSum = games.stream().mapToLong(Game::calcPower).sum();
        System.out.println("sum of game power for test input: " + powerSum);

        games = parseInput(lines);
        powerSum = games.stream().mapToLong(Game::calcPower).sum();
        System.out.println("sum of game power for test input: " + powerSum);
    }

}
