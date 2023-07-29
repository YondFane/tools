package com.yfan.tools.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 数据源配置类
 *
 * @Author: YFAN
 * @CreateTime: 2022-07-02 16:33
 */
@Configuration
public class DataSourceConfig {

    /**
     * 数据源1
     * spring.datasource.db1：application.properteis中对应属性的前缀
     *
     * @return
     */
    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSourceOne() {
        DataSourceUtil.dbNameSet.add("db1");
        return DataSourceBuilder.create().build();
    }

    /**
     * 数据源2
     * spring.datasource.db2：application.properteis中对应属性的前缀
     *
     * @return
     */
    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSourceTwo() {
        DataSourceUtil.dbNameSet.add("db2");
        DataSource build = DataSourceBuilder.create().build();
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSourceOne());
        // 多数据源集合
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put("db1", dataSourceOne());
        dsMap.put("db2", dataSourceTwo());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置多数据源后IOC中存在多个数据源了，事务管理器需要重新配置，不然器不知道选择哪个数据源
     * 事务管理器此时管理的数据源将是动态数据源dynamicDataSource
     * 配置@Transactional注解
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
