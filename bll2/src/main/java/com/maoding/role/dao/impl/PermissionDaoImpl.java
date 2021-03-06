package com.maoding.role.dao.impl;


import com.google.common.collect.Lists;
import com.maoding.core.base.dao.GenericDao;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.entity.PermissionEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("permissionDao")
public class PermissionDaoImpl extends GenericDao<PermissionEntity> implements PermissionDao {

    @Override
    public List<PermissionEntity> getDefaultPermission() {
        return this.sqlSession.selectList("PermissionEntityMapper.getDefaultPermission");
    }

    /**
     * 方法描述：角色-权限 只查询角色对应的权限-
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     */
    @Override
    public List<PermissionDTO> getPermissionByRole2(Map<String, Object> map) {
        return this.sqlSession.selectList("GetPermissionUserMapper.getPermissionByRole2",map);
    }

    /**
     * 方法描述：角色-权限 只userId的权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     */
    @Override
    public List<PermissionDTO> getPermissionByRoleAndUserForApp(Map<String, Object> map) {
        return this.sqlSession.selectList("GetPermissionUserMapper.getPermissionByRoleAndUserForApp",map);
    }

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     */
    @Override
    public String getPermissionCodeByUserId(Map<String, Object> map) {
        return this.sqlSession.selectOne("GetPermissionMapper.getPermissionCodeByUserId",map);
    }

    @Override
    public int getCompanyUserIsHasPermission(Map<String, Object> map) {
        return this.sqlSession.selectOne("GetPermissionByUserAndRoleMapper.getCompanyUserIsHasPermission",map);
    }

}