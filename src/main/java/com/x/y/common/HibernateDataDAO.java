package com.x.y.common;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface HibernateDataDAO {
    HibernateTemplate getDAOTemplate();

    Session getDAOSession();
}