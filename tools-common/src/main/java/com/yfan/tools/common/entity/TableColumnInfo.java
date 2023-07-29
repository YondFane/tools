package com.yfan.tools.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表的列信息
 * @Author: YFAN
 * @CreateTime: 2022-07-02 23:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableColumnInfo {

    // 字段名
    private String columnName;
    // 数据类型
    private String dataType;
    // 字段备注
    private String columnComment;
    // 约束
    private String columnKey;
    // auto_increment
    private String extra;
    // 字段类型
    private String columnType;

}
