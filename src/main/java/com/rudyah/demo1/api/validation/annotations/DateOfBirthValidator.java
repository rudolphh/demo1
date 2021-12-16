package com.rudyah.demo1.api.validation.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateOfBirthValidator implements
        ConstraintValidator<DateOfBirthConstraint, LocalDate> {

    private DateOfBirthConstraint check;

    @Override
    public void initialize(DateOfBirthConstraint constraint) {
        this.check = constraint;
    }

    @Override
    public boolean isValid(LocalDate object, ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }

        return isValidDate(object, check.pattern());
    }

    public static boolean isValidDate(LocalDate inDate, String format) {
        // TEST IF inDate IS VALID RETURN TRUE/FALSE
        boolean valid = false;

        try {
            System.out.println(inDate.toString());
            System.out.println(format);
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(inDate.toString(),
                    DateTimeFormatter.ofPattern(format)
                            .withResolverStyle(ResolverStyle.STRICT)
            );

            valid = true;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return valid;
    }

}