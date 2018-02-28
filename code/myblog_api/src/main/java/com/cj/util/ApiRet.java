package com.cj.util;

/**
 * 通用返回辅助类
 *
 * @param <T>
 *            返回的数据的类型
 */
public class ApiRet<T> {
	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String msg;

	/**
	 * 如果是分页请求，这个字段表示总条目，否则该字段无意义
	 */
	private int total;

	/**
	 * 返回的数据
	 */
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

	/**
	 * 返回成功
	 * 
	 * @param data
	 *            返回的数据
	 * @return
	 */
	public static <T> ApiRet<T> ok(T data) {
		return new ApiRet<T>(ErrCode.SUCC.getCode(), ErrCode.SUCC.getMsg(), data);
	}

	/**
	 * 返回成功-分页模式
	 * 
	 * @param total
	 *            总条数
	 * @param data
	 *            返回的数据
	 * @return
	 */
	public static <T> ApiRet<T> ok(int total, T data) {
		return new ApiRet<T>(ErrCode.SUCC.getCode(), ErrCode.SUCC.getMsg(), total, data);
	}

	/**
	 * 返回失败
	 * 
	 * @param code
	 *            错误码
	 * @return
	 */
	public static ApiRet<Void> err(ErrCode code) {
		return new ApiRet<Void>(code.getCode(), code.getMsg(), null);
	}

	/**
	 * 返回成功 无数据
	 * 
	 * @return
	 */
	public static ApiRet<Void> ok() {
		return ok(null);
	}

	public enum ErrCode {
		SUCC("ok", "成功"),
		NOT_LOGIN("perm.not_login", "您还未登录"),
		ERR_PASSWORD("perm.error_password", "错误的密码或者验证码"),
		CAPTCHA_NOT_GENERATE("perm.invalid_captcha", "验证码未生成"),
		ILLEGAL_ARGUMENT("sys.invalid_params", "参数错误"),
		NOT_FOUND("sys.not_found", "未找到请求的数据");

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
