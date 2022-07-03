package com.yfan.yyadmin.common.util;

import cn.hutool.core.io.IoUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
*
* 文件处理工具类
*@Author: YFAN
*@CreateTime: 2022-07-03 12:46
*/
public class FileUtil extends cn.hutool.core.io.FileUtil {


    /**
     *
     * 下载
     * @param file
     * @param request
     * @param response
     * @return: void
     * @author: YFAN
     * @date: 2022/7/3/003 12:49
     **/
    public static void  downLoad(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        IoUtil.copy(fileInputStream, outputStream);
        IoUtil.close(fileInputStream);
        IoUtil.close(outputStream);
    }

}
