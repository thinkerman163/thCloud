
package com.glodon.water.model.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glodon.water.model.config.common.Config;
import com.glodon.water.model.config.common.ConfigConstant;
import com.glodon.water.model.config.redis.RedisConfig;
import com.glodon.water.model.config.redis.RedisConstant;
import com.glodon.water.model.util.ZkUtil;

/**
 * Copyright (c) 2017, glodon, All rights reserved
 * <p>
 *  启动加载 初始化类，基于注解实现顺序启动加载相关配置和资源
 * </p>
 *
 * @version 0.0.1, 2017/07
 * @since JDK1.8
 */
@Configuration
public class BootLoadConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BootLoadConfig.class);      
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private Config config;
  
    @Bean(initMethod = "init")
    // 初始化配置
    public ZkUtil zkClientCreate() throws Exception {    
    	logger.info("{}", config); 
    	ConfigConstant.init(config);
        logger.info("{}", redisConfig); 
        RedisConstant.init(redisConfig);  
        ZkUtil zkUtil = new ZkUtil();
        return zkUtil;
    }
   

}
