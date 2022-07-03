/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.yfan.yyadmin.common.entity;

import com.yfan.yyadmin.common.util.GeneratorUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 表的列信息
 * @author: YFAN
 * @date: 2022/7/3/003 15:36
 **/
@Data
@NoArgsConstructor
public class ColumnInfo implements Serializable {

    // 表名
    private String tableName;

    // 数据库字段名称
    private String columnName;

    // 数据库字段类型
    private String columnType;

    // 数据库字段键类型
    private String keyType;

    // 字段额外的参数
    private String extra;

    // 数据库字段描述
    private String remark;

    // 是否必填
    private Boolean notNull;

    // 是否在列表显示
    private Boolean listShow;

    // 是否表单显示
    private Boolean formShow;

    // 表单类型
    private String formType;

    // 查询 1:模糊 2：精确
    private String queryType;

    // 字典名称
    private String dictName;

    // 日期注解
    private String dateAnnotation;

    public ColumnInfo(String tableName, String columnName, Boolean notNull,
                      String columnType, String remark, String keyType, String extra) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.keyType = keyType;
        this.extra = extra;
        this.notNull = notNull;
        if(GeneratorUtil.PK.equalsIgnoreCase(keyType) && GeneratorUtil.EXTRA.equalsIgnoreCase(extra)){
            this.notNull = false;
        }
        this.remark = remark;
        this.listShow = true;
        this.formShow = true;
    }
}
