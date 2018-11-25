package com.glodon.water.user.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */  
@SuppressWarnings("deprecation")
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {  
  
	 @Autowired
	 private InterceptorConfig interceptorConfig;
	 
	 
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径和排除拦截路径  
    	//registry.addInterceptor(interceptorConfig).addPathPatterns("/**").excludePathPatterns("/*").excludePathPatterns("/demo/*");  
    	registry.addInterceptor(interceptorConfig)
    	.addPathPatterns("/**")
    	.excludePathPatterns("/eurekaUnRegister")
    	.excludePathPatterns("/login");  
    }  
} 