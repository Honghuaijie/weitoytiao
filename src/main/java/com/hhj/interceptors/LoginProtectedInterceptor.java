package com.hhj.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhj.utils.JwtHelper;
import com.hhj.utils.Result;
import com.hhj.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: LoginProtectedInterceptor
 * Package: com.hhj.interceptors
 * Description:
 *      登陆包含拦截器，检查请求头中是否包含有效token
 *      有，有效，放行
 *      没有，无效，返回504
 * @Author honghuaijie
 * @Create 2023/12/17 16:13
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */

@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.从请求头中获取token
        String token = request.getHeader("token");
        //2.检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);


        //3.有效放行
        if (!expiration){
            return true;
        }

        //4.无效就返回
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        //将对象转换为json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;

    }
}
