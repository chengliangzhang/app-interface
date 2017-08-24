package com.maoding.v2.user.dto;

/**
 * @Author ZhujieChen
 * @Description:
 * @Date: Created in 11:17 2016/12/28
 * @Modified By:
 */
public class V2CompanyUserTableDTO {
    /**
     * id
     */
    private String id;
    /**
     * 公司id
     */
    private String companyId;

    /**
     * 用户id
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

    /**个人头像**/
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
