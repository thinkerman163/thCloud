package com.glodon.water.user.controller;

import com.glodon.water.common.common.constant.ApiServiceContanst;
import com.glodon.water.common.common.entity.User;
import com.glodon.water.common.model.request.LoginVO;
import com.glodon.water.common.model.result.LoginResult;
import com.glodon.water.common.model.result.UserResult;
import com.glodon.water.user.service.ILoginService;
import com.glodon.water.user.service.IUserService;
import com.netflix.discovery.EurekaClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class UserController {
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EurekaClient eurekaClient;
    
    @Autowired
    private IUserService userServiceImpl;   
    
    @Autowired
    private ILoginService loginServiceImpl;   
    
    @ResponseBody
    @GetMapping("/eurekaUnRegister")
    public String shutDown() {
        eurekaClient.shutdown();
        return "eurekaUnRegistering";
    }

    /**
     * 用户登录接口，只需要用户名和密码相同即可
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public LoginResult login(LoginVO user) {
        logger.info("用户登录：username:" + user.getUsername());       
        LoginResult result = new LoginResult();
        if(loginServiceImpl.loginAccess(user.getUsername(), user.getPassword(), user.getLogintype()))
        {
        	User userinfo=userServiceImpl.getUserByUserName(user.getUsername());
            String token = userinfo.getId()+"|"+System.currentTimeMillis();
            result.setToken(token);
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_OK);
        } else {
            result.setErrMsg("用户名或密码错误");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_FAIL);
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/getUserInfoByToken")
    public UserResult getUserInfoByToken(String token) {
        logger.info("获取用户信息：token:" + token);
        UserResult result = new UserResult();
        if (StringUtils.isNotBlank(token)) {
            // 查询数据库获取用户信息
            result.setUsername("username");
            result.setNickname("nickname");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_OK);
        } else {
            result.setErrMsg("invalid token!");
            result.setStatus(ApiServiceContanst.ResultCode.RESULT_FAIL);
        }
        return result;
    }

}
