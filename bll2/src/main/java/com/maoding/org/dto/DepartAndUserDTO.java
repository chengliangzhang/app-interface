package com.maoding.org.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartAndUserDTO extends DepartDataDTO {

    /**
     * 成员数（companyUserList.size()）
     */
    private Integer userCount = 0;

    private List<CompanyUserDataDTO> companyUserList = new ArrayList<>();

    public Integer getUserCount() {
        userCount = companyUserList.size();
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public List<CompanyUserDataDTO> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUserDataDTO> companyUserList) {
        this.companyUserList = companyUserList;
    }
}
