package ua.edu.univ.schedule.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import ua.edu.univ.schedule.repository.TeamRepository;
import ua.edu.univ.schedule.model.Team;

import java.util.List;
import java.util.Optional;

@Stateless
public class TeamService implements ITeamService {

    @Inject
    private TeamRepository teamRepository;

    private static final List<String> FORBIDDEN_NAMES = List.of("error", "test", "fail");

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Team getOrCreateTeam(String teamName) {
        if (FORBIDDEN_NAMES.contains(teamName.toLowerCase())) {
            throw new IllegalStateException(
                    "Team name '" + teamName + "' is reserved, transaction will rollback"
            );
        }
        Optional<Team> existing = teamRepository.findByName(teamName);
        if (existing.isPresent()) {
            return existing.get();
        }
        Team newTeam = new Team();
        newTeam.setName(teamName);
        return teamRepository.save(newTeam);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll().toList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }
}