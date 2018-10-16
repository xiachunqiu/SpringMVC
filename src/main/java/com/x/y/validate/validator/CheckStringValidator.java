package com.x.y.validate.validator;

import com.x.y.validate.annotation.CheckString;

import javax.validation.ConstraintValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckStringValidator implements ConstraintValidator<CheckString, String> {
    // 数字、字母、中文、标点符号
    private String chineseEnglishNumberPunctuation = "^[a-zA-Z0-9\u4E00-\u9FA5\\pP]+$";
    private String checkRegex = chineseEnglishNumberPunctuation;

    public void initialize(CheckString checkString) {
    }

    public boolean isValid(String value, javax.validation.ConstraintValidatorContext arg1) {
        if (value == null) {
            return true;
        }
        value = value.trim();
        Pattern pattern = Pattern.compile(checkRegex);
        Matcher match = pattern.matcher(value);
        return match.matches();
    }
}