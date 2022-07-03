package com.yfan.yyadmin.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表信息
 * @Author: YFAN
 * @CreateTime: 2022-07-02 20:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {

    // 表名
    private String tableName;
    // 存储引擎
    private String engine;
    // 表注释
    private String tableComment;
    // 创建时间
    private String createTime;

}
