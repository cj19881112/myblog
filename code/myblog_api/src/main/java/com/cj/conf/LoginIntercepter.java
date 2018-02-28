package com.cj.conf;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cj.sys.model.NeedAuth;
import com.cj.util.excep.NotYetLoginException;

public class LoginIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Method method = ((HandlerMethod) handler).getMethod();
		if (method.getAnnotation(NeedAuth.class) != null) {
			String sessionKey = (String) request.getSession().getAttribute(com.cj.util.Constants.SESSION_KEY);
			if (StringUtils.isEmpty(sessionKey)) {
				throw new NotYetLoginException();
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
