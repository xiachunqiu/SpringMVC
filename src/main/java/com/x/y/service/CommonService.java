package com.x.y.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.x.y.common.Pager;

import java.io.Serializable;
import java.util.List;

@Service
public class CommonService extends BaseService {
    public <T> T getEntityByUniqueKey(Class<T> objectClass, String fieldName, String fieldValue) {
        return super.getCommonDao().getEntityByUniqueKey(objectClass, fieldName, fieldValue);
    }

    public void addEntity(Object entity) throws DataAccessException {
        super.getCommonDao().addEntity(entity);
    }

    public void saveOrUpdate(Object entity) throws DataAccessException {
        super.getCommonDao().saveOrUpdate(entity);
    }

    public void merge(Object entity) throws DataAccessException {
        super.getCommonDao().merge(entity);
    }

    public void updateEntity(Object entity) throws DataAccessException {
        super.getCommonDao().updateEntity(entity);
    }

    public void deleteEntity(Object entity) throws DataAccessException {
        super.getCommonDao().deleteEntity(entity);
    }

    public <T> T getEntityById(Serializable id, Class<T> objectClass) throws DataAccessException {
        return super.getCommonDao().queryEntityById(id, objectClass);
    }

    public <T> List<T> getListByObj(T object, Pager pager, String sqlString) throws DataAccessException {
        return super.getCommonDao().getListByObj(object, pager, sqlString);
    }

    public <T> List<T> getListByObj(T object, Pager pager) throws DataAccessException {
        return super.getCommonDao().getListByObj(object, pager, null);
    }

    public int getCountByObj(Object object, String sqlString) throws DataAccessException {
        return super.getCommonDao().getCountByObj(object, sqlString);
    }

    public int getCountByObj(Object object) throws DataAccessException {
        return super.getCommonDao().getCountByObj(object, null);
    }

    public <T> List<T> getListForSearch(T object, Pager pager, String sqlString) throws DataAccessException {
        return super.getCommonDao().getListForSearch(object, pager, sqlString);
    }

    public <T> List<T> getListForSearch(T object, Pager pager) throws DataAccessException {
        return super.getCommonDao().getListForSearch(object, pager, null);
    }

    public int getCountForSearch(Object object, String sqlString) throws DataAccessException {
        return super.getCommonDao().getCountForSearch(object, sqlString);
    }

    public int getCountForSearch(Object object) throws DataAccessException {
        return super.getCommonDao().getCountForSearch(object, null);
    }
}