package com.glodon.water.getway.api.fallback;

import org.springframework.stereotype.Component;

import com.glodon.water.getway.api.UserApi;

@Component
public class UserApiFallback implements UserApi {
    @Override
    public String getUser(String user) {
        return null;
    }

    @Override
    public String getUser1(String user) {
        return "fallback=>" + user;
    }
}
