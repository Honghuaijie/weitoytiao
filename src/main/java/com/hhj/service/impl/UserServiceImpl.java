package com.hhj.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhj.pojo.User;
import com.hhj.service.UserService;
import com.hhj.mapper.UserMapper;
import com.hhj.utils.JwtHelper;
import com.hhj.utils.MD5Util;
import com.hhj.utils.Result;
import com.hhj.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 25220
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2023-12-11 22:14:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 传入token，返回相对应的用户
     *
     * 1.token是否在有效期
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        //判断token是否在有效期内 true过期
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //失效,未登录看待
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //根据token，解析为userid
        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);

        //需要将密码置为空，因为不想密码被显示
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser",user);

        return Result.ok(data);

    }


    /**
     * 登陆业务
     * 1.根据账户，查询用户对象
     * 2.如果账号为null，查询失败，账户错误 ！501
     * 3.对比，密码，失败， 返回503的错误
     * 4.根据用户id生成一个token,token ->result 返回
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //根据账号查询数据
        //1.设置条件
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //eq(列名,属性值)  列名通过user里面的get方法得到对应的列名
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());

        //2.传入相对应的条件，进行查询
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);


        //如果账户错误
        if (loginUser == null) {
            //传入空，表示错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //如果密码错误
        //在比较密码之前，需要先对密码进行加入，因为数据库中的密码是加入过的
        //如果密码不为空，并且加密后数据库中的密码loginUser相同，就表示登陆成功
        String encrypt = MD5Util.encrypt(user.getUserPwd());

        if (!StringUtils.isEmpty(user.getUserPwd()) &&
                encrypt.equals(loginUser.getUserPwd())){
            //登陆成功
            //1.根据用户id生成token (这里一定要使用loginuser，
            //              因为这个对象里面存放的是用户的全部数据,而形参传入的user只有账户和密码)
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            //2.将token封装到result中返回
            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);



    }



    /**
     *  注册业务
     *
     *  判断用户名是否已经注册
     * @param username
     * @return code200 表示用户名不存在，为505表示用户名存在
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUsername,username);

        User user = userMapper.selectOne(lambdaQueryWrapper);

        if (user !=null){
            //说明数据库中存在该用户，报错
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        //说明可以注册
        return Result.ok(null);
    }

    /**
     * 注册用户
     * 1.判断用户名是否存在，如果存在就直接返回505
     * 2.如果用户名不存在，就可以直接存入到数据库中，并返回成功
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {

        Result result = checkUserName(user.getUsername());

        //如果返回的result的code为505就说明数据库中存在该用户名，不让注册
        if (result.getCode() == 505){
            return result;
        }

        //在将用户数据插入到数据库中前，需要对密码进行加密
        String encrypt = MD5Util.encrypt(user.getUserPwd());
        user.setUserPwd(encrypt);
        //将用户，存入到数据库中
        userMapper.insert(user);

        return result;
    }
}




