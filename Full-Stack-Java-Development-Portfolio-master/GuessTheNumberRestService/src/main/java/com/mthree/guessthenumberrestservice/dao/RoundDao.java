/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dao;

import com.mthree.guessthenumberrestservice.dto.Round;
import java.util.List;

/**
 *
 * @author josep
 */
public interface RoundDao {
    
    List<Round> getAllRoundsByGameId(int gameId);
    Round getRoundById(int roundId);
    Round addRound(Round round);
    public List<Round> getAllRounds();
    void deleteRoundById(int roundId);
    
}
