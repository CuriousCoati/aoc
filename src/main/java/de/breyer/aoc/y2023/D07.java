package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2023.camelcards.Hand;

@AocPuzzle("2023_07")
public class D07 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var hands = parseInput(false);
        Collections.sort(hands);
        var totalWinnings = calcTotalWinnings(hands);
        System.out.println("total winnings: " + totalWinnings);
    }

    private List<Hand> parseInput(boolean activateJoker) {
        var hands = new ArrayList<Hand>();

        for (var line : lines) {
            var split = line.split(" ");
            hands.add(new Hand(split[0], Integer.parseInt(split[1]), activateJoker));
        }

        return hands;
    }

    private long calcTotalWinnings(List<Hand> hands) {
        var totalWinnings = 0L;
        for (int i = 0; i < hands.size(); i++) {
            totalWinnings += (long) hands.get(i).getBid() * (hands.size() - i);
        }
        return totalWinnings;
    }

    @Override
    protected void partTwo() {
        var hands = parseInput(true);
        Collections.sort(hands);
        var totalWinnings = calcTotalWinnings(hands);
        System.out.println("total winnings: " + totalWinnings);
    }

}
