package com.cj.conf;

import javax.servlet.ServletException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
@MapperScan("com.cj.*.dao")
public class MyConfiguration {
	public static final String PASSWORD = "hexijiehaha";

	public static final String CAPTCHA = "1234";

	@Value("${kaptcha.border:no}")
	private String border;

	@Value("${kaptcha.textproducer.font.color:ref}")
	private String fontColor;

	@Value("${kaptcha.textproducer.font.size:43}")
	private String fontSize;

	@Value("${kaptcha.textproducer.font.names:Arial}")
	private String fontName;

	@Value("${kaptcha.image.width:135}")
	private String width;

	@Value("${kaptcha.image.height:50}")
	private String height;

	@Value("${kaptcha.textproducer.char.string:ACDEFHKPRSTWX345679}")
	private String chars;

	@Value("${kaptcha.noise.color:black}")
	private String noiseColor;

	@Value("${kaptcha.textproducer.char.length:4}")
	private String charLength;

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

}
