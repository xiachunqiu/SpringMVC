package com.x.y.validate.annotation;

import com.x.y.validate.validator.BirthdayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthdayValidator.class)
public @interface Birthday {
    String message() default "出生日期不能超过当前时间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}