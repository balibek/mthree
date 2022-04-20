package com.balibek.mastermind.data;

import com.balibek.mastermind.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;
    

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setGameId(rs.getInt("gameId"));
            gm.setInProgress(rs.getBoolean("inProgress"));
            gm.setAnswer(rs.getString("answer"));
            return gm;
        }
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT gameId, inProgress FROM game;";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        final String SELECT_GAME_BY_ID = "SELECT gameId, inProgress, answer FROM game WHERE gameId = ?;";
        return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
    }

    @Override
    @Transactional
    public Game addGame(String answer, boolean inProgress) {
        Game game = new Game();
        game.setAnswer(answer);
        game.setInProgress(inProgress);
        final String sql = "INSERT INTO game(answer, inProgress) VALUES (?,?);";
        jdbc.update(sql,
                game.getAnswer(),
                game.isInProgress());
        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
    }

}
