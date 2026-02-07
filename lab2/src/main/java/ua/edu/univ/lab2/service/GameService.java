package ua.edu.univ.lab2.service;

import ua.edu.univ.lab2.model.Game;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {
    private static List<Game> games = new ArrayList<>();

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

    public void addGame(Game game) {
        game.setId((long) (games.size() + 1));
        games.add(game);
    }
}
