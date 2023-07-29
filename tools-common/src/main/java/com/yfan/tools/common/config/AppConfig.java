package com.yfan.tools.common.config;

import com.yfan.tools.common.mapper.generator.GeneratorMapper;
import com.yfan.tools.common.mapper.generator.MySQLGeneratorMapper;
import com.yfan.tools.common.mapper.generator.OracleGeneratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: YFAN
 * @CreateTime: 2022-07-02 16:20
 */
@Configuration
public class AppConfig {

    @Value("${current.datasource}")
    private String database;
    @Autowired
    private MySQLGeneratorMapper mySQLGeneratorMapper;

    @Autowired
    private OracleGeneratorMapper oracleGeneratorMapper;


    @Bean
    @Primary
    public GeneratorMapper getGeneratorDao() {
        // 根据当前数据源获取Mapper
        if ("mysql".equalsIgnoreCase(database)) {
            return mySQLGeneratorMapper;
        } else if ("oracle".equalsIgnoreCase(database)) {
            return oracleGeneratorMapper;
        } else {
            throw new RuntimeException("不支持当前数据库：" + database);
        }
    }
}
