package com.maoding.project.service.impl;

import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.OrgDynamicDao;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.dynamic.dto.ZProjectDTO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.message.dao.MessageDao;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserTableDTO;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.project.dao.*;
import com.maoding.project.dto.*;
import com.maoding.project.entity.*;
import com.maoding.project.service.ProjectDesignContentService;
import com.maoding.project.service.ProjectService;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.system.entity.DataDictionaryEntity;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.task.dao.ProjectProcessTimeDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dao.ProjectTaskRelationDao;
import com.maoding.task.dto.ProjectIssueDataDTO;
import com.maoding.task.dto.ProjectTaskDTO;
import com.maoding.task.dto.ProjectTaskListDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.entity.ProjectTaskRelationEntity;
import com.maoding.task.service.ProjectManagerService;
import com.maoding.task.service.ProjectTaskService;
import com.maoding.v2.project.dto.*;
import com.maoding.v2.user.dto.V2CompanyUserTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：项目Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectService")
public class ProjectServiceImpl extends GenericService<ProjectEntity>  implements ProjectService {
	//项目dao
	@Autowired
	private ProjectDao projectDao;
	//数据字典dao
	@Autowired
	private DataDictionaryService dataDictionaryService;
	//设计范围dao
	@Autowired
	private ProjectDesignRangeDao projectDesignRangeDao;
	//设计阶段dao
	@Autowired
	private ProjectDesignContentService projectDesignContentService;

	@Autowired
	private ProjectManagerService projectManagerService;

	//设计依据dao
	@Autowired
	private ProjectDesignBasisDao projectDesignBasisDao;

	//项目审核
	@Autowired
	private ProjectAuditDao projectAuditDao;

	@Autowired
	private ProjectTaskService projectTaskService;


	@Autowired
	private ProjectConstructDao projectConstructDao;

	@Autowired
	private CompanyDao companyDao;


	@Autowired
	private ProjectTaskDao projectTaskDao;

    @Autowired
    private MyTaskDao myTaskDao;

	@Autowired
	private OrgDynamicDao orgDynamicDao;

	@Autowired
	private ProjectCostService projectCostService;

	@Autowired
	private CompanyUserDao companyUserDao;

	@Autowired
	private ProjectSkyDriverService projectSkyDriverService;

	@Autowired
	private ProjectSkyDriverDao projectSkyDriverDao;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProjectProcessTimeDao projectProcessTimeDao;

	@Autowired
	private ProjectTaskRelationDao projectTaskRelationDao;

	@Autowired
	private OrgDynamicService orgDynamicService;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private CollaborationService collaborationService;

	@Autowired
	private ProjectMemberService projectMemberService;

	@Autowired
	private DynamicService dynamicService;

	@Autowired
	private ZInfoDAO zInfoDAO;

//	@Autowired
//	private DynamicService dynamicService;

	@Value("${fastdfs.url}")
	protected String fastdfsUrl;


	/**
	 * 方法描述：v2项目列表(param:type:0,全部项目，1，我参与的项目)
	 * 作者：chenzhujie
	 * 日期：2016/12/24
	 */
	@Override
	public List<V2ProjectTableDTO> getV2ProjectList(Map<String, Object> param) throws Exception {
		if (null != param.get("pageIndex")) {
			int page = (Integer) param.get("pageIndex");
			int pageSize = (Integer) param.get("pageSize");
			param.put("startPage", page * pageSize);
			param.put("endPage", pageSize);
		}
		CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) param.get("accountId"),(String) param.get("appOrgId"));
		if(companyUserEntity==null){
			return new ArrayList<>();
		}
		param.put("companyUserId",companyUserEntity.getId());
		List<V2ProjectTableDTO> list = projectDao.getV2ProjectList(param);
		for(V2ProjectTableDTO dto:list){
			//处理立项人
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyId",dto.getCompanyId());
			map.put("userId",dto.getCreateBy());
			map.put("fastdfsUrl",this.fastdfsUrl);
			dto.setUser(this.companyUserDao.getCompanyUserByUserId(map));
		}
		this.detailProjectsPersonAndDesignStatus(list,(String) param.get("appOrgId"),(String) param.get("accountId"),companyUserEntity.getId());

		return list;
	}

	/**
	 * 方法描述：经营列表总数据（用于分页）
	 * 作者：MaoSF
	 * 日期：2016/7/29
	 *
	 * @param param
	 * @param:
	 * @return:
	 */
	@Override
	public int getProjectListByConditionCount(Map<String, Object> param) throws Exception{
		return projectDao.getProjectListByConditionCount(param);
	}


	/**
	 * 方法描述：删除项目（逻辑删除）
	 * 作者：MaoSF
	 * 日期：2016/8/4
	 *
	 * @param id
	 * @param:
	 * @return:
	 */
	@Override
	public AjaxMessage deleteProjectById(String id) throws Exception {
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setId(id);
		projectEntity.setPstatus("1");
		this.updateById(projectEntity);

        MyTaskEntity myTaskEntity = new MyTaskEntity();
        myTaskEntity.setStatus("2");
        myTaskEntity.setProjectId(id);
        myTaskDao.updateStatesByTargetId(myTaskEntity);

		myTaskDao.deleteProjectTask(id);

		orgDynamicDao.updatefield2ByTargetId(id);

		//通知协同
		this.collaborationService.pushSyncCMD_PU(id);;

		//删除消息
		messageDao.deleteMessage(id);

		//删除群组
//		Map<String,String> map = new HashMap<>();
//		map.put("orgId",id);
//		imGroupService.removeGroupDestination(map);
		return new AjaxMessage().setCode("0").setInfo("删除成功");
	}


	/**
	 * 方法描述：根据id查询项目信息
	 * 作者：MaoSF
	 * 日期：2016/7/28
	 *
	 * @param id
	 * @param:
	 * @return:
	 */
	@Override
	public ProjectDTO getProjectById(String id,String companyId,String userId) throws Exception{
		ProjectDTO projectDTO= new ProjectDTO();
		//项目基本信息
		ProjectEntity projectEntity = projectDao.selectById(id);
		BaseDTO.copyFields(projectEntity,projectDTO);

		//项目功能
		if(!StringUtil.isNullOrEmpty(projectEntity.getBuiltType())){
			String[] idList = projectEntity.getBuiltType().split(",");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("idList",idList);
			List<DataDictionaryEntity> dataDictionaryList = this.dataDictionaryService.getDataByParemeter(map);
			if(!CollectionUtils.isEmpty(dataDictionaryList)){
				String bulidType="";
				for (DataDictionaryEntity dataDictionary:dataDictionaryList){
					bulidType+=dataDictionary.getName()+" · ";
				}
				if(!StringUtil.isNullOrEmpty(bulidType)){
					bulidType = bulidType.substring(0,bulidType.lastIndexOf("·"));
				}
				projectDTO.setBuiltTypeName(bulidType);
			}
		}
		//获取甲方名称
		if(!StringUtil.isNullOrEmpty(projectEntity.getConstructCompany())){
			ProjectConstructEntity constructCompany = projectConstructDao.selectById(projectEntity.getConstructCompany());
			if(constructCompany!=null){
				projectDTO.setConstructCompanyName(constructCompany.getCompanyName());
			}
		}

		//乙方公司
		if(!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid())){
			CompanyEntity companyB = companyDao.selectById(projectEntity.getCompanyBid());
			if(companyB!=null){
				projectDTO.setCompanyBidName(companyB.getAliasName());
			}
		}

		//合同签订日期
		//List<ProjectAuditEntity> signEntity = projectAuditDao.getProjectAuditEntityByProjectAndType(id,"2");
		if(!StringUtil.isNullOrEmpty(projectEntity.getContractDate())){
			projectDTO.setSignDate(DateUtils.formatDate(projectEntity.getContractDate()));
		}

		//合同附件
		ProjectSkyDriveEntity projectSkyDrive = this.projectSkyDriverService.getProjectContractAttach(id);
		if(projectSkyDrive!=null){
			projectDTO.setFileName(projectSkyDrive.getFileName());
			projectDTO.setFilePath(projectSkyDrive.getFileGroup()+"/"+projectSkyDrive.getFilePath());
		}

		//项目设计依据
//		List<ProjectDesignBasisDTO> projectDesignBasisList = projectDesignBasisDao.getDesignBasisByProjectId(id);
//		projectDTO.setProjectDesignBasisList(projectDesignBasisList);

		//项目设计范围
		List<ProjectDesignRangeEntity> projectDesignRangeEntityList = projectDesignRangeDao.getDesignRangeByProjectId(id);
		projectDTO.setProjectDesignRangeList(BaseDTO.copyFields(projectDesignRangeEntityList,ProjectDesignRangeDTO.class));

		// 20170616  删除projectDesignContentDao模块，屏蔽此代码，如果APP端开放此接口，再做调整
//		//项目设计阶段
		List<ProjectDesignContentDTO> projectDesignContentList = projectDesignContentService.getProjectDesignContentByProjectId(id,null);
//		if(!(companyId.equals(projectEntity.getCompanyId()) || companyId.equals(projectEntity.getCompanyBid()))){
//			for(ProjectDesignContentDTO designContentDTO:projectDesignContentList){
//				designContentDTO.setStartTime(null);
//				designContentDTO.setEndTime(null);
//			}
//		}
		projectDTO.setProjectDesignContentList(projectDesignContentList);

		return projectDTO;
	}

	/**
	 * 方法描述：根据id查询项目信息
	 * 作者：MaoSF
	 * 日期：2016/7/28
	 *
	 * @param id
	 * @param companyId
	 * @param userId
	 * @param:
	 * @return:
	 */

	public Map<String, Object> getProjectDetail(String id, String companyId, String userId) throws Exception {
		//项目详情
		ProjectDTO projectDTO= this.getProjectById(id,companyId,userId);
		//甲方
		List<ProjectConstructDTO> constructList = projectConstructDao.getConstructByCompanyId(companyId);
		//乙方
		List<CompanyEntity> companyList =new ArrayList<CompanyEntity>();
		CompanyEntity companyEntity=companyService.selectById(companyId);
		String rootId = companyService.getRootCompanyId(companyId);
		companyList.add(companyEntity);
		if(!StringUtil.isNullOrEmpty(rootId) && !companyId.equals(rootId)){
			CompanyEntity parentCompanyEntity = companyService.selectById(rootId);
			companyList.add(parentCompanyEntity);
		}

		if(!StringUtil.isNullOrEmpty(projectDTO.getCompanyBid()) && !projectDTO.getCompanyBid().equals(projectDTO.getCompanyId())){
			//乙方经营负责人
			//获取当前项目在当前团队的经营负责人和项目负责人
			List<ProjectMemberDTO> managerListPartB = this.projectMemberService.listProjectMemberByParam(id,projectDTO.getCompanyBid(),null,null);
			for(ProjectMemberDTO managerDTO:managerListPartB){
				if(managerDTO.getMemberType()==ProjectMemberType.PROJECT_OPERATOR_MANAGER){
					projectDTO.setPartBManagerId(managerDTO.getCompanyUserId());
					projectDTO.setPartBManagerName(managerDTO.getUserName());
				}
				if(managerDTO.getMemberType()==ProjectMemberType.PROJECT_DESIGNER_MANAGER){
					projectDTO.setPartBDesignerId(managerDTO.getCompanyUserId());
					projectDTO.setPartBDesignerName(managerDTO.getUserName());
				}
			}
		}

		Map<String, Object> returnMap = new HashMap<String,Object>();
		returnMap.put("proectDetail",projectDTO);
		returnMap.put("constructList",constructList);
		returnMap.put("companyList",companyList);
		returnMap.put("operatorManager",this.projectMemberService.getOperatorManagerDTO(id,companyId));
		return returnMap;
	}

	/**
	 * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
	 * 作   者：LY
	 * 日   期：2016/8/8 14:38
	 */
	public List<ProjectDTO> getProjectListByCompanyId(Map<String, Object> param){
		return projectDao.getProjectListByCompanyId(param);
	}


	/**************************************新的接口****************************************************/



	/**
	 * 方法描述：获取立项项目的基础数据（甲方和项目经营人）
	 * 作者：MaoSF
	 * 日期：2016/12/07
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public ProjectBaseDataDTO getAddProjectOfBaseData(String companyId) throws Exception {
		ProjectBaseDataDTO projectBaseDataDTO = new ProjectBaseDataDTO();
//		//1：获取当前公司的所有甲方
//		List<ProjectConstructDTO> projectConstructList=projectConstructDao.getConstructByCompanyId(companyId);
		//获取当前团队项目经营人员
		Map<String,Object> map = new HashMap<>();
		map.put("permissionId","25");
		map.put("companyId",companyId);
		List<CompanyUserTableDTO> companyUserList = companyUserDao.getCompanyUserByPermissionId(map);
		List<V2CompanyUserTableDTO> list = BaseDTO.copyFields(companyUserList,V2CompanyUserTableDTO.class);
		//projectBaseDataDTO.setProjectConstructList(projectConstructList);
		projectBaseDataDTO.setCompanyUserList(list);
		return projectBaseDataDTO;
	}


	private ResponseBean validateSaveAddProject(AddProjectDTO dto) throws Exception{

		if(StringUtil.isNullOrEmpty(dto.getProjectName())){
			return ResponseBean.responseError("项目名称不能为空");
		}
		if(StringUtil.isNullOrEmpty(dto.getConstructName())){
			return ResponseBean.responseError("建设单位名称不能为空");
		}
		if(CollectionUtils.isEmpty(dto.getProjectDesignRangeList())){
			return ResponseBean.responseError("设计范围不能为空");
		}
		if(CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
			return ResponseBean.responseError("设计阶段不能为空");
		}
		return ResponseBean.responseSuccess();
	}

	/**
	 * 方法描述：建设单位处理
	 * 作者：MaoSF
	 * 日期：2016/12/7
	 * @param:
	 * @return:
	 */
	private void handeProjectConstruct(AddProjectDTO dto) throws Exception{
		if(StringUtil.isNullOrEmpty(dto.getConstructId())){
			ProjectConstructEntity constructEntity = this.projectConstructDao.getConstructByName(dto.getConstructName(),dto.getAppOrgId());
			if(constructEntity!=null){
				dto.setConstructId(constructEntity.getId());
			}else {
				constructEntity = new ProjectConstructEntity();
				constructEntity.setId(StringUtil.buildUUID());
				constructEntity.setCompanyName(dto.getConstructName());
				constructEntity.setCompanyId(dto.getAppOrgId());
				this.projectConstructDao.insert(constructEntity);
				dto.setConstructId(constructEntity.getId());
			}
		}
	}



	/**
	 * 方法描述：查询项目相关数据
	 * 作者：Chenzhujie
	 * 日期：2016/12/13
	 * @param:dto
	 * @return:
	 */
	@Override
	public Map<String, Object>  getProjectsAbouts(Map<String, Object> param)throws Exception{
		String accountId = (String)param.get("accountId");
		String projectId = (String)param.get("id");
		String companyId = (String)param.get("appOrgId");
		Map<String,Object> mapProject = new HashMap<>();
		if(StringUtil.isNullOrEmpty(companyId)){
			return mapProject;
		}
		//项目信息
		ProjectEntity project = projectDao.selectById(projectId);

		NewProjectAboutsDTO newProjectAboutsDTO = new NewProjectAboutsDTO();
		newProjectAboutsDTO.setId(project.getId());
		newProjectAboutsDTO.setProjectName(project.getProjectName());
        newProjectAboutsDTO.setPercentageCompletion(getProjectBasicNum(projectId));
		newProjectAboutsDTO.setCompanyId(project.getCompanyId());
        //项目立项人
		CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(project.getCreateBy(),project.getCompanyId());
		if(companyUserEntity!=null){
			newProjectAboutsDTO.setAddProjectPerson(companyUserEntity.getUserName());
			if(!companyId.equals(project.getCompanyId())){
				CompanyEntity companyEntity = this.companyDao.selectById(project.getCompanyId());
				if(companyEntity!=null){
					String companyName = companyEntity.getAliasName();
					newProjectAboutsDTO.setAddProjectPerson(companyUserEntity.getUserName()+" ("+companyName+")");
				}
			}
		}
		//获取甲方名称
		if (!StringUtil.isNullOrEmpty(project.getConstructCompany())) {
			ProjectConstructEntity constructCompany = projectConstructDao.selectById(project.getConstructCompany());
			newProjectAboutsDTO.setConstructCompanyName(constructCompany.getCompanyName());
		}
		//项目设计范围
		List<ProjectDesignRangeEntity> projectDesignRangeEntityList = projectDesignRangeDao.getDesignRangeByProjectId(projectId);
		newProjectAboutsDTO.setProjectDesignRangeList(BaseDTO.copyFields(projectDesignRangeEntityList, ProjectDesignRangeDTO.class));
		mapProject.put("project",newProjectAboutsDTO);

		//任务概况
		List<ProjectTaskDTO> taskList = this.projectTaskService.getProjectTaskRootData(projectId,companyId);
		if(!CollectionUtils.isEmpty(taskList)){//设计到设计阶段字段上
			String projectDesignContent = "";
			for(ProjectTaskDTO dto:taskList){
				projectDesignContent+=dto.getTaskName()+" ";
			}
			newProjectAboutsDTO.setProjectDesignContent(projectDesignContent);
		}
		mapProject.put("taskList",taskList);

		//负责人
		//获取当前项目在当前团队的经营负责人和项目负责人
		List<ProjectMemberDTO> managerList = new ArrayList<>();
		managerList.add(this.projectMemberService.getOperatorManagerDTO((String)param.get("id"),(String)param.get("appOrgId")));
		managerList.add(this.projectMemberService.getOperatorManagerDTO((String)param.get("id"),(String)param.get("appOrgId")));
				//projectManagerDao.getProjectManager((String)param.get("id"),(String)param.get("appOrgId"),this.fastdfsUrl);
		for(ProjectMemberDTO managerDTO:managerList){
			if(accountId.equals(managerDTO.getUserId())){
				managerDTO.setFlag(1);
			}
		}
		mapProject.put("managerList",managerList);

		//项目文档
		List<ProjectSkyDriveListDTO> fileList = this.projectSkyDriverDao.getProjectUploadFile(projectId);
		mapProject.put("fileCount",fileList.size());
		if(!CollectionUtils.isEmpty(fileList) && fileList.size()>1){
			mapProject.put("fileList",fileList.subList(0,2));
		}else {
			mapProject.put("fileList",fileList);
		}


		//签发数据
		List<ProjectIssueDataDTO> issueDataList = this.projectTaskDao.getTaskIssueData(projectId);
		mapProject.put("issueDataList",issueDataList);

		CompanyUserEntity companyUserEntity2 = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
		if(companyUserEntity2!=null){
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("id",projectId);
			map.put("companyUserId",companyUserEntity2.getId());
			int flag = this.projectDao.getProjectFlag(map);
			mapProject.put("flag",flag+"");

			//获取当前人在当前项目的权限
			Map<String,Object> roleMap = this.projectNavigationRole(project,companyId,accountId,companyUserEntity2.getId());
			mapProject.put("roleMap",roleMap);
		}

		int deleteFlag = this.handleProjectEditRole(projectId,project.getCompanyId(),companyId,accountId,companyUserEntity2.getId(),project.getCreateBy());
		mapProject.put("deleteFlag",deleteFlag);
        return mapProject;
	}


	//百分比
	public String getProjectBasicNum(String id)throws  Exception{
		ProjectEntity projectDTO = this.projectDao.selectById(id);
		int completionsNum=0;
		int totalNum=23;
		if(!StringUtil.isNullOrEmpty(projectDTO.getProjectNo())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getProjectName())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getStatus())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getConstructCompany())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getBuiltType())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getProjectType())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getCompanyBid())){
			completionsNum++;
		}

		//省份
		if(!StringUtil.isNullOrEmpty(projectDTO.getProvince())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getDetailAddress())){
			completionsNum++;
		}

		//经济指标
		if(!StringUtil.isNullOrEmpty(projectDTO.getBaseArea())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getVolumeRatio())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getCoverage())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getTotalConstructionArea())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getCapacityArea())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getGreeningRate())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getBuiltHeight())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getInvestmentEstimation())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getBuiltFloorDown())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getBuiltFloorUp())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getInvestmentEstimation())){
			completionsNum++;
		}
		if(!StringUtil.isNullOrEmpty(projectDTO.getContractDate())){
			completionsNum++;
		}
		//合同签订时间
//		List<ProjectAuditEntity> signEntity = projectAuditDao.getProjectAuditEntityByProjectAndType(id,"2");
//		if(!CollectionUtils.isEmpty(signEntity)){
//			completionsNum++;
//		}

		//设计范围一定有
		completionsNum++;

		//设计阶段一定有
		completionsNum++;

		double d = (double)completionsNum/totalNum*100;
		return (int)d+"%";
	}


	/************************************v2***************************************************/


	/**
	 * 方法描述：处理设计阶段
	 * 作者：MaoSF
	 * 日期：2017/3/22
	 * @param:
	 * @return:
	 */
	private void detailProjectsPersonAndDesignStatus(List<V2ProjectTableDTO> data,String companyId,String accountId,String companyUserId) throws Exception{
		for(V2ProjectTableDTO dto:data){
			List<ProjectTaskDTO> list = this.projectTaskDao.getProjectTaskRootData(dto.getId());
			for(ProjectTaskDTO dto1:list){
				this.projectTaskService.setTaskState(dto1);
			}
			dto.setTaskList(BaseDTO.copyFields(list,ProjectTaskListDTO.class));
			dto.setDeleteFlag( this.handleDeleteRole(dto.getId(),dto.getCompanyId(),companyId,accountId,companyUserId,dto.getCreateBy()));
		}
	}

	private int handleDeleteRole(String projectId, String projectCompanyId,String companyId,String accountId, String companyUserId,String createBy) throws Exception{

		if(projectCompanyId.equals(companyId)){
			if(accountId.equals(createBy)){
				return 1;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<CompanyUserTableDTO> companyUserList = null;

			//企业负责人
			map.put("permissionId", "50");//企业负责人权限id
			map.put("companyId", projectCompanyId);
			companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if (!CollectionUtils.isEmpty(companyUserList)) {
				if(companyUserList.get(0).getUserId().equals(accountId)){
					return 1;
				}
			}
		}

		return 0;
	}



    /**
     * 方法描述：项目编辑权限
     * 作者：MaoSF
     * 日期：2017/3/22
     * @param:
     * @return:
     */
    private int handleProjectEditRole(String projectId, String projectCompanyId,String companyId,String accountId, String companyUserId,String createBy) throws Exception{
    	if(projectCompanyId.equals(companyId)){
			if(accountId.equals(createBy)){
				return 1;
			}
			ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, projectCompanyId);
			if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
				return 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			List<CompanyUserTableDTO> companyUserList = null;

			//项目基本信息编辑
			map.put("permissionId", "20");//项目基本信息编辑人权限id
			map.put("companyId", projectCompanyId);
			companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if (!CollectionUtils.isEmpty(companyUserList)) {
				for(CompanyUserTableDTO dto:companyUserList){
					if(dto.getUserId().equals(accountId)){
						return 1;
					}
				}
			}
		}
        return 0;
    }


	/**
	 *
	 * 项目群组成员
	 * @param
	 * @return
	 * @throws Exception
     */
	public List<ProjectUserDTO> getProjectParticipantsList(String projectId) throws  Exception{
		List<ProjectUserDTO> list = this.projectDao.getProjectParticipation(projectId,this.fastdfsUrl);
		return list;
	}

	//添加立项动态
	public void setProjectDynamicsByProject(ProjectEntity projectEntity) throws Exception{
		//组织动态
		this.orgDynamicService.combinationDynamicForProject(projectEntity.getId(),projectEntity.getCompanyId(),projectEntity.getCreateBy());
	}


	/**
	 * 方法描述：项目立项及修改
	 * 作者：MaoSF
	 * 日期：2016/12/6
	 *
	 * @param:
	 * @return:
	 */
	@Override
	public ResponseBean saveOrUpdateProjectNew(ProjectDTO dto) throws Exception {
		AjaxMessage ajaxMessage = this.validateSaveOrUpdateProject(dto);
		if (!"0".equals(ajaxMessage.getCode())) {
			return ResponseBean.responseError((String) ajaxMessage.getInfo());
		}

		//如果建设单位id为null，则通过建设单位名查找或增加新的建设单位
		if(StringUtil.isNullOrEmpty(dto.getConstructCompany())){
			//处理甲方
			if(!StringUtil.isNullOrEmpty(dto.getConstructCompanyName())){
				String constructId = this.handlProjectConstructCompanyName(dto.getConstructCompanyName(),dto.getCompanyId());
				dto.setConstructCompany(constructId);
			}
		}
		int partBFlag = this.projectPartB(dto);

		String companyBid = null;//定义乙方
		//更新人
		String accountId = dto.getAccountId();
		//项目
		ProjectEntity projectEntity = new ProjectEntity();
		BaseDTO.copyFields(dto, projectEntity);

		ProjectEntity originProject = (dto.getId() != null) ? projectDao.selectById(dto.getId()) : null; //保存原有的项目内容
		ZProjectDTO originProjectEx = zInfoDAO.getProjectByProject(originProject); //保存原有项目的设计范围和功能分类

		boolean isUpdate = false;
		if (StringUtil.isNullOrEmpty(dto.getId())) {
			//新增项目信息
			String id = StringUtil.buildUUID();
			dto.setId(id);
			if(null==dto.getProjectType()||"".equals(dto.getProjectType())){
				projectEntity.setProjectType(SystemParameters.PROJECT_TYPE_ID);
			}
			projectEntity.setId(id);
			projectEntity.setPstatus("0");
			projectEntity.setStatus("1");
			projectEntity.setCreateBy(accountId);
			projectDao.insert(projectEntity);

			//创建项目群
//			Map<String,String> projectParam = new HashMap<>();
//			projectParam.put("orgId",id);
//			projectParam.put("userId",accountId);
//			projectParam.put("orgName",projectEntity.getProjectName());
//			projectParam.put("groupType","3");
//			imGroupService.createGroupDestination(projectParam);
			this.projectMemberService.saveProjectMember(projectEntity.getId(),projectEntity.getCompanyId(),null, ProjectMemberType.PROJECT_CREATOR,accountId,false);

			//创建默认磁盘
            this.projectSkyDriverService.createProjectFile(projectEntity);

			/*************处理设计阶段*************/
			this.handleProjectDesignContent(dto.getProjectDesignContentList(), projectEntity.getId(), dto.getCompanyId(), dto.getAccountId(),dto.getProjectManagerId(),partBFlag);

			//保存经营负责人
			setProjectManagerForCompany(projectEntity.getCompanyId(), projectEntity.getId(), "51", accountId, true);
			//保存设计负责人
			setProjectManagerForCompany(projectEntity.getCompanyId(), projectEntity.getId(), "52", accountId, true);

			//发送动态消息
			this.setProjectDynamicsByProject(projectEntity);



		} else {//修改
			ProjectEntity projectEntity1 = this.projectDao.selectById(dto.getId());
			companyBid = projectEntity1.getCompanyBid();
			projectEntity.setUpdateBy(accountId);
			if(null==dto.getProjectType()||"".equals(dto.getProjectType())){
				projectEntity.setProjectType(SystemParameters.PROJECT_TYPE_ID);
			}
			projectDao.update(projectEntity);//更新全部字段
			isUpdate = true;
			/*************处理设计阶段*************/
			//查询经营负责人
			ProjectMemberEntity manager = this.projectMemberService.getDesignManager(projectEntity.getId(),projectEntity.getCompanyId());
			String managerId=null;
			if(manager!=null){
				managerId = manager.getCompanyUserId();
			}
			this.handleProjectDesignContent(dto.getProjectDesignContentList(), dto.getId(), dto.getCompanyId(), dto.getAccountId(),managerId,partBFlag);

			//修改项目群

//			if(!StringUtil.isNullOrEmpty(projectEntity.getProjectName())){
//				imGroupService2.updateImGroup(projectEntity.getId(),projectEntity.getProjectName(), ImGroupType.PROJECT);
//			}
		}

		//处理乙方变更
		if(partBFlag==1 || partBFlag==2 || partBFlag==4  ){
			this.projectCostService.handPartBChange(projectEntity.getId(),accountId,partBFlag);

			if((partBFlag==2 || partBFlag==4) && !StringUtil.isNullOrEmpty(companyBid)){//删除原来的经营负责人
                handleIsDeletePartBManager(projectEntity.getId(),companyBid);
			}

			if (!StringUtil.isNullOrEmpty(dto.getCompanyBid()) && !dto.getCompanyBid().equals(dto.getCompanyId())) {
				//查看乙方是否存在此项目的经营负责人及设计负责人，如果不存在则设立并发送通知
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("projectId", projectEntity.getId());
				map.put("companyId", dto.getCompanyBid());
				List<ProjectMemberEntity> list = this.projectMemberService.listProjectMember(projectEntity.getId(),dto.getCompanyBid(),null,null);
				if (list.size() == 0){ //不存在此项目的负责人
					setProjectManagerForCompany(dto.getCompanyBid(),projectEntity.getId(),"51",accountId, false);
					setProjectManagerForCompany(dto.getCompanyBid(),projectEntity.getId(),"52",accountId, false);
				}
			}

//			if(partBFlag!=2){
//				//发送动态
//				this.orgDynamicService.combinationDynamicForPartyB(projectEntity.getId(),dto.getCompanyBid(),dto.getAccountId());
//			}
		}
		if(partBFlag==3){//注释：郭志彬 乙方是其他组织，并且没有改变乙方组织，但是改变了乙方的经营负责人，这里也需要处理经营负责人变更，因此把partBFlag==3加上
			//保存经营负责人
				this.saveProjectManagerForPartB(projectEntity.getId(),dto.getCompanyBid(),dto.getPartBManagerId(),dto.getPartBDesignerId(),dto.getAccountId());
		}
		//处理协同


		String projectId = dto.getId();

		/***************end******************/
		//先删除设计范围
		projectDesignRangeDao.deleteDRangeByProjectId(projectId);
		//保存设计范围
		ProjectDesignRangeEntity projectDesignRangeEntity = null;
		if (dto.getProjectDesignRangeList().size() > 0) {
			int seq = 1;
			for (ProjectDesignRangeDTO designRangeDTO : dto.getProjectDesignRangeList()) {
				projectDesignRangeEntity = new ProjectDesignRangeEntity();
				designRangeDTO.setId(StringUtil.buildUUID());
				BaseDTO.copyFields(designRangeDTO, projectDesignRangeEntity);
				projectDesignRangeEntity.setProjectId(projectId);
				projectDesignRangeEntity.setCreateBy(accountId);
				projectDesignRangeEntity.setUpdateBy(accountId);
				projectDesignRangeEntity.setSeq(seq++);
				projectDesignRangeDao.insert(projectDesignRangeEntity);
			}
		}

		//合同签订数据插入审核表中
		//1.删除审核表中的备案数据
		List<ProjectAuditEntity> projectAuditEntitys = projectAuditDao.getProjectAuditEntityByProjectAndType(projectId, "2");

		projectAuditDao.delAuditByProjectAndType(projectId, "2");
		if (!StringUtil.isNullOrEmpty(dto.getSignDate())) {
			ProjectAuditEntity auditEntity = new ProjectAuditEntity();
			auditEntity.setId(StringUtil.buildUUID());
			auditEntity.setProjectId(projectId);
			auditEntity.setAuditType("2");
			auditEntity.setUserId(dto.getAccountId());
			auditEntity.setAuditDate(dto.getSignDate());
			auditEntity.setAuditStatus("0");
			auditEntity.setCreateBy(dto.getAccountId());
			auditEntity.setUpdateBy(dto.getAccountId());
			projectAuditDao.insert(auditEntity);
			if (CollectionUtils.isEmpty(projectAuditEntitys)) {
				//发送合同评审消息
			}

		}
		//添加项目动态
		dynamicService.addDynamic(originProject,projectEntity,dto.getCurrentCompanyId(),dto.getAccountId()); //普通内容
		if ((originProject != null) && (projectEntity != null) &&
				((projectEntity.getStatus() == null) || (originProject.getStatus().equals(projectEntity.getStatus())))) {
			ZProjectDTO targetProjectEx = zInfoDAO.getProjectByProject(projectEntity);
			dynamicService.addDynamic(originProjectEx, targetProjectEx, dto.getCurrentCompanyId(), dto.getAccountId()); //设计范围、功能分类、合同签订日期
		}

		//通知协同
		this.collaborationService.pushSyncCMD_PU(projectEntity.getId());

		//推送消息
		this.sendMsgForAddProject(projectEntity.getId(),projectEntity.getCompanyId());

		return ResponseBean.responseSuccess("保存成功").addData("id",projectId);
	}


	/**
	 * 设置某公司某项目的管理者
	 * @param companyId 公司编号
	 * @param projectId 项目编号
	 * @param permissionId 权限编号，经营负责人：51，设计负责人：52
	 * @param accountId 立项人用户编号
	 * @param isCreator 是否立项方
	 */
	private void setProjectManagerForCompany(String companyId, String projectId, String permissionId, String accountId, Boolean isCreator) throws Exception {
		ProjectMemberEntity pm2 = (isSettingIssueManager(permissionId) ?
				projectMemberService.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_OPERATOR_MANAGER,null) :
				projectMemberService.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null));
		if (pm2 == null) {
			//签发到的公司中选择具备相应权限的人员中选择第一个填入项目经营负责人位置
			Map<String, Object> map = new HashMap<>();
			map.put("permissionId", permissionId);//相应权限id
			map.put("companyId", companyId);
			String userId = null;
			String companyUserId = null;
			List<CompanyUserTableDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if (companyUserList.size() > 0) {
				CompanyUserTableDTO cut = null;
				if(isCreator){
					for(CompanyUserTableDTO userTableDTO:companyUserList){
						if(userTableDTO.getUserId().equals(accountId)){//如果当前人就是经营负责人。则设置为经营负责人
							cut = userTableDTO;
						}
					}
				}
				if(cut == null){//如果当前人不是经营负责人，则默认第一个
					cut = companyUserList.get(0);
				}
				if (cut != null) {
					userId = cut.getUserId();
					companyUserId = cut.getId();
				}
			} else if (isCreator){ //如果没有定义总监，保持原有逻辑，设置立项人为经营负责人及设计负责人
				userId = accountId;
				CompanyUserEntity cue = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
				if (cue != null) {
					companyUserId = cue.getId();
				}
			}
			if ((companyUserId == null) || (userId == null)) return;
			//给其推送任务或消息
			if (isSettingIssueManager(permissionId)) {
				if(isCreator){//此处加上 && isCreator 区分立项还是乙方设置，添加乙方经营负责人不需要推送任务
					this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,null,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,!accountId.equals(userId));
				}else {//如果是乙方，则只推送消息
					//只推送消息
					this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,!isCreator);
				}
			} else {
				//如果立项人不是设计负责人时，才推送消息
				this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,( !isCreator ||  !accountId.equals(userId)));
			}
		}
	}
	private Boolean isSettingIssueManager(String permissionId){
		return ("51".equals(permissionId));
	}

    private void handleIsDeletePartBManager(String projectId,String companyBid) throws Exception{
        //查询生产或经营的任务
        List<ProjectTaskEntity> list = this.projectTaskDao.getProjectTaskByCompanyIdOfOperater(projectId,companyBid);
        if(CollectionUtils.isEmpty(list)){
			projectMemberService.deleteProjectMember(projectId, companyBid,null,null);
        }
    }

	/**
	 * 方法描述：保存项目（数据验证）
	 * 作者：MaoSF
	 * 日期：2016/7/29
	 * @param:
	 * @return:
	 */
	private AjaxMessage validateSaveOrUpdateProject(ProjectDTO dto){

		if(StringUtil.isNullOrEmpty(dto.getProjectName())){
			return new AjaxMessage().setCode("1").setInfo("项目名称不能为空");
		}else if(dto.getProjectName().length()>100){
			return new AjaxMessage().setCode("1").setInfo("项目名称长度过长");
		}
//		if(StringUtil.isNullOrEmpty(dto.getConstructCompanyName())){
//			return new AjaxMessage().setCode("1").setInfo("甲方名称不能为空");
//		}

//		if(CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
//			return new AjaxMessage().setCode("1").setInfo("设计范围不能为空");
//		}
//		if(StringUtil.isNullOrEmpty(dto.getId()) && StringUtil.isNullOrEmpty(dto.getProjectManagerId())){
//			return new AjaxMessage().setCode("1").setInfo("经营负责人不能为空");
//		}
		if(CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
			return new AjaxMessage().setCode("1").setInfo("设计阶段不能为空");
		}

		if (!CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
			Map<String,String> contentNames = new HashMap<>();
			for(ProjectDesignContentDTO designContentDTO:dto.getProjectDesignContentList()){
				if(contentNames.containsKey(designContentDTO.getContentName())){
					return new AjaxMessage().setCode("1").setInfo(designContentDTO.getContentName()+"重复");
				}else {
					contentNames.put(designContentDTO.getContentName(),designContentDTO.getContentName());
				}
			}
		}

		if(dto.getInvestmentEstimation() !=null &&
				(dto.getInvestmentEstimation().compareTo(new BigDecimal(-99999999999999.999999))<0
						|| dto.getInvestmentEstimation().compareTo(new BigDecimal(99999999999999.999999))>0)){
			return new AjaxMessage().setCode("1").setInfo("投资估算长度过长");
		}

		if(dto.getTotalContractAmount() !=null &&
				(dto.getTotalContractAmount().compareTo(new BigDecimal(-99999999999999.999999))<0
						|| dto.getTotalContractAmount().compareTo(new BigDecimal(99999999999999.999999))>0)){
			return new AjaxMessage().setCode("1").setInfo("合同总金额长度过长");
		}


		return new AjaxMessage().setCode("0");
	}

	/**
	 * 方法描述：判断乙方的变更的情况，费用部分的数据变更
	 * 作者：MaoSF
	 * 日期：2017/3/2
	 * @param:
	 * @return:
	 */
	private int projectPartB(ProjectDTO dto){
		if(StringUtil.isNullOrEmpty(dto.getId()) && !StringUtil.isNullOrEmpty(dto.getCompanyBid())){
			return 1;//新增并且选择了乙方
		}
		if(!StringUtil.isNullOrEmpty(dto.getId())){
			ProjectEntity entity = this.projectDao.selectById(dto.getId());
			String companyBid = entity.getCompanyBid();
			if(StringUtil.isNullOrEmpty(entity.getCompanyBid())){
				companyBid = entity.getCompanyId();//为了后面少null的判断，因为乙方设置为自己和不设是一样
			}
			if(companyBid.equals(entity.getCompanyId())){//原来没有设置乙方，或许乙方是自己的情况
				if(!StringUtil.isNullOrEmpty(dto.getCompanyBid())&&!dto.getCompanyBid().equals(dto.getCompanyId())){
					return 1;//全部设置
				}
			}else {//存在乙方，并且乙方不是自己
				if(StringUtil.isNullOrEmpty(dto.getCompanyBid()) || dto.getCompanyBid().equals(entity.getCompanyId())){
					return 2;//全部删除
				}else {
					if(!companyBid.equals(dto.getCompanyBid())){
						return 4;//先删除后全部重置
					}else {
						return 3;//没有变更乙方，但是乙方是其他组织
					}
				}
			}
		}
		return 0;
	}


	/**
	 * 方法描述：编辑，新增项目，处理设计阶段（非接口）
	 * 作者：MaoSF
	 * 日期：2016/8/12
	 *
	 * @param:
	 * @return:
	 */
	private void handleProjectDesignContent(List<ProjectDesignContentDTO> dtoList, String projectId, String companyId, String accountId,String managerId,int partBFlag) throws Exception {
		int seq = 1;
		String notDeleteIds = "";
		List<ProjectTaskEntity> oldTaskList = this.projectTaskService.listProjectTaskContent(projectId);
		for (ProjectDesignContentDTO projectDesignContentDTO : dtoList) {

			projectDesignContentDTO.setAppOrgId(companyId);
			//处理设计阶段信息
			if (StringUtil.isNullOrEmpty(projectDesignContentDTO.getId())) {
				String taskId = saveTask(projectId,companyId,projectDesignContentDTO.getContentName(),(seq++),projectDesignContentDTO.getStartTime(),projectDesignContentDTO.getEndTime());
				//如果有时间则添加到ProjectProcessTimeEntity中
				if(!StringUtil.isNullOrEmpty(projectDesignContentDTO.getStartTime()) && !StringUtil.isNullOrEmpty(projectDesignContentDTO.getEndTime()))
				{
					this.saveProjectTime(projectDesignContentDTO,taskId);
				}

			} else {
				for (ProjectTaskEntity entity : oldTaskList) {
					if (entity.getId().equals(projectDesignContentDTO.getId())) {
                        ProjectTaskEntity projectTaskEntity= this.projectTaskDao.selectById(entity.getId());
                        //如果有时间则添加到ProjectProcessTimeEntity中
                        if(!StringUtil.isNullOrEmpty(projectDesignContentDTO.getStartTime()) && !StringUtil.isNullOrEmpty(projectDesignContentDTO.getEndTime()))
                        {
                            this.saveProjectTime(projectDesignContentDTO,entity.getId());
							projectTaskEntity.setStartTime(projectDesignContentDTO.getStartTime());
							projectTaskEntity.setEndTime(projectDesignContentDTO.getEndTime());
                        }
						projectTaskEntity.setTaskName(projectDesignContentDTO.getContentName());
						projectTaskEntity.setSeq((seq++));
						this.projectTaskService.updateById(projectTaskEntity);
						notDeleteIds += entity.getId() + ",";
					}
				}
			}
		}
		//删除以前，而现在没有的
		for (ProjectTaskEntity entity : oldTaskList) {
			if (notDeleteIds.indexOf(entity.getId()) < 0) {
			    //把设计阶段下面的所以任务都设置为无效
                projectTaskService.deleteProjectTaskByIdForDesignContent(entity.getId(),accountId);
				messageDao.deleteMessage(entity.getId());
			}
		}
	}

	//保存任务信息（首次任务Id为阶段Id）
	private String saveTask(String projectId,String companyId,String taskName,int seq,String startTime,String endTime) throws Exception {
		ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
		projectTaskEntity.setId(StringUtil.buildUUID());
		projectTaskEntity.setCompanyId(companyId);
		projectTaskEntity.setProjectId(projectId);
		projectTaskEntity.setSeq(seq);
		projectTaskEntity.setTaskLevel(1);
		projectTaskEntity.setTaskName(taskName);
		projectTaskEntity.setTaskStatus("0");
		projectTaskEntity.setTaskPath(projectTaskEntity.getId());
		projectTaskEntity.setTaskType(1);
		projectTaskEntity.setStartTime(startTime);
		projectTaskEntity.setEndTime(endTime);
		this.projectTaskService.insert(projectTaskEntity);
		return projectTaskEntity.getId();

	}


	//保存设定的设计阶段的时间
	private void saveProjectTime(ProjectDesignContentDTO dto,String targetId){
	    Map<String,Object> map = new HashMap<String,Object>();
        map.put("targetId",targetId);
        map.put("type","1");
        List<ProjectProcessTimeEntity> list  = this.projectProcessTimeDao.getProjectProcessTime(map);
        if(CollectionUtils.isEmpty(list)){
            ProjectProcessTimeEntity projectProcessTimeEntity = new ProjectProcessTimeEntity();
            projectProcessTimeEntity.setTargetId(targetId);
            projectProcessTimeEntity.setId(StringUtil.buildUUID());
            projectProcessTimeEntity.setType(1);
			projectProcessTimeEntity.setCompanyId(dto.getAppOrgId());
            projectProcessTimeEntity.setStartTime(dto.getStartTime());
            projectProcessTimeEntity.setEndTime(dto.getEndTime());
            projectProcessTimeDao.insert(projectProcessTimeEntity);

			//查询是否存在计划进度时间
			map.put("type","2");
			List<ProjectProcessTimeEntity> list2  = this.projectProcessTimeDao.getProjectProcessTime(map);
			if(CollectionUtils.isEmpty(list2)){
				//默认为计划进度时间
				projectProcessTimeEntity.setId(StringUtil.buildUUID());
				projectProcessTimeEntity.setType(2);
				projectProcessTimeDao.insert(projectProcessTimeEntity);
			}

        }else {
            //暂时不做更新处理
           // ProjectProcessTimeEntity projectProcessTimeEntity = list.get(list.size()-1);

        }
	}


	private void saveProjectManagerForPartB(String projectId,String companyId,String managerId,String designerId,String accountId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("projectId",projectId);
		map.put("companyId",companyId);
		ProjectMemberEntity operatorManager = this.projectMemberService.getOperatorManager(projectId,companyId);

		if(operatorManager!=null){
			//map.put("type",SystemParameters.MESSAGE_TYPE_1); //郭志彬注释：这里type字段是int类型，在transferManager方法中取出来的又是String类型，发生类型转换错误，因此这里改成String,此行未修改
			map.put("type","1"); //郭志彬注释：这里type字段是int类型，在transferManager方法中取出来的又是String类型，发生类型转换错误，因此这里改成String，此行已修改
			map.put("companyUserId",managerId);
			map.put("id",operatorManager.getId());
			map.put("appOrgId",companyId);
			projectManagerService.transferManager(map);

		}else {
			ProjectManagerEntity entity = new ProjectManagerEntity();
			entity.setCompanyId(companyId);
			entity.setProjectId(projectId);
			entity.setCreateBy(accountId);
			if (!StringUtil.isNullOrEmpty(managerId)) {    //保存经营人
//				entity.setCompanyUserId(managerId);
//				entity.setType(1);
//				//保存经营人
//				entity.setId(StringUtil.buildUUID());
//				this.projectManagerDao.insert(entity);
//
//				//推送安排任务负责人任务
//				this.myTaskService.saveMyTask(projectId,SystemParameters.ARRANGE_TASK_DESIGN,companyId,managerId);
//
//				//把人员添加到群组
//				this.addUserToProjectGroup(projectId,managerId,null,projectId,SystemParameters.PROJECT_MANAGER);
//
//				//发送消息
//				this.sendMessageToPartBManager(projectId,companyId,managerId,SystemParameters.MESSAGE_TYPE_1,projectEntity.getProjectName());

				this.projectMemberService.saveProjectMember(projectId,companyId,managerId,null,ProjectMemberType.PROJECT_OPERATOR_MANAGER,accountId);
			}
		}
	}

	/**
	 * 方法描述：处理甲方
	 * 作者：MaoSF
	 * 日期：2016/12/29
	 * @param:
	 * @return:
	 */
	private String handlProjectConstructCompanyName(String constructCompanyName,String companyId){

		ProjectConstructEntity projectConstructEntity = this.projectConstructDao.getConstructByName(constructCompanyName,companyId);
		if(projectConstructEntity!=null){
			return projectConstructEntity.getId();
		}else {
			projectConstructEntity = new ProjectConstructEntity();
			projectConstructEntity.setId(StringUtil.buildUUID());
			projectConstructEntity.setCompanyId(companyId);
			projectConstructEntity.setCompanyName(constructCompanyName);
			this.projectConstructDao.insert(projectConstructEntity);
			return projectConstructEntity.getId();
		}
	}

	/**
	 * 项目参与组织
	 */
	public List<Map<String,Object>> getProjectTaskCompany(String projectId)throws Exception{
		return projectTaskDao.getProjectTaskCompany(projectId);
	}

	/**
	 * 方法描述：（新建项目，项目修改）发送消息给相关组织成员
	 * 作者：MaoSF
	 * 日期：2017/2/20
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	private void sendMsgForAddProject(String id,String companyId){
		List<String> companyList = new ArrayList<String>();
		companyList.add(companyId);
		//查询项目相关的组织
		List<ProjectTaskRelationEntity> relationList = projectTaskRelationDao.getProjectTaskRelationByProjectId(id);
		if(!CollectionUtils.isEmpty(relationList)){
			for(ProjectTaskRelationEntity relationEntity:relationList){
				if(!companyList.contains(relationEntity.getFromCompanyId())){
					companyList.add(relationEntity.getFromCompanyId());
				}
				if(!companyList.contains(relationEntity.getToCompanyId())){
					companyList.add(relationEntity.getToCompanyId());
				}
			}
		}
		this.sendMsgToRelationCompanyUser(companyList);
	}
	/**
	 * 方法描述：（新建项目，任务签发给其他组织）发送消息给相关组织成员
	 * 作者：MaoSF
	 * 日期：2017/2/20
	 *
	 * @param companyList
	 * @param:
	 * @return:
	 */
	@Override
	public ResponseBean sendMsgToRelationCompanyUser(List<String> companyList) {

//		//查询companyList中所有的人员
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("companyList",companyList);
//		List<String> userIdList = this.companyUserDao.getUserByCompanyForSendMsg(param);
//
//		//发送消息
//		try{
//			Map<String, Object> messageMap = new HashMap<String, Object>();
//			messageMap.put("messSourceType", "company");
//			messageMap.put("messServerType", SystemParameters.PROJECT_TYPE);//项目类型的通知
//			messageMap.put("messType", "2");//通知公告类型
//			messageMap.put("noticeTitle", "Project");
//			messageMap.put("noticeContent", "Operator");
//			messageMap.put("userIdList",userIdList);
//			messageProducer.sendSystemMessage(systemMessageDestination, messageMap);
//		}catch (Exception e){
//			e.printStackTrace();
//			return ResponseBean.responseError("推送失败");
//		}

		return ResponseBean.responseSuccess("推送成功");
	}

	/**
	 * 方法描述：获取项目菜单权限
	 * 作者：MaoSF
	 * 日期：2017/3/27
	 *
	 * @param map
	 * @param:
	 * @return:
	 */
	@Override
	public ResponseBean projectNavigationRoleInterface(Map<String, Object> map) throws Exception {
		String companyId = (String)map.get("appOrgId");
		String accountId = (String)map.get("accountId");
		String projectId = (String)map.get("id");
		ProjectEntity project = this.projectDao.selectById(projectId);
		CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
		if(project==null || companyUser==null){
			return ResponseBean.responseError("数据错误");
		}
		Map<String,Object> map1 = this.projectNavigationRole(project,companyId,accountId,companyUser.getId());
		return ResponseBean.responseSuccess().setData(map1);
	}

	/**
	 * 方法描述：获取项目菜单权限
	 * 作者：MaoSF
	 * 日期：2017/3/18
	 */
	public Map<String, Object> projectNavigationRole(ProjectEntity project,  String companyId,String accountId,String companyUserId) throws Exception {
		Map<String, Object> role = new HashMap<String, Object>();
//		int deleteFlag = this.handleDeleteRole(projectId,project.getCompanyId(),companyId,accountId,companyUserId,project.getCreateBy());
//		if(deleteFlag==1){
//			role.put("deleteFlag","1");
//		}

		int flag = this.handleNavigationRole(project.getId(),companyId,accountId,companyUserId);
		if(flag==1){
			role.put("flag","1");
		}
		//---------------合同回款菜单权限-------------
		if (project.getCompanyId().equals(companyId) || companyId.equals(project.getCompanyBid())) {
			if(flag==1){
				role.put("flag1","1");
			}
		}

		//---------------技术审查费菜单权限-------------
		if(!StringUtil.isNullOrEmpty(project.getCompanyBid()) && !project.getCompanyBid().equals(project.getCompanyId()) && (project.getCompanyId().equals(companyId) || companyId.equals(project.getCompanyBid()))){
			if(flag==1){
				role.put("flag2","1");
			}
		}

		//--------------------合作设计费-------------------------
		List<ProjectTaskRelationEntity> relationEntities = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(project.getId(),companyId);
		if(!CollectionUtils.isEmpty(relationEntities)){//若存在签发，则具有权限
			if(flag==1){
				for(ProjectTaskRelationEntity entity:relationEntities){
					if(companyId.equals(entity.getFromCompanyId()) || companyId.equals(entity.getToCompanyId())){
						role.put("flag3","1");
						break;
					}
				}
			}
		}

		//---------------其他费用菜单权限-------------
		if(flag==1){
			role.put("flag4","1");
		}
		return role;
	}


	private int handleNavigationRole(String projectId, String companyId,String accountId, String companyUserId) throws Exception{
		ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, companyId);
		if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
			return 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyUserTableDTO> companyUserList = null;

		//企业负责人
		map.put("permissionId", "50");//企业负责人权限id
		map.put("companyId", companyId);
		companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
		if (managerEntity!=null && !CollectionUtils.isEmpty(companyUserList)) {
			if(companyUserList.get(0).getUserId().equals(accountId)){
				return 1;
			}
		}

		//财务人员
		map.put("permissionId", "49");//
		map.put("companyId", companyId);
		companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
		if (managerEntity != null && !CollectionUtils.isEmpty(companyUserList)) {
			for (CompanyUserTableDTO userTableDTO : companyUserList) {
				if (userTableDTO.getUserId().equals(accountId)) {
					return 1;
				}
			}
		}

		return 0;
	}



    /**
     * 方法描述：设计阶段添加与修改
     * 作者：MaoSF
     * 日期：2017/4/20
     *
     * @param designContentDTO
     * @param:
     * @return:
     */
    @Override
    public ResponseBean saveOrUpdateProjectDesign(ProjectDesignContentDTO designContentDTO) throws Exception {

        if(StringUtil.isNullOrEmpty(designContentDTO.getId())){//新增
			String taskId = this.saveTask(designContentDTO.getProjectId(),designContentDTO.getCompanyId(),designContentDTO.getContentName(),0,designContentDTO.getStartTime(),designContentDTO.getEndTime());
			if (!StringUtil.isNullOrEmpty(designContentDTO.getStartTime()) && !StringUtil.isNullOrEmpty(designContentDTO.getEndTime())) {
				ProjectProcessTimeEntity projectProcessTimeEntity = new ProjectProcessTimeEntity();
				projectProcessTimeEntity.setTargetId(taskId);
				projectProcessTimeEntity.setId(StringUtil.buildUUID());
				projectProcessTimeEntity.setCompanyId(designContentDTO.getCompanyId());
				projectProcessTimeEntity.setStartTime(designContentDTO.getStartTime());
				projectProcessTimeEntity.setEndTime(designContentDTO.getEndTime());
				projectProcessTimeEntity.setType(1);
				projectProcessTimeEntity.setCreateBy(designContentDTO.getAccountId());
				projectProcessTimeEntity.setCreateDate(DateUtils.getDate());
				projectProcessTimeDao.insert(projectProcessTimeEntity);
			}
			//保存项目动态
			ProjectTaskEntity target = projectTaskDao.selectById(taskId);
			dynamicService.addDynamic(null,target,designContentDTO.getCompanyId(),designContentDTO.getAccountId());

        }else {//修改
            //修改任务名称
            ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(designContentDTO.getId());
			//保留原有数据
			ProjectTaskEntity origin = null;
			if (taskEntity != null){
				origin = new ProjectTaskEntity();
				BeanUtilsEx.copyProperties(taskEntity,origin);
			}
			taskEntity.setTaskName(designContentDTO.getContentName());
            this.projectTaskService.updateById(taskEntity);
			//添加项目动态
			dynamicService.addDynamic(origin,taskEntity,designContentDTO.getCompanyId(),designContentDTO.getAccountId());
        }

        return ResponseBean.responseSuccess("保存成功");
    }

}
