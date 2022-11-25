package de.breyer.aoc.y2021;

import java.util.Objects;

public class GameStateD21 {

    private final int scorePlayerOne;
    private final int positionPlayerOne;
    private final int scorePlayerTwo;
    private final int positionPlayerTwo;
    private final boolean playerOneTurn;

    public int getScorePlayerOne() {
        return scorePlayerOne;
    }

    public int getPositionPlayerOne() {
        return positionPlayerOne;
    }

    public int getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    public int getPositionPlayerTwo() {
        return positionPlayerTwo;
    }

    public boolean isPlayerOneTurn() {
        return playerOneTurn;
    }

    public GameStateD21(int scorePlayerOne, int positionPlayerOne, int scorePlayerTwo, int positionPlayerTwo, boolean playerOneTurn) {
        this.scorePlayerOne = scorePlayerOne;
        this.positionPlayerOne = positionPlayerOne;
        this.scorePlayerTwo = scorePlayerTwo;
        this.positionPlayerTwo = positionPlayerTwo;
        this.playerOneTurn = playerOneTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameStateD21 gameState = (GameStateD21) o;
        return scorePlayerOne == gameState.scorePlayerOne && positionPlayerOne == gameState.positionPlayerOne
                && scorePlayerTwo == gameState.scorePlayerTwo && positionPlayerTwo == gameState.positionPlayerTwo
                && playerOneTurn == gameState.playerOneTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scorePlayerOne, positionPlayerOne, scorePlayerTwo, positionPlayerTwo, playerOneTurn);
    }
}
