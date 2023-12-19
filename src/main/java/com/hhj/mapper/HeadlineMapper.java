package com.hhj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hhj.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhj.pojo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 25220
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-12-11 22:14:25
* @Entity com.hhj.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage<Map> page, @Param("portalVo") PortalVo portalVo);

    Map selectDetailMap(Integer hid);
}




