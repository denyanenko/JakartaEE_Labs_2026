package ua.edu.univ.schedule.dao;

import ua.edu.univ.schedule.model.Game;

import java.util.List;
import java.util.Optional;

public interface IGameDAO {
    Game create(Game game);
    Optional<Game> read(Long id);
    List<Game> readAll();
    boolean update(Game game);
    boolean delete(Long id);
    List<Game> searchByTeamName(String teamName);
    List<Game> getPaged(int limit, int offset, String teamName);
}
