package ua.edu.univ.schedule.service;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ua.edu.univ.schedule.dao.IGameDAO;
import ua.edu.univ.schedule.model.Game;

import java.util.List;
import java.util.Optional;

@Stateless
public class GameService implements IGameService {
    @Inject
    private IGameDAO gameDAO;

    @Override
    public List<Game> getAllGames() {
        return gameDAO.readAll();
    }

    @Override
    public List<Game> searchGamesByTeam(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            return getAllGames();
        }
        return gameDAO.searchByTeamName(teamName);
    }

    @Override
    public List<Game> getPagedGames(int page, int size, String teamName) {
        int offset = (page - 1) * size;
        return gameDAO.getPaged(size, offset, teamName);
    }

    @Override
    public Optional<Game> getGameById(Long id) {
        return gameDAO.read(id);
    }

    @Override
    public Game addGame(Game game) {
        return gameDAO.create(game);
    }

    @Override
    public Optional<Game> updateGame(Long id, Game updatedGame) {
        updatedGame.setId(id);
        if (gameDAO.update(updatedGame)) {
            return Optional.of(updatedGame);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteGame(Long id) {
        return gameDAO.delete(id);
    }
}