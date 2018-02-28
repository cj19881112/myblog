package com.cj.conf;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cj.util.CaptchaUtil;
import com.cj.util.excep.IllegalCaptchaModeException;
import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter {

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

	/**
	 * 注册所有的拦截器，包括： 登录拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
