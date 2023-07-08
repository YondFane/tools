package com.yfan.yyadmin.common.config;

import com.yfan.yyadmin.common.vo.DataSourceVo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @BelongsProject: yyAdmin
 * @BelongsPackage: com.yfan.yyadmin.common.config
 * @Description: 数据源工具类
 * @Author: YFAN
 * @CreateTime: 2023-07-07 23:47
 * @Version: 1.0
 */
public class DataSourceUtil {

    /**
     * 记录数据源名称
     */
    public static Set<String> dbNameSet = new HashSet<>();

    /**
     * 记录数据源
     */
    public static Map<String, DataSourceVo> dataSourceVoMap = new HashMap<>();

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "db1";
    /**
     *  数据源属于一个公共的资源
     *  采用ThreadLocal可以保证在多线程情况下线程隔离
     */
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private static String CURRENT_DS = DEFAULT_DS;

    /**
     * 设置数据源名
     * @param dbType
     */
    public static void setDB(String dbType) {
        CURRENT_DS = dbType;
//        contextHolder.set(dbType);
    }

    /**
     * 获取数据源名
     * @return
     */
    public static String getDB() {
        return CURRENT_DS;
//        return (contextHolder.get());
    }

    /**
     * 清除数据源名
     */
    public static void clearDB() {
//        contextHolder.remove();
    }
}
