package com.glodon.water.common.model.request;

public class LoginVO {
	private String username;
	private String password;
	private Integer logintype;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getLogintype() {
		return logintype;
	}

	public void setLogintype(Integer logintype) {
		this.logintype = logintype;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
