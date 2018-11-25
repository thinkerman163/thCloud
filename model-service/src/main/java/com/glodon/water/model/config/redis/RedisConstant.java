
package com.glodon.water.model.config.redis;

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
public class RedisConstant {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RedisConstant.class);

    public static String sADDR;
    public static String sPORT;  
    public static String sPASSWORD;

    public static void init(RedisConfig redisConfig) {
        setAddr(redisConfig.getAddress());
        setPort(redisConfig.getPort());
        setPassword(redisConfig.getPassword());      
        logger.info("addr={},sPort={},redisMaxCount={},password={}", sADDR, sPORT, sPASSWORD);
    }

    public static void setAddr(String addr) {
      
    	sADDR = addr;
    }

    public static void setPort(int port) {
      
        sPORT = String.valueOf(port);
    }
   

    public static void setPassword(String password) {       
    	sPASSWORD = password;
    }

}
