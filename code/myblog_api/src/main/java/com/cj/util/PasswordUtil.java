package com.cj.util;

import org.springframework.util.DigestUtils;

public abstract class PasswordUtil {

	public static String encrypt(String password, String captcha) {
		if (null == password || null == captcha) {
			throw new IllegalArgumentException();
		}

		String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
		String kaptchaMd5 = DigestUtils.md5DigestAsHex(captcha.getBytes()).toUpperCase();

		return DigestUtils.md5DigestAsHex((passwordMd5 + kaptchaMd5).getBytes()).toUpperCase();
	}

}
