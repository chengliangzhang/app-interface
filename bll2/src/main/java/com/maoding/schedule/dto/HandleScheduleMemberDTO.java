package com.maoding.schedule.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.org.dto.CompanyUserAppDTO;

public class HandleScheduleMemberDTO extends BaseDTO {

    private String scheduleId;

    private Integer status;

    private String refuseReason;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }
}
