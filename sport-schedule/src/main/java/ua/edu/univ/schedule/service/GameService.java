package ua.edu.univ.schedule.service;


import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import ua.edu.univ.schedule.repository.GameRepository;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class GameService implements IGameService {
    @Inject
    private GameRepository gameRepository;

    @Inject
    private ITeamService teamService;

    private void prepareTeams(Game game) {
        Team home = teamService.getOrCreateTeam(game.getHomeTeam().getName());
        Team away = teamService.getOrCreateTeam(game.getAwayTeam().getName());
        game.setHomeTeam(home);
        game.setAwayTeam(away);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Game> getAllGames() {
        Order<Game> sortOrder = Order.by(Sort.desc("dateTime"));
        return gameRepository.findAll(sortOrder);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Game> searchGamesByTeam(String teamName) {

        if (teamName == null || teamName.trim().isEmpty()) {
            return getAllGames();
        }

        String searchPattern = "%" + teamName.trim() + "%";
        return gameRepository.findByTeamName(searchPattern);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Game> getPagedGames(int page, int size, String teamName) {
        PageRequest pageRequest = PageRequest.ofPage(page).size(size);
        Order<Game> sortOrder = Order.by(Sort.desc("dateTime"));

        Page<Game> resultPage;

        if (teamName == null || teamName.trim().isEmpty()) {
            resultPage = gameRepository.findAll(pageRequest, sortOrder);
        } else {
            String searchPattern = "%" + teamName.trim() + "%";
            resultPage = gameRepository.findByTeamName(
                    searchPattern, pageRequest
            );
        }

        return resultPage.content();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Game addGame(Game game) {
        prepareTeams(game);
        return gameRepository.save(game);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Optional<Game> updateGame(Long id, Game updatedGame) {
        updatedGame.setId(id);
        prepareTeams(updatedGame);
        return Optional.of(gameRepository.save(updatedGame));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteGame(Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Game> addGames(List<Game> games) {
        if (games == null || games.isEmpty()) {
            throw new IllegalArgumentException("Games list cannot be empty");
        }

        List<Game> saved = new ArrayList<>();
        for (Game game : games) {
            prepareTeams(game);
            saved.add(gameRepository.save(game));
        }
        return saved;
    }
}