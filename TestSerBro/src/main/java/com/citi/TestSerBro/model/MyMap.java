package com.citi.TestSerBro.model;

public class MyMap {
	 private int instance;
	 private String data;
	 
	public MyMap(int instance, String data) {
		super();
		this.instance = instance;
		this.data = data;
	}
	public int getInstance() {
		return instance;
	}
	public void setInstance(int instance) {
		this.instance = instance;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
