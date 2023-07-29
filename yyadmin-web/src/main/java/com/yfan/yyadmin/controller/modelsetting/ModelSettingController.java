package com.yfan.yyadmin.controller.modelsetting;

import cn.hutool.core.util.StrUtil;
import com.yfan.yyadmin.common.config.DataSourceUtil;
import com.yfan.yyadmin.generator.model.ModelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * @BelongsProject: yyAdmin
 * @BelongsPackage: com.yfan.yyadmin.controller.modelsetting
 * @Description: 模板配置控制类
 * @Author: YFAN
 * @CreateTime: 2023-07-29 19:43
 * @Version: 1.0
 */
@Controller
@RequestMapping("/yyadmin/modelsetting")
public class ModelSettingController {

    @Autowired
    private ModelContext modelContext;

    /**
     * @description: 查询模板内容
     * @author: YFAN
     * @date: 2023/7/29/029 20:33
     * @return: java.lang.Object
     **/
    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData() {
        return modelContext.getModelMap();
    }

}
