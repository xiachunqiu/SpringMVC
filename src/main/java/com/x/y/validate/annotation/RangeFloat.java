package com.x.y.validate.annotation;

import com.x.y.validate.validator.RangeFloatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeFloatValidator.class)
public @interface RangeFloat {
    String message() default "数值不在范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    float min() default 0.0F;

    float max() default Float.MAX_VALUE;
}