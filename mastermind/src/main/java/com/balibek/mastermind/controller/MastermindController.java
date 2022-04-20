package com.balibek.mastermind.controller;

import com.balibek.mastermind.models.Game;
import com.balibek.mastermind.models.Round;
import com.balibek.mastermind.service.MastermindServiceLayer;
import java.util.List;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mastermind")
public class MastermindController {
    
    private final MastermindServiceLayer service;

    public MastermindController(MastermindServiceLayer service) {
        this.service = service;
    }
    
    @GetMapping
    public List<Game> allGames() {
        return service.getAllGames(); 
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game begin() {
        System.out.println("Begin Post");
        return service.addGame(); 
        
    }
    
    @PostMapping("/guess")
    //@ResponseStatus(HttpStatus.CREATED)
    public Round guess(@RequestBody GuessRequestBody body) {
        System.out.println("GuessEx game id " + body.getGameId() + ", guess " + body.getGuess());
        return service.makeGuess(body.getGameId(), body.getGuess()); 
    }
    private static final class GuessRequestBody {
        private int gameId;
        private String guess;

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public String getGuess() {
            return guess;
        }

        public void setGuess(String guess) {
            this.guess = guess;
        }  
    }
   
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) {
        Game result = service.getGameById(gameId);
        if(result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/round/{gameId}")
    public ResponseEntity<List<Round>> findRoundById(@PathVariable int gameId) {
        List<Round> result = service.getAllRounds(gameId);
        if(result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
