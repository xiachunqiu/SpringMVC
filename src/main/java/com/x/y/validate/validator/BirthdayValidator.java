package com.x.y.validate.validator;

import com.x.y.validate.annotation.Birthday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class BirthdayValidator implements ConstraintValidator<Birthday, Date> {
    public void initialize(Birthday birthday) {
    }

    public boolean isValid(Date value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return !value.after(new Date());
    }
}