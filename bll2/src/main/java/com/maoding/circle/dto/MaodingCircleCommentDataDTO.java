package com.maoding.circle.dto;

import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.Date;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public class MaodingCircleCommentDataDTO {

    private String id;

    private String projectId;

    private String circleId;

    private String replyUserId;

    private String commentUserId;

    private String companyId;

    private String comment;

    private Integer commentType;

    private Date createDate;

    private String commentAccountId;

    /**
     * 评论者用户名
     */
    private CompanyUserAppDTO commentUser;

    /**
     * 被回复者
     */
    private CompanyUserAppDTO replyUser;

    private Integer replyType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId == null ? null : commentUserId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public CompanyUserAppDTO getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CompanyUserAppDTO commentUser) {
        this.commentUser = commentUser;
    }

    public CompanyUserAppDTO getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(CompanyUserAppDTO replyUser) {
        this.replyUser = replyUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCommentAccountId() {
        return commentAccountId;
    }

    public void setCommentAccountId(String commentAccountId) {
        this.commentAccountId = commentAccountId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }
}
