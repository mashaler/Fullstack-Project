/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.service;

import com.mthree.guessthenumberrestservice.controller.BadGuessFormatException;
import com.mthree.guessthenumberrestservice.controller.GameNotFoundException;
import com.mthree.guessthenumberrestservice.dao.GameDao;
import com.mthree.guessthenumberrestservice.dao.RoundDao;
import com.mthree.guessthenumberrestservice.dto.Game;
import com.mthree.guessthenumberrestservice.dto.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmlunit.util.Linqy;

/**
 *
 * @author josep
 */
@Component 
public class ServiceImpl implements Service{

    GameDao gameDao;
    RoundDao roundDao;
    
    //@Autowired
    public ServiceImpl(GameDao gameDao, RoundDao roundDao){
        
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    /**
     * Generate random number. 4 digits long. Cant start with 0
     * @return
     */
    public String generateCorrectSolution() {
        
        List<Integer> allNumbers = new ArrayList<>();
        allNumbers = Stream.of(0,1,2,3,4,5,6,7,8,9).collect(Collectors.toList());
        
        int[] randomNumbers = new int[4];
        do{
            Collections.shuffle(allNumbers);
            for(int i=0; i< 4; i++){
            randomNumbers[i] = allNumbers.get(i);
            }
        }while (randomNumbers[0] == 0);
        
        StringBuilder strNum = new StringBuilder();
        for(int number: randomNumbers){
            strNum.append(number);
        }
        
        String correctSolution = strNum.toString();
        return correctSolution;
    }

    /**
     * Score users guess. Return score as string
     * @param correctSolution
     * @param guessedSolution
     * @return
     */
    @Override
    public String scoreGuess(String correctSolution, String guessedSolution) {
        StringBuilder scoreBoard = new StringBuilder();
        
        for(int i=0 ; i<4; i++){
            if(guessedSolution.charAt(i) == correctSolution.charAt(i)){
                scoreBoard.append("e");
            }else if(correctSolution.contains(Character.toString(guessedSolution.charAt(i)))){
                
                scoreBoard.append("p");
            }else{
                scoreBoard.append("0");
            }
        }
        
        scoreBoard = scoreBoard.insert(1, ":");
        scoreBoard = scoreBoard.insert(3, ":");
        scoreBoard = scoreBoard.insert(5, ":");
        
        String scoreString = scoreBoard.toString();
        return scoreString;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        
        for(Game game : games){
            if(!game.getGameOver()){
                game.setCorrectSolution("Game's not over yet. No spoilers!");
            }
        }
        return games;
    }
    
    @Override
    public int startNewGame(){
        
        Game newGame = new Game();
        newGame.setCorrectSolution(generateCorrectSolution());
        newGame = gameDao.addGame(newGame);
        
        return newGame.getGameId();
    }

    @Override
    public Game getGameById(int gameId) {
        Game game = gameDao.getGameById(gameId);
        return game;
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) throws  GameNotFoundException{

        List<Round> allRoundsForGame = roundDao.getAllRoundsByGameId(gameId);

        if(allRoundsForGame.size() == 0){
            throw new GameNotFoundException("No guess have been made for this game.");
        }else{
            return allRoundsForGame;
        }

    }

    @Override
    public Round guessCorrectSolution(Round round) throws GameNotFoundException, BadGuessFormatException {
        Game currentGame = gameDao.getGameById(round.getGameId());
        if (currentGame == null){
            throw new GameNotFoundException("That Game does not exist");
        } else if(currentGame.getGameOver() == true){
            throw new GameNotFoundException("That game is already over. Please choose a different game.");
        }
        
        String correctSolution = currentGame.getCorrectSolution();
        String userGuess = round.getUserGuess();
        Set<Character> guessSet = new HashSet<Character>();
        for(int i =0 ; i<userGuess.length() ; i++){
            guessSet.add(userGuess.charAt(i));
        }
        
        if (guessSet.size() != 4 | userGuess.length()>4){
            throw new BadGuessFormatException("Please enter a four unique digits.");
        }else if(userGuess.charAt(0) == '0'){
            throw new BadGuessFormatException("Please don't start your number with '0'.");
        }
        
        String score = scoreGuess(correctSolution, userGuess);
        round.setResult(score);
        round.setTime(LocalDateTime.now());
        
        if(correctSolution.equals(userGuess)){
            currentGame.setGameOver(true);
            gameDao.updateGame(currentGame);
        }
        
        return roundDao.addRound(round);
    }
    
}
