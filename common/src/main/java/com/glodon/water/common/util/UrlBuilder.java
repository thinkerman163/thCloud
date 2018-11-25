package com.glodon.water.common.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;


/**
 * OAuth API地址拼装
 * 
 * @author zhouxing 2014-3-26
 */
public class UrlBuilder {

    private static final String ACCOUNT_SERVER_URL = "https://account.glodon.com";
    private static final String CAS_ACCOUNT_URL = "https://account.glodon.com/cas";
    private static final String API_ACCOUNT_URL = "https://account.glodon.com/api";

    private static final String LOGIN_TOKEN_URL    = "{0}/api/login_token?access_token={1}";

    private static final String ACCESS_TOKEN_URL   = "{0}/oauth2/token?grant_type={1}";
    private static final String REFRESH_ACCESS_TOKEN_URL   = "{0}/oauth2/token";
    private static final String CHECK_TOKEN_URL   = "{0}/oauth2/check_token?token={1}";

    private static final String LOGIN_REDIRECT_URL = "{0}/tokenLogin?token={1}&target_url={2}";

    private static final String GET_USER_URL       = "{0}/account/user?access_token={1}";
    
    private static final String NAME_CHECK_URL       = "{0}/identity?id={1}";
    private static final String USER_FETCHER_URL     = "{0}/userid?identity={1}";

    //正式地址
    private static final String CAPTCHA_FETCHER_URL  = "{0}/captcha";
    private static final String SIGN_UP_URL          = "{0}/signup";
    private static final String SMS_CODE_FETCHER_URL = "{0}/code?mobile={1}&type=signup";
    
    private static final String USER_INFO = "{0}/api/userinfo?access_token={1}";
    
    private static final String LOCAL_APP_URL = "/redirectIndex";
    private static final String USER_TICKET = "{0}/serviceValidate?service_key={1}&ticket={2}";


    /**
     * 拼装Login跳转到account的URL
     * 
     * @return 跳转到account的URL
     * @throws UnsupportedEncodingException u
     */
    public static String buildLoginRedirectUrl(String loginToken,String LocalAppUrl) throws UnsupportedEncodingException {
        return MessageFormat.format(LOGIN_REDIRECT_URL, ACCOUNT_SERVER_URL, loginToken,
                                    URLEncoder.encode(LocalAppUrl + LOCAL_APP_URL, "UTF-8"));
    }

    /**
     * 拼装Login获取login_Token的跳转account的URL
     * 
     * @return Login获取login_Token的跳转account的URL
     */
    public static String buildLoginTokenUrl(String accessToken) {
        return MessageFormat.format(LOGIN_TOKEN_URL, ACCOUNT_SERVER_URL, accessToken);
    }

    /**
     * 拼装用户凭证方式获取token的URL
     * 
     * @return token的URL
     */
    public static String buildGrantByPasswordUrl() {
        return MessageFormat.format(ACCESS_TOKEN_URL, ACCOUNT_SERVER_URL, "password");
    }

    public static String buildRefreshTokenUrl() {
        return MessageFormat.format(REFRESH_ACCESS_TOKEN_URL, ACCOUNT_SERVER_URL);
    }

    public static String buildCheckTokenUrl(String accessToken) {
        return MessageFormat.format(CHECK_TOKEN_URL, ACCOUNT_SERVER_URL, accessToken);
    }

    /**
     * 拼装用户凭证方式获取token的URL
     * 
     * @return token的URL
     */
    public static String buildGetUserUrl(String accessToken) {
        return MessageFormat.format(GET_USER_URL, ACCOUNT_SERVER_URL, accessToken);
    }

    /**
     * 拼装检查用户名的URL
     * 
     * @param name 用户名
     * @return 检查用户名的URL
     */
    public static String buildNameCheckerUrl(String name) {
        return MessageFormat.format(NAME_CHECK_URL, API_ACCOUNT_URL, name);
    }

    /**
     * 拼装根据用户名获取用户信息的URL
     * 
     * @param name 用户名
     * @return 获取用户信息的URL
     */
    public static String buildUserFetcherUrl(String name) {
        return MessageFormat.format(USER_FETCHER_URL, API_ACCOUNT_URL, name);
    }

    /**
     * 拼装获取图片验证码的URL
     * 
     * @return 获取图片验证码的URL
     */
    public static String buildCaptchaFetcherUrl() {
        return MessageFormat.format(CAPTCHA_FETCHER_URL, API_ACCOUNT_URL);
    }

    /**
     * 拼装发送短信验证码的URL
     * 
     * @param mobile 手机
     * @return 发送短信验证码的URL
     */
    public static String buildSmsCodeFetcherUrl(String mobile) {
        return MessageFormat.format(SMS_CODE_FETCHER_URL, API_ACCOUNT_URL, mobile);
    }

    /**
     * 拼装用户注册的URL
     * 
     * @return 用户注册的URL
     */
    public static String buildSignupUrl() {
        return MessageFormat.format(SIGN_UP_URL, API_ACCOUNT_URL);
    }
    
    /**
     * 根据access_token获取用户信息
     * 
     * @return 用户信息
     */
    public static String buildUserInfoUrl(String accessToken) {
        return MessageFormat.format(USER_INFO, ACCOUNT_SERVER_URL,accessToken);
    }

    /**
     * 获取用户ticket
     */
    public static String bulidUserTicket(String ticket) {
        return MessageFormat.format(USER_TICKET, CAS_ACCOUNT_URL, BasicAuthBuilder.getServiceKey(),ticket);
    }
    /**
     * 拼装本地应用的路径
     * 
     */
    @SuppressWarnings("unused")
	private static String buildLocalAppUrl(HttpServletRequest request) {
       // HttpServletRequest request = ServletContextUtil.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        if (80 != request.getServerPort()) {
            sb.append(":");
            sb.append(request.getServerPort());
        }
        sb.append(request.getContextPath());
        return sb.toString();
    }
}
