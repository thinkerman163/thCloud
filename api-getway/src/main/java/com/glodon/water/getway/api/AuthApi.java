package com.glodon.water.getway.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glodon.water.common.model.result.AuthResult;

@FeignClient("AUTH-SERVICE")
public interface AuthApi {

	@RequestMapping(value = "/auth/encodeToken")
	AuthResult encodeToken(@RequestParam("token") String token);

	@RequestMapping(value = "/auth/decodeToken")
	AuthResult parseToken(@RequestParam("token") String token);
	
	@RequestMapping(value = "/auth/pathAuthorization")	
	AuthResult pathAuthorization(@RequestParam("user_id") Integer user_id, @RequestParam("path") String path);
}
