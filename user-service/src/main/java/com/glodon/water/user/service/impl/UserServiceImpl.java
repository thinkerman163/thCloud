package com.glodon.water.user.service.impl;

import java.util.*;
import com.glodon.water.common.common.entity.User;
import com.glodon.water.user.dao.*;
import com.glodon.water.user.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class UserServiceImpl  implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper usermapper;   

    @Override
    public Integer saveUser(User user) {
        usermapper.insertSelective(user);
        log.debug("保存用户成功"+user.getUsername());
        return user.getId();
    }

   
    @Override
    public User getUserByUserName(String userName) {
        User user = new User();
        user.setUsername(userName);
        user.setIsDelete(false);
        List<User> users = usermapper.select(user);
        if (users!=null&&users.size()>0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    /**
     * @author xus-d
     * @date 2016年10月11日 下午3:06:14
     * @see com.glodon.water.common.service.IUserService#updateUserByUserName(com.glodon.water.common.entity.User)
     */
    public int updateUserByUserName(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", user.getUsername());
        user.setUsername(null);
        int result = usermapper.updateByExampleSelective(user, example);
        return result;
    }

    @Override
    public int updateUserById(User user) {
        int res = usermapper.updateByPrimaryKeySelective(user);
        return res;
    }     
    
    
    /**
     * 根据ID查询用户
     *
     * @param id
     */
    @Override
    public User getUserById(Integer id) {
        return usermapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getUsersByIds(List<String> list) {
        return usermapper.getUsersByIds(list);
    }     


}
