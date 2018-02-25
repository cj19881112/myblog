package com.cj.sys.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cj.sys.service.SysConfigService;

@RunWith(SpringRunner.class)
@WebMvcTest(SysConfigController.class)
public class SysConfigControllerTest {
	@Autowired
	private MockMvc mvc;

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

}
