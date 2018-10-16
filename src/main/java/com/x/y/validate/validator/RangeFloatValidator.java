package com.x.y.validate.validator;

import com.x.y.validate.annotation.RangeFloat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeFloatValidator implements ConstraintValidator<RangeFloat, Float> {
    private Float min = 0.0F;
    private Float max = Float.MAX_VALUE;

    public void initialize(RangeFloat rangeFloat) {
        min = rangeFloat.min();
        max = rangeFloat.max();
    }

    public boolean isValid(Float value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return value >= min && value <= max;
    }
}