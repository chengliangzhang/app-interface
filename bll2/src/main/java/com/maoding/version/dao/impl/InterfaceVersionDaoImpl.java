package com.maoding.version.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.version.dao.InterfaceVersionDao;
import com.maoding.version.entity.InterfaceVersionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandy on 2017/10/12.
 */
@Service("interfaceVersionDao")
public class InterfaceVersionDaoImpl extends GenericDao<InterfaceVersionEntity> implements InterfaceVersionDao {

    @Override
    public List<InterfaceVersionEntity> selectInterfaceByParam(InterfaceVersionEntity entity) {
        return this.sqlSession.selectList("InterfaceVersionEntityMapper.selectInterfaceByParam",entity);
    }
}
