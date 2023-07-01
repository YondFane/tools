package com.yfan.yyadmin.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:34
 */

public enum FtlType {

    CONTROLLER("CONTROLLER","控制层类"),
    ENTITY("ENTITY","控制层类"),
    DAO("DAO","数据访问接口"),
    DAOIMPL("DAOIMPL","数据访问实现类"),
    SERVICE("SERVICE","业务接口"),
    SERVICEIMPL("SERVICEIMPL","业务实现类"),
    DTO("DTO","传输类"),
    MAPPER("MAPPER","MAPPER"),
    QUERYCRITERIA("QUERYCRITERIA","搜索");

    private String code;

    private String dec;

    private FtlType(String code, String dec){
        this.code = code;
        this.dec = dec;
    }

    public String getCode() {
        return code;
    }

    public String getDec() {
        return dec;
    }
}
