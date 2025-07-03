/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dao;

import com.mthree.guessthenumberrestservice.dto.Game;
import java.util.List;

/**
 *
 * @author josep
 */
public interface GameDao {
    
    List<Game> getAllGames();
    
    Game addGame(Game game);
    
    Game getGameById(int gameId);
    
    void updateGame(Game round);
    
    void deleteGameById(int gameId);
}
