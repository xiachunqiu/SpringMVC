package com.x.y.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.x.y.common.Pager;

import java.io.Serializable;
import java.util.List;

@Service
public class CommonService extends BaseService {
    public <T> T getEntityByUniqueKey(Class<T> clss, String fieldName, String fieldValue) {
        return super.getCommonDao().getEntityByUniqueKey(clss, fieldName, fieldValue);
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

    public <T> T getEntityById(Serializable id, Class<T> clss) throws DataAccessException {
        return super.getCommonDao().queryEntityById(id, clss);
    }

    public List<?> getListByObj(Object object, Pager pager, String sqlString) throws DataAccessException {
        return super.getCommonDao().getListByObj(object, pager, sqlString);
    }

    public Integer getCountByObj(Object object, String sqlString) throws DataAccessException {
        return super.getCommonDao().getCountByObj(object, sqlString);
    }

    public List<?> getListForSearch(Object object, Pager pager, String sqlString) throws DataAccessException {
        return super.getCommonDao().getListForSearch(object, pager, sqlString);
    }

    public Integer getCountForSearch(Object object, String sqlString) throws DataAccessException {
        return super.getCommonDao().getCountForSearch(object, sqlString);
    }
}