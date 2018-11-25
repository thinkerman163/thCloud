package com.glodon.water.model.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope
@Controller
@RequestMapping("/")
public class ManageController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String sererName;

    @Value("${system.order.serverName}")
    private String serverName;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private EurekaClient eurekaClient;

    @ResponseBody
    @RequestMapping("/info")
    public Object info() {
        return client.getServices() + ",serverName:" + serverName;
    }
   
    @ResponseBody
    @GetMapping("/eurekaUnRegister")
    public String shutDown() {
        eurekaClient.shutdown();
        return "eurekaUnRegistering";
    }
}
