package com.glodon.water.model.config.security;

import java.util.Base64;

import org.springframework.context.annotation.Configuration;

import com.glodon.water.common.common.constant.Constants;
import com.glodon.water.common.common.enumpo.TokenTypeEnum;
import com.glodon.water.model.config.common.ConfigConstant;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig implements RequestInterceptor
{
 
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        // 生成远程调用认证token
        String token = TokenCreate();
        requestTemplate.header(Constants.IN_PARAM_TOKEN, token);
    }
    
    
    private String TokenCreate()
    {
   	 String innertoken=TokenTypeEnum.FEIGN.getName()+"|"+ConfigConstant.ServerName+"|"+System.currentTimeMillis()+"|"+System.currentTimeMillis();			
        byte[] bytes = innertoken.getBytes();  
        innertoken=Base64.getEncoder().encodeToString(bytes);   
        return innertoken;
    }

	
}
