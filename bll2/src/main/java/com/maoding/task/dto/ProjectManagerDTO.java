package com.maoding.task.dto;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectManagerDTO
 * 类描述：项目经营人、负责人实体
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
public class ProjectManagerDTO {

    private String id;

    /**
     * 1：项目经营人，2：项目负责人
     */
    private int type;

    /**
     *company_user表中的id
     */
    private String companyUserId;

    /**
     * 账号id（用来判断 userId == accountId :flag=1)
     */
    private String userId;

    /**
     *名字
     */
    private String userName;

    /**
     * 账号名字
     */
    private String accountName;

    /**
     * 移交标示，如果1：可以移交，0：不可以移交
     */
    private int flag;

    /**
     * 头像
     */
    private String imgUrl;

    /**
     *电话号码
     */
    private String cellphone;

    /**
     *邮箱
     */
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId == null ? null : companyUserId.trim();
    }

    public String getUserName() {
        return userName;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}