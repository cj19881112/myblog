package com.cj.util;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.cj.conf.MyConfiguration;
import com.cj.util.excep.CaptchaNotGenerateException;
import com.google.code.kaptcha.Constants;

public abstract class CaptchaUtil {
	public static final String FIX = "fix";

	public static final String RANDOM = "random";

	private static String captchaMode = RANDOM;

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

	public static void setCaptchaMode(String captchaMode) throws IllegalCaptchaModeException {
		if (captchaMode.equals(FIX) || captchaMode.equals(RANDOM)) {
			CaptchaUtil.captchaMode = captchaMode;
		} else {
			throw new IllegalCaptchaModeException();
		}
	}
}
