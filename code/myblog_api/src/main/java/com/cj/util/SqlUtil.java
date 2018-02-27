package com.cj.util;

import org.springframework.util.StringUtils;

public abstract class SqlUtil {
	public static String like(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		return "%" + key + "%";
	}
}
