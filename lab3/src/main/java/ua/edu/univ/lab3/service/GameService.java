package ua.edu.univ.lab3.service;

import ua.edu.univ.lab3.model.Game;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameService {
    private static List<Game> games = new ArrayList<>();
    private static long nextId = 5;

    static {
        games.add(new Game(1L, "Dynamo Kyiv", "Shakhtar Donetsk", LocalDateTime.now().plusDays(1), null, false));
        games.add(new Game(2L, "Real Madrid", "Barcelona", LocalDateTime.now().minusDays(2), "3-2", true));
        games.add(new Game(3L, "Liverpool", "Manchester City", LocalDateTime.now().plusHours(3), null, false));
        games.add(new Game(4L, "AC Milan", "Inter Milan", LocalDateTime.now().minusDays(4), "0-0", true));
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
                .filter(game -> game.getHomeTeam().toLowerCase().contains(lowerTeam) ||
                             game.getAwayTeam().toLowerCase().contains(lowerTeam))
                .collect(Collectors.toList());
    }

    public List<Game> getPagedGames(int page, int size, String teamName) {
        List<Game> filtered = games.stream()
                .filter(game -> teamName == null || teamName.isEmpty() ||
                        game.getHomeTeam().toLowerCase().contains(teamName.toLowerCase()) ||
                        game.getAwayTeam().toLowerCase().contains(teamName.toLowerCase()))
                .collect(Collectors.toList());

        int fromIndex = (page - 1) * size;
        if (fromIndex >= filtered.size()) {
            return new ArrayList<>();
        }
        int toIndex = Math.min(fromIndex + size, filtered.size());
        return filtered.subList(fromIndex, toIndex);
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
            existingGame.setHomeTeam(updatedGame.getHomeTeam());
            existingGame.setAwayTeam(updatedGame.getAwayTeam());
            existingGame.setDateTime(updatedGame.getDateTime());
            existingGame.setScore(updatedGame.getScore());
            existingGame.setCompleted(updatedGame.isCompleted());
            return existingGame;
        });
    }

    public boolean deleteGame(Long id) {
        return games.removeIf(g -> g.getId().equals(id));
    }
}
