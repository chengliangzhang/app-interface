package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by Idccapp22 on 2016/8/9.
 */
public class ProjectContractAmountDTO extends BaseDTO {

    private String totalContractAmount;
    private String sumCount;

    public String getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(String totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public String getSumCount() {
        return sumCount;
    }

    public void setSumCount(String sumCount) {
        this.sumCount = sumCount;
    }
}
