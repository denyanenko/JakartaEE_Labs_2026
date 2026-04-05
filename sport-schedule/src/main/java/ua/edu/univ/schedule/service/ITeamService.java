package ua.edu.univ.schedule.service;

import jakarta.ejb.Local;
import ua.edu.univ.schedule.model.Team;

import java.util.List;
import java.util.Optional;

@Local
public interface ITeamService {
    Team getOrCreateTeam(String teamName);

    List<Team> getAllTeams();

    Optional<Team> getTeamById(Long id);
}
