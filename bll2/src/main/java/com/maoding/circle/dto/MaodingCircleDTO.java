package com.maoding.circle.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public class MaodingCircleDTO extends BaseDTO{

    private String userId;

    private String companyId;

    private Integer circleType;

    private String content;

    private String projectId;

    private String targetId;//前端传递过来的id

    /**
     * 需要通知的用户的id
     */
    private List<CompanyUserAppDTO> userList = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Integer getCircleType() {
        return circleType;
    }

    public void setCircleType(Integer circleType) {
        this.circleType = circleType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }
}
