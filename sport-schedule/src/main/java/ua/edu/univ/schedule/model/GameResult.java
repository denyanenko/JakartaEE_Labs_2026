package ua.edu.univ.schedule.model;

import jakarta.validation.constraints.Min;

public class GameResult {
    private Long id;
    
    @Min(value = 0, message = "Score cannot be negative")
    private int homeScore;
    
    @Min(value = 0, message = "Score cannot be negative")
    private int awayScore;
    
    private boolean completed;

    public GameResult() {}

    public GameResult(int homeScore, int awayScore, boolean completed) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getHomeScore() { return homeScore; }
    public void setHomeScore(int homeScore) { this.homeScore = homeScore; }

    public int getAwayScore() { return awayScore; }
    public void setAwayScore(int awayScore) { this.awayScore = awayScore; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getScoreDisplay() {
        return completed ? homeScore + " - " + awayScore : "TBD";
    }
}
