package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectRpointEntity
 * 类描述：合同回款节点(maoding_web_project_rpoint)
 * 作    者：MaoSF
 * 日    期：2015年8月14日-下午3:50:14
 */
public class ProjectPointEntity extends BaseEntity {
  
	private static final long serialVersionUID = 1L;

	/**
	 * 项目id
	 */
    private String projectId;

    /**
     * 公司id(当前备案组织id)
     */
    private String companyId;

    /**
     * 节点名称
     */
    private String pointName;

	/**
	 * 应收金额
	 */
    private BigDecimal receivableMoney;

    /**
     * 已付金额
     */
    private BigDecimal payedMoney;

    /**
     * 开票金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 1为首付，2为节点金额
     */
    private String type;
    
    /**
     * 节点状态（1为通过，0为未通过）
     */
    private String pointState;
    
    /**
     * 回款条件日期（如果此字段不为空，则可以回款，开票)
     */
    private String payDate;
    
    private int seq;

    /**
     * 状态：0：生效，1：不生效（删除）
     */
    private String status;
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName == null ? null : pointName.trim();
    }

    public BigDecimal getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(BigDecimal receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public BigDecimal getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(BigDecimal payedMoney) {
        this.payedMoney = payedMoney;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPointState() {
		return pointState;
	}

	public void setPointState(String pointState) {
		this.pointState = pointState;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}