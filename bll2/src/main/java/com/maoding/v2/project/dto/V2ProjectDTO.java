package com.maoding.v2.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.project.dto.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2ProjectDTO
 * 作    者：ChenZJ
 * 日    期：2016年12/27
 */
public class V2ProjectDTO extends BaseDTO {
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
     * 项目类型
     */
    private String projectType;

    /**
     * 项目类型名称
     */
    private String projectTypeName;

    /**
     * 项目编号
     */
    private String projectNo;



    /**
     * 基地面积
     */
    private String baseArea;

    /**
     * 覆盖率
     */
    private String coverage;

    /**
     * 总建筑面积
     */
    private String totalConstructionArea;

    /**
     * 绿化率
     */
    private String greeningRate;

    /**
     * 计容面积
     */
    private String capacityArea;

    /**
     * 核增面积
     */
    private String increasingArea;

    /**
     * 建筑高度
     */
    private String builtHeight;

    /**
     * 建筑层数(地上)
     */
    private String builtFloorUp;

    /**
     * 建筑层数(地下)
     */
    private String builtFloorDown;

    /**
     * 投资估算
     */
    private BigDecimal investmentEstimation;

    /**
     * 合同签订日期
     */
    private String signDate;

    /**
     * 合同备案日期
     */
    private String auditPreTheftDate;

    /**
     * 合同总金额
     */
    private BigDecimal totalContractAmount;

    /**
     * 合同电子文件
     */
    private String filePath;

    /**
     * 合同电子文件名称
     */
    private String fileName;

    /**
     * 建筑功能
     */
    private String builtType;

    /**
     * 建筑功能名称
     */
    private String builtTypeName;

    /**
     * 组织Id
     */
    private String companyId;

    /**
     * 企业所属省
     */
    private String province;

    /**
     * 企业所属市
     */
    private String city;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 容积率
     */
    private String volumeRatio;

    /**
     * 公司名称
     */
    private String companyName;

    /***
     * 乙方
     */
    private List<CompanyDTO> partBList = new ArrayList<>();

    /**
     * 甲方
     */
    private List<ProjectConstructDTO> projectConstructList= new ArrayList<>();

    /**
     * 设计阶段列表
     */
    private List<V2ProjectDesignContentDTO> projectDesignContentList = new ArrayList<V2ProjectDesignContentDTO>();

    /**
     * 设计范围列表
     */
    private List<ProjectDesignRangeDTO> projectDesignRangeList = new ArrayList<ProjectDesignRangeDTO>();

    /**
     * 设计依据
     */
    private List<ProjectDesignBasisDTO> projectDesignBasisList = new ArrayList<ProjectDesignBasisDTO>();


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<CompanyDTO> getPartBList() {
        return partBList;
    }

    public void setPartBList(List<CompanyDTO> partBList) {
        this.partBList = partBList;
    }

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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getBaseArea() {
        return baseArea;
    }

    public void setBaseArea(String baseArea) {
        this.baseArea = baseArea;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getTotalConstructionArea() {
        return totalConstructionArea;
    }

    public void setTotalConstructionArea(String totalConstructionArea) {
        this.totalConstructionArea = totalConstructionArea;
    }

    public String getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        this.greeningRate = greeningRate;
    }

    public String getCapacityArea() {
        return capacityArea;
    }

    public void setCapacityArea(String capacityArea) {
        this.capacityArea = capacityArea;
    }

    public String getIncreasingArea() {
        return increasingArea;
    }

    public void setIncreasingArea(String increasingArea) {
        this.increasingArea = increasingArea;
    }

    public String getBuiltHeight() {
        return builtHeight;
    }

    public void setBuiltHeight(String builtHeight) {
        this.builtHeight = builtHeight;
    }

    public String getBuiltFloorUp() {
        return builtFloorUp;
    }

    public void setBuiltFloorUp(String builtFloorUp) {
        this.builtFloorUp = builtFloorUp;
    }

    public String getBuiltFloorDown() {
        return builtFloorDown;
    }

    public void setBuiltFloorDown(String builtFloorDown) {
        this.builtFloorDown = builtFloorDown;
    }

    public BigDecimal getInvestmentEstimation() {
        return investmentEstimation;
    }

    public void setInvestmentEstimation(BigDecimal investmentEstimation) {
        this.investmentEstimation = investmentEstimation;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getAuditPreTheftDate() {
        return auditPreTheftDate;
    }

    public void setAuditPreTheftDate(String auditPreTheftDate) {
        this.auditPreTheftDate = auditPreTheftDate;
    }

    public BigDecimal getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(BigDecimal totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBuiltType() {
        return builtType;
    }

    public void setBuiltType(String builtType) {
        this.builtType = builtType;
    }

    public String getBuiltTypeName() {
        return builtTypeName;
    }

    public void setBuiltTypeName(String builtTypeName) {
        this.builtTypeName = builtTypeName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(String volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public List<V2ProjectDesignContentDTO> getProjectDesignContentList() {
        return projectDesignContentList;
    }

    public void setProjectDesignContentList(List<V2ProjectDesignContentDTO> projectDesignContentList) {
        this.projectDesignContentList = projectDesignContentList;
    }

    public List<ProjectDesignRangeDTO> getProjectDesignRangeList() {
        return projectDesignRangeList;
    }

    public void setProjectDesignRangeList(List<ProjectDesignRangeDTO> projectDesignRangeList) {
        this.projectDesignRangeList = projectDesignRangeList;
    }

    public List<ProjectDesignBasisDTO> getProjectDesignBasisList() {
        return projectDesignBasisList;
    }

    public void setProjectDesignBasisList(List<ProjectDesignBasisDTO> projectDesignBasisList) {
        this.projectDesignBasisList = projectDesignBasisList;
    }



    public List<ProjectConstructDTO> getProjectConstructList() {
        return projectConstructList;
    }

    public void setProjectConstructList(List<ProjectConstructDTO> projectConstructList) {
        this.projectConstructList = projectConstructList;
    }
}
