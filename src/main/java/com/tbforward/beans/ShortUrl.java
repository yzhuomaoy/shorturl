package com.tbforward.beans;

public class ShortUrl {
	private int id;
	private String code;
	private String url;
	
	public ShortUrl(){}
	
	public ShortUrl(String code, String url) {
		this.code = code;
		this.url = url;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "{ID=" + id + ",Code=" + code + ",Url=" + url + "}";
	}
}
