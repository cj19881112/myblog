package com.cj.util;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import com.cj.util.excep.CaptchaNotGenerateException;
import com.cj.util.excep.IllegalCaptchaModeException;
import com.google.code.kaptcha.Constants;

public class CaptchaUtilTest {

	/**
	 * 固定的验证码
	 * 
	 * @throws CaptchaNotGenerateException
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testGetCaptcha_returnFix() throws CaptchaNotGenerateException, IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);
		String capcha = CaptchaUtil.getCaptcha(new MockHttpSession());
		assertThat(capcha).isEqualTo(com.cj.util.Constants.CAPTCHA);

		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);
		HttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "9712");
		capcha = CaptchaUtil.getCaptcha(session);
		assertThat(capcha).isEqualTo(com.cj.util.Constants.CAPTCHA);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testGetCaptcha_returnRandom() throws CaptchaNotGenerateException, IllegalCaptchaModeException {
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
	 * @throws IllegalCaptchaModeException
	 */
	@Test(expected = CaptchaNotGenerateException.class)
	public void testGetCaptcha_throwCapchaNotGenerateException()
			throws CaptchaNotGenerateException, IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		HttpSession session = new MockHttpSession();
		CaptchaUtil.getCaptcha(session);
	}

	/**
	 * 随机验证码,返回session中的验证码
	 * 
	 * @param captcha
	 * @throws CaptchaNotGenerateException
	 * @throws IllegalCaptchaModeException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetCaptcha_throwIllegalArgumentException()
			throws CaptchaNotGenerateException, IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		CaptchaUtil.getCaptcha(null);
	}

	@Test
	public void testSetCaptchaMode() throws CaptchaNotGenerateException, IllegalCaptchaModeException {
		String captcha = "1080";
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha);
		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);
		assertThat(CaptchaUtil.getCaptcha(session)).isEqualTo(com.cj.util.Constants.CAPTCHA);

		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		assertThat(CaptchaUtil.getCaptcha(session)).isEqualTo(captcha);
	}

	@Test(expected = IllegalCaptchaModeException.class)
	public void testSetCaptchaMode_illegalMode() throws IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX + "__");
	}
}
