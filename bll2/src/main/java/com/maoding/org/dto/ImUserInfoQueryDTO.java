package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/8/3.
 */
public class ImUserInfoQueryDTO extends BaseDTO{

    /**
     * 账号id
     */
    private String userId;

    /**
     * 图片服务器地址
     */
    private String fastdfsUrl;

    /*-------------项目群组需要添加一下参数------------------*/
    /**
     * 团队成员id（companyUser表中的id）
     */
    private String companyUserId;

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 项目id
     */
    private String projectId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFastdfsUrl() {
        return fastdfsUrl;
    }

    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
