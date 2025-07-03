/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dao;

import com.mthree.guessthenumberrestservice.TestApplicationConfiguration;
import com.mthree.guessthenumberrestservice.dto.Game;
import com.mthree.guessthenumberrestservice.dto.Round;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author josep
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoImplTest {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    public RoundDaoImplTest() {
    }
    
   
    @Before
    public void setUp() {
        List<Round> rounds = roundDao.getAllRounds();
        
        for(Round round : rounds){
            roundDao.deleteRoundById(round.getRoundId());
        }
        
        List<Game> games = gameDao.getAllGames();
        
        for(Game game : games){
            gameDao.deleteGameById(game.getGameId());
        }
    }
    
   
    /**
     * Test of getAllRoundsByGameId method, of class RoundDaoImpl.
     */
    @Test
    public void testAddGetAllRoundsByGameId() {
        Game testGame = new Game();
        
        testGame.setCorrectSolution("1234");
        testGame.setGameOver(false);
        gameDao.addGame(testGame);
        
        Round testRound1 = new Round();
        testRound1.setGameId(testGame.getGameId());
        testRound1.setUserGuess("3214");
        testRound1.setTime(LocalDateTime.now());
        testRound1.setResult("RESULT");
        testRound1 = roundDao.addRound(testRound1);
        
        Round testRound2 = new Round();
        testRound2.setGameId(testGame.getGameId());
        testRound2.setUserGuess("4567");
        testRound2.setTime(LocalDateTime.now());
        testRound2.setResult("RESULT");
        testRound2 = roundDao.addRound(testRound2);
        
        List<Round> rounds = roundDao.getAllRoundsByGameId(testGame.getGameId());
        
        assertEquals(2, rounds.size());
       // assertNotNull(testRound1 = roundDao.getRoundById(testRound1.getRoundId()));
        //assertNotNull(testRound2 = roundDao.getRoundById(testRound2.getRoundId()));

        assertTrue(rounds.contains(testRound1));
        assertTrue(rounds.contains(testRound2));
    }

    /**
     * Test of getRoundById method, of class RoundDaoImpl.
     */
    @Test
    public void testGetRoundById() {
        Game testGame = new Game();
        
        testGame.setCorrectSolution("1234");
        testGame.setGameOver(false);
        gameDao.addGame(testGame);
        
        Round testRound1 = new Round();
        testRound1.setGameId(testGame.getGameId());
        testRound1.setUserGuess("3214");
        testRound1.setTime(LocalDateTime.now());
        testRound1.setResult("RESULT");
        testRound1 = roundDao.addRound(testRound1);
        
        Round roundBackFromDao = roundDao.getRoundById(testRound1.getRoundId());
        
        assertEquals(testRound1, roundBackFromDao);
    }
}
