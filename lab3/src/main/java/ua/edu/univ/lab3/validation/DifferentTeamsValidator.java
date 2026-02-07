package ua.edu.univ.lab3.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.edu.univ.lab3.model.Game;

public class DifferentTeamsValidator implements ConstraintValidator<DifferentTeams, Game> {
    @Override
    public boolean isValid(Game game, ConstraintValidatorContext context) {
        if (game == null) {
            return true;
        }
        if (game.getHomeTeam() == null || game.getAwayTeam() == null) {
            return true;
        }
        return !game.getHomeTeam().equalsIgnoreCase(game.getAwayTeam());
    }
}
