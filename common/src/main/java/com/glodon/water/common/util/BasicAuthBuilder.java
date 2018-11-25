package com.glodon.water.common.util;

/**
 * Basic的头信息
 * 
 * @author zhouxing 2014-3-26
 */
public class BasicAuthBuilder {

    // 科云项目-用户中心-正式app_key和app_secret
    private static final String SERVICE_KEY = "t3SJKBKyw3qmHLrwb4V0v2eMtbLPWqkD";
    private static final String SERVER_SECRET = "PvzVFOJhmUCLKP1PmbEUWywc5pfHlfx0";

    /**
     * 创建Basic认证头信息中的Key
     * 
     */
    public static String headerKey() {
        return "Authorization";
    }

    /**
     * 创建Basic认证头信息中的Value
     * 
     */
    public static String headerValue() {
        String credential = SERVICE_KEY + ":" + SERVER_SECRET;
        String encoder = Base64.encodeToString(credential);
        return "Basic " + encoder;
    }

    /**
     * 创建Bearer认证头信息中的Value, 适用于已经登陆的接口
     *
     */
    public static String bearerHeaderValue(String accessToken) {
        return "Bearer " + accessToken;
    }
    
    /**
     * 创建Basic认证头信息中的客户端应用的token
     * 
     */
    public static String headerClientCredential(String clientCredential) {
        return "Basic " + clientCredential;
    }

    public static String getServiceKey(){
        return SERVICE_KEY;
    }


}
