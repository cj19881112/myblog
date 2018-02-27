package com.cj.util;

import org.springframework.util.StringUtils;

public abstract class SqlUtil {
	/**
	 * 再字符串的两边加上%
	 * 
	 * @param key
	 *            需要处理的字符串
	 * @return 如果字符串为null则返回null，否则返回字符串两边加上%
	 */
	public static String like(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		return "%" + key + "%";
	}
}
