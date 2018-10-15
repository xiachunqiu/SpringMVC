package com.x.y.common;

import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import com.x.y.utils.DateUtils;
import com.x.y.utils.StringUtils;

public class ObjectToStringConverter implements Converter<Object, String> {
    private String outputDatePattern;

    public ObjectToStringConverter() {
    }

    public String convert(Object object) {
        if (object == null) {
            return "";
        } else if (object instanceof Date) {
            if (StringUtils.isNull(this.outputDatePattern)) {
                this.outputDatePattern = "yyyy-MM-dd HH:mm:ss";
            }
            return DateUtils.format(this.outputDatePattern, (Date)object);
        } else {
            return object.toString();
        }
    }

    public String getOutputDatePattern() {
        return this.outputDatePattern;
    }

    public void setOutputDatePattern(String outputDatePattern) {
        this.outputDatePattern = outputDatePattern;
    }
}