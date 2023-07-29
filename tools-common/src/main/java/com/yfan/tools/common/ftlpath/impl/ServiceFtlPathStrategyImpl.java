package com.yfan.tools.common.ftlpath.impl;

import com.yfan.tools.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * Service模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:22
 */
public class ServiceFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }
        return packageModulePath + "service" + File.separator + className + "Service.java";
    }
}
