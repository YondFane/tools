package com.yfan.yyadmin.common.ftlpath;

/**
 * 处理ftl模板的路径
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:18
 */
public interface FtlPathStrategy {
    /**
     * 获取ftl路径
     * @param className
     * @param packagePath
     * @param packageModulePath
     * @return
     */
    String getFtlPath(String className, String packagePath, String packageModulePath);
}
