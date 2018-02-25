package com.cj.sys;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.RestHelper;
import com.cj.util.ApiRet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SysConfigControllerIT {

	@Autowired
	private TestRestTemplate rest;

	@Test
	public void testGetSysConfig_returnAllConfig() throws Exception {
		ResponseEntity<ApiRet<Map<String, String>>> ret = new RestHelper(rest).getForEntity("/api/sys/get_sys_conf",
				new ParameterizedTypeReference<ApiRet<Map<String, String>>>() {
				});
		assertThat(ret.getBody().code).isEqualTo(ApiRet.SUCC);
		assertThat(ret.getBody().data.get("hello")).isEqualTo("world");
	}
}
