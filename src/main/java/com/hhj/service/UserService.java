package com.hhj.service;

import com.hhj.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhj.utils.Result;

/**
* @author 25220
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-12-11 22:14:25
*/
public interface UserService extends IService<User> {

    /**
     * 登陆业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 根据输入的username，来判断是否存在该用户名
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册用户
     * 1.判断用户名是否存在，如果存在就直接返回505
     * 2.如果用户名不存在，就可以直接存入到数据库中，并返回成功
     *
     * @param user
     * @return
     */
    Result regist(User user);
}
