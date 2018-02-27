package com.cj.util;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.cj.conf.MyConfiguration;
import com.cj.util.excep.CaptchaNotGenerateException;
import com.google.code.kaptcha.Constants;

public abstract class CaptchaUtil {
	/**
	 * 固定验证码模式
	 */
	public static final String FIX = "fix";

	/**
	 * 随机验证码模式
	 */
	public static final String RANDOM = "random";

	/**
	 * 系统当前的验证码模式，默认是随机的
	 */
	private static String captchaMode = RANDOM;

	/**
	 * 获取session中的验证码，如果当前是固定模式，返回固定的验证码，否则返回session中的验证码
	 * @param session 用户会话
	 * @return 如果session不为空，返回session中的验证码
	 * @throws CaptchaNotGenerateException session中不存在验证码
	 */
	public static String getCaptcha(HttpSession session) throws CaptchaNotGenerateException {
		if (null == session) {
			throw new IllegalArgumentException();
		}

		if (!captchaMode.equals(FIX) && !captchaMode.equals(RANDOM)) {
			throw new IllegalStateException();
		}

		String captcha = null;
		if (captchaMode.equalsIgnoreCase(FIX)) {
			captcha = MyConfiguration.CAPTCHA;
		} else {
			captcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		}

		if (StringUtils.isEmpty(captcha)) {
			throw new CaptchaNotGenerateException();
		}

		return captcha;
	}

	/**
	 * 设置系统当前的验证码生成模式
	 * @param captchaMode 验证码生成模式
	 * @throws IllegalCaptchaModeException 设置的模式不正确
	 */
	public static void setCaptchaMode(String captchaMode) throws IllegalCaptchaModeException {
		if (captchaMode.equals(FIX) || captchaMode.equals(RANDOM)) {
			CaptchaUtil.captchaMode = captchaMode;
		} else {
			throw new IllegalCaptchaModeException();
		}
	}
}
