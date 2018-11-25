package com.glodon.water.common.common.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Package:     com.glodon.water.common.vo : GlodonAccountGrantVO
 * Description: 广联达账号认证值对象
 *
 * @author zhangjq 2018/1/25
 * @version 1.0
 */
public class GlodonAccountGrantVO implements Serializable {
    private static final long serialVersionUID = 3817247774058366726L;

    @SerializedName(value = "accessToken", alternate = {"access_token"})
    private String accessToken;
    @SerializedName(value = "refreshToken", alternate = {"refresh_token"})
    private String refreshToken;
    @SerializedName(value = "tokenType", alternate = {"token_type"})
    private String tokenType;
    @SerializedName(value = "expiresIn", alternate = {"expires_in"})
    private Long expiresIn;
    private String scope;
    private String error;
    @SerializedName(value = "errorDescription", alternate = {"error_description"})
    private String errorDescription;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
