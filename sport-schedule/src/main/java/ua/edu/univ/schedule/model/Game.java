package ua.edu.univ.schedule.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.edu.univ.schedule.validation.DifferentTeams;
import java.time.LocalDateTime;

@DifferentTeams
public class Game {
    private Long id;

    @NotNull(message = "Home team cannot be null")
    @Valid
    private Team homeTeam;

    @NotNull(message = "Away team cannot be null")
    @Valid
    private Team awayTeam;

    @NotNull(message = "Date and time cannot be null")
    private LocalDateTime dateTime;

    @Valid
    private GameResult result;

    public Game() {
        this.result = new GameResult(0, 0, false);
    }

    public Game(Long id, Team homeTeam, Team awayTeam, LocalDateTime dateTime, GameResult result) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.result = result;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Team getHomeTeam() { return homeTeam; }
    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }

    public Team getAwayTeam() { return awayTeam; }
    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public GameResult getResult() { return result; }
    public void setResult(GameResult result) { this.result = result; }
}
