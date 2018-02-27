package com.cj.blog.model;

public enum EnumBoolean {
	TRUE("1"), FALSE("0");

	private String code;

	private EnumBoolean(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
