package com.x.y.utils;

import lombok.extern.log4j.Log4j2;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

@Log4j2
public class MemcachedUtils {
    private static MemcachedClient memcachedClient = null;

    private MemcachedUtils() {
    }

    static {
        try {
            MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11211"));
            memcachedClient = builder.build();
        } catch (Exception e) {
            log.error("初始仳memcached出错", e);
        }
    }

    public static void set(String key, int time, Object object) {
        try {
            if (memcachedClient != null) {
                memcachedClient.set(key, time, object);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static Object get(String key) {
        Object value = null;
        try {
            if (memcachedClient == null) {
                return null;
            }
            value = memcachedClient.get(key);
        } catch (Exception e) {
            log.error("", e);
        }
        return value;
    }

    public static void delete(String key) {
        try {
            if (memcachedClient != null) {
                memcachedClient.delete(key);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void flushAll() {
        try {
            if (memcachedClient != null) {
                memcachedClient.flushAll();
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}