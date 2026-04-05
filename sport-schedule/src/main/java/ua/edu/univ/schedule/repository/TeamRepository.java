package ua.edu.univ.schedule.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Param;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import ua.edu.univ.schedule.model.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE t.name = :name")
    Optional<Team> findByName(@Param("name") String name);
}