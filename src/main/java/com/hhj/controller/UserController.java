package com.hhj.controller;

import com.hhj.pojo.User;
import com.hhj.service.UserService;
import com.hhj.utils.JwtHelper;
import com.hhj.utils.Result;
import com.hhj.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserController
 * Package: com.hhj.controller
 * Description:
 *      用户模块
 * @Author honghuaijie
 * @Create 2023/12/12 21:42
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */

@RestController
@RequestMapping("user")  //设置根路径为user 那么在网页上访问就可以直接通过/user/模块名
@CrossOrigin  //跨域问题的解决
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 输入账号和密码，判断是否相同
     * @param user
     * @return
     */
    @PostMapping("login")
    public Result<User> login(@RequestBody User user){  //将传入的数据自动整合成user对象
        Result resuit = userService.login(user);
        return resuit;
    }


    /**
     * 传入token，来返回相对应的用户
     * 主要是用来区别用户的
     * @return
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){ //使用请求头来传递数据，因为token太长了，放在路径中，不好看
        Result result = userService.getUserInfo(token);
        return result;
    }


    /**
     * 注册时，判断输入的用户名是否在数据中已经存在
     *
     * @param username  用户注册时输入的用户名
     * @return
     */
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }


    /**
     * 用户注册功能
     * 判断用户是否可以存入到数据库中，
     * 如果可以就存入
     * 如果不可以就报505
     *
     * @param user
     * @return
     */
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }


    /**
     * 判断传入的token是否有效
     * @param token
     * @return
     */
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        //有效就返回false，无效就返回true
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //无效
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);

    }



}
