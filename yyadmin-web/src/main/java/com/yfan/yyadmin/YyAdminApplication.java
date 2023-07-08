package com.yfan.yyadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * 主启动类
 * @author: YFAN
 * @date: 2022/7/3/003 16:27
 **/
//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YyAdminApplication.class, args);
    }

}
