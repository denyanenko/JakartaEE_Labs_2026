package ua.edu.univ.schedule.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Team {
    private Long id;

    @NotNull(message = "Team name cannot be null")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    private String name;

    public Team() {}

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return name != null ? name.equalsIgnoreCase(team.name) : team.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
