package com.cj.util;

public class ApiRet<T> {
	private String code;
	private String msg;
	private int total;
	private T data;

	public ApiRet() {
	}

	public ApiRet(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ApiRet(String code, String msg, int total, T data) {
		this.code = code;
		this.msg = msg;
		this.total = total;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> ApiRet<T> ok(T data) {
		return new ApiRet<T>(ErrCode.SUCC.getCode(), ErrCode.SUCC.getMsg(), data);
	}

	public static <T> ApiRet<T> ok(int total, T data) {
		return new ApiRet<T>(ErrCode.SUCC.getCode(), ErrCode.SUCC.getMsg(), total, data);
	}

	public static ApiRet<Void> err(ErrCode code) {
		return new ApiRet<Void>(code.getCode(), code.getMsg(), null);
	}

	public static ApiRet<Void> ok() {
		return ok(null);
	}

	public enum ErrCode {
		SUCC("0000", "成功"), ERR_PASSWORD("0001", "错误的密码或者验证码"), CAPTCHA_NOT_GENERATE("0002",
				"验证码未生成"), ILLEGAL_ARGUMENT("0004", "参数错误");

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
