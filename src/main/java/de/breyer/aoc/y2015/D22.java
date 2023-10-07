package de.breyer.aoc.y2015;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_22")
public class D22 extends AbstractAocPuzzle {

    private static final List<Spell> SPELLS = new ArrayList<>();

    static {
        SPELLS.add(new MagicMissile());
        SPELLS.add(new Drain());
        SPELLS.add(new Shield());
        SPELLS.add(new Poison());
        SPELLS.add(new Recharge());
    }

    @Override
    protected void partOne() {
        var lowestMana = findLowestManaToWinFight(false);
        System.out.println("lowest mana to win fight: " + lowestMana);
    }

    private int findLowestManaToWinFight(boolean hardMode) {
        int lowestManaCost = Integer.MAX_VALUE;
        Queue<FightState> states = new ArrayDeque<>();
        states.add(new FightState(createPlayer(), createBoss(), 0, true));

        do {
            FightState state = states.poll();

            if (hardMode && state.isPlayerTurn()) {
                state.getPlayer().takeDamage(1);
                if (state.getPlayer().isDead()) {
                    continue;
                }
            }

            state.getPlayer().applyEffects();
            state.getBoss().applyEffects();

            if (state.getBoss().isDead()) {
                lowestManaCost = Math.min(lowestManaCost, state.getManaCost());
            } else {
                if (state.isPlayerTurn()) {
                    var nextStates = playerTurn(state);

                    for (FightState nextState : nextStates) {
                        if (nextState.getBoss().isDead()) {
                            lowestManaCost = Math.min(lowestManaCost, nextState.getManaCost());
                        } else {
                            if (nextState.getManaCost() < lowestManaCost) {
                                states.add(nextState);
                            }
                        }
                    }
                } else {
                    bossTurn(state).ifPresent(states::add);
                }
            }
        } while (!states.isEmpty());

        return lowestManaCost;
    }

    private RpgCharacter createPlayer() {
        return new RpgCharacter(50, 0, 0, 500);
    }

    private RpgCharacter createBoss() {
        return new RpgCharacter(51, 9, 0, 0);
    }

    private Optional<FightState> bossTurn(FightState state) {
        var damage = Math.max(state.getBoss().getDamage() - state.getPlayer().getArmor(), 1);
        state.getPlayer().takeDamage(damage);

        if (state.getPlayer().isDead()) {
            return Optional.empty();
        } else {
            return Optional.of(new FightState(state));
        }
    }

    private List<FightState> playerTurn(FightState state) {
        var nextStates = new ArrayList<FightState>();

        for (Spell spell : SPELLS) {
            if (spell.canCast(state.getPlayer(), state.getBoss())) {
                var player = new RpgCharacter(state.getPlayer());
                var boss = new RpgCharacter(state.getBoss());

                var target = spell.getClass() == Shield.class || spell.getClass() == Recharge.class ? player : boss;
                spell.cast(player, target);

                nextStates.add(new FightState(player, boss, state.getManaCost() + spell.getManaCost(), false));
            }
        }

        return nextStates;
    }

    @Override
    protected void partTwo() {
        var lowestMana = findLowestManaToWinFight(true);
        System.out.println("lowest mana to win fight: " + lowestMana);
    }

}
