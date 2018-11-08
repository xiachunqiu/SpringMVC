package com.x.y.utils;

import org.springframework.web.context.WebApplicationContext;

public final class MyWebApplicationContextUtils {
    private static WebApplicationContext webApplicationContext;

    private MyWebApplicationContextUtils() {
    }

    public static WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        MyWebApplicationContextUtils.webApplicationContext = webApplicationContext;
    }
}