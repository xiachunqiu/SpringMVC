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

    public <T> T getEntityByUniqueKey(Class<T> clss, String fieldName, String fieldValue) {
        String entityName = clss.getSimpleName();
        Query query = hibernateDataDAO.getDAOSession().createQuery("from " + entityName + " where " + fieldName + "=:" + fieldName);
        query.setParameter(fieldName, fieldValue);
        return (T) query.uniqueResult();
    }

    public List<?> getListByObj(Object object, Pager pager, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResult(object, pager, hibernateDataDAO, sqlString);
    }

    public Integer getCountByObj(Object object, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResultCount(object, hibernateDataDAO, sqlString);
    }

    public List<?> getListForSearch(Object object, Pager pager, String sqlString) throws DataAccessException {
        return DaoCommon.getQueryResultForSearch(object, pager, hibernateDataDAO, sqlString);
    }

    public Integer getCountForSearch(Object object, String sqlString) throws DataAccessException {
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

    public <T> T queryEntityById(Serializable id, Class<T> t) throws DataAccessException {
        return hibernateDataDAO.getDAOTemplate().get(t, id);
    }
}