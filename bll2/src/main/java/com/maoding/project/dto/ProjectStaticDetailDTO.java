package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectStaticDetailDTO
 * 类描述：新增合同详情
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
public class ProjectStaticDetailDTO extends BaseDTO {

    private String totalContractAmount;
    private String signDate;
    private String projectName;

    public String getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(String totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
