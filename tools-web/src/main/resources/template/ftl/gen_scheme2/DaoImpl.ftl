package ${packageModule}.dao.impl;

import ${packageModule}.entity.${className};
import ${package}.common.hibernate.HibernateBaseDao;
import ${packageModule}.dao.${className}Dao;
/**
*
* @author ${author}
* @date ${date}
**/
public class ${className}DaoImpl extends HibernateBaseDao<${className}, String>
    implements ${className}Dao {

    @Override
    protected Class<${className}> getEntityClass() {
        return ${className}.class;
    }
}