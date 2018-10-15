package com.x.y.validate.annotation;

import com.x.y.validate.validator.RangeDoubleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeDoubleValidator.class)
public @interface RangeDouble {
    String message() default "数值不在范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default 0.0D;

    double max() default Double.MAX_VALUE;
}