package com.maoding.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSaveDTO
 * 类描述：项目立项保存DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:35:50
 */
public class ProjectDTO extends BaseDTO {
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
     * 乙方经营负责人id
     */
    private String partBManagerId;

    /**
     * 乙方经营负责人名
     */
    private String partBManagerName;

    /**
     * 乙方技术负责人id
     */
    private String partBDesignerId;

    /**
     *乙方技术负责人名
     */
    private String partBDesignerName;

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
     * 立项日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date projectCreateDate;

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
     * 合同总金额
     */
    private BigDecimal totalContractAmount;

    /**
     * 合同电子文件
     */
    private String filePath;


    /**
     *项目经营人
     */
    private String projectManagerId;


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
     *县，区
     */
    private String county;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 容积率
     */
    private String volumeRatio;


    //标示（乙方是否能改变，1：不能改变）
    private String companyBidFlag;



    /**
     *项目状态
     */
    private String status;

    /**
     * 设计阶段列表
     */
    private List<ProjectDesignContentDTO> projectDesignContentList = new ArrayList<ProjectDesignContentDTO>();


    /**
     * 设计范围列表
     */
    private List<ProjectDesignRangeDTO> projectDesignRangeList = new ArrayList<ProjectDesignRangeDTO>();

    /**
     * 设计依据
     */
    private List<ProjectDesignBasisDTO> projectDesignBasisList = new ArrayList<ProjectDesignBasisDTO>();

    /**
     * 合同签订日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date contractDate;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getCompanyBid() {
        return companyBid;
    }

    public void setCompanyBid(String companyBid) {
        this.companyBid = companyBid;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public List<ProjectDesignContentDTO> getProjectDesignContentList() {
        return projectDesignContentList;
    }

    public void setProjectDesignContentList(List<ProjectDesignContentDTO> projectDesignContentList) {
        this.projectDesignContentList = projectDesignContentList;
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
        if(StringUtil.isNullOrEmpty(signDate)){
            return null;
        }
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
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

    public List<ProjectDesignRangeDTO> getProjectDesignRangeList() {
        return projectDesignRangeList;
    }

    public void setProjectDesignRangeList(List<ProjectDesignRangeDTO> projectDesignRangeList) {
        this.projectDesignRangeList = projectDesignRangeList;
    }

    public String getConstructCompanyName() {
        return constructCompanyName;
    }

    public void setConstructCompanyName(String constructCompanyName) {
        this.constructCompanyName = constructCompanyName;
    }

    public String getCompanyBidName() {
        return companyBidName;
    }

    public void setCompanyBidName(String companyBidName) {
        this.companyBidName = companyBidName;
    }

    public String getCompanyBidFlag() {
        return companyBidFlag;
    }

    public void setCompanyBidFlag(String companyBidFlag) {
        this.companyBidFlag = companyBidFlag;
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

    public String getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(String volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getBuiltTypeName() {
        return builtTypeName;
    }

    public void setBuiltTypeName(String builtTypeName) {
        this.builtTypeName = builtTypeName;
    }

    public List<ProjectDesignBasisDTO> getProjectDesignBasisList() {
        return projectDesignBasisList;
    }

    public void setProjectDesignBasisList(List<ProjectDesignBasisDTO> projectDesignBasisList) {
        this.projectDesignBasisList = projectDesignBasisList;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPartBManagerId() {
        return partBManagerId;
    }

    public void setPartBManagerId(String partBManagerId) {
        this.partBManagerId = partBManagerId;
    }

    public String getPartBDesignerId() {
        return partBDesignerId;
    }

    public void setPartBDesignerId(String partBDesignerId) {
        this.partBDesignerId = partBDesignerId;
    }

    public String getPartBManagerName() {
        return partBManagerName;
    }

    public void setPartBManagerName(String partBManagerName) {
        this.partBManagerName = partBManagerName;
    }

    public String getPartBDesignerName() {
        return partBDesignerName;
    }

    public void setPartBDesignerName(String partBDesignerName) {
        this.partBDesignerName = partBDesignerName;
    }

    public Date getContractDate() {
        if(!StringUtil.isNullOrEmpty(this.signDate)){
            contractDate = DateUtils.str2Date(this.signDate,DateUtils.date_sdf);
        }
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getProjectCreateDate() {
        return projectCreateDate;
    }

    public void setProjectCreateDate(Date projectCreateDate) {
        this.projectCreateDate = projectCreateDate;
    }
}
