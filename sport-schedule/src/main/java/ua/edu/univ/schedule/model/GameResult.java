package ua.edu.univ.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;

@Embeddable
public class GameResult {

    @Column(name = "home_score")
    @Min(value = 0, message = "Score cannot be negative")
    private int homeScore;

    @Column(name = "away_score")
    @Min(value = 0, message = "Score cannot be negative")
    private int awayScore;

    @Column(name = "is_completed")
    private boolean completed;

    public GameResult() {}

    public GameResult(int homeScore, int awayScore, boolean completed) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.completed = completed;
    }

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
