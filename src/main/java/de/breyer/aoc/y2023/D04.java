package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2023.scratchcard.Scratchcard;

@AocPuzzle("2023_04")
public class D04 extends AbstractAocPuzzle {

    private int maxCardNumber = 0;

    @Override
    protected void partOne() {
        var cards = parseInput();
        var totalPoints = cards.stream().mapToLong(Scratchcard::calcPoints).sum();
        System.out.println("total points of scratchcards: " + totalPoints);
    }

    private List<Scratchcard> parseInput() {
        var scratchcards = new ArrayList<Scratchcard>();

        for (var line : lines) {
            var cardSplit = line.replaceAll(" +", " ").split("\\|");
            var firstSplit = cardSplit[0].trim().split(" ");
            var secondSplit = cardSplit[1].trim().split(" ");

            var number = Integer.parseInt(firstSplit[1].replaceAll(":", ""));
            maxCardNumber = Math.max(maxCardNumber, number);

            var card = new Scratchcard(number);
            for (int i = 2; i < firstSplit.length; i++) {
                card.getWinningNumbers().add(Integer.parseInt(firstSplit[i]));
            }
            for (var stringNumber : secondSplit) {
                card.getActualNumbers().add(Integer.parseInt(stringNumber));
            }
            scratchcards.add(card);
        }

        return scratchcards;
    }

    @Override
    protected void partTwo() {
        var cards = parseInput();
        var countMap = createCopiesAndCount(cards);
        var totalCards = countMap.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("total scratchcards: " + totalCards);
    }

    private Map<Integer, Long> createCopiesAndCount(List<Scratchcard> cards) {
        var countMap = new HashMap<Integer, Long>();
        for (var card : cards) {
            // increase count for original card
            countMap.put(card.getNumber(), countMap.getOrDefault(card.getNumber(), 0L) + 1L);

            var matchingNumberCount = card.getCountOfMatchingNumbers();
            for (int i = 0; i < matchingNumberCount; i++) {
                var nextNumber = card.getNumber() + i + 1;
                if (nextNumber > maxCardNumber) {
                    break;
                }
                countMap.put(nextNumber, countMap.getOrDefault(nextNumber, 0L) + countMap.get(card.getNumber()));
            }
        }
        return countMap;
    }

}
