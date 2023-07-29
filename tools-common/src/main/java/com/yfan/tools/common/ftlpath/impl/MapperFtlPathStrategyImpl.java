package com.yfan.tools.common.ftlpath.impl;

import com.yfan.tools.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * Mapper模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:22
 */
public class MapperFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "service" + File.separator + "mapstruct" + File.separator + className + "Mapper.java";
        }
        return packageModulePath + "service" + File.separator + "mapstruct" + File.separator + className + "Mapper.java";
    }
}
