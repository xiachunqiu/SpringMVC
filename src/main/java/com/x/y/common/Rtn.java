package com.x.y.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(Include.NON_NULL)
public class Rtn implements Serializable {
    private ReturnValueType value;
    private String des;
    private Object object;
    private List dataList;
    private Pager pager;

    public Rtn(ReturnValueType value, String des) {
        this.value = value;
        this.des = des;
    }
}