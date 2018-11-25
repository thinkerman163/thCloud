
package com.glodon.water.model.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String password;
    private String  address;
    private int port;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "RedisConfig [password=" + password + ", address=" + address + ", port=" + port + "]";
    }



}
