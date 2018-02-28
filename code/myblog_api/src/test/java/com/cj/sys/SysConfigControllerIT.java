package com.cj.sys;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.RestHelper;
import com.cj.util.ApiRet;
import com.cj.util.CaptchaUtil;
import com.cj.util.Constants;
import com.cj.util.PasswordUtil;
import com.cj.util.excep.IllegalCaptchaModeException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SysConfigControllerIT {

	@Autowired
	private TestRestTemplate rest;

	/**
	 * 测试登录
	 * 
	 * @throws IllegalCaptchaModeException
	 */
	@Test
	public void testLogin_returnSuccess() throws IllegalCaptchaModeException {
		testLogin_returnSuccess(new RestHelper(rest));
	}

	public static void testLogin_returnSuccess(RestHelper restHelper) throws IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(CaptchaUtil.FIX);

		String password = PasswordUtil.encrypt(Constants.PASSWORD, Constants.CAPTCHA);

		Map<String, Object> params = new HashMap<>();
		params.put("password", password);
		ResponseEntity<ApiRet<Void>> ret = restHelper.postForEntity("/api/sys/login", params,
				new ParameterizedTypeReference<ApiRet<Void>>() {
				});
		assertThat(ret.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
	}

	/**
	 * 获取所有配置文件
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSysConfig_returnAllConfig() throws Exception {
		ResponseEntity<ApiRet<Map<String, String>>> ret = new RestHelper(rest).getForEntity("/api/sys/get_sys_conf",
				new ParameterizedTypeReference<ApiRet<Map<String, String>>>() {
				});
		assertThat(ret.getBody().getCode()).isEqualTo(ApiRet.ErrCode.SUCC.getCode());
		assertThat(ret.getBody().getData().get("hello")).isEqualTo("world");
	}

	/**
	 * 获取图片验证码
	 */
	@Test
	public void testKaptcha_returnImg() {
		ResponseEntity<byte[]> resp = requestKaptcha("");
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(resp.getBody().length).isGreaterThan(0);
	}

	/**
	 * 获取两次图片验证码不相同,因为没有controller，所以在这里测试边界
	 */
	@Test
	public void testKaptcha_returnDifferentImg() {
		ResponseEntity<byte[]> resp1 = requestKaptcha("1");
		assertThat(resp1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(resp1.getBody().length).isGreaterThan(0);

		ResponseEntity<byte[]> resp2 = requestKaptcha("2");
		assertThat(resp2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(resp2.getBody().length).isGreaterThan(0);

		assertThat(!Arrays.equals(resp1.getBody(), resp2.getBody())).isTrue();
	}

	/**
	 * 获取图片验证码
	 * 
	 * @param r
	 *            随机数
	 * @return 图片的二级制数组
	 */
	private ResponseEntity<byte[]> requestKaptcha(String r) {
		rest.getRestTemplate().getMessageConverters().add(new ByteArrayHttpMessageConverter());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		Map<String, Object> params = new HashMap<>();
		params.put("_r", r);
		HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(params), headers);
		ResponseEntity<byte[]> resp = rest.exchange("/api/kaptcha.jpg", HttpMethod.GET, entity, byte[].class);
		return resp;
	}
}
