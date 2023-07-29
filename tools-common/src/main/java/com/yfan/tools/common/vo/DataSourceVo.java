package com.yfan.tools.common.vo;

import lombok.Data;

/**
 * @BelongsProject: tools
 * @BelongsPackage: com.yfan.tools.common.vo
 * @Description: 数据源vo
 * @Author: YFAN
 * @CreateTime: 2023-07-08 01:32
 * @Version: 1.0
 */
@Data
public class DataSourceVo {

    // 是否激活为当前数据源
    private Boolean selected;
    // 数据源名称
    private String dbName;
    // 驱动名称
    private String driverClassName;
    // 用户名称
    private String username;
    // 用户密码
    private String password;
    // 数据库链接
    private String jdbcUrl;

}
