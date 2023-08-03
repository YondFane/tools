package com.yfan.tools.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileReader;

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
                //config = new PropertiesConfiguration("/config/generator.properties");
                config  = new PropertiesConfiguration();
                config.load(new FileReader(System.getProperty("user.dir")  + "/config/generator.properties"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return config;
    }
}
