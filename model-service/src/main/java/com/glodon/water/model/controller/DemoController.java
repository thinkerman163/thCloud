package com.glodon.water.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodon.water.model.api.UserApi;
import com.glodon.water.model.util.RedisUtil;

import java.util.concurrent.atomic.AtomicLong;

@RefreshScope
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Value("${system.order.serverName}")
    private String serverName; 

    @Autowired
    private UserApi userApi;
    
    @ResponseBody
    @RequestMapping("/test")
    public String test(String info) {
    	RedisUtil.setValue("test", info);
        return "port:" + port + ",serverName:" + sererName + ",getUser info ==>" + userApi.getUser(info)
        +",Redis info ==>" + RedisUtil.getValue("test");
        
     //   return "port:" + port + ",serverName:" + sererName + ",getRedis info ==>" + RedisUtil.getValue("test");
    }

    private AtomicLong counter = new AtomicLong(1);

    /**
     * 每次請求計數器加一，当请求的次数不为3的倍数时，休眠2.1s返回
     *
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping("/testRetry")
    public String testRetry() throws InterruptedException {
        if (counter.incrementAndGet() % 3 != 0) {
            Thread.sleep(2100);
        }
        return "port:" + port;
    }
    
    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello(String req) {
        return "req:" + req + ",from : port:" + port + ",serverName:" + sererName;
    }

    @RequestMapping("/order")
    @ResponseBody
    public String getOrder(String user) throws InterruptedException {
        System.out.println("=====================>进入order===>");
      //  Thread.sleep(10_000);
        System.out.println("=====================>结束order====>");
        return "Get user[ " + user + "] order from port:" + port + ",serverName:" + sererName;
    }     
   
   
}
