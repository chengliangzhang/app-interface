package com.maoding.labor.dto;

import com.maoding.core.base.dto.QueryDTO;

public class QueryLaborHourDTO extends QueryDTO {

    private String date;

    private String companyUserId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

}
