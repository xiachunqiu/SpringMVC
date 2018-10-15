package com.x.y.common;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDataDAOImpl extends HibernateDaoSupport implements HibernateDataDAO {
    public HibernateDataDAOImpl() {
    }

    public HibernateTemplate getDAOTemplate() {
        return this.getHibernateTemplate();
    }

    public Session getDAOSession() {
        return this.getSession();
    }
}