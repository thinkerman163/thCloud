package com.glodon.water.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodon.water.auth.auth.AccessTokenAuthManager;
import com.glodon.water.auth.auth.IRoleFunctionService;
import com.glodon.water.common.common.entity.RoleFunction;
import com.glodon.water.common.model.result.AuthResult;


@Controller
@RequestMapping("/auth")
public class AuthController {

	protected static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AccessTokenAuthManager authTokenManager;
	@Autowired
    private IRoleFunctionService roleFunctionService;

	@Autowired
	private DiscoveryClient client;

	@ResponseBody
	@RequestMapping("/info")
	public Object info() {
		return client.getServices();
	}
	
	

	/**
	 * 解密信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping("/decodeToken")
	@ResponseBody
	public AuthResult decodeToken(String token) {
		AuthResult authModel = new AuthResult();
		authModel.setStatus(HttpServletResponse.SC_OK);
		try {
			authModel.setToken(authTokenManager.parseToken(token));
		} catch (Exception e) {
			logger.warn("decodeTokenFail", e);
			authModel.setErrMsg("invalid token!!!");
			authModel.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return authModel;
	}

	/**
	 * 生成加密信息
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/encodeToken")
	@ResponseBody()
	public AuthResult encodeToken(String token) throws Exception {
		AuthResult authModel = new AuthResult();
		authModel.setStatus(HttpServletResponse.SC_OK);
		authModel.setToken(authTokenManager.generateToken(token));
		return authModel;
	}
	
	
	@RequestMapping("/pathAuthorization")
	@ResponseBody
	public AuthResult pathAuthorization(Integer user_id, String path) {

		AuthResult authModel = new AuthResult();

		// 查询角色
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", user_id);
		//paramMap.put("corpId", corp_id);
		paramMap.put("path", path);

		List<RoleFunction> functions = roleFunctionService.selectRoleFunctionsByPath(paramMap);

		if (functions == null || functions.size() == 0) {
			authModel.setErrMsg("invalid path!");
			authModel.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			authModel.setStatus(HttpServletResponse.SC_OK);
		}

		return authModel;

	}
}
