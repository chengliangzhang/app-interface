package com.maoding.hxIm.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/7/12.
 */
public class ImGroupUserDTO extends BaseDTO{


    /**
     * 群组id
     */
    private String groupId;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 用户id(用于退出群组，删除成员)
     */
    private String userIds;

    private String userId;

    private String companyUserId;

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

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }
}
