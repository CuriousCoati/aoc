package de.breyer.aoc.y2023.cubegame;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Game {

    private final int id;
    private final List<Round> rounds = new ArrayList<>();

    public boolean isValid(int maxRed, int maxGreen, int maxBlue) {
        var valid = true;
        for (var round : rounds) {
            if (round.red() > maxRed || round.green() > maxGreen || round.blue() > maxBlue) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public long calcPower() {
        var minRed = rounds.stream().mapToInt(Round::red).max().orElse(1);
        var minGreen = rounds.stream().mapToInt(Round::green).max().orElse(1);
        var minBlue = rounds.stream().mapToInt(Round::blue).max().orElse(1);

        return (long) minRed * minGreen * minBlue;
    }
}
