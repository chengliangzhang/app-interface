package com.maoding.role.dto;

import com.maoding.core.base.dto.BaseDTO;

public class UserPermissionDTO extends BaseDTO {
    /**
     * 公司ID
     */
    private String companyId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 权限ID
     */
    private String permissionId;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }


}