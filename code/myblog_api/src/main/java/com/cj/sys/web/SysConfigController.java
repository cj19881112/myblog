package com.cj.sys.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.sys.service.SysConfigService;
import com.cj.util.ApiRet;
import com.cj.util.CaptchaUtil;
import com.cj.util.Constants;
import com.cj.util.PasswordUtil;
import com.cj.util.excep.CaptchaNotGenerateException;

@Controller
@RequestMapping("/api/sys")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 获取所有的系统配置
	 * 
	 * @return 所有系统配置
	 */
	@RequestMapping("/get_sys_conf")
	public @ResponseBody ApiRet<Map<String, String>> getSysConfig() {
		return ApiRet.ok(sysConfigService.getSysConfig());
	}

	/**
	 * 用户登录
	 * 
	 * @param password
	 *            用户密码
	 * @param session
	 *            用户回话
	 * @return
	 * @throws CaptchaNotGenerateException
	 *             用户还未获取验证码
	 */
	@RequestMapping("/login")
	public @ResponseBody ApiRet<Void> login(String password, HttpSession session) throws CaptchaNotGenerateException {
		String captcha = CaptchaUtil.getCaptcha(session);
		String realPassword = PasswordUtil.encrypt(Constants.PASSWORD, captcha);
		if (realPassword.equals(password)) {
			session.setAttribute(Constants.SESSION_KEY, Constants.SESSION_KEY);
			return ApiRet.ok();
		} else {
			return ApiRet.err(ApiRet.ErrCode.ERR_PASSWORD);
		}
	}

	/**
	 * 处理为生成验证码异常，返回对应的错误码
	 * 
	 * @return
	 */
	@ExceptionHandler(CaptchaNotGenerateException.class)
	public @ResponseBody ApiRet<Void> errorHandler() {
		return ApiRet.err(ApiRet.ErrCode.CAPTCHA_NOT_GENERATE);
	}
}
