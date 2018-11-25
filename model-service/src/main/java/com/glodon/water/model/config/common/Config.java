
package com.glodon.water.model.config.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Copyright (c) 2017, glodon, All rights reserved
 * <p>
 * Common配置 封装对象，对应Application.properties配置文件中 prefix="redis"的属性
 * </p>
 * 
 * @version 0.0.1, 2017/07
 * @since JDK1.8
 */
@Validated
@Component
public class Config {

   @Value("${server.iproute.list}")
   private String iproute;
   
   @Value("${spring.application.name}")
   private String serverName;


    public String getIproute() {
        return iproute;
    }
    
    public String getServerName() {
        return serverName;
    }
   

    @Override
    public String toString() {
        return "CommonConfig [iproute=" + iproute +  ",serverName="+serverName+"]";
    }



}
