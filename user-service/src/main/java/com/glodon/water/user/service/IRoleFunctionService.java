
package com.glodon.water.user.service;

import java.util.List;
import java.util.Map;

import com.glodon.water.common.common.entity.RoleFunction;

public interface IRoleFunctionService {    
	
	/**
     * 
     * @Description 返回权限列表
     * @author xus-d
     * @date 2016年7月12日 下午4:14:05
     * @param roleFunctions 角色集合
     */   
    List<RoleFunction> selectRoleFunctionsByUser(Map<String, Object> param);
    /**
     * 
     * @Description 返回权限列表通过path
     * @author xus-d
     * @date 2016年7月12日 下午4:14:05
     * @param roleFunctions 角色集合
     */   
    List<RoleFunction> selectRoleFunctionsByPath(Map<String, Object> param);
}
