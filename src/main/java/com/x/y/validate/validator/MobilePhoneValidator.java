package com.x.y.validate.validator;

import com.x.y.utils.StringUtils;
import com.x.y.validate.annotation.MobilePhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {
    public void initialize(MobilePhone mobilePhone) {
    }

    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        if (StringUtils.isNull(value)) {
            return true;
        }
        value = value.trim();
        Pattern phonePattern = Pattern.compile("^1\\d{10}$");
        Matcher matcher = phonePattern.matcher(value);
        return matcher.find();
    }
}