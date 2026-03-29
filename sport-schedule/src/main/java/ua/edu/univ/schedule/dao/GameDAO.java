package ua.edu.univ.schedule.dao;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.Team;

import java.util.List;
import java.util.Optional;

@Stateless
public class GameDAO implements IGameDAO {

    @PersistenceContext(unitName = "SchedulePU")
    private EntityManager em;

    private Team getOrCreateTeam(String teamName) {
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.name = :name", Team.class);
        query.setParameter("name", teamName);

        List<Team> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.getFirst();
        }

        Team newTeam = new Team();
        newTeam.setName(teamName);
        em.persist(newTeam);
        return newTeam;
    }

    @Override
    public Game create(Game game) {
            prepareTeams(game);
            em.persist(game);
            return game;
    }

    @Override
    public Optional<Game> read(Long id) {
        return Optional.ofNullable(em.find(Game.class, id));
    }

    @Override
    public List<Game> readAll() {
        return em.createQuery("SELECT g FROM Game g ORDER BY g.dateTime DESC", Game.class)
                .getResultList();
    }

    @Override
    public boolean update(Game game) {
            prepareTeams(game);
            em.merge(game);
            return true;
    }

    @Override
    public boolean delete(Long id) {
        Game game = em.find(Game.class, id);
        if (game != null) {
            em.remove(game);
            return true;
        }
        return false;
    }

    @Override
    public List<Game> searchByTeamName(String teamName) {
        String jpql = "SELECT g FROM Game g WHERE g.homeTeam.name LIKE :name OR g.awayTeam.name LIKE :name";
        return em.createQuery(jpql, Game.class)
                .setParameter("name", "%" + teamName + "%")
                .getResultList();
    }

    @Override
    public List<Game> getPaged(int limit, int offset, String teamName) {
        String jpql = "SELECT g FROM Game g WHERE (:name IS NULL OR g.homeTeam.name LIKE :name OR g.awayTeam.name LIKE :name) ORDER BY g.dateTime DESC";

        TypedQuery<Game> query = em.createQuery(jpql, Game.class);

        String searchPattern = (teamName == null || teamName.trim().isEmpty()) ? null : "%" + teamName + "%";
        query.setParameter("name", searchPattern);

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    private void prepareTeams(Game game) {
        Team home = getOrCreateTeam(game.getHomeTeam().getName());
        Team away = getOrCreateTeam(game.getAwayTeam().getName());

        game.setHomeTeam(home);
        game.setAwayTeam(away);
    }
}