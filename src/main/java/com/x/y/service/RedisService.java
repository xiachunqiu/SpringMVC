package com.x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class RedisService {
    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate;
    @Autowired
    @Qualifier("redisTemplate")
    protected RedisTemplate<Serializable, Serializable> redisTemplateSerializable;

    public void setCacheObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getCacheObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object setCacheList(String key, List<Object> dataList) {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            for (Object aDataList : dataList) {
                listOperation.rightPush(key, aDataList);
            }
        }
        return listOperation;
    }

    public List<Object> getCacheList(String key) {
        List<Object> dataList = new ArrayList<>();
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.leftPop(key));
        }
        return dataList;
    }

    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public long remove(String key, long i, Object obj) {
        return redisTemplate.opsForList().remove(key, i, obj);
    }

    public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet) {
        BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
        for (Object aDataSet : dataSet) {
            setOperation.add(aDataSet);
        }
        return setOperation;
    }

    public Set<Object> getCacheSet(String key) {
        Set<Object> dataSet = new HashSet<>();
        BoundSetOperations<String, Object> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    public int setCacheMap(String key, Map<String, Object> dataMap) {
        if (null != dataMap) {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                if (hashOperations != null) {
                    hashOperations.put(key, entry.getKey(), entry.getValue());
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        return dataMap.size();
    }

    public Map<Object, Object> getCacheMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void setCacheIntegerMap(String key, Map<Integer, Object> dataMap) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, Object> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
    }

    public Map<Object, Object> getCacheIntegerMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public long deleteMap(String key) {
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate.opsForHash().delete(key);
    }

    public long del(final byte[] key) {
        return (long) redisTemplateSerializable.execute((RedisCallback<Object>) connection -> connection.del(key));
    }
}