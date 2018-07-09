package com.maoding.org.dto;

import com.maoding.core.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 成员列表（按公司分组）
 */
public class CompanyUserGroupDTO implements Serializable {

    private String companyId;

    private String companyName;

    private Integer type;//类型（0:项目参与人,1:组织)

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

    public Integer getType() {
        if(StringUtil.isNullOrEmpty(companyId)){
            type = 0; //项目参与人
        }else {
            type = 1; //组织类型
        }
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
