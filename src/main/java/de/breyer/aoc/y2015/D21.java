package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2015_21")
public class D21 extends AbstractAocPuzzle {

    private static final List<ShopItem> WEAPONS = new ArrayList<>();
    private static final List<ShopItem> ARMOR = new ArrayList<>();
    private static final List<ShopItem> RINGS = new ArrayList<>();

    static {
        WEAPONS.add(new ShopItem("Dagger", 8, 4, 0));
        WEAPONS.add(new ShopItem("Shortsword", 10, 5, 0));
        WEAPONS.add(new ShopItem("Warhammer", 25, 6, 0));
        WEAPONS.add(new ShopItem("Longsword", 40, 7, 0));
        WEAPONS.add(new ShopItem("Greataxe", 74, 8, 0));

        ARMOR.add(new ShopItem("Leather", 13, 0, 1));
        ARMOR.add(new ShopItem("Chainmail", 31, 0, 2));
        ARMOR.add(new ShopItem("Splintmail", 53, 0, 3));
        ARMOR.add(new ShopItem("Bandedmail", 75, 0, 4));
        ARMOR.add(new ShopItem("Platemail", 102, 0, 5));

        RINGS.add(new ShopItem("Damage +1", 25, 1, 0));
        RINGS.add(new ShopItem("Damage +2", 50, 2, 0));
        RINGS.add(new ShopItem("Damage +3", 100, 3, 0));
        RINGS.add(new ShopItem("Defense +1", 20, 0, 1));
        RINGS.add(new ShopItem("Defense +2", 40, 0, 2));
        RINGS.add(new ShopItem("Defense +3", 80, 0, 3));
    }

    @Override
    protected void partOne() {
        var lowestCost = findLowestCostToWinFight();
        System.out.println("lowest cost to win fight: " + lowestCost);
    }

    private int findLowestCostToWinFight() {
        var playerPermutations = createPlayerPermutations();
        int lowestCostToWin = Integer.MAX_VALUE;

        for (Pair<RpgCharacter, Integer> permutation : playerPermutations) {
            if (simulateFight(permutation.getFirst())) {
                lowestCostToWin = Math.min(lowestCostToWin, permutation.getSecond());
            }
        }

        return lowestCostToWin;
    }

    private List<Pair<RpgCharacter, Integer>> createPlayerPermutations() {
        var permutations = new ArrayList<Pair<RpgCharacter, Integer>>();

        // only weapon
        for (var weapon : WEAPONS) {
            permutations.add(createPlayer(weapon));
        }

        // weapon and armor
        for (var weapon : WEAPONS) {
            for (var armor : ARMOR) {
                permutations.add(createPlayer(weapon, armor));
            }
        }

        // weapon, armor and ring
        for (var weapon : WEAPONS) {
            for (var armor : ARMOR) {
                for (var ring : RINGS) {
                    permutations.add(createPlayer(weapon, armor, ring));
                }
            }
        }

        // weapon, armor and two rings
        for (var weapon : WEAPONS) {
            for (var armor : ARMOR) {
                for (int i = 0; i < RINGS.size() - 1; i++) {
                    for (int j = i + 1; j < RINGS.size(); j++) {
                        permutations.add(createPlayer(weapon, armor, RINGS.get(i), RINGS.get(j)));
                    }
                }
            }
        }

        // weapon and ring
        for (var weapon : WEAPONS) {
            for (var ring : RINGS) {
                permutations.add(createPlayer(weapon, ring));
            }
        }

        // weapon and two rings
        for (var weapon : WEAPONS) {
            for (int i = 0; i < RINGS.size() - 1; i++) {
                for (int j = i + 1; j < RINGS.size(); j++) {
                    permutations.add(createPlayer(weapon, RINGS.get(i), RINGS.get(j)));
                }
            }
        }

        return permutations;
    }

    private Pair<RpgCharacter, Integer> createPlayer(ShopItem... items) {
        var cost = 0;
        var damage = 0;
        var armor = 0;

        for (var item : items) {
            cost += item.cost();
            damage += item.damage();
            armor += item.armor();
        }

        return new Pair<>(new RpgCharacter(100, damage, armor), cost);
    }

    private RpgCharacter createBoss() {
        return new RpgCharacter(103, 9, 2);
    }

    private boolean simulateFight(RpgCharacter player) {
        var fighters = new ArrayList<RpgCharacter>();
        fighters.add(player);
        fighters.add(createBoss());

        do {
            var attacker = fighters.get(0);
            var defender = fighters.get(1);

            var damage = Math.max(attacker.getDamage() - defender.getArmor(), 1);
            defender.takeDamage(damage);

            fighters.clear();
            fighters.add(defender);
            fighters.add(attacker);
        } while (fighters.get(0).getHitPoints() > 0 && fighters.get(1).getHitPoints() > 0);

        return player.getHitPoints() > 0;
    }

    @Override
    protected void partTwo() {
        var highestCost = findHighestCostToLoseFight();
        System.out.println("highest cost to lose fight: " + highestCost);
    }

    private int findHighestCostToLoseFight() {
        var playerPermutations = createPlayerPermutations();
        int highestCostToLose = Integer.MIN_VALUE;

        for (Pair<RpgCharacter, Integer> permutation : playerPermutations) {
            if (!simulateFight(permutation.getFirst())) {
                highestCostToLose = Math.max(highestCostToLose, permutation.getSecond());
            }
        }

        return highestCostToLose;
    }
}
