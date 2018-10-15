package com.x.y.validate.annotation;

import com.x.y.validate.validator.MobilePhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobilePhoneValidator.class)
public @interface MobilePhone {
    String message() default "手机号码不正确！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}