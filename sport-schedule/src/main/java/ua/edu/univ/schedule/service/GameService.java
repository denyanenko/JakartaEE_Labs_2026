package ua.edu.univ.schedule.service;

import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.GameResult;
import ua.edu.univ.schedule.model.Team;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameService {
    private static final List<Game> games = new ArrayList<>();
    private static long nextId = 5;

    static {
        Team t1 = new Team(1L, "Dynamo Kyiv");
        Team t2 = new Team(2L, "Shakhtar Donetsk");
        Team t3 = new Team(3L, "Real Madrid");
        Team t4 = new Team(4L, "Barcelona");
        Team t5 = new Team(5L, "Liverpool");
        Team t6 = new Team(6L, "Manchester City");
        Team t7 = new Team(7L, "AC Milan");
        Team t8 = new Team(8L, "Inter Milan");

        games.add(new Game(1L, t1, t2, LocalDateTime.now().plusDays(1), new GameResult(0, 0, false)));
        games.add(new Game(2L, t3, t4, LocalDateTime.now().minusDays(2), new GameResult(3, 2, true)));
        games.add(new Game(3L, t5, t6, LocalDateTime.now().plusHours(3), new GameResult(0, 0, false)));
        games.add(new Game(4L, t7, t8, LocalDateTime.now().minusDays(4), new GameResult(1, 1, true)));
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }

    public List<Game> searchGamesByTeam(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            return getAllGames();
        }
        String lowerTeam = teamName.toLowerCase();
        return games.stream()
                .filter(game -> game.getHomeTeam().getName().toLowerCase().contains(lowerTeam) ||
                             game.getAwayTeam().getName().toLowerCase().contains(lowerTeam))
                .collect(Collectors.toList());
    }

    public Optional<Game> getGameById(Long id) {
        return games.stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    public Game addGame(Game game) {
        game.setId(nextId++);
        games.add(game);
        return game;
    }

    public Optional<Game> updateGame(Long id, Game updatedGame) {
        return getGameById(id).map(existingGame -> {
            existingGame.getHomeTeam().setName(updatedGame.getHomeTeam().getName());
            existingGame.getAwayTeam().setName(updatedGame.getAwayTeam().getName());
            existingGame.setDateTime(updatedGame.getDateTime());
            existingGame.setResult(updatedGame.getResult());
            return existingGame;
        });
    }

    public boolean deleteGame(Long id) {
        return games.removeIf(g -> g.getId().equals(id));
    }
}
