package com.glodon.water.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MODEL-SERVICE")
public interface ModelApi {

    @RequestMapping(value = "/demo/order")
    String getOrder(@RequestParam("user") String user);

    @RequestMapping(value = "/demo/testRetry")
    String testRetry();
}
