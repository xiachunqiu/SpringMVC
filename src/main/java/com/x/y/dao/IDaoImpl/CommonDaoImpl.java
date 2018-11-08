package com.x.y.dao.IDaoImpl;

import com.x.y.common.DaoCommon;
import com.x.y.common.HibernateDataDAO;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.x.y.common.Pager;

import java.io.Serializable;
import java.util.List;

@Repository
public class CommonDaoImpl {
    @Autowired
    private HibernateDataDAO hibernateDataDAO;

    public <T> T getEntityByUniqueKey(Class<T> objectClass, String fieldName, String fieldValue) {
        String entityName = objectClass.getSimpleName();
        Query query = hibernateDataDAO.getDAOSession().createQuery("from " + entityName + " where " + fieldName + "=:" + fieldName);
        query.setParameter(fieldName, fieldValue);
        return (T) query.uniqueResult();
    }

    public <T> List<T> getListByObj(T object, Pager pager, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResult(object, pager, hibernateDataDAO, sqlString);
    }

    public int getCountByObj(Object object, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResultCount(object, hibernateDataDAO, sqlString);
    }

    public <T> List<T> getListForSearch(T object, Pager pager, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResultForSearch(object, pager, hibernateDataDAO, sqlString);
    }

    public int getCountForSearch(Object object, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResultCountForSearch(object, hibernateDataDAO, sqlString);
    }

    public void addEntity(Object entity) throws DataAccessException {
        hibernateDataDAO.getDAOTemplate().save(entity);
    }

    public void saveOrUpdate(Object entity) throws DataAccessException {
        hibernateDataDAO.getDAOTemplate().saveOrUpdate(entity);
    }

    public void merge(Object entity) throws DataAccessException {
        hibernateDataDAO.getDAOTemplate().merge(entity);
    }

    public void updateEntity(Object entity) throws DataAccessException {
        hibernateDataDAO.getDAOTemplate().update(entity);
    }

    public void deleteEntity(Object entity) throws DataAccessException {
        hibernateDataDAO.getDAOTemplate().delete(entity);
    }

    public <T> T queryEntityById(Serializable id, Class<T> objectClass) throws DataAccessException {
        return hibernateDataDAO.getDAOTemplate().get(objectClass, id);
    }
}