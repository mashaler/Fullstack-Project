/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.service;

import com.mthree.guessthenumberrestservice.TestApplicationConfiguration;
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
public class ServiceImplTest {
    
    @Autowired
    Service testServ;
    
    public ServiceImplTest() {
    }
    
  

    /**
     * Test of guessCorrectSolution method, of class ServiceImpl.
     */
    @Test
    public void testGuessCorrectSolution() {
        
        String testGuess ="1234";
        String testSolution ="1679";
        
        String correctScore = "e:0:0:0";
        
        String sampleScore = " ";
        
        sampleScore = testServ.scoreGuess(testSolution, testGuess);
        
        assertEquals(correctScore, sampleScore);
               
    }
    @Test
    public void testGuessCorrectSolution2() {
        
        String testGuess ="1234";
        String testSolution ="1432";
        
        String correctScore = "e:p:e:p";
        
        String sampleScore = " ";
        
        sampleScore = testServ.scoreGuess(testSolution, testGuess);
        
        assertEquals(correctScore, sampleScore);
               
    }
    @Test
    public void testGuessCorrectSolution3() {
        
        String testGuess ="1234";
        String testSolution ="2567";

        String correctScore = "0:p:0:0";
        String sampleScore = " ";
        
        sampleScore = testServ.scoreGuess(testSolution, testGuess);
        
        assertEquals(correctScore, sampleScore);
               
    }
    
    
}
