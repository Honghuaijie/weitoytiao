package com.hhj.service;

import com.hhj.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhj.pojo.PortalVo;
import com.hhj.utils.Result;

/**
* @author 25220
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-12-11 22:14:25
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 根据传入的关键字，返回对应的查询信息
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 1.根据hid，返回对应新闻的详细信息
     * 2.让改新闻的浏览量加1
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    //添加数据
    Result publish(Headline headline,String token);

    /**
     * 根据id和传入的参数，修改数据
     * @param headline
     * @return
     */
    Result updateHeadLine(Headline headline);
}
