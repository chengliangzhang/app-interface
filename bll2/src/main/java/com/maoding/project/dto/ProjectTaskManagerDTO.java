package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.project.entity.ProjectAttachEntity;
import com.maoding.project.entity.ProjectTaskResponsiblerEntity;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskManagerDTO
 * 类 描 述：任务分解DTO
 * 作    者：MaoSF
 * 日    期：2016年8月3日-下午1:47:23
 */
public class ProjectTaskManagerDTO extends BaseDTO {

    /**
     * 设计阶段Id
     */
    private String contentRealId;
    /**
     * 设计阶段名称
     */
    private String contentText;

    /**
     * 设计阶段名称状态
     */
    private String contentStatus;

    /**
     * 任务名称层级关系，以,隔开
     */
    private String hierarchyPath;

    /**
     * 任务id层级关系，以,隔开
     */
    private String hierarchyPathId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 节点Id
     */
    private String realId;

    /**
     * 节点名称
     */
    private String text;

    /**
     * 当前节点对应的实体
     */
    private  Object treeEntity;

    /**
     * 类型图标
     */
    private String type;
	
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 进度开始时间
     */
    private String taskScheduleStatime;

    /**
     * 进度结束时间
     */
    private String taskScheduleEndtime;

    /**
     * 描述
     */
    private String taskDesc;

    /**
     * 设计人（多个以,隔开）
     */
    private String taskDesigner;

    /**
     * 负责人（多个以,隔开）
     */
    private String taskHead;

    /**
     * 设计人姓名（多个以,隔开）
     */
    private String taskDesignerName;

    /**
     * 负责人姓名（多个以,隔开）
     */
    private String taskHeadName;

    /**
     * 项目ID
     */
    private String taskProjectId;

    /**
     * 附件（多个以,隔开）
     */
    private String taskAttachment;
    
    /**
     * 组织ID
     */
    private String taskCompanyId;
    
    /**
     * 父级任务路径字符串拼接(id-id...)
     */
    private String taskParentPath;
    /**
     * 父级任务ID
     */
    private String taskParent;

    /**
     * 任务分解状态
     */
    private String taskStatus;
    
    /**
     * 关联服务内容的id【关联服务内容的id（任务强关联服务内容），其他子任务没有scontend_id为null】
     */
    private String scontentId;


    /**
     * 状态（0：生效，1：不生效（逻辑删除）
     */
    private String status;
    /**
     * 当前组织ID
     */
    private String orgId;

    /**
     * 创建人id
     */
    private String taskPublisher;

    /**
     * 创建人姓名
     */
    private String createByName;
    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 目标公司Id
     */
    private String toCompany;

    /**
     * 项目所在公司id
     */
    private String companyId;

    /**
     * 子任务
     */
    private List<ProjectTaskManagerDTO> children = new ArrayList<ProjectTaskManagerDTO>();

    /**
     * 树状态
     */
    private Map<String,Object> state = new HashMap<String,Object>();

    /**
     * 任务附件
     */
    private List<ProjectAttachEntity> attachArr = new ArrayList<ProjectAttachEntity>();

    /**
     * fastdfsUrl
     */
    private String fastdfsUrl;

    /**
     * 状态名
     */
    private String statusName;

    /**
     * 是否跟我相关
     */
    private String isAssociatedMe;

    private int relationStatus;

    private String endTime;//当前任务的完成时间


    List<ProjectTaskResponsiblerEntity> ProjectTaskResponsiblerEntityList=new ArrayList<ProjectTaskResponsiblerEntity>();

    public List<ProjectTaskResponsiblerEntity> getProjectTaskResponsiblerEntityList() {
        return ProjectTaskResponsiblerEntityList;
    }

    public void setProjectTaskResponsiblerEntityList(List<ProjectTaskResponsiblerEntity> ProjectTaskResponsiblerEntityList) {
        this.ProjectTaskResponsiblerEntityList = ProjectTaskResponsiblerEntityList;
    }

    public List<ProjectTaskResponsiblerEntity> getProjectMajorPersonEntityList() {
        return projectMajorPersonEntityList;
    }

    public void setProjectMajorPersonEntityList(List<ProjectTaskResponsiblerEntity> projectMajorPersonEntityList) {
        this.projectMajorPersonEntityList = projectMajorPersonEntityList;
    }

    List<ProjectTaskResponsiblerEntity> projectMajorPersonEntityList=new ArrayList<ProjectTaskResponsiblerEntity>();

    /**
     * 最后变更开始时间
     */
    private String progressStartTime;

    /**
     * 最后变更结束时间
     */
    private String progressEndTime;

    /**
     * 计划进度共多少天
     */
    private int allDay;

    private String editStatus;//是否可以编辑流程

    private int isSplit;//1：是否可以被分解
    //经营带过来的时间
    private String planProgressStartTime;

    //经营带过来的时间
    private String planProgressEndTime;

    private int maxLayer;//最大层数

    //是否可以设置负责人(1,可以，0不可以)
    private String isSettingPerson;

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

    public String getIsAssociatedMe() {
        return isAssociatedMe;
    }

    public void setIsAssociatedMe(String isAssociatedMe) {
        this.isAssociatedMe = isAssociatedMe;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }


    public String getTaskScheduleStatime() {
		return taskScheduleStatime;
	}

	public void setTaskScheduleStatime(String taskScheduleStatime) {
		this.taskScheduleStatime = taskScheduleStatime;
	}

	public String getTaskScheduleEndtime() {
		return taskScheduleEndtime;
	}

	public void setTaskScheduleEndtime(String taskScheduleEndtime) {
		this.taskScheduleEndtime = taskScheduleEndtime;
	}

	public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc == null ? null : taskDesc.trim();
    }

    public String getTaskDesigner() {
        return taskDesigner;
    }

    public void setTaskDesigner(String taskDesigner) {
        this.taskDesigner = taskDesigner == null ? null : taskDesigner.trim();
    }

    public String getTaskHead() {
        return taskHead;
    }

    public void setTaskHead(String taskHead) {
        this.taskHead = taskHead == null ? null : taskHead.trim();
    }

    public String getTaskProjectId() {
        return taskProjectId;
    }

    public void setTaskProjectId(String taskProjectId) {
        this.taskProjectId = taskProjectId == null ? null : taskProjectId.trim();
    }

    public String getTaskAttachment() {
        return taskAttachment;
    }

    public void setTaskAttachment(String taskAttachment) {
        this.taskAttachment = taskAttachment == null ? null : taskAttachment.trim();
    }

	public String getTaskCompanyId() {
		return taskCompanyId;
	}

	public void setTaskCompanyId(String taskCompanyId) {
		this.taskCompanyId = taskCompanyId;
	}

	public String getTaskParentPath() {
		return taskParentPath;
	}

	public void setTaskParentPath(String taskParentPath) {
		this.taskParentPath = taskParentPath;
	}

	public String getTaskParent() {
		return taskParent;
	}

	public void setTaskParent(String taskParent) {
		this.taskParent = taskParent;
	}

	public String getTaskStatus() {
        return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getScontentId() {
		return scontentId;
	}

	public void setScontentId(String scontentId) {
		this.scontentId = scontentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

    public String getTaskPublisher() {
        return taskPublisher;
    }

    public void setTaskPublisher(String taskPublisher) {
        this.taskPublisher = taskPublisher;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getToCompany() {
        return toCompany;
    }

    public void setToCompany(String toCompany) {
        this.toCompany = toCompany;
    }

    public List<ProjectTaskManagerDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectTaskManagerDTO> children) {
        this.children = children;
    }

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTreeEntity() {
        return treeEntity;
    }

    public void setTreeEntity(Object treeEntity) {
        this.treeEntity = treeEntity;
    }

    public String getType() {
        String type = "";
        if(!StringUtil.isNullOrEmpty(taskStatus)){
            switch (taskStatus){
                case ("0"):
                    type = "wait";break;
                case ("1"):
                    type = "cancel";break;
                case ("2"):
                    type = "pause";break;
                case ("3"):
                    type = "running";break;
                case ("4"):
                    type = "success";break;
                default:
                    ;
            }
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProjectAttachEntity> getAttachArr() {
        return attachArr;
    }

    public void setAttachArr(List<ProjectAttachEntity> attachArr) {
        this.attachArr = attachArr;
    }

    public String getTaskDesignerName() {
        return taskDesignerName;
    }

    public void setTaskDesignerName(String taskDesignerName) {
        this.taskDesignerName = taskDesignerName;
    }

    public String getTaskHeadName() {
        return taskHeadName;
    }

    public void setTaskHeadName(String taskHeadName) {
        this.taskHeadName = taskHeadName;
    }

    public String getFastdfsUrl() {
        return fastdfsUrl;
    }

    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public String getContentRealId() {
        return contentRealId;
    }

    public void setContentRealId(String contentRealId) {
        this.contentRealId = contentRealId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getStatusName() {
        if(!StringUtil.isNullOrEmpty(taskStatus)){
            switch (taskStatus){
                case ("0"):
                    statusName = "未开始";break;
                case ("1"):
                    statusName = "已取消";break;
                case ("2"):
                    statusName = "暂停中";break;
                case ("3"):
                    statusName = "进行中";break;
                case ("4"):
                    statusName = "已完成";break;
                default:
                    ;
            }
        }
        return statusName;
    }

    public String getHierarchyPath() {
        return hierarchyPath;
    }

    public void setHierarchyPath(String hierarchyPath) {
        this.hierarchyPath = hierarchyPath;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getHierarchyPathId() {
        return hierarchyPathId;
    }

    public void setHierarchyPathId(String hierarchyPathId) {
        this.hierarchyPathId = hierarchyPathId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getProgressStartTime() {
        return progressStartTime;
    }

    public void setProgressStartTime(String progressStartTime) {
        this.progressStartTime = progressStartTime;
    }

    public String getProgressEndTime() {
        return progressEndTime;
    }

    public void setProgressEndTime(String progressEndTime) {
        this.progressEndTime = progressEndTime;
    }

    /**
     * 服务内容初设计划时间的总天数
     * @return
     */
    public int getAllDay() {
        String startTime=this.taskScheduleStatime;
        String endTime=this.taskScheduleEndtime;
        if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
            allDay=0;
        }else {
            Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
            Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
            allDay=DateUtils.daysOfTwo(endDate,startDate);
        }
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus;
    }

    public int getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(int isSplit) {
        this.isSplit = isSplit;
    }

    public int getMaxLayer() {
        return maxLayer;
    }

    public void setMaxLayer(int maxLayer) {
        this.maxLayer = maxLayer;
    }

    public String getIsSettingPerson() {
        return isSettingPerson;
    }

    public void setIsSettingPerson(String isSettingPerson) {
        this.isSettingPerson = isSettingPerson;
    }
}