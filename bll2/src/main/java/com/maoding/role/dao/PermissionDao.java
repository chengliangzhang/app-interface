package com.maoding.role.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.entity.PermissionEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PermissionService
 * 类描述：权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
public interface PermissionDao extends BaseDao<PermissionEntity> {
    /**
     * 方法描述：获取所有权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    List<PermissionDTO> getAllPermission();


    /**
     * 方法描述：获取当前角色下下所有的权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     * @return:
     */
    List<PermissionDTO> getPermissionByRole(Map<String, Object> map);


    /**
     * 方法描述：角色-权限 只查询角色对应的权限-
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     * @return:
     */
    List<PermissionDTO> getPermissionByRole2(Map<String, Object> map);



    /**
     * 方法描述：角色-权限 只userId的权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     * @return:
     */
    List<PermissionDTO> getPermissionByRoleAndUserForApp(Map<String, Object> map);

    /**
     * 方法描述：获取当前用户下所有的权限（包含了没有的权限，整个系统的权限封装成父子List）
     * 获取用户权限 封装成主从结构的数据（type为null,则未拥有的权限，type！=null,则查询自己的权限，用于人员列表使用）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    List<PermissionDTO> getPermissionByUserId(Map<String, Object> map);

    /**
     * 方法描述：获取当前用户的权限（只含有自己具有的权限）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    List<PermissionDTO> getUserPermission(Map<String, Object> map);

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    String getPermissionCodeByUserId(Map<String, Object> map);
    /**
     * 方法描述：获取当前角色下下所有的权限CodeWs
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    String getPermissionCodeByUserIdWs(Map<String, Object> map);

}