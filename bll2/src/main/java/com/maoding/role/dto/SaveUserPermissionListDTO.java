package com.maoding.role.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SaveUserPermissionListDTO
 * 类描述：公司人员权限DTO（从权限中添加成员）
 * 作    者：MaoSF
 * 日    期：2016年11月16日-下午5:38:15
 */
public class SaveUserPermissionListDTO extends BaseDTO {

    /**
     * 管理员的userId（如果更改了管理员则传递，如果没有，无需传递）
     */
    private String adminUserId;

    /**
     * 企业负责人的userId（如果更改了企业负责人则传递，如果没有，无需传递）
     */
    private String orgManagerUserId;

    //普通权限修改的人员及权限集
    private List<SaveUserPermission2DTO> userPermissionList = new ArrayList<SaveUserPermission2DTO>();

    public List<SaveUserPermission2DTO> getUserPermissionList() {
        return userPermissionList;
    }

    public void setUserPermissionList(List<SaveUserPermission2DTO> userPermissionList) {
        this.userPermissionList = userPermissionList;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getOrgManagerUserId() {
        return orgManagerUserId;
    }

    public void setOrgManagerUserId(String orgManagerUserId) {
        this.orgManagerUserId = orgManagerUserId;
    }
}