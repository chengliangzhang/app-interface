package com.maoding.v2.project.dto;

import com.maoding.core.base.dto.NewBaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：NewProjectDTO
 * 类描述：项目DTO
 * 作    者：ChenZJ
 * 日    期：2016年12月06日-下午4:35:50
 */
public class NewProjectDTO extends NewBaseDTO {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**公司id*/

    private String companyId;
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
