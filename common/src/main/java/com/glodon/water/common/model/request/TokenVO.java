package com.glodon.water.common.model.request;

import com.glodon.water.common.common.enumpo.TokenTypeEnum;

public class TokenVO {
	//用户id
	private Integer userid;
	//token时间戳
	private long timestamp;
	
	//登录时间戳
    private long loginTimestamp;
		
	//token类型
	private TokenTypeEnum tokenTypeEnum;
	
	//来源
	String source;

	public Integer getUserId() {
		return userid;
	}

	public void setUserId(Integer userid) {
		this.userid = userid;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	

	public long getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(long loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public TokenTypeEnum getTokenTypeEnum() {
		return tokenTypeEnum;
	}

	public void setTokenTypeEnum(TokenTypeEnum tokenTypeEnum) {
		this.tokenTypeEnum = tokenTypeEnum;
	}

	
}
