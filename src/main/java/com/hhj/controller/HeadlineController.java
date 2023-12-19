package com.hhj.controller;

import com.hhj.pojo.Headline;
import com.hhj.service.HeadlineService;
import com.hhj.utils.JwtHelper;
import com.hhj.utils.Result;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: HeadlineController
 * Package: com.hhj.controller
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/12/17 15:51
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    /**
     * 登陆以后才可以访问
     * 根据传入的title，article，type，和token中的userId,发布一个文章
     * @param headline
     * @param token
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        //2.加入数据库
        Result result = headlineService.publish(headline,token);
        return result;
    }


    /**
     * 不同担心当前角色有没有权限修改这个记录，因为前端会进行过滤
     * 根据传入的hid 回显给前端hid,title，article，type
     * @param hid
     * @return
     */
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Headline headline = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline",headline);

        return Result.ok(data);

    }


    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadLine(headline);
        return result;
    }


    /**
     * 删除指定新闻
     * @param hid
     * @return
     */
    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
        boolean b = headlineService.removeById(hid);

        return Result.ok(null);

    }


}
