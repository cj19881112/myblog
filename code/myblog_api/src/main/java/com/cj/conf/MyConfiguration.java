package com.cj.conf;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cj.util.CaptchaUtil;
import com.cj.util.IllegalCaptchaModeException;
import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
@MapperScan("com.cj.*.dao")
public class MyConfiguration {
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
	 * 验证码生成模式: fix-固定 random-随机
	 */
	@Value("${kaptcha.mode:random}")
	private String mode;

	/**
	 * 验证码是否边框 yes|no
	 */
	@Value("${kaptcha.border:no}")
	private String border;

	/**
	 * 验证码字体颜色
	 */
	@Value("${kaptcha.textproducer.font.color:red}")
	private String fontColor;

	/**
	 * 验证码字体大小
	 */
	@Value("${kaptcha.textproducer.font.size:43}")
	private String fontSize;

	/**
	 * 验证码字体
	 */
	@Value("${kaptcha.textproducer.font.names:Arial}")
	private String fontName;

	/**
	 * 验证码图片宽度
	 */
	@Value("${kaptcha.image.width:135}")
	private String width;

	/**
	 * 验证码图片高度
	 */
	@Value("${kaptcha.image.height:50}")
	private String height;

	/**
	 * 验证码可选的字符
	 */
	@Value("${kaptcha.textproducer.char.string:ACDEFHKPRSTWX345679}")
	private String chars;

	/**
	 * 验证码噪声颜色
	 */
	@Value("${kaptcha.noise.color:black}")
	private String noiseColor;

	/**
	 * 生成的验证码长度
	 */
	@Value("${kaptcha.textproducer.char.length:4}")
	private String charLength;

	/**
	 * kaptcha配置类，生成servlet
	 * 
	 * @return kaptcha-servlet
	 * @throws ServletException
	 */
	@Bean(name = "captchaProducer")
	public ServletRegistrationBean servletRegistrationBean() throws ServletException {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/api/kaptcha.jpg");
		servlet.addInitParameter("kaptcha.border", border);
		servlet.addInitParameter("kaptcha.textproducer.font.color", fontColor);
		servlet.addInitParameter("kaptcha.image.width", width);
		servlet.addInitParameter("kaptcha.textproducer.char.string", chars);
		servlet.addInitParameter("kaptcha.image.height", height);
		servlet.addInitParameter("kaptcha.textproducer.font.size", fontSize);
		servlet.addInitParameter("kaptcha.noise.color", noiseColor);
		servlet.addInitParameter("kaptcha.textproducer.char.length", charLength);
		servlet.addInitParameter("kaptcha.textproducer.font.names", fontName);
		return servlet;
	}

	/**
	 * 系统初始化后将验证码的生成模式设置到util类中
	 * 
	 * @throws IllegalCaptchaModeException
	 *             如果配置文件設置的mode不时fix|random
	 */
	@PostConstruct
	public void initCaptchaMode() throws IllegalCaptchaModeException {
		CaptchaUtil.setCaptchaMode(mode);
	}
}
