package com.cj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cj.sys.dao.SysConfigMapper;
import com.cj.sys.model.Conf;
import com.cj.sys.service.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigMapper mapper;

	/**
	 * 获取系统配置，如果没有配置返回空的集合
	 */
	@Override
	public Map<String, String> getSysConfig() {
		List<Conf> confs = mapper.getSysConfig();
		Map<String, String> confMap = new HashMap<>();
		if (null != confs) {
			for (Conf c : confs) {
				confMap.put(c.getConfName(), c.getConfVal());
			}
		}
		return confMap;
	}

}
