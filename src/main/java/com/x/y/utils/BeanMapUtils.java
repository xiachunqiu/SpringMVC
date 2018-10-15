package com.x.y.utils;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class BeanMapUtils {
	public static Object mapToBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = beanClass.newInstance();
		BeanUtils.populate(obj, map);
		return obj;
	}

	public static Map<?, ?> BeanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		return new BeanMap(obj);
	}
}