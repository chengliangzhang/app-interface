package com.maoding.projectmember.dto;

public class ProjectMemberDTO {

    private String id;

    private String projectId;

    private String companyId;

    private String accountId;

    /**
     * 账号id（用来判断 userId == accountId :flag=1)
     */
    private String userId;

    private String companyUserId;

    private Integer memberType;

    private Integer type;

    private String targetId;

    private String companyUserName;

    /**
     *名字
     */
    private String userName;

    private String accountName;

    private String imgUrl;

    private String cellphone;

    private String email;

    /**
     * 移交标示，如果1：可以移交，0：不可以移交
     */
    private int flag;

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
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId == null ? null : companyUserId.trim();
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserId() {
        //此处return accountId
        return accountId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        //此处return companyUserName
        return this.companyUserName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getType() {
        //此处return memberType
        return this.memberType;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}