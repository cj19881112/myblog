package com.cj.util;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import com.cj.conf.MyConfiguration;
import com.cj.util.excep.CaptchaNotGenerateException;
import com.google.code.kaptcha.Constants;

public class CaptchaUtilTest {

	/**
	 * 固定的验证码
	 * 
	 * @throws CaptchaNotGenerateException
	 */
	@Test
	public void testGetCaptcha_returnFix() throws CaptchaNotGenerateException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);
		String capcha = CaptchaUtil.getCaptcha(new MockHttpSession());
		assertThat(capcha).isEqualTo(MyConfiguration.CAPTCHA);

		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);
		HttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "9712");
		capcha = CaptchaUtil.getCaptcha(session);
		assertThat(capcha).isEqualTo(MyConfiguration.CAPTCHA);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 */
	@Test
	public void testGetCaptcha_returnRandom() throws CaptchaNotGenerateException {
		String captchaMock = "9712";
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		HttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captchaMock);

		String capcha = CaptchaUtil.getCaptcha(session);

		assertThat(captchaMock).isEqualTo(capcha);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 */
	@Test(expected = CaptchaNotGenerateException.class)
	public void testGetCaptcha_throwCapchaNotGenerateException() throws CaptchaNotGenerateException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		HttpSession session = new MockHttpSession();
		CaptchaUtil.getCaptcha(session);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetCaptcha_throwIllegalArgumentException() throws CaptchaNotGenerateException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		CaptchaUtil.getCaptcha(null);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetCaptcha_throwIllegalStateException() throws CaptchaNotGenerateException {
		String captchaMock = "9712";
		CaptchaUtil.setCaptchaMode("-1");
		HttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captchaMock);
		CaptchaUtil.getCaptcha(session);
	}
}
