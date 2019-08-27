/**
 * @Project Name:mp-01
 * @File Name:MybatisPlusConfig.java
 * @Package Name:com.hujianguo.mybatisplus.config
 * @Date: 2018-11-01 15:12:06
 * @author: 胡建国
 * Copyright (c) 2018, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈mybatisplus配置〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2018-11-01 15:12:06
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hujianguo.mapper")
public class MybatisPlusConfig {


    /**
     * 功能描述: br>
     * 〈分页插件〉
     *
     * @param
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    /**
     * 功能描述: br>
     * 〈乐观锁插件〉
     *
     * @param
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


}
