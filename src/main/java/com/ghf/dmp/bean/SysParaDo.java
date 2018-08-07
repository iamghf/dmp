package com.ghf.dmp.bean;

import java.io.Serializable;

public class SysParaDo implements Serializable{
	private static final long serialVersionUID = 2885635601710519086L;
	
	private int id;
	private String code;
	private String type;
	private String vlaue;
	private int state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVlaue() {
		return vlaue;
	}
	public void setVlaue(String vlaue) {
		this.vlaue = vlaue;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
