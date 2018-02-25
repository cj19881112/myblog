package com.cj.sys.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cj.sys.model.Conf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysConfigMapperTest {

	@Autowired
	SysConfigMapper mapper;

	@Test
	public void testGetSysConfig_returnPredefine() {
		List<Conf> confs = mapper.getSysConfig();
		assertThat("返回的配置的值不正确", confs.get(0).getConfVal(), is("world"));
		assertThat("返回的配置的名称不正确", confs.get(0).getConfName(), is("hello"));
	}
}
