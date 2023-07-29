package com.yfan.tools.common.ftlpath.impl;

import com.yfan.tools.common.ftlpath.FtlPathStrategy;

import java.io.File;

/**
 * QueryCriteria模板
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:29
 */
public class QueryCriteriaFtlPathStrategyImpl implements FtlPathStrategy {
    @Override
    public String getFtlPath(String className, String packagePath, String packageModulePath) {
        if (packagePath.equals(packageModulePath)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "QueryCriteria.java";
        }
        return packageModulePath + "service" + File.separator + "dto" + File.separator + className + "QueryCriteria.java";
    }
}
