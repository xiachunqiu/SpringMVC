package com.x.y.common;

import jodd.props.Props;
import jodd.util.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@Log4j2
public abstract class ConfigProps {
    private static Props props;

    public static void init() {
        props = new Props();
        try {
            Properties p = loadProperty();
            props.load(p);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AssertionError("读取config.properties文件，不允许出错。", e);
        }
    }

    private static Properties loadProperty() {
        Properties p = new Properties();
        try {
            URL pl = Thread.currentThread().getContextClassLoader().getResource("/");
            if (pl == null || pl.toString().contains("repository")) {
                pl = Thread.class.getResource("/");
            }
            String path = pl.getPath();
            if (path.startsWith("jar:file:")) {
                path = StringUtil.replace(path, "jar:file:", "");
            }
            path = StringUtil.replace(path, "file:", "");
            if (path.contains("WEB-INF")) {
                path = path.substring(0, path.indexOf("WEB-INF"));
                path = path + "/" + "WEB-INF" + "/" + "classes" + "/" + "config.properties";
            } else {
                path = path + File.separator + "config.properties";
            }
            File file = new File(path);
            p.load(new FileInputStream(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("", e);
        }
        return p;
    }

    public static String getProp(String key) {
        if (props == null) {
            init();
        }
        return props.getValue(key);
    }
}