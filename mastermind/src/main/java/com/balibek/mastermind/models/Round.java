package com.balibek.mastermind.models;

import java.time.LocalDateTime;

public class Round {
    int roundId;
    String guess;
    String result;
    LocalDateTime roundTime;
    Game game;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(LocalDateTime roundTime) {
        this.roundTime = roundTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
}

