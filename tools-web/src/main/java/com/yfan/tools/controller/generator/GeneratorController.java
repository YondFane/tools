package com.yfan.tools.controller.generator;

import com.yfan.tools.common.entity.TableInfo;
import com.yfan.tools.generator.service.GeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 主要访问控制器类
 *
 * @Author: YFAN
 * @CreateTime: 2022-07-02  15:44
 */
@Controller
@RequestMapping("/tools/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    /**
     * 查询数据库表列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object list(@RequestParam Map<String, Object> params) {
        List<TableInfo> mapPageInfo = generatorService.queryList(params);
        return mapPageInfo;
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletRequest request, HttpServletResponse response) throws IOException {
        generatorService.generatorCode(tables, request, response);
    }


}
