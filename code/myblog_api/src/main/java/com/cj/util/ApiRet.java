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

	public static ApiRet<Void> err(String code, String msg) {
		return new ApiRet<Void>(code, msg, null);
	}
}
