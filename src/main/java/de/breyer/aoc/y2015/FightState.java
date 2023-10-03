package de.breyer.aoc.y2015;

import lombok.Getter;

public class FightState {

    @Getter
    private final RpgCharacter player;
    @Getter
    private final RpgCharacter boss;
    @Getter
    private final int manaCost;
    @Getter
    private final boolean playerTurn;

    public FightState(RpgCharacter player, RpgCharacter boss, int manaCost, boolean playerTurn) {
        this.player = player;
        this.boss = boss;
        this.manaCost = manaCost;
        this.playerTurn = playerTurn;
    }

    public FightState(FightState state) {
        this(state.player, state.boss, state.manaCost, !state.playerTurn);
    }
}
