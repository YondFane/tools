package com.yfan.tools.common.ftlpath.impl;

import com.yfan.tools.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * ServiceImpl模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:22
 */
public class ServiceImplFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        return packageModulePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
    }
}
