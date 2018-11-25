
package com.glodon.water.model.config.common;

import org.slf4j.LoggerFactory;
/**
 * Copyright (c) 2017, glodon, All rights reserved
 * <p>
 * 常量类：事件服务使用，启动应用时初始化加载相关redis信息配置
 * </p>
 * 
 * @version 0.0.1, 2017/07
 * @since JDK1.8
 */
public class ConfigConstant {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigConstant.class);

    public static String[] IPRoute;
    public static String ServerName;
  
    public static void init(Config config) {
        setIproute(config.getIproute());    
        setServerName(config.getServerName());   
        logger.info("Iproute={}", config.getIproute());
    }

    public static void setIproute(String iproute) {      
    	if(iproute==null)IPRoute=null;
    	else IPRoute = iproute.split(",");
    }
    
    public static void setServerName(String servername) {      
    	ServerName=servername;
    }
   

}
