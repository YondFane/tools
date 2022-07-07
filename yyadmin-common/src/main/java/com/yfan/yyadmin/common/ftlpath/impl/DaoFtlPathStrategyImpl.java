package com.yfan.yyadmin.common.ftlpath.impl;

import com.yfan.yyadmin.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * Dao模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:22
 */
public class DaoFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }
        return packageModulePath + "dao" + File.separator + className + "Dao.java";
    }
}
