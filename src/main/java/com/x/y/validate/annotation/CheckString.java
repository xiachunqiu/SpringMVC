package com.x.y.validate.annotation;

import com.x.y.validate.validator.CheckStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckStringValidator.class)
public @interface CheckString {
    String message() default "字符串填写错误！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}