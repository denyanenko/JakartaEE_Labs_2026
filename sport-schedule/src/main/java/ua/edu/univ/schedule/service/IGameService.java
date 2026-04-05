package ua.edu.univ.schedule.service;

import jakarta.ejb.Local;
import ua.edu.univ.schedule.model.Game;

import java.util.List;
import java.util.Optional;

@Local
public interface IGameService {
    List<Game> getAllGames();

    List<Game> searchGamesByTeam(String teamName);

    List<Game> getPagedGames(int page, int size, String teamName);

    Optional<Game> getGameById(Long id);

    Game addGame(Game game);

    Optional<Game> updateGame(Long id, Game updatedGame);

    boolean deleteGame(Long id);

    List<Game> addGames(List<Game> games);
}
