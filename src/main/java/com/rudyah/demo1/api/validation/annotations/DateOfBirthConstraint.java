package com.rudyah.demo1.api.validation.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateOfBirthValidator.class)
@Documented
public @interface DateOfBirthConstraint {
    String pattern();
    String message() default "Invalid date of birth, format 'yyyy-mm-dd'";;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
