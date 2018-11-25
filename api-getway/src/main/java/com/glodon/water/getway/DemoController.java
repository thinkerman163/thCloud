package com.glodon.water.getway;

import com.glodon.water.common.common.constant.ApiServiceContanst;
import com.glodon.water.getway.api.ModelApi;
import com.glodon.water.getway.api.UserApi;
import com.glodon.water.getway.model.result.ZuulResult;
import com.glodon.water.getway.service.HystrixTestService;
import com.glodon.water.getway.service.RetryableService;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/biz")
public class DemoController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serverName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private UserApi userApi;
    @Autowired
    private ModelApi orderApi;

    @Autowired
    private RetryableService retryService;

    @Autowired
    private HystrixTestService hystrixTestService;

    @ResponseBody
    @RequestMapping("/retryTest")
    public String retryTest(int type) {
        return retryService.testRetry(type);
    }

    @ResponseBody
    @RequestMapping("/info")
    public Object info() {
        return client.getServices();
    }

    @ResponseBody
    @RequestMapping("/errorController")
    public String error() {
        return "error";
    }
    
    @Hystrix
    @RequestMapping("/zuul")
    @ResponseBody()
    public ZuulResult testFeign(@RequestParam(defaultValue = "defaultUser", required = false) String user) throws Exception {
        ZuulResult model = new ZuulResult();
        try
        {
        model.setUserInfo(userApi.getUser(user));
        model.setOrderInfo(orderApi.getOrder(user));
        model.setSystemMsg("port:" + port + ",serverName:" + serverName);
        model.setErrMsg(hystrixTestService.getTestValue("==="));
        model.setExtra(userApi.getUser1("extra"));     
        model.setStatus(ApiServiceContanst.ResultCode.RESULT_OK);
        }
        catch(Exception e)
        {
        	model.setErrMsg(e.getMessage());
        	model.setStatus(ApiServiceContanst.ResultCode.RESULT_FAIL);        	
        }
        return model;
    }
   
}
