package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectServerContentDTO
 * 类描述：服务内容DTO
 * 作    者：MaoSF
 * 日    期：2015年8月10日-上午11:40:31
 */
public class ProjectServerContentDTO extends BaseDTO {
	/*******服务内容信息*******/
	/**
	 * 项目id
	 */
	private String projectId;

	/**
	 * 设计阶段id(取自设计阶段表主键id)
	 */
	private String designContentId;

	private String designContentName;

	/**
	 * 服务内容名称
	 */
	private String serverName;

	/**
	 * 形象进度开始时间
	 */
	private String actualProgressStartTime;

	/**
	 * 形象进度结束时间
	 */
	private String actualProgressEndTime;

	/**
	 * 计划进度开始时间
	 */
	private String planProgressStartTime;

	/**
	 * 计划进度结束时间
	 */
	private String planProgressEndTime;

	/**
	 * 完成时间
	 */
	private String actualProgressDate;

	/**
	 * 一共多少天
     */
	private int allDay;

	/**
	 * 超时多少天
     */
	private int overDate;

	/**
	 * 形象进度状态(0.未开始，1.进行中，2.已完成)
	 */
	private String actualProgressStatus;

	/**
	 * 服务内容排序
	 */
	private int contentSeq;
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

	private String taskId;

	private int taskLevel;

	/**
	 * 发包人
     */
	private String fromCompany;

	/**
	 * 发包人name
	 */
	private String fromCompanyName;

	/**
	 * 发包人name，简称
	 */
	private String fromCompanyShortName;


	/**
	 * 设计人
	 */
	private String toCompany;

	/**
	 * 设计人name
	 */
	private String toCompanyName;

	/**
	 * 设计人name简称
	 */
	private String toCompanyShortName;

	/**
	 * 管理费
	 */
	private BigDecimal manageFee;

	/**
	 * 管理费到款
	 */
	private BigDecimal managePaidFee;

	/**
	 * 管理费付款
	 */
	private BigDecimal managePayFee;
	/**
	 * 已审查的管理费（用于接收后台数据，便于计算isMap）
	 */
	private BigDecimal auditManageFee;

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
	private String serverContentId;

	/**
	 * 设计费
	 */
	private BigDecimal contractDesignFee;

	/**
	 * 设计费到款
	 */
	private BigDecimal contractDesignPayFee;

	/**
	 * 设计费付款
	 */
	private BigDecimal contractDesignPaidFee;

	/**
	 * 已审核的费用（用于接收后台数据，便于计算auditStatus）
     */
	private BigDecimal auditContractDesignFee;

	/**
	 * toCompany的类型（0，公司，1，部门）
     */
	private String orgType;

	/**
	 * 服务内容(合作设计费审核日期）
     */
	private String auditDate;
	/**
	 * 合作设计费审核状态(0:未完成，1：完成一部分，2：全部完成)
	 */
	private String auditStatus;

	/**
	 * 是否更新任务标题
     */
	private String isUptTaskMana;

	/**
	 * 父级服务内容（二次签发时，传递的参数）
     */
	private ProjectServerContentDTO parentServerContent;

	/**
	 * 合作设计费总条数
     */
	private int contractDesignFeeCount;

	/**
	 * 已审核的合作设计费总条数
	 */
	private int auditContractDesignFeeCount;


	/**
	 * 技术审查费总条数
     */
	private int manageFeeCount;

	/**
	 *已审核的 技术审查费总条数
	 */
	private int auditManageFeeCount;

    /**
     * 0,未开始,1：正常完成，2：超期完成，3：正常进行，4：超期进行，5：无状态,
     */
    private int actualStatus;


	/**
	 * 对应的设计阶段的状态表示（修改，删除服务内容时，返回对应的设计阶段的状态）
     */
	private int designContentActualStatus;

	/**
	 * 对应服务内容中最小的开始时间
	 */
	private String minProgressStarttime;

	/**
	 * 对应服务内容中最大的结束时间
	 */
	private String maxProgressEndtime;

	/**
	 * 子级服务内容（查询服务内容，把子级内容）
	 */
	private List<ProjectServerContentDTO> subServerContentList = new ArrayList<ProjectServerContentDTO>();

	/**
	 * 设计费标识（1：代表不可见，***显示）
     */
	private String designFeeFlag;

	/**
	 * 技术审查费标识（1：代表不可见，***显示）
	 */
	private String manageFeeFlag;

	private int deleteFlag;//1:为不可删除

	private int isTwoIssue;//是否进行二次签发：0：无，>0：有二次签发

	private String serverFullName;//服务内容展示全称（设计阶段名/服务内容名)

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDesignContentId() {
		return designContentId;
	}

	public void setDesignContentId(String designContentId) {
		this.designContentId = designContentId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getContentSeq() {
		return contentSeq;
	}

	public void setContentSeq(int contentSeq) {
		this.contentSeq = contentSeq;
	}

	public String getPaidConfirm() {
		return paidConfirm;
	}

	public void setPaidConfirm(String paidConfirm) {
		this.paidConfirm = paidConfirm;
	}

	public String getParentScontentId() {
		return parentScontentId;
	}

	public void setParentScontentId(String parentScontentId) {
		this.parentScontentId = parentScontentId;
	}

	public String getScontentPath() {
		return scontentPath;
	}

	public void setScontentPath(String scontentPath) {
		this.scontentPath = scontentPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromCompany() {
		return fromCompany;
	}

	public void setFromCompany(String fromCompany) {
		this.fromCompany = fromCompany;
	}

	public String getToCompany() {
		return toCompany;
	}

	public void setToCompany(String toCompany) {
		this.toCompany = toCompany;
	}

	public String getToCompanyName() {

		if(this.toCompanyName!=null && this.toCompanyName.indexOf(",")>-1){
			return this.toCompanyName.split(",")[0];
		}
		return toCompanyName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}

	public BigDecimal getManageFee() {
		return manageFee;
	}

	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	public BigDecimal getManagePaidFee() {
		return managePaidFee;
	}

	public void setManagePaidFee(BigDecimal managePaidFee) {
		this.managePaidFee = managePaidFee;
	}

	public String getIsMap() {

		if(this.manageFeeCount==0 || this.auditManageFeeCount==0){
			isMap = "0";
		}else {
			if(this.manageFeeCount>this.auditManageFeeCount){
				isMap="1";
			}else {
				isMap="2";
			}
		}
		return isMap;
	}

	public void setIsMap(String isMap) {
		this.isMap = isMap;
	}

	public String getMapDate() {
		return mapDate;
	}

	public void setMapDate(String mapDate) {
		this.mapDate = mapDate;
	}

	public String getServerContentId() {
		return serverContentId;
	}

	public void setServerContentId(String serverContentId) {
		this.serverContentId = serverContentId;
	}

	public BigDecimal getContractDesignFee() {
		return contractDesignFee;
	}

	public void setContractDesignFee(BigDecimal contractDesignFee) {
		this.contractDesignFee = contractDesignFee;
	}

	public BigDecimal getContractDesignPaidFee() {
		return contractDesignPaidFee;
	}

	public void setContractDesignPaidFee(BigDecimal contractDesignPaidFee) {
		this.contractDesignPaidFee = contractDesignPaidFee;
	}

	public String getActualProgressStartTime() {
		return actualProgressStartTime;
	}

	public void setActualProgressStartTime(String actualProgressStartTime) {
		this.actualProgressStartTime = actualProgressStartTime;
	}

	public String getActualProgressEndTime() {
		return actualProgressEndTime;
	}

	public void setActualProgressEndTime(String actualProgressEndTime) {
		this.actualProgressEndTime = actualProgressEndTime;
	}

	public String getPlanProgressStartTime() {
		return planProgressStartTime;
	}

	public void setPlanProgressStartTime(String planProgressStartTime) {
		this.planProgressStartTime = planProgressStartTime;
	}

	public String getPlanProgressEndTime() {
		return planProgressEndTime;
	}

	public void setPlanProgressEndTime(String planProgressEndTime) {
		this.planProgressEndTime = planProgressEndTime;
	}

	public String getActualProgressStatus() {
		return actualProgressStatus;
	}

	public void setActualProgressStatus(String actualProgressStatus) {
		this.actualProgressStatus = actualProgressStatus;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public String getFromCompanyShortName() {
		return fromCompanyShortName;
	}

	public void setFromCompanyShortName(String fromCompanyShortName) {
		this.fromCompanyShortName = fromCompanyShortName;
	}

	public String getToCompanyShortName() {
		if(this.toCompanyName!=null && this.toCompanyName.indexOf(",")>-1){
			return this.toCompanyName.split(",")[1];
		}
		return toCompanyShortName;
	}

	public void setToCompanyShortName(String toCompanyShortName) {
		this.toCompanyShortName = toCompanyShortName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public BigDecimal getManagePayFee() {
		return managePayFee;
	}

	public void setManagePayFee(BigDecimal managePayFee) {
		this.managePayFee = managePayFee;
	}

	public BigDecimal getContractDesignPayFee() {
		return contractDesignPayFee;
	}

	public void setContractDesignPayFee(BigDecimal contractDesignPayFee) {
		this.contractDesignPayFee = contractDesignPayFee;
	}

	public int getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(int taskLevel) {
		this.taskLevel = taskLevel;
	}

	public ProjectServerContentDTO getParentServerContent() {
		return parentServerContent;
	}

	public void setParentServerContent(ProjectServerContentDTO parentServerContent) {
		this.parentServerContent = parentServerContent;
	}

	public List<ProjectServerContentDTO> getSubServerContentList() {
		return subServerContentList;
	}

	public void setSubServerContentList(List<ProjectServerContentDTO> subServerContentList) {
		this.subServerContentList = subServerContentList;
	}

	public String getActualProgressDate() {
		return actualProgressDate;
	}

	public void setActualProgressDate(String actualProgressDate) {
		this.actualProgressDate = actualProgressDate;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditStatus() {

		if(this.contractDesignFeeCount==0 || this.auditContractDesignFeeCount==0){
			auditStatus = "0";
		}else {
			if(this.contractDesignFeeCount>this.auditContractDesignFeeCount){
				auditStatus="1";
			}else {
				auditStatus="2";
			}
		}
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getDesignContentName() {
		return designContentName;
	}

	public void setDesignContentName(String designContentName) {
		this.designContentName = designContentName;
	}

	public String getIsUptTaskMana() {
		return isUptTaskMana;
	}

	public void setIsUptTaskMana(String isUptTaskMana) {
		this.isUptTaskMana = isUptTaskMana;
	}

	public BigDecimal getAuditManageFee() {
		return auditManageFee;
	}

	public void setAuditManageFee(BigDecimal auditManageFee) {
		this.auditManageFee = auditManageFee;
	}

	public BigDecimal getAuditContractDesignFee() {
		return auditContractDesignFee;
	}

	public void setAuditContractDesignFee(BigDecimal auditContractDesignFee) {
		this.auditContractDesignFee = auditContractDesignFee;
	}

    public int getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(int actualStatus) {
        this.actualStatus = actualStatus;
    }

	/**
	 * 服务内容初设计划时间的总天数
	 * @return
     */
    public int getAllDay() {
		String startTime=this.planProgressStartTime;
		String endTime=this.planProgressEndTime;
		if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
			allDay=0;
		}else {
			Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
			Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
			allDay=DateUtils.daysOfTwo(endDate,startDate)+1;
		}
		return allDay;
	}

//	/**
//	 * 任务阶段（服务内容的总天数）
//	 * @return
//     */
//	public int getAllDay2() {
//		String startTime = "";
//		String endTime = "";
//		if(!StringUtil.isNullOrEmpty(this.getActualProgressStartTime()) || !StringUtil.isNullOrEmpty(this.getActualProgressEndTime())){
//			startTime=this.actualProgressStartTime;
//			endTime=this.actualProgressEndTime;
//		}else {
//			startTime=this.planProgressStartTime;
//			endTime=this.planProgressEndTime;
//		}
//		if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
//			allDay=0;
//		}else {
//			Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
//			Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
//			allDay=DateUtils.daysOfTwo(endDate,startDate);
//		}
//		return allDay;
//	}

	public void setAllDay(int allDay) {
		this.allDay = allDay;
	}

	public void setOverDate(int overDate) {
		this.overDate = overDate;
	}

	public int getOverDate() {
		String startTime = "";
		String endTime = "";
		if(!StringUtil.isNullOrEmpty(this.getActualProgressStartTime()) || !StringUtil.isNullOrEmpty(this.getActualProgressEndTime())){
			startTime=this.actualProgressStartTime;
			endTime=this.actualProgressEndTime;
		}else {
			startTime=this.planProgressStartTime;
			endTime=this.planProgressEndTime;
		}
		if(!StringUtil.isNullOrEmpty(endTime)){
			Date end = DateUtils.str2Date(endTime,DateUtils.date_sdf);
			Date today = DateUtils.getDate();
			this.overDate = DateUtils.daysOfTwo(end,today);
		}
		return overDate;
	}

	public int getContractDesignFeeCount() {
		return contractDesignFeeCount;
	}

	public void setContractDesignFeeCount(int contractDesignFeeCount) {
		this.contractDesignFeeCount = contractDesignFeeCount;
	}

	public int getAuditContractDesignFeeCount() {
		return auditContractDesignFeeCount;
	}

	public void setAuditContractDesignFeeCount(int auditContractDesignFeeCount) {
		this.auditContractDesignFeeCount = auditContractDesignFeeCount;
	}

	public int getManageFeeCount() {
		return manageFeeCount;
	}

	public void setManageFeeCount(int manageFeeCount) {
		this.manageFeeCount = manageFeeCount;
	}

	public int getAuditManageFeeCount() {
		return auditManageFeeCount;
	}

	public void setAuditManageFeeCount(int auditManageFeeCount) {
		this.auditManageFeeCount = auditManageFeeCount;
	}

	public int getDesignContentActualStatus() {
		return designContentActualStatus;
	}

	public void setDesignContentActualStatus(int designContentActualStatus) {
		this.designContentActualStatus = designContentActualStatus;
	}

	public String getMinProgressStarttime() {
		return minProgressStarttime;
	}

	public void setMinProgressStarttime(String minProgressStarttime) {
		this.minProgressStarttime = minProgressStarttime;
	}

	public String getMaxProgressEndtime() {
		return maxProgressEndtime;
	}

	public void setMaxProgressEndtime(String maxProgressEndtime) {
		this.maxProgressEndtime = maxProgressEndtime;
	}

	public String getDesignFeeFlag() {
		return designFeeFlag;
	}

	public void setDesignFeeFlag(String designFeeFlag) {
		this.designFeeFlag = designFeeFlag;
	}

	public String getManageFeeFlag() {
		return manageFeeFlag;
	}

	public void setManageFeeFlag(String manageFeeFlag) {
		this.manageFeeFlag = manageFeeFlag;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
     * 1：正常完成，2：超期完成，3：正常进行，4：超期进行，5：无状态,0:无状态
     */
	public void initCompleteRatio_bak() {
		String startTime = "";
		String endTime = "";
		if(!StringUtil.isNullOrEmpty(this.getActualProgressStartTime()) || !StringUtil.isNullOrEmpty(this.getActualProgressEndTime())){
			startTime=this.actualProgressStartTime;
			endTime=this.actualProgressEndTime;
		}else {
			startTime=this.planProgressStartTime;
			endTime=this.planProgressEndTime;
		}

		if ("1".equals(this.getActualProgressStatus()) && !StringUtil.isNullOrEmpty(endTime)) {
			Date progressDate1 = DateUtils.str2Date(this.getActualProgressDate(),DateUtils.date_sdf);
			Date planEndTime = DateUtils.str2Date(endTime,DateUtils.date_sdf);
			this.overDate = DateUtils.daysOfTwo(planEndTime,progressDate1);
			if(this.overDate<0){
				actualStatus=2;
			}else {
				actualStatus=1;
			}
			return;
		}

		if ("1".equals(this.getActualProgressStatus()) && StringUtil.isNullOrEmpty(endTime)) {
			this.overDate = 0;
            actualStatus=1;
			return;
		}

		if (!"1".equals(this.getActualProgressStatus())){
			//如果开始时间==null，结束时间也为null
			if(StringUtil.isNullOrEmpty(endTime) && StringUtil.isNullOrEmpty(startTime)){
				this.actualProgressStatus="none";
				actualStatus=0;
				return;
			}
			//如果开始时间不为空
			if(!StringUtil.isNullOrEmpty(startTime)){
				Date today=new Date();
				today =DateUtils.str2Date(DateUtils.date2Str(today,DateUtils.date_sdf),DateUtils.date_sdf) ;
				Date planEndTime = DateUtils.str2Date(startTime,DateUtils.date_sdf);
				this.overDate = DateUtils.daysOfTwo(planEndTime,today);
				if(this.overDate>0){
					this.actualProgressStatus="none";
					actualStatus=5;//未开始
				}else {
					if(StringUtil.isNullOrEmpty(endTime) || DateUtils.datecompareDate(DateUtils.date2Str(today,DateUtils.date_sdf),endTime)<=0){
						actualStatus=3;//正常进行
					}else {
						actualStatus=4;//超时进行
					}
				}
			}else {
				if(!StringUtil.isNullOrEmpty(endTime)){
					Date today=new Date();
					today =DateUtils.str2Date(DateUtils.date2Str(today,DateUtils.date_sdf),DateUtils.date_sdf) ;
					if(DateUtils.datecompareDate(DateUtils.date2Str(today,DateUtils.date_sdf),endTime)<=0){
						actualStatus=3;//正常进行
					}else {
						actualStatus=4;//超时进行
					}
				}
			}

		}
	}

	public void initCompleteRatio(String projectStatus){
		String startTime = "";
		String endTime = "";
		if(!StringUtil.isNullOrEmpty(this.getActualProgressStartTime()) || !StringUtil.isNullOrEmpty(this.getActualProgressEndTime())){
			startTime=this.actualProgressStartTime;
			endTime=this.actualProgressEndTime;
		}else {
			startTime=this.planProgressStartTime;
			endTime=this.planProgressEndTime;
		}

		String endDate = this.getActualProgressDate();
		int taskStatus = 10;
		//taskStatus计算
		if ("1".equals(this.actualProgressStatus)){//如果已经完成
			if(StringUtil.isNullOrEmpty(endTime)){
				taskStatus = 9;//完成
			}else {//如果结束时间不为空
				if (DateUtils.datecompareDate(endDate,endTime)<=0){//正常完成
					taskStatus  = 7;//正常完成
				}else {//超时完成
					taskStatus  = 8;//超时完成
				}
			}
		}else {//如果没完成

			//1.开始为空&&结束时间为空
			if (StringUtil.isNullOrEmpty(startTime) && StringUtil.isNullOrEmpty(endTime)) {
				taskStatus = 10;//空状态
			}
			//2.开始为空&&结束时间不为空
			if (StringUtil.isNullOrEmpty(startTime) && !StringUtil.isNullOrEmpty(endTime)) {
				Date today = new Date();
				if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), endTime) <= 0) {//进行中
					taskStatus = 5;//进行中
				} else {
					taskStatus = 6;//超时进行
				}
			}
			//3.开始不为空&&结束时间为空
			if (!StringUtil.isNullOrEmpty(startTime) && StringUtil.isNullOrEmpty(endTime)) {
				Date today = new Date();
				if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), startTime) >= 0) {//进行中
					taskStatus = 5;//进行中
				} else {
					taskStatus = 4;//未开始
				}
			}
			//4.开始不为空&&结束时间为不空
			if (!StringUtil.isNullOrEmpty(startTime) && !StringUtil.isNullOrEmpty(endTime)) {
				Date today = new Date();
				if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), startTime) >= 0) {//进行中
					if (DateUtils.datecompareDate(DateUtils.date2Str(today, DateUtils.date_sdf), endTime) <= 0) {//正常进行
						taskStatus = 3;//正常进行
					} else {//超时进行
						taskStatus = 6;//超时进行
					}
				} else {
					taskStatus = 4;//未开始
				}
			}
		}

		if(!"0".equals(projectStatus)){
			int status_ = 0;
			int status1 = 0;
			if(!StringUtil.isNullOrEmpty(projectStatus)){
				status1 = Integer.parseInt(projectStatus);
			}

			if(status1==1){
				status_ = 1;
			}
			if(status1==2){
				status_ = 9;
			}
			if(status1==3){
				status_ = 2;
			}

			if(!(taskStatus==7 || taskStatus==8 || taskStatus==9)){
				taskStatus = status_;
			}
		}
		actualStatus = taskStatus;
	}

	public int getIsTwoIssue() {
		return isTwoIssue;
	}

	public void setIsTwoIssue(int isTwoIssue) {
		this.isTwoIssue = isTwoIssue;
	}

	public String getServerFullName() {
		return serverFullName;
	}

	public void setServerFullName(String serverFullName) {
		this.serverFullName = serverFullName;
	}
}