package com.yfan.yyadmin.generator.service;

import cn.hutool.core.util.ZipUtil;
import com.yfan.yyadmin.common.entity.ColumnInfo;
import com.yfan.yyadmin.common.entity.GenConfig;
import com.yfan.yyadmin.common.entity.TableColumnInfo;
import com.yfan.yyadmin.common.entity.TableInfo;
import com.yfan.yyadmin.common.mapper.generator.GeneratorMapper;
import com.yfan.yyadmin.common.util.FileUtil;
import com.yfan.yyadmin.common.util.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器主要处理类
 *
 * @Author: YFAN
 * @CreateTime: 2022-07-02 16:21
 */
@Service
@Slf4j
public class GeneratorService {

    @Autowired
    private GeneratorMapper generatorMapper;

    /**
     * 查询数据表表
     *
     * @param query
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author: YFAN
     * @date: 2022/7/2/002 22:57
     **/
    public List<TableInfo> queryList(Map<String, Object> query) {
        // 需先定义查询的分页参数
//        PageHelper.startPage(1, 10);
        // 一个普通的查询在其后，这个就会被自动加上分页
        List<TableInfo> tableInfolist = generatorMapper.queryList(query);
        // 获取分页信息
//        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(tableInfolist);
        return tableInfolist;
    }

    /**
     * 生成代码
     *
     * @param tables
     * @param response
     * @return: void
     * @author: YFAN
     * @date: 2022/7/2/002 22:57
     **/
    public void generatorCode(String tables, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableArray = tables.split(",");
        File[] filePaths = new File[tableArray.length];
        int index = 0;
        for (String tableName : tableArray) {
            //查询表信息
            TableInfo tableInfo = queryTable(tableName);
            //查询列信息
            List<TableColumnInfo> tableColumnInfos = queryColumns(tableName);
            // 配置信息
            GenConfig config = new GenConfig(tableName, tableInfo.getTableComment());
            List<ColumnInfo> columnInfoList = new ArrayList<>();
            tableColumnInfos.forEach(tableColumnInfo -> {
                ColumnInfo columnInfo = new ColumnInfo(tableInfo.getTableName(), tableColumnInfo.getColumnName(),
                        true, tableColumnInfo.getDataType(),tableColumnInfo.getColumnType(), tableColumnInfo.getColumnComment(),
                        tableColumnInfo.getColumnKey(), tableColumnInfo.getExtra());
                columnInfoList.add(columnInfo);
            });
            // 生成代码文件
            String filepath = GeneratorUtil.generatorFile(columnInfoList, config);
            filePaths[index++] = new File(filepath);
        }
        // 临时路径
        String rootPath = GeneratorUtil.getGenTempPath();
        String zipPath = rootPath + ".zip";
        File zipFile = new File(zipPath);
        // 打包成zip
        ZipUtil.zip(zipFile, true, filePaths);
        // 下载
        FileUtil.downLoad(zipFile, request, response);
        //删除文件
        FileUtil.del(zipFile);
    }

    /**
     * 查询表信息
     *
     * @param tableName
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @author: YFAN
     * @date: 2022/7/2/002 23:00
     **/
    public TableInfo queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    public Map<String, String> queryTableMap(String tableName) {
        return generatorMapper.queryTableMap(tableName);
    }

    /**
     * 查询列信息
     *
     * @param tableName
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author: YFAN
     * @date: 2022/7/2/002 23:00
     **/
    public List<TableColumnInfo> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }

    public List<Map<String, String>> queryColumnsMap(String tableName) {
        return generatorMapper.queryColumnsMap(tableName);
    }
}
