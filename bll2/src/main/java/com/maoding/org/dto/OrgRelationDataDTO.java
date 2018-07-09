package com.maoding.org.dto;

import com.maoding.org.entity.CompanyEntity;
import com.maoding.role.dto.OrgRoleTypeDTO;

public class OrgRelationDataDTO {

    private CompanyEntity company;

    private CompanyUserAppDTO systemManager;

    OrgRoleTypeDTO roleType;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CompanyUserAppDTO getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(CompanyUserAppDTO systemManager) {
        this.systemManager = systemManager;
    }

    public OrgRoleTypeDTO getRoleType() {
        return roleType;
    }

    public void setRoleType(OrgRoleTypeDTO roleType) {
        this.roleType = roleType;
    }
}
