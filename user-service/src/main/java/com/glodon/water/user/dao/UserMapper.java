package com.glodon.water.user.dao;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;


import com.glodon.water.common.common.entity.User;

public interface UserMapper extends Mapper<User> {
    List<String> getUsersByIds(List<String> list);
}
