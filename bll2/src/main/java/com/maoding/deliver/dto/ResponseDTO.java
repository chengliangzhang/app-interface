package com.maoding.deliver.dto;

import com.maoding.core.base.dto.BaseShowDTO;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/18
 * @description : 查询交付任务时返回列表内的负责人信息
 */
public class ResponseDTO extends BaseShowDTO {
    /** companyUser名称 */
    private String companyUserName;
    /** 用户编号 */
    private String userId;
    /** 用户名称 */
    private String userName;
    /** 用户电话 */
    private String cellPhone;
    /** 用户头像地址 */
    private String fileFullPath;
    /** 是否已完成 */
    private String isFinished;

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }
}
