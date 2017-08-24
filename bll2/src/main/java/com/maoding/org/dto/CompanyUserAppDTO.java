package com.maoding.org.dto;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyUserAppDTO
 * 类描述：公司人员信息(组织人员列表Entity，此Entity不对应数据库中的表)
 * 作    者：MaoSF
 * 日    期：2016年7月6日-下午5:38:15
 */
public class CompanyUserAppDTO{

    private String id;

    /**
     * userId
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;
    
    /**
     * 手机号码
     */
    private String cellphone;

    /**
     *邮箱
     */
    private String email;

    /**
     * app要求头像地址
     */
    private String imgUrl;

    private String title;//所处任务的标题

    private String completeTime;//完成时间,设计任务完成时间

    private String accountName;

    /**
     * 是否可以设置经营负责人/设计负责人（1：可以，0：不可以）
     */
    private int isUpdateOperator;

    private String targetId;

    private String companyId;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getIsUpdateOperator() {
        return isUpdateOperator;
    }

    public void setIsUpdateOperator(int isUpdateOperator) {
        this.isUpdateOperator = isUpdateOperator;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}