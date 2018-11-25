package com.glodon.water.user.dao;

import com.glodon.water.common.common.entity.RoleFunction;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;


public interface RoleFunctionMapper extends Mapper<RoleFunction>, MySqlMapper<RoleFunction> {   
    List<RoleFunction> selectRoleFunctionsByUser(Map<String, Object> param);

	List<RoleFunction> selectRoleFunctionsByPath(Map<String, Object> param);
}