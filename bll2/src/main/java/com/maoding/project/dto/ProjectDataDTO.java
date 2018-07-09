package com.maoding.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sandy on 2017/8/30.
 */
public class ProjectDataDTO {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 甲方
     */
    private String constructCompany;


    /**
     * 甲方
     */
    private String constructCompanyName;


    /**
     * 乙方
     */
    private String companyBid;

    /**
     * 乙方
     */
    private String companyBidName;

    /**
     * 组织Id
     */
    private String companyId;

    /**
     * 立项组织
     */
    private String companyName;

    /**
     * 企业所属省
     */
    private String province;

    /**
     * 企业所属市
     */
    private String city;

    /**
     *县，区
     */
    private String county;

    /**
     * 详细地址
     */
    private String detailAddress;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getConstructCompany() {
        return constructCompany;
    }

    public void setConstructCompany(String constructCompany) {
        this.constructCompany = constructCompany;
    }

    public String getConstructCompanyName() {
        return constructCompanyName;
    }

    public void setConstructCompanyName(String constructCompanyName) {
        this.constructCompanyName = constructCompanyName;
    }

    public String getCompanyBid() {
        return companyBid;
    }

    public void setCompanyBid(String companyBid) {
        this.companyBid = companyBid;
    }

    public String getCompanyBidName() {
        return companyBidName;
    }

    public void setCompanyBidName(String companyBidName) {
        this.companyBidName = companyBidName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }


}
