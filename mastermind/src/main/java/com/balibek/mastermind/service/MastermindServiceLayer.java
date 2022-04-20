package com.balibek.mastermind.service;

import com.balibek.mastermind.models.Game;
import com.balibek.mastermind.models.Round;
import java.util.List;

public interface MastermindServiceLayer {
    
     List<Game> getAllGames();
     Game getGameById(int gameId);
     Game addGame();
     
     List<Round> getAllRounds(int gameId);
     Round makeGuess(int gameId, String guess);
    
}
