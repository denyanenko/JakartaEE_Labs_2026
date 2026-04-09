package ua.edu.univ.schedule.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "teams")
@NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Team name cannot be null")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "homeTeam")
    @JsonbTransient
    private List<Game> homeGames;

    @OneToMany(mappedBy = "awayTeam")
    @JsonbTransient
    private List<Game> awayGames;

    public Team() {}

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Game> getHomeGames() { return homeGames; }
    public void addHomeGame(Game game) {
        this.homeGames.add(game);
        game.setHomeTeam(this); // Обов'язково встановлюємо зворотний зв'язок!
    }

    public List<Game> getAwayGames() { return awayGames; }
    public void addAwayGame(Game game) {
        this.awayGames.add(game);
        game.setAwayTeam(this);
    }
}
