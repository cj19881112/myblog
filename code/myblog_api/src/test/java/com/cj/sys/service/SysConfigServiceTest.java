package com.cj.sys.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cj.sys.dao.SysConfigMapper;
import com.cj.sys.model.Conf;
import com.cj.sys.service.impl.SysConfigServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SysConfigServiceTest {
	@InjectMocks
	SysConfigServiceImpl sysConfigService;

	@Mock
	SysConfigMapper mapper;

	static final String KEY = "hello";
	static final String VAL = "world";

	@Test
	public void testGetSysConfig_returnOne() {
		List<Conf> mockData = new LinkedList<>();
		mockData.add(new Conf("hello", "world"));
		when(mapper.getSysConfig()).thenReturn(mockData);

		Map<String, String> conf = sysConfigService.getSysConfig();

		assertThat("返回的配置个数过多", conf.size(), is(1));
		assertThat("hello对应的配置应该是world", conf.get(KEY), is(VAL));
	}

	@Test
	public void testGetSysConfig_returnEmpty() {
		when(mapper.getSysConfig()).thenReturn(null);

		Map<String, String> conf = sysConfigService.getSysConfig();

		assertThat("返回的配置个数过多", conf.size(), is(0));
	}
}
