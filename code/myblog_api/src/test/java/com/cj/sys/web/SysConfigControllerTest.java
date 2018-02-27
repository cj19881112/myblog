package com.cj.sys.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cj.conf.MyConfiguration;
import com.cj.sys.service.SysConfigService;
import com.cj.util.CaptchaUtil;
import com.cj.util.PasswordUtil;
import com.google.code.kaptcha.Constants;

@RunWith(SpringRunner.class)
@WebMvcTest(SysConfigController.class)
public class SysConfigControllerTest {
	@Autowired
	private MockMvc mvc;

	private MockHttpSession session = new MockHttpSession();

	@MockBean
	private SysConfigService mockService;

	@Test
	public void testGetSysConfig_returnAll() throws Exception {
		Map<String, String> mockData = new HashMap<>();
		mockData.put("hello", "world");
		when(mockService.getSysConfig()).thenReturn(mockData);
		this.mvc.perform(get("/api/sys/get_sys_conf")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("0000"))).andExpect(jsonPath("$.data.hello", is("world")));
	}

	@Test
	public void testLogin_success() throws Exception {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		
		String captcha = "1111", password = MyConfiguration.PASSWORD;
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha);

		this.mvc.perform(
				post("/api/sys/login").param("password", PasswordUtil.encrypt(password, captcha)).session(session))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code", is("0000")));

		assertThat(session.getAttribute(MyConfiguration.SESSION_KEY)).isNotNull();
	}

	@Test
	public void testLogin_errorCapcha() throws Exception {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		
		String captcha = "1111", password = MyConfiguration.PASSWORD;
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "1112");

		this.mvc.perform(
				post("/api/sys/login").param("password", PasswordUtil.encrypt(password, captcha)).session(session))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code", is("0001")));
	}

	@Test
	public void testLogin_errorPassword() throws Exception {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		
		String captcha = "1111";
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha);
		this.mvc.perform(post("/api/sys/login").param("password", PasswordUtil.encrypt("-1", captcha)).session(session))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code", is("0001")));
	}

	@Test
	public void testLogin_chaptchaNotGenerate() throws Exception {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.RANDOM);
		
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		this.mvc.perform(post("/api/sys/login").param("password", PasswordUtil.encrypt("-1", "1234")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.code", is("0002")));
	}
}
