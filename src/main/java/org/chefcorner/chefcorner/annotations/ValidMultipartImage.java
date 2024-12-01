package org.chefcorner.chefcorner.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.chefcorner.chefcorner.validators.ImageFileValidator;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMultipartImage {
    String message() default "Invalid image file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}