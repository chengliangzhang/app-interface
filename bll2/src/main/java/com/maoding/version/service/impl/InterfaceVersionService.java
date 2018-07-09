package com.maoding.version.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.version.entity.InterfaceVersionEntity;

/**
 * Created by sandy on 2017/10/12.
 */
public interface InterfaceVersionService {

    void saveInterface(InterfaceVersionEntity entity) throws Exception;
}
