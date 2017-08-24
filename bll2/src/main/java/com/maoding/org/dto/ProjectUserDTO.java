package com.maoding.org.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectUserDTO
 * 类描述：项目群组成员
 * 作    者：MaoSF
 * 日    期：2016年7月6日-下午5:38:15
 */
public class ProjectUserDTO {

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 成员
     */
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }
}