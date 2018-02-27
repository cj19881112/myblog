package com.cj.util;

import org.springframework.util.DigestUtils;

public abstract class PasswordUtil {

	/**
	 * 加密用户密码，加密方式为，MD5(MD5(U)+MD5(C)),其中U为用户密码,C为验证码，MD5的结果字符均为大写
	 * 
	 * @param password
	 *            用户密码
	 * @param captcha
	 *            图片验证码
	 * @return 加密后的密码，32位大写字母
	 */
	public static String encrypt(String password, String captcha) {
		if (null == password || null == captcha) {
			throw new IllegalArgumentException();
		}

		String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
		String kaptchaMd5 = DigestUtils.md5DigestAsHex(captcha.getBytes()).toUpperCase();

		return DigestUtils.md5DigestAsHex((passwordMd5 + kaptchaMd5).getBytes()).toUpperCase();
	}

}
