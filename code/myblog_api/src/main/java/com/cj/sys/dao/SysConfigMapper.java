package com.cj.sys.dao;

import java.util.List;

import com.cj.sys.model.Conf;

public interface SysConfigMapper {
	/**
	 * 获取配置表总的数据
	 * @return
	 */
	List<Conf> getSysConfig();
}
