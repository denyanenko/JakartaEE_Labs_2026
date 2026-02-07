package ua.edu.univ.lab2.model;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private String homeTeam;
    private String awayTeam;
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
