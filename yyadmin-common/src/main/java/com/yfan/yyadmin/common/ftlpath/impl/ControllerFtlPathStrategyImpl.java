package com.yfan.yyadmin.common.ftlpath.impl;

import com.yfan.yyadmin.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * Controller模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:22
 */
public class ControllerFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        return packageModulePath + "controller" + File.separator + className + "Controller.java";
    }
}
