package com.cj.util;

import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {
	public static Date now() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
