
package com.glodon.water.user.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glodon.water.common.common.entity.RoleFunction;
import com.glodon.water.user.dao.RoleFunctionMapper;
import com.glodon.water.user.service.IRoleFunctionService;

@Service
@Transactional
public class RoleFunctionServiceImpl implements IRoleFunctionService {
    @Autowired
    private RoleFunctionMapper roleFunctionMapper;   
    @Override
    public List<RoleFunction> selectRoleFunctionsByUser(Map<String, Object> param) {
        return roleFunctionMapper.selectRoleFunctionsByUser(param);
    }
	@Override
	public List<RoleFunction> selectRoleFunctionsByPath(Map<String, Object> param) {
		  return roleFunctionMapper.selectRoleFunctionsByPath(param);
	}  

}
