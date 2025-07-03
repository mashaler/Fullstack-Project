/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.service;

import com.mthree.guessthenumberrestservice.controller.BadGuessFormatException;
import com.mthree.guessthenumberrestservice.controller.GameNotFoundException;
import com.mthree.guessthenumberrestservice.dto.Game;
import com.mthree.guessthenumberrestservice.dto.Round;
import java.util.List;

/**
 *
 * @author josep
 */

public interface Service {
    

    String scoreGuess(String correctSolution, String guessedSolution);
    
    List<Game> getAllGames();
    
    Game getGameById(int gameId);
    
    int startNewGame();
    
    List<Round> getAllRoundsByGameId(int gameId) throws GameNotFoundException;
    
    Round guessCorrectSolution(Round round) throws GameNotFoundException, BadGuessFormatException;
}
