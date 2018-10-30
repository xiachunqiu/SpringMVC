
package com.x.y.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;

import com.x.y.utils.StringUtils;
import jodd.util.StringUtil;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;

public final class DaoCommon {
    public static <T> List<T> getQueryResult(T object, Pager pager, HibernateDataDAO hibernateDataDAO, String sqlString) {
        StringBuffer queryBuffer = new StringBuffer();
        String objectName = object.getClass().getName();
        queryBuffer.append("from ").append(objectName).append(" where 1=1 ");
        setQueryBufferForAccurateSearch(queryBuffer, object);
        if (StringUtils.isNotNull(sqlString)) {
            queryBuffer.append(" ").append(sqlString);
        }
        Query query = hibernateDataDAO.getDAOSession().createQuery(queryBuffer.toString());
        setQueryValueForAccurateSearch(query, object);
        setQueryPager(query, pager);
        return (List<T>) query.list();
    }

    public static Integer getQueryResultCount(Object object, HibernateDataDAO hibernateDataDAO, String sqlString) {
        StringBuffer queryBuffer = new StringBuffer();
        String objectName = object.getClass().getName();
        queryBuffer.append("select count(*) from ").append(objectName).append(" where 1=1 ");
        setQueryBufferForAccurateSearch(queryBuffer, object);
        if (StringUtils.isNotNull(sqlString)) {
            queryBuffer.append(" ").append(sqlString);
        }
        Query query = hibernateDataDAO.getDAOSession().createQuery(queryBuffer.toString());
        setQueryValueForAccurateSearch(query, object);
        Long count = (Long) query.uniqueResult();
        return count.intValue();
    }

    public static List<?> getQueryResultForSearch(Object object, Pager pager, HibernateDataDAO hibernateDataDAO, String sqlString) {
        StringBuffer queryBuffer = new StringBuffer();
        String objectName = object.getClass().getName();
        queryBuffer.append("from ").append(objectName).append(" where 1=1 ");
        setQueryBufferForStringSearch(queryBuffer, object);
        if (StringUtils.isNotNull(sqlString)) {
            queryBuffer.append(" ").append(sqlString);
        }
        Query query = hibernateDataDAO.getDAOSession().createQuery(queryBuffer.toString());
        setQueryValueForStringSearch(query, object);
        setQueryPager(query, pager);
        return query.list();
    }

    public static Integer getQueryResultCountForSearch(Object object, HibernateDataDAO hibernateDataDAO, String sqlString) {
        StringBuffer queryBuffer = new StringBuffer();
        String objectName = object.getClass().getName();
        queryBuffer.append("select count(*) from ").append(objectName).append(" where 1=1 ");
        setQueryBufferForStringSearch(queryBuffer, object);
        if (StringUtils.isNotNull(sqlString)) {
            queryBuffer.append(" ").append(sqlString);
        }
        Query query = hibernateDataDAO.getDAOSession().createQuery(queryBuffer.toString());
        setQueryValueForStringSearch(query, object);
        Long count = (Long) query.uniqueResult();
        return count.intValue();
    }

    private static void setQueryBufferForStringSearch(StringBuffer queryBuffer, Object object) {
        try {
            packageRelevanceClassForStringSearch(queryBuffer, object, "");
        } catch (Exception var3) {
            throw new DataAccessException(var3.getMessage(), var3) {
            };
        }
    }

    private static List<Class<?>> getAllowClass() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(String.class);
        classes.add(Long.class);
        classes.add(Float.class);
        classes.add(Double.class);
        classes.add(Integer.class);
        classes.add(Boolean.class);
        return classes;
    }

    private static void setQueryBufferForAccurateSearch(StringBuffer queryBuffer, Object object) {
        try {
            packageRelevanceClass(queryBuffer, object, "");
        } catch (Exception var3) {
            throw new DataAccessException(var3.getMessage(), var3) {
            };
        }
    }

    private static Boolean judgeField(Field field, Object object) {
        try {
            if (Modifier.isFinal(field.getModifiers()) || field.get(object) == null) {
                return false;
            }
            Transient transientTemp = field.getAnnotation(Transient.class);
            if (transientTemp != null) {
                return false;
            }
        } catch (Exception var3) {
            throw new DataAccessException(var3.getMessage(), var3) {
            };
        }
        return true;
    }

    private static String replaceDOT(String string) {
        return StringUtil.replace(string, ".", "DOT");
    }

    private static void packageRelevanceClassForStringSearch(StringBuffer queryBuffer, Object object, String className) {
        try {
            className = className.trim();
            Class<?> srClass = object.getClass();
            Field[] fields = srClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String pfildName = StringUtils.isNull(className) ? fieldName : className + "." + fieldName;
                field.setAccessible(true);
                Class<?> clss = field.getType();
                if (judgeField(field, object)) {
                    if (!getAllowClass().contains(clss)) {
                        if (field.get(object) instanceof EnumEntry) {
                            setQueryBuffer2(queryBuffer, "and " + pfildName + " = :" + replaceDOT(pfildName), field.get(object));
                            continue;
                        }
                        packageRelevanceClassForStringSearch(queryBuffer, field.get(object), pfildName);
                    }
                    if (getAllowClass().contains(clss)) {
                        if (clss.equals(String.class)) {
                            setQueryBuffer2(queryBuffer, "and " + pfildName + " like :" + replaceDOT(pfildName), field.get(object));
                        } else {
                            setQueryBuffer2(queryBuffer, "and " + pfildName + " = :" + replaceDOT(pfildName), field.get(object));
                        }
                    }
                }
            }
        } catch (Exception var12) {
            throw new DataAccessException(var12.getMessage(), var12) {
            };
        }
    }

    private static void packageRelevanceClassValueForStringSearch(Query query, Object object, String className) {
        try {
            className = className.trim();
            Class<?> srClass = object.getClass();
            Field[] fields = srClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String pfildName = StringUtils.isNull(className) ? fieldName : className + "." + fieldName;
                field.setAccessible(true);
                Class<?> clss = field.getType();
                if (judgeField(field, object)) {
                    if (!getAllowClass().contains(clss)) {
                        if (field.get(object) instanceof EnumEntry) {
                            setQueryValue(query, field.get(object), replaceDOT(pfildName), field.get(object));
                            continue;
                        }
                        packageRelevanceClassValueForStringSearch(query, field.get(object), pfildName);
                    }
                    if (getAllowClass().contains(clss)) {
                        if (clss.equals(String.class)) {
                            setQueryValue(query, field.get(object), replaceDOT(pfildName), "%" + field.get(object) + "%");
                        } else {
                            setQueryValue(query, field.get(object), replaceDOT(pfildName), field.get(object));
                        }
                    }
                }
            }
        } catch (Exception var12) {
            throw new DataAccessException(var12.getMessage(), var12) {
            };
        }
    }

    private static void packageRelevanceClass(StringBuffer queryBuffer, Object object, String className) {
        try {
            className = className.trim();
            Class<?> srClass = object.getClass();
            Field[] fields = srClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String pfildName = StringUtils.isNull(className) ? fieldName : className + "." + fieldName;
                field.setAccessible(true);
                Class<?> clss = field.getType();
                if (judgeField(field, object)) {
                    if (!getAllowClass().contains(clss)) {
                        if (field.get(object) instanceof EnumEntry) {
                            setQueryBuffer2(queryBuffer, "and " + pfildName + " = :" + replaceDOT(pfildName), field.get(object));
                            continue;
                        }
                        packageRelevanceClass(queryBuffer, field.get(object), pfildName);
                    }
                    if (getAllowClass().contains(clss)) {
                        setQueryBuffer2(queryBuffer, "and " + pfildName + " = :" + replaceDOT(pfildName), field.get(object));
                    }
                }
            }
        } catch (Exception var12) {
            throw new DataAccessException(var12.getMessage(), var12) {
            };
        }
    }

    private static void packageRelevanceClassValue(Query query, Object object, String className) {
        try {
            className = className.trim();
            Class<?> srClass = object.getClass();
            Field[] fields = srClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String pfildName = StringUtils.isNull(className) ? fieldName : className + "." + fieldName;
                field.setAccessible(true);
                Class<?> clss = field.getType();
                if (judgeField(field, object)) {
                    if (!getAllowClass().contains(clss)) {
                        if (field.get(object) instanceof EnumEntry) {
                            setQueryValue(query, field.get(object), replaceDOT(pfildName), field.get(object));
                            continue;
                        }
                        packageRelevanceClassValue(query, field.get(object), pfildName);
                    }
                    if (getAllowClass().contains(clss)) {
                        setQueryValue(query, field.get(object), replaceDOT(pfildName), field.get(object));
                    }
                }
            }
        } catch (Exception var12) {
            throw new DataAccessException(var12.getMessage(), var12) {
            };
        }
    }

    private static void setQueryValueForAccurateSearch(Query query, Object object) {
        try {
            packageRelevanceClassValue(query, object, "");
        } catch (Exception var3) {
            throw new DataAccessException(var3.getMessage(), var3) {
            };
        }
    }

    private static void setQueryValueForStringSearch(Query query, Object object) {
        try {
            packageRelevanceClassValueForStringSearch(query, object, "");
        } catch (Exception var3) {
            throw new DataAccessException(var3.getMessage(), var3) {
            };
        }
    }

    private static void setQueryBuffer2(StringBuffer queryBuffer, String value, Object fieldValue) {
        if (fieldValue != null) {
            if (fieldValue instanceof String) {
                if (StringUtils.isNotNull((String) fieldValue)) {
                    queryBuffer.append(" ").append(value.trim()).append(" ");
                }
            } else {
                queryBuffer.append(" ").append(value.trim()).append(" ");
            }
        }
    }

    private static void setQueryValue(Query query, Object param, String key, Object value) {
        if (param instanceof String) {
            if (StringUtils.isNotNull((String) param) && value != null) {
                query.setParameter(key, value);
            }
        } else if (param != null && value != null) {
            query.setParameter(key, value);
        }
    }

    private static void setQueryPager(Query query, Pager pager) throws DataAccessException {
        if (pager != null) {
            query.setFirstResult(pager.getStartPos());
            query.setMaxResults(pager.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(12);
        }
    }
}