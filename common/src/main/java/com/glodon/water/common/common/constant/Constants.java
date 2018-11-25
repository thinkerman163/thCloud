package com.glodon.water.common.common.constant;

/**
 * @author: hxy
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 * @date: 2017/10/24 10:15
 */
public class Constants {

    public static final String SUCCESS_CODE = "100";
    public static final String SUCCESS_MSG = "请求成功";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    public static final String SESSION_USER_PERMISSION = "userPermission";
    
    /**
     * token的key值
     */
    public static final String PARAM_TOKEN = "token";
    public static final String IN_PARAM_TOKEN = "innertoken";
	
    
    /**
     * ajax返回成功
     */
    public static final String SC_AJAX_SUCCESS = "success";
    public static final String SC_AJAX_ERROR = "error";
}