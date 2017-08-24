package com.maoding.role.service.impl;


import com.maoding.core.base.service.GenericService;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.entity.PermissionEntity;
import com.maoding.role.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PermissionService
 * 类描述：权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
@Service("permissionService")
public class PermissionServiceImpl  extends GenericService<PermissionEntity> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 方法描述：获取所有权限
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param:
     * @return:
     */
    @Override
    public List<PermissionDTO> getAllPermission() {
        return permissionDao.getAllPermission();
    }

    /**
     * 方法描述：获取当前角色下下所有的权限
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param map
     * @param:roleId，companyId
     * @return:
     */
    @Override
    public List<PermissionDTO> getPermissionByRole(Map<String, Object> map) {
        return permissionDao.getPermissionByRole(map);
    }

    /**
     * 方法描述：获取当前员工下下所有的权限
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param map
     * @param:userId，companyId
     * @return:
     */
    @Override
    public List<PermissionDTO> getPermissionByUserId(Map<String, Object> map) {
        return permissionDao.getPermissionByUserId(map);
    }

    /**
     * 方法描述：获取当前用户的权限（只含有自己具有的权限）
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param map
     * @param:userId，companyId
     * @return:
     */
    @Override
    public List<PermissionDTO> getUserPermission(Map<String, Object> map) {
        List<PermissionDTO> permissionList = permissionDao.getUserPermission(map);
        List<PermissionDTO> newPermissionList = new ArrayList<PermissionDTO>();
        if(!CollectionUtils.isEmpty(permissionList)){
            String roleNotshow = "project_view_point,project_design_fee_view,project_manage_fee_view,report_new_contract,report_contract_pay,report_design_fee,report_manage_fee,report_static_fee,report_finance_static,project_point_edit,project_point_condition,project_design_fee_set,project_design_fee_audit,project_manage_fee_set,project_manage_fee_audit,project_point_invoice,project_point_pay,project_design_fee_pay,project_design_fee_paid,project_manage_fee_pay,project_manage_fee_paid,finance_fixed_edit,project_task_confirm";
            for(PermissionDTO dto:permissionList){
                if(roleNotshow.indexOf(dto.getCode())>-1){
                    continue;
                }
                newPermissionList.add(dto);
            }
        }
        return newPermissionList;
    }

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param map
     * @param:userId，companyId
     * @return:
     */
    @Override
    public String getPermissionCodeByUserId(Map<String, Object> map) {
        return permissionDao.getPermissionCodeByUserId(map);
    }

    /**
     * 方法描述：获取当前角色下下所有的权限CodeWs
     * 作者：MaoSF
     * 日期：2016/11/2
     *
     * @param map
     * @param:userId，companyId
     * @return:
     */
    @Override
    public String getPermissionCodeByUserIdWs(Map<String, Object> map) {
        return permissionDao.getPermissionCodeByUserIdWs(map);
    }
}