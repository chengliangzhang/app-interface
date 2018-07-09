package com.maoding.statistic.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.type.Alias;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Chengliang.zhang on 2017/5/8.
 */
@Alias("StatisticDetailQueryDTO")
public class StatisticDetailQueryDTO extends BaseDTO {
    /**
     * 要查询的公司ID列表
     */
    private List<String> companyIdList;
    /**
     * 费用类型（收支类型）
     * 1:合同回款，2:技术审查费，3:合作设计费，4:其他费用
     * SystemParameters.FEE_TYPE_CONTRACT -- SystemParameters.FEE_TYPE_OTHER_DEBIT
     */
    private String feeType;

    private List<String> feeTypeList;

    /**
     * 关联组织
     */
    private String combineCompanyId;
    /**
     * 是否合并报表
     */
    private Boolean typeIsUnion;
    /**
     * 起始日期
     */
    private Date startDate;
    /**
     * 终止日期
     */
    private Date endDate;
    /**
     * 显示第几页
     */
    private Integer pageIndex;
    /**
     * 显示每页行数
     */
    private Integer pageSize;
    /**
     * 返回的统计列表从符合条件的记录集中第几条记录开始
     */
    private Integer startLine;
    /**
     * 返回的统计列表最多为多少条
     */
    private Integer linesCount;

    /**
     * 按照项目名称搜索
     */
    private String projectNameMask;
    /**
     * 单位：1-万元，其他-元
     */
    Integer unitType;
    /**
     * 收支类型：0为正数，1为负数
     */
    Integer profitType;
    /**
     * 排序字段，目前只能为profitDate
     */
    String orderField;
    /**
     * 排序方向：1-升序，其他-降序
     */
    Integer direction;

    /**
     * 应收组织ID
     */
    private String receivableId;
    /**
     * 应付组织ID
     */
    private String paymentId;

    private String associatedOrg;
    private String projectName;
    private String companyId;
    //利润报表查询条件
    private String date;

    private String pcode;

    private String year;


    public List<String> getCompanyIdList() {
        return companyIdList;
    }

    public void setCompanyIdList(List<String> companyIdList) {
        this.companyIdList = companyIdList;
    }

    public String getFeeType() {
        if(StringUtil.isNullOrEmpty(feeType) && !CollectionUtils.isEmpty(this.feeTypeList)){
            feeType = StringUtils.join(feeTypeList, ",");
        }
        if(StringUtil.isNullOrEmpty(feeType)){
            feeType = null;
        }
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCombineCompanyId() {
        return combineCompanyId;
    }

    public void setCombineCompanyId(String combineCompanyId) {
        this.combineCompanyId = combineCompanyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = DateUtils.addDays(startDate, -1);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartLine() {
        if (startLine == null) {
            startLine = ((getPageIndex() != null) && (getPageSize() != null)) ? getPageIndex() * getPageSize() : null;
        }
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Integer getLinesCount() {
        if (linesCount == null) {
            linesCount = getPageSize();
        }
        return linesCount;
    }

    public void setLinesCount(Integer linesCount) {
        this.linesCount = linesCount;
    }

    public Boolean getTypeIsUnion() {
        return typeIsUnion;
    }

    public void setTypeIsUnion(Boolean typeIsUnion) {
        this.typeIsUnion = typeIsUnion;
    }

    public String getProjectNameMask() {
        return projectNameMask;
    }

    public void setProjectNameMask(String projectNameMask) {
        this.projectNameMask = projectNameMask;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getProfitType() {
        return profitType;
    }

    public void setProfitType(Integer profitType) {
        this.profitType = profitType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAssociatedOrg() {
        if(StringUtil.isNullOrEmpty(associatedOrg)){
            associatedOrg = null;
        }
        return associatedOrg;
    }

    public void setAssociatedOrg(String associatedOrg) {
        this.associatedOrg = associatedOrg;
    }

    public String getProjectName() {
        if(StringUtil.isNullOrEmpty(projectName)){
            projectName = null;
        }
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<String> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }
}
