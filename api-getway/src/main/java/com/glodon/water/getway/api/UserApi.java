package com.glodon.water.getway.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glodon.water.getway.api.fallback.UserApiFallback;

@FeignClient(value = "USER-SERVICE", fallback = UserApiFallback.class)
public interface UserApi {

    @RequestMapping(value = "/demo/getUser")
    String getUser(@RequestParam("user") String user);

    @RequestMapping(value = "/demo/getUser1")
    String getUser1(@RequestParam("user") String user);
}
