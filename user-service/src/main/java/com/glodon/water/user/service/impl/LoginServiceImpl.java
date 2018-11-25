
package com.glodon.water.user.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glodon.water.common.common.entity.User;
import com.glodon.water.common.common.enumpo.LoginTypeEnum;
import com.glodon.water.common.common.vo.GlodonAccountGrantVO;
import com.glodon.water.common.util.BasicAuthBuilder;
import com.glodon.water.common.util.TokenUtil;
import com.glodon.water.common.util.UrlBuilder;
import com.glodon.water.user.service.ILoginService;
import com.glodon.water.user.service.IUserService;
import com.google.gson.Gson;

@Service
public class LoginServiceImpl implements ILoginService {
	protected static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
    @Autowired
    private IUserService userServiceImpl;   
   	
	/**
     * 通过用户的用户名和密码获取token(广联云)
     *
     * @param username 用户名
     * @param password 密码
     */
	@Override
	public boolean loginAccess(String username, String password, Integer loginType) {
		try {
			if (loginType == null)
				loginType = LoginTypeEnum.own.getIndex();
			if (loginType == LoginTypeEnum.glodon.getIndex()) {
				GlodonAccountGrantVO glodonAccountGrantVO = Glodongrant(username, password);
				if (null == glodonAccountGrantVO) {
					return false;
				}
				String accessToken = glodonAccountGrantVO.getAccessToken();
				if (accessToken == null || accessToken.length() == 0) {
					return false;
				} else {
					// 更新用户密码信息
					User user = userServiceImpl.getUserByUserName(username);
					String user_password = TokenUtil.encoderInnerToken(password);
					user.setPassworde(user_password);
					userServiceImpl.updateUserByUserName(user);
					return true;
				}
			} else if (loginType == LoginTypeEnum.own.getIndex()) {
				User user = userServiceImpl.getUserByUserName(username);
				if (user == null)
					return false;
				if (password == null)
					return false;
				if (user.getPassword() == null || user.getPassword().length() == 0)
					return false;
				String user_password = TokenUtil.decoderInnerToken(user.getPassword());
				if (password.equals(user_password)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;

	}
    /**
     * 通过用户的用户名和密码获取token(广联云)
     *
     * @param username 用户名
     * @param password 密码
     */
    private GlodonAccountGrantVO Glodongrant(String username, String password) {
        // 创建POST，并在http请求头中加入Basic认证信息
        HttpPost httpPost = new HttpPost(UrlBuilder.buildGrantByPasswordUrl());
        httpPost.addHeader(BasicAuthBuilder.headerKey(), BasicAuthBuilder.headerValue());

        // 拼装form表单中的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", username));
        nvps.add(new BasicNameValuePair("password", password));
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(nvps);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        httpPost.setEntity(formEntity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String output = null;
        try {
            // 执行http请求
            response = httpclient.execute(httpPost);
            output = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        if (output != null && output.length() > 0) {
            GlodonAccountGrantVO glodonAccountGrantVO = gson.fromJson(output, GlodonAccountGrantVO.class);
            return glodonAccountGrantVO;
        }
        return null;
    }
    
    
   

}
