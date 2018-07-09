package com.maoding.circle.dto;

import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Creator: sandy
 * Date:2017/11/15
 * 类名：app-interface
 */
public class MaodingCircleDataDTO {

    private String id;

    private String projectId;

    private String publishUserId;

    private String companyId;

    private String companyName;

    private Integer circleType;

    private String content;

    private Date createDate;

    private int commentCount;

    private String currentUserOrgId;
    /**
     * 当前发布卯丁圈的用户的信息
     */
    private CompanyUserAppDTO publishUser ;

    //图片集合
    private List<String> attachList = new ArrayList<>();

    //缩略图片集合
    private List<String> thumbAttachList = new ArrayList<>();

    //点赞集合
    private  List<MaodingCircleCommentDataDTO> praiseList = new ArrayList<>();

    //评论集合
    private  List<MaodingCircleCommentDataDTO> commentList = new ArrayList<>();

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

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<String> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<String> attachList) {
        this.attachList = attachList;
    }

    public List<MaodingCircleCommentDataDTO> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<MaodingCircleCommentDataDTO> praiseList) {
        this.praiseList = praiseList;
    }

    public List<MaodingCircleCommentDataDTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<MaodingCircleCommentDataDTO> commentList) {
        this.commentList = commentList;
    }

    public CompanyUserAppDTO getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(CompanyUserAppDTO publishUser) {
        this.publishUser = publishUser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCurrentUserOrgId() {
        return currentUserOrgId;
    }

    public void setCurrentUserOrgId(String currentUserOrgId) {
        this.currentUserOrgId = currentUserOrgId;
    }

    public List<String> getThumbAttachList() {
//        thumbAttachList = new ArrayList<>();
//        for(String path:attachList){
//            String suffix = path.substring(path.lastIndexOf("."),path.length());
//            String thumbPath = path.substring(0,path.lastIndexOf("."));
//            thumbAttachList.add(thumbPath+"_250x250"+suffix);
//        }
        return thumbAttachList;
    }

    public void setThumbAttachList(List<String> thumbAttachList) {
        this.thumbAttachList = thumbAttachList;
    }
}
