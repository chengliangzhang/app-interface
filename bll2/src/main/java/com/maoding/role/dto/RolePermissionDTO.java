package com.maoding.role.dto;

import com.maoding.core.base.dto.BaseDTO;

public class RolePermissionDTO extends BaseDTO {

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 权限ID
     */
    private String permissionId;



    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

}