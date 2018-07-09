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
public class MaodingCircleCommentDTO extends BaseDTO{

    private String circleId;

    private String replyUserId;

    private String replyCommentId;//被评论的评论id

    private String commentUserId;

    private String companyId;

    private String comment;

    private Integer commentType; //1：点赞，2：评论

    /**
     * 需要通知的用户的id
     */
    private List<CompanyUserAppDTO> userList = new ArrayList<>();

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

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
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

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }
}
