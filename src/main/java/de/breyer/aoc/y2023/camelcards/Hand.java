package de.breyer.aoc.y2023.camelcards;

import java.util.HashMap;
import java.util.Map.Entry;
import lombok.Data;

@Data
public class Hand implements Comparable<Hand> {

    private final String hand;
    private final int bid;
    private final int score;
    private final boolean activateJoker;

    public Hand(String hand, int bid, boolean activateJoker) {
        this.hand = hand;
        this.bid = bid;
        this.activateJoker = activateJoker;
        this.score = calcScore(hand);
    }

    private int calcScore(String hand) {
        var charMap = new HashMap<Character, Integer>();
        for (var c : hand.toCharArray()) {
            charMap.put(c, charMap.getOrDefault(c, 0) + 1);
        }

        if (activateJoker && charMap.containsKey('J')) {
            int jokerCount = charMap.remove('J');
            if (jokerCount == 5) {
                charMap.put('J', 5);
            } else if (jokerCount == 4 || jokerCount == 3) {
                var otherCard = charMap.keySet().iterator().next();
                charMap.put(otherCard, charMap.get(otherCard) + jokerCount);
            } else if (jokerCount == 2 || jokerCount == 1) {
                Entry<Character, Integer> highestEntry = null;
                for (var entry : charMap.entrySet()) {
                    if (highestEntry == null || entry.getValue() > highestEntry.getValue()) {
                        highestEntry = entry;
                    }
                }

                charMap.put(highestEntry.getKey(), highestEntry.getValue() + jokerCount);
            }
        }

        if (charMap.size() == 1) { // five of a kind
            return 7;
        } else if (charMap.size() == 2) {
            if (charMap.containsValue(4)) { // four of a kind
                return 6;
            } else { // full house
                return 5;
            }
        } else if (charMap.size() == 3) {
            if (charMap.containsValue(3)) { // three of a kind
                return 4;
            } else { // tow pair
                return 3;
            }
        } else if (charMap.size() == 4) { // one pair
            return 2;
        } else {
            return 1; // high card
        }
    }

    @Override
    public int compareTo(Hand o) {
        var diff = o.score - this.score;
        if (diff == 0) {
            return compareCards(o);
        } else {
            return diff;
        }
    }

    private int compareCards(Hand o) {
        for (int i = 0; i < hand.length(); i++) {
            if (hand.charAt(i) != o.hand.charAt(i)) {
                return getValueByCharacter(o.hand.charAt(i)) - getValueByCharacter(hand.charAt(i));
            }
        }
        return 0;
    }

    public int getValueByCharacter(char character) {
        return switch (character) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> activateJoker ? 1 : 11;
            case 'T' -> 10;
            case '9' -> 9;
            case '8' -> 8;
            case '7' -> 7;
            case '6' -> 6;
            case '5' -> 5;
            case '4' -> 4;
            case '3' -> 3;
            case '2' -> 2;
            default -> throw new IllegalArgumentException("Invalid character: " + character);
        };
    }
}
