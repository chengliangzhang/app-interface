package com.maoding.version.service.impl;

import com.maoding.version.dao.InterfaceVersionDao;
import com.maoding.version.entity.InterfaceVersionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by sandy on 2017/10/12.
 */
@Service("interfaceVersionService")
public class InterfaceVersionServiceImpl implements InterfaceVersionService {

    @Autowired
    private InterfaceVersionDao interfaceVersionDao;

    @Override
    public void saveInterface(InterfaceVersionEntity entity) throws Exception {
        if(CollectionUtils.isEmpty(this.interfaceVersionDao.selectInterfaceByParam(entity))){
            entity.initEntity();
            interfaceVersionDao.insert(entity);
        }
    }
}
