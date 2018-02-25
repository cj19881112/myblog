package com.cj.util;

public class ApiRet<T> {
	public String code;
	public String msg;
	public T data;

	public static final String SUCC = "0000";

	public ApiRet() {
	}

	public ApiRet(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T> ApiRet<T> ok(T data) {
		return new ApiRet<T>(SUCC, "", data);
	}

	public static ApiRet<Void> err(ErrCode code) {
		return new ApiRet<Void>(code.getCode(), code.getMsg(), null);
	}

	public static ApiRet<Void> ok() {
		return ok(null);
	}

	public enum ErrCode {
		ERR_PASSWORD("0001", "错误的密码或者验证码"), 
		CAPTCHA_NOT_GENERATE("0002", "验证码未生成");

		private String code;
		private String msg;

		ErrCode(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
}
