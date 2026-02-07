package ua.edu.univ.lab3.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.edu.univ.lab3.validation.DifferentTeams;
import java.time.LocalDateTime;

@DifferentTeams
public class Game {
    private Long id;

    @NotNull(message = "Home team cannot be null")
    @Size(min = 2, max = 50, message = "Home team name must be between 2 and 50 characters")
    private String homeTeam;

    @NotNull(message = "Away team cannot be null")
    @Size(min = 2, max = 50, message = "Away team name must be between 2 and 50 characters")
    private String awayTeam;

    @NotNull(message = "Date and time cannot be null")
    private LocalDateTime dateTime;

    private String score;
    private boolean completed;

    public Game() {}

    public Game(Long id, String homeTeam, String awayTeam, LocalDateTime dateTime, String score, boolean completed) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.score = score;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }

    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
