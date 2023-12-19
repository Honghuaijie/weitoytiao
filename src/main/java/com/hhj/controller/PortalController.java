package com.hhj.controller;

import com.hhj.pojo.PortalVo;
import com.hhj.pojo.Type;
import com.hhj.service.HeadlineService;
import com.hhj.service.TypeService;
import com.hhj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: PortalController
 * Package: com.hhj.controller
 * Description:
 *      首页模块
 * @Author honghuaijie
 * @Create 2023/12/14 21:59
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */

@RestController //放入IOC容器，并声明支持JSON数据
@RequestMapping("portal") //设置根路径
@CrossOrigin //跨域
public class PortalController {

    @Autowired
    private TypeService typeService;

    //针对headline表进行查询，所以使用headlineService
    @Autowired
    private HeadlineService headlineService;
    /**
     * 查询所有分类并动态展示新闻类别栏位
     * @return
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        List<Type> list = typeService.list();
        return Result.ok(list);
    }


    /**
     * 首页查询信息显示
     * 根据传入的关键字搜索，
     * 并返回含页码数,页大小,总页数,总记录数,当前页数据等信息,并根据时间降序,浏览量降序排序
     * @param portalVo
     * @return
     */
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }


    /**
     * 显示头条详情
     *  接受新闻的ID，根据新闻的ID显示该新闻的详细信息
     * @return
     */
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }




}
