/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dto;

import java.util.Objects;

/**
 *
 * @author josep
 */
public class Game {
    
    private int gameId;
    private String correctSolution;
    private boolean gameOver;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getCorrectSolution() {
        return correctSolution;
    }

    public void setCorrectSolution(String solutionNumber) {
        this.correctSolution = solutionNumber;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameStatus) {
        this.gameOver = gameStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.gameId;
        hash = 59 * hash + Objects.hashCode(this.correctSolution);
        hash = 59 * hash + (this.gameOver ? 1 : 0);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.gameOver != other.gameOver) {
            return false;
        }
        if (!Objects.equals(this.correctSolution, other.correctSolution)) {
            return false;
        }
        return true;
    }
    
    
}
