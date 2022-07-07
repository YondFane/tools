package com.yfan.yyadmin.common.ftlpath.impl;

import com.yfan.yyadmin.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * Entity模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:21
 */
public class EntityFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }
        return packageModulePath + "entity" + File.separator + className + ".java";
    }
}
