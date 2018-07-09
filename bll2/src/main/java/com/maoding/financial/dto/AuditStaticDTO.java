package com.maoding.financial.dto;

import com.maoding.core.util.StringUtil;

import java.math.BigDecimal;

public class AuditStaticDTO {

    private String title; //标题

    private String total; //总条目数（请假，出差），总金额（报销，费用）

    private String auditData; //已审核通过的目数（请假，出差），总金额（报销，费用）

    private String allocateData; //已拨款的目数，总金额（报销，费用）

    public AuditStaticDTO(){}

    public AuditStaticDTO(String title){
        this.title = title;
        this.total="0";
        this.auditData="0";
        this.allocateData="0";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAuditData() {
        return auditData;
    }

    public void setAuditData(String auditData) {
        this.auditData = auditData;
    }

    public void setData(String total,String auditData){
        if(!StringUtil.isNullOrEmpty(total)){
            this.total = total;
        }
        if(!StringUtil.isNullOrEmpty(auditData)){
            this.auditData = auditData;
        }
    }

    public void setData(String total,String auditData,String allocateData){
        setData(total,auditData);
        if(!StringUtil.isNullOrEmpty(allocateData)){
            this.allocateData = allocateData;
        }
    }

    public String getAllocateData() {
        return allocateData;
    }

    public void setAllocateData(String allocateData) {
        this.allocateData = allocateData;
    }
}
