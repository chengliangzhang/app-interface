package com.maoding.role.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SaveUserPermission2DTO
 * 类描述：公司人员权限DTO（从权限中添加成员）
 * 作    者：MaoSF
 * 日    期：2016年11月16日-下午5:38:15
 */
public class UserPermissionListDTO extends BaseDTO {

    /**
     * 权限ID
     */
    private String permissionId;
    /**
     * userID
     */
    private List<CompanyUserAppDTO> companyUserList = new ArrayList<CompanyUserAppDTO>();

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public List<CompanyUserAppDTO> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUserAppDTO> companyUserList) {
        this.companyUserList = companyUserList;
    }
}