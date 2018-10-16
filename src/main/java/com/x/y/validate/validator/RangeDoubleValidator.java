package com.x.y.validate.validator;

import com.x.y.validate.annotation.RangeDouble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeDoubleValidator implements ConstraintValidator<RangeDouble, Double> {
    private Double min = 0.0D;
    private Double max = Double.MAX_VALUE;

    public void initialize(RangeDouble rangeDouble) {
        min = rangeDouble.min();
        max = rangeDouble.max();
    }

    public boolean isValid(Double value, ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        return value >= min && value <= max;
    }
}