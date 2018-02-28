package com.cj.util;

public abstract class Constants {
	/**
	 * 默认密码
	 */
	public static final String PASSWORD = "hexijiehaha";

	/**
	 * 如果系统配置成不动态生成验证码，固定返回
	 */
	public static final String CAPTCHA = "1234";

	/**
	 * 登陆后，session中用户登录的凭证
	 */
	public static final String SESSION_KEY = "myblog_session_key";

	/**
	 * 文章的摘要的长度
	 */
	public static int BRIEF_LENGTH = 32;

}
