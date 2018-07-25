package com.maoding.deliver.dto;

import com.maoding.org.dto.CompanyUserAppDTO;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/18
 * @description : 查询交付任务时返回列表内的负责人信息
 */
public class ResponseDTO extends CompanyUserAppDTO {
    /** 名称 */
    private String name;
    /** companyUser名称 */
    private String companyUserName;
    /** 用户电话 */
    private String cellPhone;
    /** 是否已完成 */
    private String isFinished;

    public String getName() {
        return getAccountName();
    }

    public void setName(String name) {
        setAccountName(name);
    }

    public String getCompanyUserName() {
        return getUserName();
    }

    public void setCompanyUserName(String companyUserName) {
        setUserName(companyUserName);
    }

    public String getCellPhone() {
        return getCellphone();
    }

    public void setCellPhone(String cellPhone) {
        setCellphone(cellPhone);
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }
}
