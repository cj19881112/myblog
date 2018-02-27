package com.cj.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import org.junit.Test;

public class DateUtilTest {

	/**
	 * 返回的当前时间不包含毫秒
	 */
	@Test
	public void testNow_returnNoMs() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.now());

		assertThat(cal.get(Calendar.MILLISECOND)).isEqualTo(0);
	}

}
