package ua.edu.univ.schedule.repository;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;
import ua.edu.univ.schedule.model.Game;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    boolean existsById(Long id);

    String TEAM_SEARCH_QUERY = "SELECT g FROM Game g WHERE g.homeTeam.name LIKE :pattern " +
            "OR g.awayTeam.name LIKE :pattern ORDER BY g.dateTime DESC";

    @Query(TEAM_SEARCH_QUERY)
    Page<Game> findByTeamName(@Param("pattern") String pattern, PageRequest pageRequest);

    @Query(TEAM_SEARCH_QUERY)
    List<Game> findByTeamName(@Param("pattern") String pattern);

    @Find
    List<Game> findAll(Order<Game> order);
}