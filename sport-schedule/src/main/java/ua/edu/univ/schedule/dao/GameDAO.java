package ua.edu.univ.schedule.dao;


import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.GameResult;
import ua.edu.univ.schedule.model.Team;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class GameDAO implements IGameDAO {

    @Resource(lookup = "java:app/jdbc/myScheduleDB")
    private DataSource dataSource;


    private static final String SQL_SELECT_TEAM_BY_NAME = "SELECT id FROM teams WHERE name = ?";
    private static final String SQL_INSERT_TEAM = "INSERT INTO teams (name) VALUES (?)";
    private static final String SQL_INSERT_GAME = "INSERT INTO games (home_team_id, away_team_id, game_datetime, home_score, away_score, is_completed) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_BASE_SELECT_GAMES =
            "SELECT g.id, g.game_datetime, g.home_score, g.away_score, g.is_completed, " +
                    "ht.id as h_id, ht.name as h_name, at.id as a_id, at.name as a_name " +
                    "FROM games g " +
                    "JOIN teams ht ON g.home_team_id = ht.id " +
                    "JOIN teams at ON g.away_team_id = at.id";

    private static final String SQL_SELECT_GAME_BY_ID = SQL_BASE_SELECT_GAMES + " WHERE g.id = ?";
    private static final String SQL_SELECT_ALL_GAMES = SQL_BASE_SELECT_GAMES;

    private static final String SQL_UPDATE_GAME = "UPDATE games SET home_team_id = ?, away_team_id = ?, game_datetime = ?, home_score = ?, away_score = ?, is_completed = ? WHERE id = ?";
    private static final String SQL_DELETE_GAME = "DELETE FROM games WHERE id = ?";

    private static final String SQL_SEARCH_GAMES_BY_TEAM = SQL_BASE_SELECT_GAMES + " WHERE ht.name LIKE ? OR at.name LIKE ?";
    private static final String SQL_SELECT_PAGED_GAMES = SQL_BASE_SELECT_GAMES + " WHERE (? IS NULL OR ht.name LIKE ? OR at.name LIKE ?) ORDER BY g.game_datetime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private Team getOrCreateTeam(Connection conn, String teamName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_TEAM_BY_NAME)) {
            stmt.setString(1, teamName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Team(rs.getLong("id"), teamName);
                }
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_TEAM, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, teamName);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Team(rs.getLong(1), teamName);
                }
            }
        }
        throw new SQLException("Failed to create team: " + teamName);
    }

    @Override
    public Game create(Game game) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_GAME, Statement.RETURN_GENERATED_KEYS)) {

            prepareTeams(conn, game);
            setGameStatementParams(stmt, game);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    game.setId(generatedKeys.getLong(1));
                }
            }
            return game;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating game", e);
        }
    }

    @Override
    public Optional<Game> read(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_GAME_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToGame(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading game", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Game> readAll() {
        return executeQuery(SQL_SELECT_ALL_GAMES);
    }

    @Override
    public boolean update(Game game) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_GAME)) {

            prepareTeams(conn, game);
            setGameStatementParams(stmt, game);

            stmt.setLong(7, game.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating game", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_GAME)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting game", e);
        }
    }

    @Override
    public List<Game> searchByTeamName(String teamName) {
        List<Game> games = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SEARCH_GAMES_BY_TEAM)) {
            String searchPattern = "%" + teamName + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    games.add(mapResultSetToGame(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching games", e);
        }
        return games;
    }

    @Override
    public List<Game> getPaged(int limit, int offset, String teamName) {
        List<Game> games = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PAGED_GAMES)) {
            if (teamName == null || teamName.trim().isEmpty()) {
                stmt.setNull(1, Types.VARCHAR);
                stmt.setNull(2, Types.VARCHAR);
                stmt.setNull(3, Types.VARCHAR);
            } else {
                String searchPattern = "%" + teamName + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
            }
            stmt.setInt(4, offset);
            stmt.setInt(5, limit);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    games.add(mapResultSetToGame(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting paged games", e);
        }
        return games;
    }

    private List<Game> executeQuery(String sql) {
        List<Game> games = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                games.add(mapResultSetToGame(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return games;
    }

    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Team homeTeam = new Team(rs.getLong("h_id"), rs.getString("h_name"));
        Team awayTeam = new Team(rs.getLong("a_id"), rs.getString("a_name"));
        GameResult result = new GameResult(
                rs.getInt("home_score"),
                rs.getInt("away_score"),
                rs.getBoolean("is_completed")
        );
        return new Game(
                rs.getLong("id"),
                homeTeam,
                awayTeam,
                rs.getTimestamp("game_datetime").toLocalDateTime(),
                result
        );
    }

    private void prepareTeams(Connection conn, Game game) throws SQLException {
        Team home = getOrCreateTeam(conn, game.getHomeTeam().getName());
        Team away = getOrCreateTeam(conn, game.getAwayTeam().getName());

        game.setHomeTeam(home);
        game.setAwayTeam(away);
    }

    private void setGameStatementParams(PreparedStatement stmt, Game game) throws SQLException {
        stmt.setLong(1, game.getHomeTeam().getId());
        stmt.setLong(2, game.getAwayTeam().getId());
        stmt.setTimestamp(3, Timestamp.valueOf(game.getDateTime()));
        stmt.setInt(4, game.getResult().getHomeScore());
        stmt.setInt(5, game.getResult().getAwayScore());
        stmt.setBoolean(6, game.getResult().isCompleted());
    }
}