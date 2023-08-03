package com.yfan.tools.generator.model;

import cn.hutool.core.io.IoUtil;
import com.yfan.tools.common.util.GenConfigUtil;
import com.yfan.tools.common.util.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: tools
 * @BelongsPackage: com.yfan.tools.generator.model
 * @Description: 模板容器
 * @Author: YFAN
 * @CreateTime: 2023-07-29 19:51
 * @Version: 1.0
 */
@Component
@Slf4j
public class ModelContext {

    // 原始模板内容容器
    private Map<String, String> modelMap = new HashMap<>();

    // 当前临时使用
    private Map<String, String> modelMapTemp = new HashMap<>();

    @PostConstruct
    public void init() throws FileNotFoundException {
        List<String> templateNames = new ArrayList<>();
        PropertiesConfiguration config = GenConfigUtil.getConfig();
        if (config != null) {
            String ftlTemplates = config.getString("generato.useFtlTemplate");
            if (!ObjectUtils.isEmpty(ftlTemplates)) {
                String[] splitFtlTemplates = ftlTemplates.split(",");
                if (splitFtlTemplates.length > 0) {
                    String ftltemplateScheme = GeneratorUtil.getFtlTemplateScheme();
                    for (String templateName : splitFtlTemplates) {
                        BufferedReader bufferedReader = null;
                        try {
                            // IDEA中可以使用，Jar不能用
                            /*File file = ResourceUtils.getFile("classpath:template/" + ftltemplateScheme + templateName + ".ftl");
                            bufferedReader = new BufferedReader(inputStreamReader);*/
                            // 正确方式
                            ClassPathResource classPathResource = new ClassPathResource("template/" + ftltemplateScheme + templateName + ".ftl");
                            InputStream inputStream = classPathResource.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            bufferedReader = new BufferedReader(inputStreamReader);

                            StringBuilder stringBuilder = new StringBuilder();
                            while (bufferedReader.ready()) {
                                stringBuilder.append(bufferedReader.readLine());
                                stringBuilder.append("<br/>");
                            }
                            log.info(templateName + "---加载完成");
                            log.info(stringBuilder.toString());
                            modelMap.put(templateName, stringBuilder.toString());
                            modelMapTemp.put(templateName, stringBuilder.toString());
                        } catch (Exception e) {
                            log.error("加载模板内容异常", e);
                        } finally {
                            IoUtil.close(bufferedReader);
                        }
                    }
                }
            }
        }
    }

    public Map<String, String> getModelMap() {
        return modelMapTemp;
    }

    public void setModelMap(Map<String, String> modelMap) {
        this.modelMapTemp = modelMap;
    }

    // 初始化
    public void initMoelMap() {
        modelMapTemp = new HashMap<>();
        modelMapTemp.putAll(modelMap);
    }
}
