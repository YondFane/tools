package com.yfan.tools.common.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: tools
 * @BelongsPackage: com.yfan.tools.common.config
 * @Description: 动态数据源
 * @Author: YFAN
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 多数据源
     **/
    private Map<Object, DataSource> resolvedDataSources = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceUtil.getDB();
    }

    /**
     * @description: 设置多数据源集合
     * @author: YFAN
     * @date: 2023/7/8/008 01:30
     * @param: targetDataSources
     **/
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        targetDataSources.forEach((k,v) ->{
            resolvedDataSources.put(k, (DataSource) v);
        });
    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        return resolvedDataSources;
    }
    
    /**
     * @description: 确定当前使用数据源
     * @author: YFAN
     * @date: 2023/7/8/008 01:31
     * @return: javax.sql.DataSource
     **/
    @Override
    protected DataSource determineTargetDataSource() {
        Assert.notNull(getResolvedDataSources(), "DataSource router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
        if (dataSource == null) {
            dataSource = super.getResolvedDefaultDataSource();
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }
}
