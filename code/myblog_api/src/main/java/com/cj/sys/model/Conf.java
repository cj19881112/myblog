package com.cj.sys.model;

public class Conf {
	private int confId;
	private String confName;
	private String confVal;
	
	public Conf() {
	}

	public Conf(String confName, String confVal) {
		this.confName = confName;
		this.confVal = confVal;
	}

	public int getConfId() {
		return confId;
	}

	public void setConfId(int confId) {
		this.confId = confId;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	public String getConfVal() {
		return confVal;
	}

	public void setConfVal(String confVal) {
		this.confVal = confVal;
	}
}
