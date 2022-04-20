package com.balibek.mastermind.data;

import com.balibek.mastermind.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoundDaoDB implements RoundDao {

    private final JdbcTemplate jdbc;
    //private Round round = new Round();

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rnd = new Round();
            rnd.setRoundId(rs.getInt("roundId"));
            rnd.setRoundTime(rs.getTimestamp("roundTime").toLocalDateTime());
            rnd.setGuess(rs.getString("guess"));
            rnd.setResult(rs.getString("result"));
            return rnd;
        }
    }

//    @Override
//    public List<Round> getRoundsForGame(Game game) {
//        final String SELECT_ROUND_FOR_GAME = "SELECT * FROM game g JOIN round r ON g.gameId=r.gameId WHERE g.gameId = ?"; //ORDER BY roundTime";
//        //List<Round> rounds = jdbc.query(SELECT_ROUND_FOR_GAME, new RoundMapper());//, gameId);
//        //addGameToRound(rounds);
//        return jdbc.query(SELECT_ROUND_FOR_GAME, new RoundMapper(), game.getGameId());
//    }

    @Override
    public List<Round> getRoundsForGameById(int gameId) {
        final String SELECT_ROUND_FOR_GAME = "SELECT * FROM game g JOIN round r ON g.gameId=r.gameId WHERE g.gameId = ? ORDER BY r.roundTime";
        return jdbc.query(SELECT_ROUND_FOR_GAME, new RoundMapper(), gameId);
    }

//    private void addGameToRound(List<Round> rounds) {
//        for (Round round1 : rounds) {
//            round1.setGame(getGameForRound(round1));
//        }
//    }

//    private Game getGameForRound(Round round) {
//        final String SELECT_ROUND_FOR_GAME = "SELECT r.* FROM game g JOIN round r ON g.gameId = r.gameId WHERE r.gameId = ?";
//        return jdbc.queryForObject(SELECT_ROUND_FOR_GAME, new GameMapper(),
//                round.getRoundId());
//    }

    @Override
    @Transactional
    public Round addRound(String result, String guess, int gameId) {
        Round round = new Round();
        round.setGuess(guess);
        round.setRoundTime(LocalDateTime.now());
        //round.setGame();
        final String sql = "INSERT INTO round(result, guess, roundTime, gameId) VALUES (?,?,?,?)";
        jdbc.update(sql,
                round.getResult(),
                round.getGuess(),
                round.getRoundTime(),
                round.getGame().getGameId());
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return round;
    }
    
    
    @Override
    public Round addRound1(Round round) {
        final String sql = "INSERT INTO round(result, guess, roundTime, gameId) VALUES (?,?,?,?)";
        jdbc.update(sql,
                round.getResult(),
                round.getGuess(),
                round.getRoundTime(),
                round.getGame().getGameId());
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return round; 
    }

}
