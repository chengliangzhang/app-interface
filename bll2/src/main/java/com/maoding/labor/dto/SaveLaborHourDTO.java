package com.maoding.labor.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveLaborHourDTO extends BaseDTO {

    private Date laborDate;

    private String companyUserId;

    List<LaborHourDTO> laborList = new ArrayList<>();

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public List<LaborHourDTO> getLaborList() {
        return laborList;
    }

    public void setLaborList(List<LaborHourDTO> laborList) {
        this.laborList = laborList;
    }
}
