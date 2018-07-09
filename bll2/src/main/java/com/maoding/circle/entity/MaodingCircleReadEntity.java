package com.maoding.circle.entity;

import com.maoding.core.base.entity.BaseEntity;

public class MaodingCircleReadEntity extends BaseEntity{

    private String userId;

    private String companyId;

    private String circleId;

    private String commentId;

    private int isEspeciallyRemind;

    private Integer isRead;

    private Integer deleted;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public int getIsEspeciallyRemind() {
        return isEspeciallyRemind;
    }

    public void setIsEspeciallyRemind(int isEspeciallyRemind) {
        this.isEspeciallyRemind = isEspeciallyRemind;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}