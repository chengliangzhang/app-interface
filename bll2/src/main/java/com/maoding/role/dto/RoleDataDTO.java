package com.maoding.role.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RoleDTO
 * 类描述：权限DTO
 * 作    者：MaoSF
 * 日    期：2016年7月11日-下午3:22:50
 */
public class RoleDataDTO {


    private String id;

    private String code;

    private String name;

    List<PermissionDTO> permissionList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<PermissionDTO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionDTO> permissionList) {
        this.permissionList = permissionList;
    }

}