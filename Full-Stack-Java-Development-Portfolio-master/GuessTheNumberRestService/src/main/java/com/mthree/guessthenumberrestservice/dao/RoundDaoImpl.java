/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.dao;

import com.mthree.guessthenumberrestservice.dto.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josep
 */
@Repository
public class RoundDaoImpl implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try{
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT *"
                    + " FROM ROUND"
                    + " WHERE gameId = ?"
                    + " ORDER BY timeOfGuess DESC;";
            
            List<Round> rounds =jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public Round getRoundById(int roundId) {
        try{
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round"
                    + " WHERE roundId = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
            
        }catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(gameId, userGuess, result, timeOfGuess) VALUES (?,?,?,?)";
        
        jdbc.update(INSERT_ROUND,
                round.getGameId(),
                round.getUserGuess(),
                round.getResult(),
                Timestamp.valueOf(round.getTime()));
        
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        
        return getRoundById(newRoundId);
    }
    
    @Override
    public List<Round> getAllRounds(){
        final String SELECT_ALL_ROUNDS = "SELECT * FROM Round;";
        
        return jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }
    
    
    @Override
    @Transactional
    public void deleteRoundById(int roundId){
        final String DELETE_ROUND = "DELETE FROM Round"
                + " WHERE roundId = ?";
        
        jdbc.update(DELETE_ROUND, roundId);
    }
    
    
    public static final class RoundMapper implements RowMapper<Round>{

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            round.setUserGuess(rs.getString("userGuess"));
            
            Timestamp time = rs.getTimestamp("timeOfGuess");
            round.setTime(time.toLocalDateTime());
            
            round.setResult(rs.getString("result"));
            return round;
        }
        
    }
}
