package com.glodon.water.user.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.glodon.water.common.common.constant.Constants;
import com.glodon.water.common.util.TokenUtil;
import com.glodon.water.user.config.common.ConfigConstant;

@Component
public class InterceptorConfig  implements HandlerInterceptor{  
	  
	private static final Logger log = LogManager.getLogger(InterceptorConfig.class);
  
    /** 
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {    
        log.info("---------------------开始进入请求地址拦截----------------------------");  
        String token=httpServletRequest.getHeader(Constants.IN_PARAM_TOKEN);
        log.info("得到token"+token);  
        String ip = TokenUtil.getIpAddr(httpServletRequest);  
        log.info("得到x-forwarded-for:"+ip); 
        boolean bresult=TokenUtil.AuthInnerToken(token, ip, ConfigConstant.IPRoute);
		return bresult;    
    }  
  
    @Override  
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {  
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {  
        log.info("---------------视图渲染之后的操作-------------------------0");  
    }  
}  