package ua.edu.univ.schedule.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.edu.univ.schedule.model.Game;
import ua.edu.univ.schedule.model.Team;

public class DifferentTeamsValidator implements ConstraintValidator<DifferentTeams, Game> {
    @Override
    public boolean isValid(Game game, ConstraintValidatorContext context) {
        if (game == null) {
            return true;
        }
        Team home = game.getHomeTeam();
        Team away = game.getAwayTeam();
        
        if (home == null || away == null || home.getName() == null || away.getName() == null) {
            return true;
        }
        return !home.getName().equalsIgnoreCase(away.getName());
    }
}
