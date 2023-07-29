package ${packageModule}.service.impl;

import ${packageModule}.entity.${className};

import ${packageModule}.dao.${className}Dao;
import ${packageModule}.service.${className}Service;
import ${package}.common.hibernate.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
*
* @description ${className}服务实现
* @author ${author}
* @date ${date}
**/
@Service

public class ${className}ServiceImpl extends BaseServiceImpl<${className}, String>
    implements ${className}Service  {

    @Autowired
    private ${className}Dao ${changeClassName}Dao;

    @Autowired
    public ${className}ServiceImpl(${className}Dao ${changeClassName}Dao) {
        super(${changeClassName}Dao);
    }
}