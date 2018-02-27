package com.cj.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SqlUtilTest {

	@Test
	public void testLike() {
		assertThat(SqlUtil.like("a")).isEqualTo("%a%");
		assertThat(SqlUtil.like("")).isEqualTo(null);
		assertThat(SqlUtil.like(null)).isEqualTo(null);
	}
}
