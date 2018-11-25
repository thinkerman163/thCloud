package com.glodon.water.model.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserApi {

	@RequestMapping(value = "/demo/getUser")
	String getUser(@RequestParam("user") String user);
}
