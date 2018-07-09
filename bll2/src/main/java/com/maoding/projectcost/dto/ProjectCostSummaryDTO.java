package com.maoding.projectcost.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostPointDTO
 * 类描述：费用节点表（记录费用在哪个节点上产生的，费用的描述，金额）
 * 项目费用收款节点表
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectCostSummaryDTO{

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 总金额的id
     */
    private String id;

    /**
     * 回款金额
     */
    private BigDecimal fee;

    private String fromCompanyId;

    private String toCompanyId;

    /**
     * 类型1:合同总金额，2：技术审查费,3合作设计费付款,4.其他费用
     */
    private Integer type;

    private List<ProjectCostPointDTO> pointList = new ArrayList<>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }

    public String getToCompanyId() {
        return toCompanyId;
    }

    public void setToCompanyId(String toCompanyId) {
        this.toCompanyId = toCompanyId;
    }

    public List<ProjectCostPointDTO> getPointList() {
        return pointList;
    }

    public void setPointList(List<ProjectCostPointDTO> pointList) {
        this.pointList = pointList;
    }
}
