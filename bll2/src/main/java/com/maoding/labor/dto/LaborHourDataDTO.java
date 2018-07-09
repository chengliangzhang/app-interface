package com.maoding.labor.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LaborHourDataDTO {

    private Date laborDate;

    private String totalLaborHours;

    List<LaborHourDTO> laborList = new ArrayList<>();

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
    }

    public List<LaborHourDTO> getLaborList() {
        return laborList;
    }

    public void setLaborList(List<LaborHourDTO> laborList) {
        this.laborList = laborList;
    }

    public String getTotalLaborHours() {
        return totalLaborHours;
    }

    public void setTotalLaborHours(String totalLaborHours) {
        this.totalLaborHours = totalLaborHours;
    }
}
