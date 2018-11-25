package com.glodon.water.user.service;

import java.util.List;
import com.glodon.water.common.common.entity.User;

public interface IUserService {
    /**
     * 保存用户
     */
    Integer saveUser(User user);
    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     */
    User getUserByUserName(String userName);

    /**
     * @Description 更新用户信息根据用户名
     * @author xus-d
     * @date 2016年7月4日 上午11:08:26
     */
    int updateUserByUserName(User user);  

    /**
     * @Description 通过id更新用户
     * @author xus-d
     * @date 2016/7/18 16:28
     */
    int updateUserById(User user);

    /**
     * 根据ID查询用户
     *
     * @param id
     */
    User getUserById(Integer id);

    List<String> getUsersByIds(List<String> list);

}
