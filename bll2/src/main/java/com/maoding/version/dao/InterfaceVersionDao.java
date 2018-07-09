package com.maoding.version.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.version.entity.InterfaceVersionEntity;

import java.util.List;

/**
 * Created by sandy on 2017/10/12.
 */
public interface InterfaceVersionDao extends BaseDao<InterfaceVersionEntity> {

    List<InterfaceVersionEntity> selectInterfaceByParam(InterfaceVersionEntity entity);
}
