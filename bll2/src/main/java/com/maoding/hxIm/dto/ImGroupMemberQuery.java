package com.maoding.hxIm.dto;

import com.maoding.core.util.StringUtil;

/**
 * Created by sandy on 2017/8/7.
 */
public class ImGroupMemberQuery {

    private String groupId;

    private String orgId;

    private String fastdfsUrl;

    private String url;

    private String projectGroup;

    private String companyId;

    private String userId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFastdfsUrl() {
        if(!StringUtil.isNullOrEmpty(this.url)){
            fastdfsUrl = url;
        }
        return fastdfsUrl;
    }

    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    public String getUrl() {
        if(!StringUtil.isNullOrEmpty(this.fastdfsUrl)){
            url = this.fastdfsUrl;
        }
        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(String projectGroup) {
        this.projectGroup = projectGroup;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
