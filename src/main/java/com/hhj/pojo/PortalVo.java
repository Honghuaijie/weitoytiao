package com.hhj.pojo;

import lombok.Data;

/**
 * 条件实体类，服务于分页查询首页头条信息
 */
@Data
public class PortalVo {
    
    private String keyWords;
    private Integer type;
    private Integer pageNum = 1;
    private Integer pageSize =10;
}