package com.yfan.yyadmin.controller.dbsource;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.yfan.yyadmin.common.config.DataSourceUtil;
import com.yfan.yyadmin.common.config.DynamicDataSource;
import com.yfan.yyadmin.common.vo.DataSourceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: yyAdmin
 * @BelongsPackage: com.yfan.yyadmin.controller.dbsource
 * @Description: 数据源控制类
 * @Author: YFAN
 * @CreateTime: 2023-07-08 00:12
 * @Version: 1.0
 */
@RestController
@RequestMapping("/yyadmin/db")
@Slf4j
public class DataSourceController {

    @Autowired
    private DataSource dataSource;

    private List<DataSourceVo> dataSourceVoList = new ArrayList<>();

    private Map<String, DataSourceVo> dataSourceVoMap = new HashMap<>();

    private Setting setting = null;

    /**
     * @description: 数据源配置加载初始化
     * @author: YFAN
     * @date: 2023/7/29/029 19:45
     **/
    @PostConstruct
    public void init() throws Exception {
        // 加载数据源配置文件
        log.info("数据源配置加载开始");
        setting = new Setting("datasource.setting");
        String groups = setting.get("groups");
        log.info("groups:{}", groups);
        String[] groupArray = groups.split(",");
        for (String groupName : groupArray) {
            DataSourceVo dataSourceVo = new DataSourceVo();
            dataSourceVo.setDbName(groupName);
            dataSourceVo.setDriverClassName(setting.getByGroup("driverClassName", groupName));
            dataSourceVo.setJdbcUrl(setting.getByGroup("jdbcUrl", groupName));
            dataSourceVo.setUsername(setting.getByGroup("username", groupName));
            dataSourceVo.setPassword(setting.getByGroup("password", groupName));
            dataSourceVoList.add(dataSourceVo);
            dataSourceVoMap.put(groupName, dataSourceVo);

            // 添加数据源
            addDataSource(dataSourceVo);
        }
        log.info("数据源配置加载结束：{}", dataSourceVoList);
        log.info("数据源配置加载结束：{}", setting);

    }

    /**
     * @description: 销毁时将数据源配置持久化
     * @author: YFAN
     * @date: 2023/7/29/029 19:44
     **/
    @PreDestroy
    public void store() {
        // 程序退出进行持久化操作
        log.info("数据源配置持久化开始：{}", setting);
        if (setting == null) {
            return;
        }
        List<String> groups = new ArrayList<>();
        dataSourceVoMap.forEach((groupName, dataSourceVo) -> {
            groups.add(groupName);
            setting.putByGroup("driverClassName", groupName, dataSourceVo.getDriverClassName());
            setting.putByGroup("jdbcUrl", groupName, dataSourceVo.getJdbcUrl());
            setting.putByGroup("username", groupName, dataSourceVo.getUsername());
            setting.putByGroup("password", groupName, dataSourceVo.getPassword());
        });
        setting.put("groups", groups.stream().collect(Collectors.joining(",")));
        setting.store();
        log.info("数据源配置持久化结束：{}", setting);
    }

    /**
     * @description: 切换数据源
     * @author: YFAN
     * @date: 2023/7/8/008 00:14
     * @param: dbName
     * @return: java.lang.Object
     **/
    @RequestMapping("/change")
    public Object change(String dbName) {
        if (StrUtil.isBlank(dbName)) {
            DataSourceUtil.setDB(DataSourceUtil.DEFAULT_DS);
            return "已切换到默认数据源" + DataSourceUtil.DEFAULT_DS;
        }
        Set<String> dbNameSet = DataSourceUtil.dbNameSet;
        // 检验是否存在数据源
        if (!dbNameSet.contains(dbName)) {
            return dbName + "数据源不存在！";
        }
        DataSourceUtil.setDB(dbName);
        return "已切换到" + dbName + "数据源";
    }

    /**
     * @description: 添加数据源
     * @author: YFAN
     * @date: 2023/7/8/008 01:38
     * @param: dataSourceVo
     * @return: java.lang.Object
     **/
    @PostMapping("/addDataSource")
    public Object addDataSource(@RequestBody DataSourceVo dataSourceVo) throws Exception {
        Set<String> dbNameSet = DataSourceUtil.dbNameSet;
        Map map = new HashMap<>();
        map.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, dataSourceVo.getDriverClassName());
        map.put(DruidDataSourceFactory.PROP_URL, dataSourceVo.getJdbcUrl());
        map.put(DruidDataSourceFactory.PROP_USERNAME, dataSourceVo.getUsername());
        map.put(DruidDataSourceFactory.PROP_PASSWORD, dataSourceVo.getPassword());
        // 初始化时建立物理连接的个数
        map.put(DruidDataSourceFactory.PROP_INITIALSIZE, "10");
        // 最小连接池数量
        map.put(DruidDataSourceFactory.PROP_MINIDLE, "10");
        // 最大连接池数量
        map.put(DruidDataSourceFactory.PROP_MAXACTIVE, "50");
        // 获取连接时最大等待时间，单位毫秒
        map.put(DruidDataSourceFactory.PROP_MAXWAIT, "60000");
        // 检测连接的间隔时间，单位毫秒
        map.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, "60000");
        // wall:防御sql注入  stat:监控统计
        map.put(DruidDataSourceFactory.PROP_FILTERS, "wall,stat");
        map.put(DruidDataSourceFactory.PROP_NAME, dataSourceVo.getDbName());

        // 创建数据源
        DataSource tDataSource = DruidDataSourceFactory.createDataSource(map);
        DynamicDataSource dynamicDataSource = (DynamicDataSource) dataSource;
        Map<Object, DataSource> resolvedDataSources = dynamicDataSource.getResolvedDataSources();
        // 添加数据源
        resolvedDataSources.put(dataSourceVo.getDbName(), tDataSource);
        dbNameSet.add(dataSourceVo.getDbName());
        return "添加成功";
    }


    /**
     * @description: 查询数据源
     * @author: YFAN
     * @date: 2023/7/8/008 00:14
     * @param: dbName
     * @return: java.lang.Object
     **/
    @RequestMapping("/list")
    public Object list(String dbName) {
        List<DataSourceVo> dataSourceVos = dataSourceVoList;
        if (StrUtil.isNotBlank(dbName)) {
            dataSourceVos = dataSourceVoList.stream().filter(dataSourceVo -> dataSourceVo.getDbName().contains(dbName)).collect(Collectors.toList());
        }
        dataSourceVos.stream().forEach(dataSourceVo -> {
            dataSourceVo.setSelected(false);
            if (DataSourceUtil.getDB().equals(dataSourceVo.getDbName())) {
                dataSourceVo.setSelected(true);
            }
        });
        return dataSourceVos;
    }
}
