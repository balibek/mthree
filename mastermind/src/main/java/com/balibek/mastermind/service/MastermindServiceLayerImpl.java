package com.balibek.mastermind.service;

import com.balibek.mastermind.data.GameDaoDB;
import com.balibek.mastermind.data.RoundDaoDB;
import com.balibek.mastermind.models.Game;
import com.balibek.mastermind.models.Round;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import static java.util.stream.Collectors.joining;
import org.springframework.stereotype.Service;

@Service
public class MastermindServiceLayerImpl implements MastermindServiceLayer {
    
    private final GameDaoDB gameDao;
    private final RoundDaoDB roundDao;
    
    public MastermindServiceLayerImpl(GameDaoDB game, RoundDaoDB round) {
        this.gameDao = game;
        this.roundDao = round;
    }
    
    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }
    
    @Override
    public Game getGameById(int gameId) {
        return gameDao.getGameById(gameId);
    }
    
    public static final String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    
    public String generationAnswer() {
        Random generator = new Random();
        StringBuilder answer = new StringBuilder();
        int index2 = 0;
        int index3 = 0;
        int index4 = 0;
        int index1 = generator.nextInt(numbers.length);
        do {
            index2 = generator.nextInt(numbers.length);
        } while (index1 == index2);
        do {
            index3 = generator.nextInt(numbers.length);
        } while (index3 == index1 || index3 == index2);
        do {
            index4 = generator.nextInt(numbers.length);
        } while (index4 == index1 || index4 == index2 || index4 == index3);
        return answer.append(index1).append(index2).append(index3).append(index4).toString();
    }
    
    public String generationAnswer1() {
        Random generator = new Random();
        HashSet<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            Integer digit = generator.nextInt(10);
            //if (!set.contains(digit)) {
                set.add(digit);
            //}
        }
        
        String answer = set.stream().map(String::valueOf).collect(joining());
        System.out.println("Nem answer generator: " + answer);
        //System.out.println("Nem answer generator: " + set.stream().map(x -> String.valueOf(x)).collect(joining()));
        return answer;
    }
    
    @Override
    public Game addGame() {
        return gameDao.addGame(generationAnswer1(), true);
    }
    
    @Override
    public List<Round> getAllRounds(int gameId) {
        return roundDao.getRoundsForGameById(gameId);
    }
    
    @Override
    public Round makeGuess(int gameId, String guess) {
        String answer = gameDao.getGameById(gameId).getAnswer();
        int exactMatch = 0;
        int partMatch = 0;
        
        String[] guessArr = guess.split("");
        String[] answerArr = answer.split("");
        
        for (String answerArr1 : answerArr) {
            for (String guessArr1 : guessArr) {
                if (guessArr1.contains(answerArr1)) {
                    partMatch++;
                }
            }
        }
        
        for (int i = 0; i < answer.length(); i++) {
            char c1 = answer.charAt(i);
            char c2 = guess.charAt(i);
            
            if (c1 == c2) {
                exactMatch++;
            }
        }
        
        String result = "e" + exactMatch + ":" + "p" + partMatch;
        
        if (exactMatch == 4) {
            gameDao.getGameById(gameId).setInProgress(false);
        }
        
        Round round = new Round();
        round.setGuess(guess);
        round.setResult(result);
        round.setRoundTime(LocalDateTime.now());
        round.setGame(getGameById(gameId));
        
        return roundDao.addRound1(round);
    }

//    @Override
//    public Round addRound(Round round) {
//        return roundDao.addRound(round);
//    }
}
