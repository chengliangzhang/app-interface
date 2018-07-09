package com.maoding.hxIm.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/8/7.
 */
public class ImGroupQuery extends BaseDTO{

    /**
     * 图片服务器地址
     */
    private String url;

    /**
     * 图片服务器地址（url=fastdfsUrl），为了兼容，参数名不一致
     */
    private String fastdfsUrl;

    private String userId;

    private String orgId;

    private String orgIdGroupId;

    private String groupId;

    private String groupType;

    private String companyId;

    private String companyUserId;

    /**
     * 如果是项目群组，则nodeId = 项目群
     */
    private String nodeId;

    private Integer type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFastdfsUrl() {
        return fastdfsUrl;
    }

    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgIdGroupId() {
        return orgIdGroupId;
    }

    public void setOrgIdGroupId(String orgIdGroupId) {
        this.orgIdGroupId = orgIdGroupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
