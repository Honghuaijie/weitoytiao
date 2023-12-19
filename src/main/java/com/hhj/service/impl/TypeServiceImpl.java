package com.hhj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhj.pojo.Type;
import com.hhj.service.TypeService;
import com.hhj.mapper.TypeMapper;
import com.hhj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 25220
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-12-11 22:14:25
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{


}




