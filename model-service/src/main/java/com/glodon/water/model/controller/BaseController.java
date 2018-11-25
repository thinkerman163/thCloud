package com.glodon.water.model.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.glodon.water.common.common.constant.Constants;
import com.glodon.water.common.common.vo.AjaxReturnVo;
import com.glodon.water.common.model.request.TokenVO;
import com.glodon.water.common.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/base")
public class BaseController {
  
	private static final Logger log = LogManager.getLogger(BaseController.class);

	
	/**
     * 
     * @Description ajax返回成功
     * @author youps-a
     * @date 2016年10月11日 下午3:23:21
     */
    public TokenVO getTokenVO() {
    	TokenVO tvo=new TokenVO();
        String token=getRequest().getHeader(Constants.IN_PARAM_TOKEN);
        tvo=TokenUtil.getInnerToken(token);
        log.info("得到token:"+tvo);
        return tvo;
    }
    
    /**
     * 
     * @Description ajax返回成功
     * @author youps-a
     * @date 2016年10月11日 下午3:23:21
     */
    public AjaxReturnVo returnAjaxSuccess() {
        AjaxReturnVo ajaxReturnVo = new AjaxReturnVo();
        ajaxReturnVo.setStatus(Constants.SC_AJAX_SUCCESS);
        return ajaxReturnVo;
    }
    
    /**
     * 
     * @Description ajax返回成功
     * @author youps-a
     * @date 2016年10月11日 下午3:24:08
     * @param data  参数
     */
    public AjaxReturnVo returnAjaxSuccess(Object data) {
        AjaxReturnVo ajaxReturnVo = new AjaxReturnVo();
        ajaxReturnVo.setStatus(Constants.SC_AJAX_SUCCESS);
        ajaxReturnVo.setData(data);
        return ajaxReturnVo;
    }
    
    /**
     * 
     * @Description ajax返回成功
     * @author youps-a
     * @date 2016年10月11日 下午3:25:31
     * @param reason    reason
     */
    public AjaxReturnVo returnAjaxError(String reason) {
        AjaxReturnVo ajaxReturnVo = new AjaxReturnVo();
        ajaxReturnVo.setStatus(Constants.SC_AJAX_ERROR);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("reason", reason);
        ajaxReturnVo.setData(retMap);
        return ajaxReturnVo;
    }

    /**
     * 
     * @Description ajax返回失败
     * @author youps-a
     * @date 2016年10月11日 下午3:23:50
     */
    public AjaxReturnVo returnAjaxError() {
        AjaxReturnVo ajaxReturnVo = new AjaxReturnVo();
        ajaxReturnVo.setStatus(Constants.SC_AJAX_ERROR);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("reason", "操作失败");
        ajaxReturnVo.setData(retMap);
        return ajaxReturnVo;
    }
    
    /**
     * 
     * @Description ajax返回失败
     * @author youps-a
     * @date 2016年10月11日 下午3:24:38
     * @param data  参数
     */
    public AjaxReturnVo returnAjaxError(Object data) {
        AjaxReturnVo ajaxReturnVo = new AjaxReturnVo();
        ajaxReturnVo.setStatus(Constants.SC_AJAX_ERROR);
        ajaxReturnVo.setData(data);
        return ajaxReturnVo;
    }

    /**
    *
    * @Description 获取request
    * @author youps-a
    * @date 2016年7月1日 下午6:51:36
    */
   public static HttpServletRequest getRequest() {
       ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                       .getRequestAttributes();
       return attrs.getRequest();
   }
   

}
