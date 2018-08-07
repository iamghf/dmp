package com.ghf.order.bean;

import java.io.Serializable;

public class MenuInfo implements Serializable {

	private static final long serialVersionUID = 8244028407118429660L;

	private long id;
	private String name;
	private long price;

	public MenuInfo(long id, String name, long price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
