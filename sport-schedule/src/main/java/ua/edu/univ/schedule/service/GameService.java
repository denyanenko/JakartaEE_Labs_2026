package ua.edu.univ.schedule.service;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ua.edu.univ.schedule.dao.IGameDAO;
import ua.edu.univ.schedule.model.Game;

import java.util.List;
import java.util.Optional;

@Stateless
public class GameService {
    @EJB
    private IGameDAO gameDAO;

    public List<Game> getAllGames() {
        return gameDAO.readAll();
    }

    public List<Game> searchGamesByTeam(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            return getAllGames();
        }
        return gameDAO.searchByTeamName(teamName);
    }

    public List<Game> getPagedGames(int page, int size, String teamName) {
        int offset = (page - 1) * size;
        return gameDAO.getPaged(size, offset, teamName);
    }

    public Optional<Game> getGameById(Long id) {
        return gameDAO.read(id);
    }

    public Game addGame(Game game) {
        return gameDAO.create(game);
    }

    public Optional<Game> updateGame(Long id, Game updatedGame) {
        updatedGame.setId(id);
        if (gameDAO.update(updatedGame)) {
            return Optional.of(updatedGame);
        }
        return Optional.empty();
    }

    public boolean deleteGame(Long id) {
        return gameDAO.delete(id);
    }
}