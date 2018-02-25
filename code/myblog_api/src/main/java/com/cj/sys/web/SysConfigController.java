package com.cj.sys.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.sys.service.SysConfigService;
import com.cj.util.ApiRet;

@Controller
@RequestMapping("/api/sys")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping("/get_sys_conf")
	public @ResponseBody ApiRet<Map<String, String>> getSysConfig() {
		return ApiRet.ok(sysConfigService.getSysConfig());
	}
}
