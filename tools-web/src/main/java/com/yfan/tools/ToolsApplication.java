package com.yfan.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Arrays;

/**
 *
 * 主启动类
 * @author: YFAN
 * @date: 2022/7/3/003 16:27
 **/
//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class ToolsApplication {

    public static void main(String[] args) {
        log.info("当前系统变量");
        log.info(Arrays.toString(args));
        SpringApplication.run(ToolsApplication.class, args);
    }

}
