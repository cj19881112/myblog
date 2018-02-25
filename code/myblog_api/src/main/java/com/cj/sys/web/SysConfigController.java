package com.cj.sys.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.conf.MyConfiguration;
import com.cj.sys.service.SysConfigService;
import com.cj.util.ApiRet;
import com.cj.util.CaptchaUtil;
import com.cj.util.PasswordUtil;
import com.cj.util.excep.CaptchaNotGenerateException;

@Controller
@RequestMapping("/api/sys")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping("/get_sys_conf")
	public @ResponseBody ApiRet<Map<String, String>> getSysConfig() {
		return ApiRet.ok(sysConfigService.getSysConfig());
	}

	@RequestMapping("/login")
	public @ResponseBody ApiRet<Void> login(String password, HttpSession session) throws CaptchaNotGenerateException {
		String captcha = CaptchaUtil.getCaptcha(session);
		String realPassword = PasswordUtil.encrypt(MyConfiguration.PASSWORD, captcha);
		if (realPassword.equals(password)) {
			return ApiRet.ok();
		} else {
			return ApiRet.err(ApiRet.ErrCode.ERR_PASSWORD);
		}
	}

	@ExceptionHandler(CaptchaNotGenerateException.class)
	public @ResponseBody ApiRet<Void> errorHandler() {
		return ApiRet.err(ApiRet.ErrCode.CAPTCHA_NOT_GENERATE);
	}
}
