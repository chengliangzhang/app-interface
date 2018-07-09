package com.maoding.mytask.dto;

import com.maoding.core.base.dto.BaseDTO;

public class HandleMyTaskDTO extends BaseDTO {

    private String result;

    private String status;

    private String paidDate;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }
}
