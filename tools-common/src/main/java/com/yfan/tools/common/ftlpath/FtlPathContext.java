package com.yfan.tools.common.ftlpath;

import com.yfan.tools.common.enums.FtlType;
import com.yfan.tools.common.ftlpath.impl.*;

import java.util.HashMap;

/**
 * ftlPath环境类
 * @Author: YFAN
 * @CreateTime: 2022-07-07  22:18
 */
public class FtlPathContext {

    private static HashMap<String, FtlPathStrategy> context = new HashMap<>();

    static {
        context.put(FtlType.CONTROLLER.getCode(), new ControllerFtlPathStrategyImpl());
        context.put(FtlType.ENTITY.getCode(), new EntityFtlPathStrategyImpl());
        context.put(FtlType.DAO.getCode(), new DaoFtlPathStrategyImpl());
        context.put(FtlType.DAOIMPL.getCode(), new DaoImplFtlPathStrategyImpl());
        context.put(FtlType.SERVICE.getCode(), new ServiceFtlPathStrategyImpl());
        context.put(FtlType.SERVICEIMPL.getCode(), new ServiceImplFtlPathStrategyImpl());
        context.put(FtlType.DTO.getCode(), new DtoFtlPathStrategyImpl());
        context.put(FtlType.QUERYCRITERIA.getCode(), new QueryCriteriaFtlPathStrategyImpl());
        context.put(FtlType.MAPPER.getCode(), new MapperFtlPathStrategyImpl());
    }

    public static FtlPathStrategy getFtlPathStrategy(String ftlType) {
        if (ftlType == null) {
            return null;
        }
        return context.get(ftlType.toUpperCase());
    }
}
