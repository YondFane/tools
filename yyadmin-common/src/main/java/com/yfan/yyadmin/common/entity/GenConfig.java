package com.yfan.yyadmin.common.entity;

import com.yfan.yyadmin.common.util.GenConfigUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 代码生成配置信息
 * @Author: YFAN
 * @CreateTime: 2022-07-02 23:51
 */
@Data
@NoArgsConstructor
public class GenConfig {

    // 表名
    private String tableName;

    // 表注释
    private String tableComment;

    // 接口名称
    private String apiAlias;

    // 包路径
    private String pack;

    // 包路径+模块路径
    private String packModule;

    // 模块名
    private String moduleName;

    // 作者
    private String author;

    // 表前缀
    private String prefix;

    // 是否覆盖
    private Boolean cover = false;

    public GenConfig(String tableName, String tableComment) {
        this.tableComment = tableComment;
        this.tableName = tableName;
        PropertiesConfiguration config = GenConfigUtil.getConfig();
        if (config != null) {
            this.setAuthor(config.getString("genconfig.author"));
            this.setPack(config.getString("genconfig.pack"));
            this.setCover("true".equals(config.getString("genconfig.cover", "false")));
            this.setApiAlias(config.getString("genconfig.apiAlias"));
            this.setModuleName(config.getString("genconfig.moduleName"));
            this.setPrefix(config.getString("genconfig.prefix"));
            this.setPackModule(config.getString("genconfig.packModule", getPack()));
        }
    }
}
