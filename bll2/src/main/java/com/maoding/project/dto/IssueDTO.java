package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.project.entity.ProjectFeeEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectServerContentDTO
 * 类描述：任务签发DTO
 * 作    者：MaoSF
 * 日    期：2015年8月10日-上午11:40:31
 */
public class IssueDTO extends BaseDTO {

    /*******服务内容信息*******/
	/**
	 * 项目id
	 */
    private String projectId;

    /**
     * 设计阶段id(取自设计阶段表主键id)
     */
    private String designContentId;

    /**
     * 任务书id
     */
    private String taskId;

    /**
     * 服务内容名称
     */
    private String serverName;


    /**
     * 服务内容排序
     */
    private String contentSeq;
    /**
     * 到付确认(1=确认)
     */
    private String paidConfirm;
    
    /**
     * 父服务内容Id
     */
    private String parentScontentId;
    
    /**
     * 服务内容节点记录
     */
    private String scontentPath;
    
    /**
     * 状态（0：生效,1:不生效（删除）
     */
    private String status;

    /************签发的信息*********/

    private String fromCompany;

    /**
     * 设计人
     */
    private String toCompany;

    /**
     * 设计人name
     */
    private String toCompanyName;

    /**
     * 管理费
     */
    private BigDecimal manageFee;

    /**
     * 管理费到款
     */
    private BigDecimal managePaidFee;

    /**
     * 是否审查
     */
    private String isMap;

    /**
     * 审查日期
     */
    private String mapDate;

    /**
     * 服务内容id
     */
    private String serviceContentId;

    /**
     * 设计费
     */
    private BigDecimal contractDesignFee;

    /**
     * 设计费到款
     */
    private BigDecimal contractDesignPaidFee;

}