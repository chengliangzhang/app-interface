package com.maoding.role.service;


import com.maoding.core.base.service.BaseService;
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
public interface PermissionService extends BaseService<PermissionEntity> {

    /**
     * 方法描述：获取所有权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    public List<PermissionDTO> getAllPermission();

    /**
     * 方法描述：获取当前角色下下所有的角色
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     * @return:
     */
    public List<PermissionDTO> getPermissionByRole(Map<String,Object> map);

    /**
     * 方法描述：获取当前角色下下所有的角色
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:roleId，companyId
     * @return:
     */
    public List<PermissionDTO> getPermissionByUserId(Map<String,Object> map);

    /**
     * 方法描述：获取当前用户的权限（只含有自己具有的权限）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    public List<PermissionDTO> getUserPermission(Map<String,Object> map);

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    public String getPermissionCodeByUserId(Map<String,Object> map);
    /**
     * 方法描述：获取当前角色下下所有的权限CodeWs
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    public String getPermissionCodeByUserIdWs(Map<String,Object> map);
}