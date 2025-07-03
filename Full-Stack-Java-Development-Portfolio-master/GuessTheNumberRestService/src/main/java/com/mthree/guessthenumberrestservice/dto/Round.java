/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author josep
 */
public class Round {
    
    private int roundId;
    private int gameId;
    private String userGuess;
    private LocalDateTime timeOfGuess;
    private String result;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserGuess() {
        return userGuess;
    }

    public void setUserGuess(String userGuess) {
        this.userGuess = userGuess;
    }

    public LocalDateTime getTime() {
        return timeOfGuess;
    }

    public void setTime(LocalDateTime time) {
        this.timeOfGuess = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.roundId;
        hash = 11 * hash + this.gameId;
        hash = 11 * hash + Objects.hashCode(this.userGuess);
        hash = 11 * hash + Objects.hashCode(this.timeOfGuess);
        hash = 11 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.userGuess, other.userGuess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.timeOfGuess, other.timeOfGuess)) {
            return false;
        }
        return true;
    }
    
    
}
