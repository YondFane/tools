package com.yfan.tools.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.*;
import com.yfan.tools.common.entity.ColumnInfo;
import com.yfan.tools.common.entity.GenConfig;
import com.yfan.tools.common.ftlpath.FtlPathContext;
import com.yfan.tools.common.ftlpath.FtlPathStrategy;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成工具类
 *
 * @Author: YFAN
 * @CreateTime: 2022-07-02 23:50
 */
public class GeneratorUtil {


    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";
    // 主键
    public static final String PK = "PRI";
    // mysql自增
    public static final String EXTRA = "auto_increment";
    // 临时文件路径
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;
    // 临时文件路径目录名
    public static final String YYADMIN_TEMP = "tools-gen-temp";
    // 默认ftl方案
    public static final String FTL_TEMPLATESCHEME = "ftl"+File.separator+"gen_scheme1"+File.separator;

    /**
     *
     * 获取生成器的临时路径
     * @return: java.lang.String
     * @author: YFAN
     * @date: 2022/7/3/003 13:28
     **/
    public static String getGenTempPath() {
        PropertiesConfiguration config = GenConfigUtil.getConfig();
        if(config!=null) {
            // 返回配置的临时路径
             String rootPath = config.getString("generator.path", GeneratorUtil.SYS_TEM_DIR + GeneratorUtil.YYADMIN_TEMP);
             // 如果配置的路径存在
             if (FileUtil.exist(rootPath)) {
                 return rootPath;
             }
        }
        // java系统临时路径
        return GeneratorUtil.SYS_TEM_DIR + GeneratorUtil.YYADMIN_TEMP;
    }

    /**
     *
     * 获取当前生成器使用的ftl模板路径
     * @return: java.lang.String
     * @author: YFAN
     * @date: 2022/7/3/003 14:08
     **/
    public static String getFtlTemplateScheme() {
        PropertiesConfiguration config = GenConfigUtil.getConfig();
        if(config!=null) {
            // 默认使用一套
            return config.getString("generator.ftlTemplateScheme", FTL_TEMPLATESCHEME);
        }
        return FTL_TEMPLATESCHEME;
    }

    /**
     *
     * 获取后端代码模板名称
     * @return: java.util.List<java.lang.String>
     * @author: YFAN
     * @date: 2022/7/3/003 16:25
     **/
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        PropertiesConfiguration config = GenConfigUtil.getConfig();
        if(config != null) {
            String ftlTemplates = config.getString("generato.useFtlTemplate");
            if (!ObjectUtils.isEmpty(ftlTemplates)) {
                String[] splitFtlTemplates = ftlTemplates.split(",");
                if (splitFtlTemplates.length>0) {
                    for (String template : splitFtlTemplates) {
                        templateNames.add(template);
                    }
                    return templateNames;
                }
            }
        }
        // 默认使用以下模板
        templateNames.add("Entity");
        templateNames.add("Dto");
        templateNames.add("Mapper");
        templateNames.add("Controller");
        templateNames.add("QueryCriteria");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Dao");
        templateNames.add("DaoImpl");
        return templateNames;
    }

    public static List<Map<String, Object>> preview(List<ColumnInfo> columns, GenConfig genConfig) {
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        List<Map<String, Object>> genList = new ArrayList<>();
        // 获取后端模版
        List<String> templates = getAdminTemplateNames();
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        String ftltemplateScheme = getFtlTemplateScheme();
        for (String templateName : templates) {
            Map<String, Object> map = new HashMap<>(1);
            Template template = engine.getTemplate(ftltemplateScheme + templateName + ".ftl");
            map.put("content", template.render(genMap));
            map.put("name", templateName);
            genList.add(map);
        }
        return genList;
    }

    /**
     *
     * 生成代码文件
     * @param columns
     * @param genConfig
     * @return: java.lang.String
     * @author: YFAN
     * @date: 2022/7/3/003 14:21
     **/
    public static String generatorFile(List<ColumnInfo> columns, GenConfig genConfig) throws IOException {
        // 拼接路径
        String tempPath = getGenTempPath() + File.separator + genConfig.getTableName() + File.separator;
        // 获取参数
        Map<String, Object> genMap = getGenMap(columns, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // 生成后端代码
        List<String> templates = getAdminTemplateNames();
        String ftltemplateScheme = getFtlTemplateScheme();
        for (String templateName : templates) {
            Template template = engine.getTemplate(ftltemplateScheme + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), tempPath + File.separator);
            if (filePath ==null) {
                continue;
            }
            File file = new File(filePath);
            // 如果非覆盖生成
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            createTemplateFile(file, template, genMap);
        }
        return tempPath;
    }

    /**
     *
     * 生成代码
     * @param columnInfos
     * @param genConfig
     * @return: void
     * @author: YFAN
     * @date: 2022/7/3/003 14:18
     **/
    public static void generatorCode(List<ColumnInfo> columnInfos, GenConfig genConfig) throws IOException {
        Map<String, Object> genMap = getGenMap(columnInfos, genConfig);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        // 生成后端代码
        List<String> templates = getAdminTemplateNames();
        String ftlTemplateScheme = getFtlTemplateScheme();
        for (String templateName : templates) {
            Template template = engine.getTemplate(ftlTemplateScheme+ templateName + ".ftl");
            String rootPath = System.getProperty("user.dir");
            String filePath = getAdminFilePath(templateName, genConfig, genMap.get("className").toString(), rootPath);

            if (filePath ==null) {
                continue;
            }
            File file = new File(filePath);

            // 如果非覆盖生成
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            createTemplateFile(file, template, genMap);
        }
    }

    /**
     *
     * 获取模版数据
     * @param columnInfos
     * @param genConfig
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @author: YFAN
     * @date: 2022/7/3/003 16:24
     **/
    private static Map<String, Object> getGenMap(List<ColumnInfo> columnInfos, GenConfig genConfig) {
        // 存储模版字段数据
        Map<String, Object> genMap = new HashMap<>(16);
        // 接口别名
        genMap.put("apiAlias", genConfig.getApiAlias());
        // 包名称
        genMap.put("package", genConfig.getPack());
        // 包名称+模块
        genMap.put("packageModule", genConfig.getPackModule());
        // 模块名称
        genMap.put("moduleName", genConfig.getModuleName());
        // 作者
        genMap.put("author", genConfig.getAuthor());
        // 创建日期
        genMap.put("date", LocalDate.now().toString());
        // 表名
        genMap.put("tableName", genConfig.getTableName().toUpperCase());
        // 表注释
        if (ObjectUtils.isEmpty(genConfig.getTableComment())) {
            genMap.put("tableComment", "");
        } else {
            genMap.put("tableComment", genConfig.getTableComment());
        }
        // 大写开头的类名
        String className = StringUtil.toCapitalizeCamelCase(genConfig.getTableName());
        // 小写开头的类名
        String changeClassName = StringUtil.toCamelCase(genConfig.getTableName());
        // 判断是否去除表前缀
        if (!StrUtil.isEmpty((genConfig.getPrefix()))) {
            className = StringUtil.toCapitalizeCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtil.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
            changeClassName = StringUtil.uncapitalize(changeClassName);
        }
        // 保存类名
        genMap.put("className", className);
        // 保存小写开头的类名
        genMap.put("changeClassName", changeClassName);
        // 存在 Timestamp 字段
        genMap.put("hasTimestamp", false);
        // 查询类中存在 Timestamp 字段
        genMap.put("queryHasTimestamp", false);
        // 存在 BigDecimal 字段
        genMap.put("hasBigDecimal", false);
        // 查询类中存在 BigDecimal 字段
        genMap.put("queryHasBigDecimal", false);
        // 是否需要创建查询
        genMap.put("hasQuery", false);
        // 自增主键
        genMap.put("auto", false);
        // 存在字典
        genMap.put("hasDict", false);
        // 存在日期注解
        genMap.put("hasDateAnnotation", false);
        // 保存字段信息
        List<Map<String, Object>> columns = new ArrayList<>();
        // 保存查询字段的信息
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        // 存储字典信息
        List<String> dicts = new ArrayList<>();
        // 存储 between 信息
        List<Map<String, Object>> betweens = new ArrayList<>();
        // 存储不为空的字段信息
        List<Map<String, Object>> isNotNullColumns = new ArrayList<>();

        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>(16);
            // 字段描述
            listMap.put("remark", column.getRemark());
            // 字段类型
            listMap.put("columnKey", column.getKeyType());
            // 主键类型
            String colType = ColUtil.cloToJava(column.getDataType());
            // 小写开头的字段名
            String changeColumnName = StringUtil.toCamelCase(column.getColumnName());
            // 大写开头的字段名
            String capitalColumnName = StringUtil.toCapitalizeCamelCase(column.getColumnName());

            if (PK.equals(column.getKeyType())) {
                // 存储主键类型
                genMap.put("pkColumnType", colType);
                // 存储小写开头的字段名
                genMap.put("pkChangeColName", changeColumnName);
                // 存储大写开头的字段名
                genMap.put("pkCapitalColName", capitalColumnName);
            }
            // 是否存在 Timestamp 类型的字段
            if (TIMESTAMP.equals(colType)) {
                genMap.put("hasTimestamp", true);
            }
            // 是否存在 BigDecimal 类型的字段
            if (BIGDECIMAL.equals(colType)) {
                genMap.put("hasBigDecimal", true);
            }
            // 主键是否自增
            if (EXTRA.equals(column.getExtra())) {
                genMap.put("auto", true);
            }
            // 主键存在字典
            if (StrUtil.isNotBlank(column.getDictName())) {
                genMap.put("hasDict", true);
                if (!dicts.contains(column.getDictName()))
                    dicts.add(column.getDictName());
            }

            // tinyint(1) 默认 Boolean
            if ("tinyint(1)".equals(column.getColumnType())) {
                colType = "Boolean";
            }
            // 存储字段类型
            listMap.put("columnType", colType);
            // 存储字原始段名称
            listMap.put("columnName", column.getColumnName().toUpperCase());
            // 不为空
            listMap.put("istNotNull", column.getNotNull());
            // 字段列表显示
            listMap.put("columnShow", column.getListShow());
            // 表单显示
            listMap.put("formShow", column.getFormShow());
            // 表单组件类型
            listMap.put("formType", StrUtil.isNotBlank(column.getFormType()) ? column.getFormType() : "Input");
            // 小写开头的字段名称
            listMap.put("changeColumnName", changeColumnName);
            //大写开头的字段名称
            listMap.put("capitalColumnName", capitalColumnName);
            // 字典名称
            listMap.put("dictName", column.getDictName());
            // 日期注解
            listMap.put("dateAnnotation", column.getDateAnnotation());

            if (StrUtil.isNotBlank(column.getDateAnnotation())) {
                genMap.put("hasDateAnnotation", true);
            }
            // 添加非空字段信息
            if (column.getNotNull()) {
                isNotNullColumns.add(listMap);
            }
            // 判断是否有查询，如有则把查询的字段set进columnQuery
            if (!StrUtil.isBlank(column.getQueryType())) {
                // 查询类型
                listMap.put("queryType", column.getQueryType());
                // 是否存在查询
                genMap.put("hasQuery", true);
                if (TIMESTAMP.equals(colType)) {
                    // 查询中存储 Timestamp 类型
                    genMap.put("queryHasTimestamp", true);
                }
                if (BIGDECIMAL.equals(colType)) {
                    // 查询中存储 BigDecimal 类型
                    genMap.put("queryHasBigDecimal", true);
                }
                if ("between".equalsIgnoreCase(column.getQueryType())) {
                    betweens.add(listMap);
                } else {
                    // 添加到查询列表中
                    queryColumns.add(listMap);
                }
            }
            // 添加到字段列表中
            columns.add(listMap);
        }
        // 保存字段列表
        genMap.put("columns", columns);
        // 保存查询列表
        genMap.put("queryColumns", queryColumns);
        // 保存字段列表
        genMap.put("dicts", dicts);
        // 保存查询列表
        genMap.put("betweens", betweens);
        // 保存非空字段信息
        genMap.put("isNotNullColumns", isNotNullColumns);
        return genMap;
    }

    /**
     *
     * 定义后端文件路径以及名称
     * @param templateName
     * @param genConfig
     * @param className
     * @param rootPath
     * @return: java.lang.String
     * @author: YFAN
     * @date: 2022/7/3/003 16:25
     **/
    private static String getAdminFilePath(String templateName, GenConfig genConfig, String className, String rootPath) {
        String projectPath = rootPath + File.separator + genConfig.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        String packageModulePath = packagePath;
        if (!ObjectUtils.isEmpty(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator;
        }

        if  (!ObjectUtils.isEmpty(genConfig.getPackModule())) {
            packageModulePath += genConfig.getPackModule().replace(".", File.separator) + File.separator;
        }
        // 获取模板对应的文件路径
        FtlPathStrategy ftlPathStrategy = FtlPathContext.getFtlPathStrategy(templateName);
        if (ftlPathStrategy != null) {
            return ftlPathStrategy.getFtlPath(className, packagePath, packageModulePath);
        }
        return null;
    }

    /**
     * @param file
     * @param template
     * @param map
     * @return: void
     * @author: YFAN
     * @date: 2022/7/3/003 12:46
     **/
    private static void createTemplateFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }

}
