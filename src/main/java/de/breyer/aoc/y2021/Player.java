package de.breyer.aoc.y2021;

public class Player {

    private int score = 0;
    private int position = -1;

    public int getScore() {
        return score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addToScore(int score) {
        this.score += score;
    }

    public boolean isFinished() {
        return score >= 1000;
    }

    public void move(int moves) {
        position += moves;
        while (position > 10) {
            position = position - 10;
        }
        score += position;
    }
}
