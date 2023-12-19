package com.hhj;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * ClassName: Main
 * Package: com.hhj
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/12/11 22:04
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
@SpringBootApplication
@MapperScan("com.hhj.mapper") //扫描mapper.xml文件
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }


    //通过添加拦截器来配置mybatis-plus插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); //分页
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());  //乐观锁
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());  //防全局修改和删除
        return interceptor;
    }
}
