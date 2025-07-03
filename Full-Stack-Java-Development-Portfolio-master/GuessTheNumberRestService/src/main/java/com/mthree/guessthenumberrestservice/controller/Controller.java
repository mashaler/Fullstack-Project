package com.mthree.guessthenumberrestservice.controller;

import com.mthree.guessthenumberrestservice.dto.Game;
import com.mthree.guessthenumberrestservice.dto.Round;
import com.mthree.guessthenumberrestservice.service.Service;
import com.mthree.guessthenumberrestservice.service.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author josep
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {

    //Attempt at using spring annotation. Not immediately obvious how it is applied in this context
//    ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
//
//    Service serv = appContext.getBean(ServiceImpl.class);

    //@Autowired
    Service serv;

    @Autowired
    public Controller(Service serv){
        this.serv = serv;

    }

    /**
     * Create new game
     * @return
     */
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame(){
        return serv.startNewGame();
    }

    /**
     * List all games
     * @return
     */
    @GetMapping("/game")
    public List<Game> getAllGames(){
        return serv.getAllGames();
    }

    /**
     * Get details of specific game
     * @param gameId
     * @return
     */
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable("gameId") int gameId){
        Game currentGame = serv.getGameById(gameId);

        //little bit of practice throwing exceptions
        if (currentGame == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }else if(!currentGame.getGameOver()){
            currentGame.setCorrectSolution("Game's not over yet. No spoilers!");
        }
        return ResponseEntity.ok(currentGame);
    }

    /**
     * Allow user to make guess
     * @param round
     * @return
     */
    @PostMapping("/guess")
    public ResponseEntity<Round> guessCorrectSolution(@RequestBody Round round) {
        
        try{
            Round currentRound = serv.guessCorrectSolution(round);
            return new ResponseEntity(currentRound, HttpStatus.CREATED);
        }catch(GameNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch(BadGuessFormatException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
            
    }

    /**
     * return list of all rounds associated with a specific game
     * @param gameId
     * @return
     */
    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> getAllRoundsFromGameId(@PathVariable("gameId") int gameId) throws GameNotFoundException{

        try{
            return new ResponseEntity(serv.getAllRoundsByGameId(gameId),HttpStatus.CREATED);
        }catch (GameNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}
