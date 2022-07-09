package com.yfan.yyadmin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * generator.properties配置类
 * @Author: YFAN
 * @CreateTime: 2022-07-03 13:07
 */
@Slf4j
public class GenConfigUtil {

    private static PropertiesConfiguration config;

    /**
     * 获取配置信息
     */
    public static PropertiesConfiguration getConfig() {
        try {
            if (config == null) {
                config = new PropertiesConfiguration("generator.properties");
            }
        } catch (ConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        return config;
    }
}
