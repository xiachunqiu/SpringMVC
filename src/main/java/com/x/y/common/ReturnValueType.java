package com.x.y.common;

public enum ReturnValueType implements EnumEntry {
    success("成功"),
    fail("失败");
    private String value;

    ReturnValueType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}