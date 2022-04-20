package com.balibek.mastermind.data;

import com.balibek.mastermind.models.Game;
import com.balibek.mastermind.models.Round;
import java.util.List;

public interface RoundDao {
    
    //List<Round> getRoundsForGame(Game game);
    List<Round> getRoundsForGameById(int gameId);
    //Round getRoundByid(int roundId);
    Round addRound(String result, String guess, int gameId);
    //void updateRound(Round round);
    Round addRound1(Round round);
    
}
