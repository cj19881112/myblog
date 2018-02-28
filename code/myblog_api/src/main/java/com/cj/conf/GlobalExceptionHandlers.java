package com.cj.conf;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cj.util.ApiRet;
import com.cj.util.ApiRet.ErrCode;
import com.cj.util.excep.NotYetLoginException;

@ControllerAdvice
public class GlobalExceptionHandlers {

	/**
	 * 处理参数错误异常，返回对应的错误码
	 * 
	 * @return 对应的错误码
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody ApiRet<Void> illegalArgumentExceptionHandler() {
		return ApiRet.err(ErrCode.ILLEGAL_ARGUMENT);
	}

	/**
	 * 处理参数错误异常，返回对应的错误码
	 * 
	 * @return 对应的错误码
	 */
	@ExceptionHandler(NotYetLoginException.class)
	public @ResponseBody ApiRet<Void> notYetLoginExceptionHandler() {
		return ApiRet.err(ErrCode.NOT_LOGIN);
	}
}
