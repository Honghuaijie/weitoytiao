package com.hhj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhj.pojo.Headline;
import com.hhj.pojo.PortalVo;
import com.hhj.service.HeadlineService;
import com.hhj.mapper.HeadlineMapper;
import com.hhj.utils.JwtHelper;
import com.hhj.utils.Result;
import com.hhj.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 25220
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-12-11 22:14:25
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 服务端根据条件搜索分页信息,
     * 首页数据查询
     * 返回含页码数,页大小,总页数,总记录数,当前页数据等信息,并根据时间降序,浏览量降序排序
     *
     * 1.进行分页数据查询
     * 2.分页数据，拼接到result即可
     *
     *
     * 注意1： 查询需要自定义SQL语句自定义mapper的方法，携带分页
     * 注意2：返回的结果List<Map>
     *
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize()); //默认每页显示10条数据
        headlineMapper.selectMyPage(page,portalVo);

        //查询的数据的集合
        List<Map> records = page.getRecords();
        //用来存放pageInfo中的数据
        Map data = new HashMap();
        data.put("pageData",records);
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        //用来存放pageInfo
        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);
        return Result.ok(pageInfo);

    }

    /**
     * 根据id查询指定新闻的详细信息
      *  1.查询对应的数据即可【多表查询 】
     *  2.修改阅读量
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        //1.实现根据id的查询(多表
        //map用来存放，查询出来的所有数据
        Map map = headlineMapper.selectDetailMap(hid);
        Map headline = new HashMap();
        headline.put("headline",map);

        //2.根据id修改该记录的version和pageViews
        Headline headLine = new Headline();
        headLine.setHid(hid);
        //该version是查询出来的，如果与数据库中的不匹配，说明该记录已经变了，就无法进行修改了
        headLine.setVersion((Integer) map.get("version"));
        headLine.setPageViews((Integer) map.get("pageViews")+1);

        headlineMapper.updateById(headLine);



        return Result.ok(headline);
    }

    @Override
    public Result publish(Headline headline,String token) {
        //1。获取userId
        int userId = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(userId);

        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headline.setPageViews(0);
        headlineMapper.insert(headline);
        return Result.ok(null);

    }


    /**
     * 修改指定id的记录
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadLine(Headline headline) {
        //补全信息（修改日期，版本）

        //获取版本
        Integer verson = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setUpdateTime(new Date());
        headline.setVersion(verson);  //乐观锁：只有拿到version，才可以更新数据



        //修改信息(会自动校验version，如果成功还会修改version)
        headlineMapper.updateById(headline);

        return Result.ok(null);

    }


}




