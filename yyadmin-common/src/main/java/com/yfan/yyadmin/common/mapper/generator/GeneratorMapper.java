package com.yfan.yyadmin.common.mapper.generator;


import com.yfan.yyadmin.common.entity.TableColumnInfo;
import com.yfan.yyadmin.common.entity.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: yanhongwei
 * @CreateTime: 2022-07-02  16:31
 */
public interface GeneratorMapper {

    /**
     *
     * 分页查询表
     * @param params
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author: YFAN
     * @date: 2022/7/2/002 16:32
     **/
    List<TableInfo> queryList(Map<String, Object> params);

    List<Map<String,Object>> queryListMap(Map<String, Object> params);
    /**
     *
     * 查询表
     * @param tableName
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @author: YFAN
     * @date: 2022/7/2/002 16:32
     **/
    TableInfo queryTable(String tableName);
    Map<String,String> queryTableMap(String tableName);
    /**
     *
     * 查询字段
     * @param tableName
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author: YFAN
     * @date: 2022/7/2/002 16:31
     **/
    List<TableColumnInfo> queryColumns(String tableName);
    List<Map<String, String>> queryColumnsMap(String tableName);

}
