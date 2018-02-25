package com.cj.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PasswordUtilTest {

	@Test
	public void testEncrypt_returnCrypted() {
		String password = PasswordUtil.encrypt("123456", "1111");
		assertThat("56E5511E916B2609BD21860063405DB1").isEqualTo(password);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncrypt_invalidPassword() {
		PasswordUtil.encrypt(null, "1111");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncrypt_invalidCaptcha() {
		PasswordUtil.encrypt("123456", null);
	}
}
