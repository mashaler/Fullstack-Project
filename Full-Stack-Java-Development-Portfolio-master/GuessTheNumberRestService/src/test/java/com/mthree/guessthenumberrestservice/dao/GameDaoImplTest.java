/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dao;

import com.mthree.guessthenumberrestservice.TestApplicationConfiguration;
import com.mthree.guessthenumberrestservice.dto.Game;
import com.mthree.guessthenumberrestservice.dto.Round;
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
public class GameDaoImplTest {
    
    @Autowired
    GameDao testDao;
    
    @Autowired
    RoundDao roundDao;
    
  
    public GameDaoImplTest(){
        
    }
//    @Autowired
//    public GameDaoImplTest(GameDao testDao) {
//        
//        this.testDao = testDao;
//    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        List<Round> rounds = roundDao.getAllRounds();
        
        for(Round round : rounds){
            roundDao.deleteRoundById(round.getRoundId());
        }
        
        List<Game> games = testDao.getAllGames();
        
        for(Game game : games){
            testDao.deleteGameById(game.getGameId());
        }
        
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class GameDaoImpl.
     */
    @Test
    public void testAddGetGame() {
        
        Game game = new Game();
        game.setCorrectSolution("1234");
        
        testDao.addGame(game);
        
        Game gameBackFromDB = testDao.getGameById(game.getGameId());
        
        assertEquals(game, gameBackFromDB);
    }

    /**
     * Test of getAllGames method, of class GameDaoImpl.
     */
    @Test
    public void testGetAllGames() {
        
      Game game1 = new Game();
      game1.setCorrectSolution("4321");
      
      Game game2 = new Game();
      game2.setCorrectSolution("4311");
      
      testDao.addGame(game1);
      testDao.addGame(game2);
      
      List<Game> games = testDao.getAllGames();
      
      assertEquals(2, games.size());
      assertTrue(games.contains(game1));
      assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDaoImpl.
     */
    @Test
    public void testUpdateGetGame() {
        
      Game game1 = new Game();
      game1.setCorrectSolution("1234");
      testDao.addGame(game1);
      
      Game gameBackFromDB = testDao.getGameById(game1.getGameId());
      assertEquals(game1, gameBackFromDB);
      
      game1.setCorrectSolution("4321");
      testDao.updateGame(game1);
      assertNotEquals(game1, gameBackFromDB);
      
      gameBackFromDB = testDao.getGameById(game1.getGameId());
      assertEquals(game1, gameBackFromDB);
    }
    
}
