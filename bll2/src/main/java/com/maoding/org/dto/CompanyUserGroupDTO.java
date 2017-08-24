package com.maoding.org.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 成员列表（按公司分组）
 */
public class CompanyUserGroupDTO implements Serializable {

    private String companyId;

    private String companyName;

    private List<CompanyUserAppDTO> userList = new ArrayList<>();

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }
}
