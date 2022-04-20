package com.balibek.mastermind.data;

import com.balibek.mastermind.models.Game;
import java.util.List;

public interface GameDao {
    
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(String answer, boolean inProgress);
    //void updateGame(Game game);
    //Game beginGame(String answer, boolean inProgress);
    //String getAnswerById()
    
}
