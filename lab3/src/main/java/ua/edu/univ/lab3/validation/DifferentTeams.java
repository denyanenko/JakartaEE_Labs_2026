package ua.edu.univ.lab3.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentTeamsValidator.class)
public @interface DifferentTeams {
    String message() default "Home team and away team must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
