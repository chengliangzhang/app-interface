package com.maoding.project.service.impl;

import com.beust.jcommander.internal.Lists;
import com.maoding.commonModule.dto.UpdateConstDTO;
import com.maoding.commonModule.service.ConstService;
import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ApiResult;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectMemberType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.*;
import com.maoding.dynamic.dao.OrgDynamicDao;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.dynamic.dto.ZProjectDTO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.enterprise.dto.EnterpriseSearchQueryDTO;
import com.maoding.hxIm.service.ImService;
import com.maoding.message.dao.MessageDao;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.project.constDefine.EnterpriseServer;
import com.maoding.project.dao.*;
import com.maoding.project.dto.*;
import com.maoding.project.entity.*;
import com.maoding.project.service.ProjectDesignContentService;
import com.maoding.project.service.ProjectService;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectcost.dao.ProjectCostDao;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.dto.ProjectMemberDataDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.property.dao.CompanyPropertyDao;
import com.maoding.property.entity.CompanyPropertyEntity;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dto.ProjectOperatorDTO;
import com.maoding.role.service.PermissionService;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.task.dao.ProjectProcessTimeDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dao.ProjectTaskRelationDao;
import com.maoding.task.dto.HomeDTO;
import com.maoding.task.dto.ProjectTaskDTO;
import com.maoding.task.dto.ProjectTaskListDTO;
import com.maoding.task.dto.TaskDataDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.entity.ProjectTaskRelationEntity;
import com.maoding.task.service.ProjectManagerService;
import com.maoding.task.service.ProjectTaskService;
import com.maoding.v2.project.dto.ProjectBaseDataDTO;
import com.maoding.v2.project.dto.V2ProjectTableDTO;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
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

	protected final Logger logger= LoggerFactory.getLogger(this.getClass());
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

	@Autowired
	private PermissionService permissionService;

	//项目审核
	@Autowired
	private ProjectAuditDao projectAuditDao;

	@Autowired
	private ProjectTaskService projectTaskService;


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
	private NoticeService noticeService;

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
	private ProjectCostDao projectCostDao;

	@Autowired
	private DynamicService dynamicService;

	@Autowired
	private ZInfoDAO zInfoDAO;

	@Autowired
	private ProjectPropertyDao projectPropertyDao;

	@Autowired
	private ImService imService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private  ProjectDesignContentDao projectDesignContentDao;

	@Autowired
	private CompanyPropertyDao companyPropertyDao;

	@Autowired
	private EnterpriseServer enterpriseServer;

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private ConstService constService;


	@Value("${fastdfs.url}")
	protected String fastdfsUrl;


	/**
	 * 方法描述：v2项目列表(param:type:0,全部项目，1，我参与的项目)
	 * 作者：chenzhujie
	 * 日期：2016/12/24
	 */
	@Override
	public List<V2ProjectTableDTO> getV2ProjectList(Map<String, Object> param) throws Exception {
		String appOrgId = (String) param.get("appOrgId");
		String accountId = (String) param.get("accountId");
		if (null != param.get("pageIndex")) {
			int page = (Integer) param.get("pageIndex");
			int pageSize = (Integer) param.get("pageSize");
			param.put("startPage", page * pageSize);
			param.put("endPage", pageSize);
		}
		CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,appOrgId);
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

			dto.setProjectOperator(this.projectNavigationRole(dto,appOrgId,accountId,companyUserEntity.getId()));

			//查询项目群组
//			ImGroupQuery query = new ImGroupQuery();
//			query.setNodeId(dto.getId());
//			query.setCompanyUserId(companyUserEntity.getId());
//			dto.setGroupList(imService.selectCustomGroupByParameter(query));
		}
		this.detailProjectsPersonAndDesignStatus(list,(String) param.get("appOrgId"),(String) param.get("accountId"),companyUserEntity.getId());

		return list;
	}


	/**
	 * 方法描述：获取项目菜单权限
	 * 作者：MaoSF
	 * 日期：2017/3/18
	 */
	public ProjectOperatorDTO projectNavigationRole(V2ProjectTableDTO project,String currentCompanyId, String accountId, String companyUserId) throws Exception {
		ProjectOperatorDTO operator = new ProjectOperatorDTO();
		String projectId = project.getId();
		int deleteFlag = this.handleDeleteRole(project.getCreateBy(), project.getCompanyId(), currentCompanyId, accountId);
		if (deleteFlag == 1) {
			operator.setProjectDelete(1);
		}

		int editFlag = this.handleProjectEditRole(project.getCompanyId(), currentCompanyId, accountId, project.getCreateBy());
		operator.setProjectEdit(editFlag);

		int flag = this.handleNavigationRole(projectId, currentCompanyId, accountId, companyUserId);
		//---------------合同回款菜单权限-------------
		if (project.getCompanyId().equals(currentCompanyId) || currentCompanyId.equals(project.getCompanyBid())) {
			if (flag == 1) {
				operator.setContractModule(1);
			}
		}
		//---------------技术审查费菜单权限-------------
		if (!StringUtil.isNullOrEmpty(project.getCompanyBid()) && !project.getCompanyBid().equals(project.getCompanyId())
				&& (project.getCompanyId().equals(currentCompanyId) || currentCompanyId.equals(project.getCompanyBid()))) {
			if (flag == 1) {
				operator.setTechnologyModule(1);
			}
			if(project.getCompanyBid().equals(currentCompanyId)){//收款方
				operator.setCooperationPaidModule(1);
			}
			if(project.getCompanyId().equals(currentCompanyId)){//付款方
				operator.setCooperationPayModule(1);
			}
		}
		//--------------------合作设计费-------------------------
		if (flag == 1) {
			String dataCompanyId = currentCompanyId;
			List<ProjectTaskRelationEntity> relationEntities = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(projectId, dataCompanyId);
			if(CollectionUtils.isEmpty(relationEntities)){//如果当前组织没有合作设计费，则继续判断当前组织是否是乙方，如果是乙方，则查询立项方的数据
				if (!StringUtil.isNullOrEmpty(project.getCompanyBid()) && project.getCompanyBid().equals(currentCompanyId)) {
					dataCompanyId = project.getCompanyId();
					relationEntities = this.projectTaskRelationDao.getProjectTaskRelationByCompanyId(projectId,dataCompanyId);
				}
			}
			for(ProjectTaskRelationEntity entity:relationEntities){
				if(currentCompanyId.equals(entity.getFromCompanyId())){
					operator.setCooperationModule(1);
					operator.setCooperationPayModule(1);
					break;
				}
			}
			for(ProjectTaskRelationEntity entity:relationEntities){
				if(currentCompanyId.equals(entity.getToCompanyId())){
					operator.setCooperationModule(1);
					operator.setCooperationPaidModule(1);
					break;
				}
			}
		}
		//---------------其他费用菜单权限-------------
		if (flag == 1) {
			operator.setOtherModule(1);
			operator.setOtherPaidModule(1);
			operator.setOtherPayModule(1);
		}
		return operator;
	}


	/**
	 * 方法描述：经营列表总数据（用于分页）
	 * 作者：MaoSF
	 * 日期：2016/7/29
	 */
	@Override
	public int getProjectListByConditionCount(Map<String, Object> param) throws Exception{
		return projectDao.getProjectListByConditionCount(param);
	}


	/**
	 * 方法描述：删除项目（逻辑删除）
	 * 作者：MaoSF
	 * 日期：2016/8/4
	 */
	@Override
	public AjaxMessage deleteProjectById(String id) throws Exception {
		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setId(id);
		projectEntity.setPstatus("1");
		this.updateById(projectEntity);

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

	@Override
	public List<ProjectPropertyDTO> getProjectBuildType(String projectId){
		ProjectEntity project = projectDao.selectById(projectId);
		if ((project != null) && (!StringUtils.isEmpty(project.getBuiltType()))) {
			return listFunction(projectId, project.getBuiltType(),true);
		} else {
			return null;
		}
	}

	private String getProjectBuildName(List<ProjectPropertyDTO> functionList){
		List<String> buildNameList = new ArrayList<>();
		functionList.stream().forEach(b->{
			if(isSelected(b)){
				buildNameList.add(b.getName());
			}
		});
		return String.join(",", buildNameList);
	}


	/**
	 * 方法描述：根据id查询项目信息
	 * 作者：MaoSF
	 * 日期：2016/7/28
	 */
	@Override
	public ProjectDTO getProjectById(String id,String companyId,String userId) throws Exception{
		ProjectDTO projectDTO= new ProjectDTO();
		//项目基本信息
		ProjectEntity projectEntity = projectDao.selectById(id);
		BaseDTO.copyFields(projectEntity,projectDTO);

		//项目功能
		if(!StringUtil.isNullOrEmpty(projectEntity.getBuiltType())){
			//填充功能分类
			List<ProjectPropertyDTO> functionList = listFunction(id,projectEntity.getBuiltType(),false);
			projectDTO.setBuiltTypeName(getProjectBuildName(functionList));
			projectDTO.setFunctionList(functionList);
		}

		//获取甲方名称
		if(!StringUtil.isNullOrEmpty(projectEntity.getConstructCompany())){
			projectDTO.setConstructCompanyName(projectDao.getEnterpriseName(projectEntity.getConstructCompany()));
		}

		//乙方公司
		if(!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid())){
			CompanyEntity companyB = companyDao.selectById(projectEntity.getCompanyBid());
			if(companyB!=null){
				projectDTO.setCompanyBidName(companyB.getAliasName());
			}
		}

		//立项组织
		CompanyEntity company = companyDao.selectById(projectEntity.getCompanyId());
		if(company!=null){
			projectDTO.setCompanyName(company.getAliasName());
		}

		//合同签订日期
		if(!StringUtil.isNullOrEmpty(projectEntity.getContractDate())){
			projectDTO.setSignDate(DateUtils.formatDate(projectEntity.getContractDate()));
		}

		//项目类型
		projectDTO.setProjectTypeName(projectEntity.getProjectType());
//		if(!StringUtil.isNullOrEmpty(projectEntity.getProjectType())){
//			DataDictionaryEntity data = this.dataDictionaryService.selectById(projectEntity.getProjectType());
//			if(data!=null){
//				projectDTO.setProjectTypeName(data.getName());
//			}
//		}

		//合同附件
		projectDTO.setContractAttachList(this.projectSkyDriverService.getProjectContractAttach(id));
//		ProjectSkyDriveEntity projectSkyDrive = this.projectSkyDriverService.getProjectContractAttach(id);
//		if(projectSkyDrive!=null){
//			projectDTO.setFileName(projectSkyDrive.getFileName());
//			projectDTO.setFilePath(fastdfsUrl+projectSkyDrive.getFileGroup()+"/"+projectSkyDrive.getFilePath());
//		}

		//项目设计范围
		List<ProjectDesignRangeEntity> projectDesignRangeEntityList = projectDesignRangeDao.getDesignRangeByProjectId(id);
		projectDTO.setProjectDesignRangeList(BaseDTO.copyFields(projectDesignRangeEntityList,ProjectDesignRangeDTO.class));

		//项目设计阶段
		List<ProjectDesignContentDTO> projectDesignContentList = projectDesignContentService.getProjectDesignContentByProjectId(id,companyId);
		projectDTO.setProjectDesignContentList(projectDesignContentList);

		//填充自定义属性
		List<CustomProjectPropertyDTO> propertyList = projectPropertyDao.listProperty(id);
		projectDTO.setProjectPropertyList(propertyList);

		return projectDTO;
	}

	/**
	 * 方法描述：根据id查询项目信息
	 * 作者：MaoSF
	 * 日期：2016/7/28
	 */

	@Override
	public Map<String, Object> getProjectDetail(String id, String companyId, String userId) throws Exception {
		//项目详情
		ProjectDTO projectDTO= this.getProjectById(id,companyId,userId);
//		//甲方
//		List<ProjectConstructDTO> constructList = projectConstructDao.getConstructByCompanyId(companyId);
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

		int editFlag = this.getProjectEditRole(id,companyId,userId);

		if (projectDTO.getStatus() != null && StringUtil.isEmpty(projectDTO.getStatusName())) {
			//todo 改为从数据库读取，目前为常量定义
			projectDTO.setStatusName(SystemParameters.PROJECT_STATUS.get(projectDTO.getStatus()));
		}

		Map<String, Object> returnMap = new HashMap<String,Object>();
		returnMap.put("projectDetail",projectDTO);
		returnMap.put("editFlag",editFlag);
	//	returnMap.put("constructList",constructList);
		returnMap.put("companyList",companyList);
		returnMap.put("operatorManager",this.projectMemberService.getOperatorManagerDTO(id,companyId));
		return returnMap;
	}

	private List<ProjectPropertyDTO> listFunction(String projectId,String builtType,boolean includeAllTemplate){
		QueryProjectDTO queryProject = new QueryProjectDTO();
		queryProject.setId(projectId);
		List<ProjectPropertyDTO> constBuiltTypeList = projectDao.listBuiltTypeConst(queryProject);
		List<ProjectPropertyDTO> customBuiltTypeList = projectDao.listBuiltTypeCustom(queryProject);
		List<ProjectPropertyDTO> list = new ArrayList<>();
		if (includeAllTemplate){
			list.addAll(constBuiltTypeList);
		} else {
			constBuiltTypeList.stream()
					.forEach(bt -> {
						//保存选中的默认功能分类列表
						if (StringUtils.contains(builtType, bt.getId())) {
							ProjectPropertyDTO functionType = createProjectPropertyDTOFrom(bt);
							;
							list.add(functionType);
						}
					});
		}
		list.addAll(customBuiltTypeList);
		return list;
	}

	private ProjectPropertyDTO createProjectPropertyDTOFrom(ProjectPropertyDTO dto){
		return dto;
	}

	/**
	 * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
	 * 作   者：LY
	 * 日   期：2016/8/8 14:38
	 */
	@Override
	public List<ProjectDTO> getProjectListByCompanyId(Map<String, Object> param){
		return projectDao.getProjectListByCompanyId(param);
	}


	/**************************************新的接口****************************************************/


	/**
	 * 方法描述：获取立项项目的基础数据（甲方和项目经营人）
	 * 作者：MaoSF
	 * 日期：2016/12/07
	 */
	@Override
	public ProjectBaseDataDTO getAddProjectOfBaseData(String companyId) throws Exception {
		ProjectBaseDataDTO projectBaseDataDTO = new ProjectBaseDataDTO();
//		//1：获取当前公司的所有甲方
		//获取当前团队项目经营人员
		Map<String,Object> map = new HashMap<>();
		map.put("permissionId","25");
		map.put("companyId",companyId);
		projectBaseDataDTO.setCompanyUserList( companyUserDao.getCompanyUserByPermissionId(map));
		return projectBaseDataDTO;
	}

	@Override
	public ProjectInfoDTO getProjectInfo(String projectId, String companyId,String accountId) throws Exception {
		ProjectInfoDTO projectInfo = new ProjectInfoDTO();

		/************项目基本信息**************/
		ProjectEntity projectEntity = this.projectDao.selectById(projectId);
		if(projectEntity == null){
			return projectInfo;
		}
		ProjectDataDTO projectDataDTO = projectInfo.getProjectDataDTO();
		BaseDTO.copyFields(projectEntity,projectDataDTO);
		//立项组织
		projectDataDTO.setCompanyName(companyDao.getCompanyName(projectEntity.getCompanyId()));

		//获取甲方名称
		if(!StringUtil.isNullOrEmpty(projectEntity.getConstructCompany())){
			projectDataDTO.setConstructCompanyName(projectDao.getEnterpriseName(projectEntity.getConstructCompany()));
		}
		//乙方公司
		if(!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid())){
			projectDataDTO.setCompanyBidName(companyDao.getCompanyName(projectEntity.getCompanyBid()));
		}
		/************项目成员**************/
		ProjectMemberDataDTO memberData = projectInfo.getMemberData();
		List<Map<String,Object>> companyList = projectTaskDao.getProjectTaskCompany(projectId);
		memberData.setOrgCount(CollectionUtils.isEmpty(companyList)?0:companyList.size());
		//成员总数
		memberData.setMemberCount(projectMemberService.getMemberCount(projectId));
		//当前公司的设计负责人和经营负责人
		List<ProjectMemberDTO> managerList = projectMemberService.listProjectManager(projectId,companyId);
		memberData.setManagerList(managerList);
		/******签发板块********/
		TaskDataDTO issueTask = projectTaskService.getProjectInfoOfTask(projectId,companyId,SystemParameters.TASK_TYPE_ISSUE);
		projectInfo.setIssueData(issueTask);
		/******生产板块********/
		projectInfo.setProduceData(projectTaskService.getProjectInfoOfTask(projectId,companyId,SystemParameters.TASK_TYPE_PRODUCT));
		/**获取费用板块的权限*/
		projectInfo.setFeeRole(this.handleNavigationRole(projectId,companyId,accountId,null));
		if(projectInfo.getFeeRole()==1){
			/**费用模块数据********/
			projectInfo.setFeeData(this.projectCostDao.getProjectAmountFee(projectId,companyId));
		}
		return projectInfo;
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
	 */
	private void detailProjectsPersonAndDesignStatus(List<V2ProjectTableDTO> data,String companyId,String accountId,String companyUserId) throws Exception{
		for(V2ProjectTableDTO dto:data){
			List<ProjectTaskDTO> list = this.projectTaskDao.getProjectTaskRootData(dto.getId(),companyId);
			for(ProjectTaskDTO dto1:list){
				this.projectTaskService.setTaskState(dto1);
			}
			dto.setTaskList(BaseDTO.copyFields(list,ProjectTaskListDTO.class));
		}
	}

	private int handleDeleteRole(String createBy,String projectCompanyId,String companyId,String accountId ) throws Exception{
		if(projectCompanyId.equals(companyId)){
			if(accountId.equals(createBy)){
				return 1;
			}
			//拥有删除项目权限
			if (permissionService.haveProjectDeletePermission(companyId, accountId)) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 方法描述：项目编辑权限
	 * 作者：MaoSF
	 * 日期：2017/3/22
	 */
	private int handleProjectEditRole(String projectCompanyId, String companyId, String accountId, String createBy) throws Exception {
		if (projectCompanyId.equals(companyId)) {//立项人
			if (accountId.equals(createBy)) {
				return 1;
			}
			//拥有删除项目权限
			if (permissionService.haveProjectEditPermission(companyId, accountId)) {
				return 1;
			}
		}
		return 0;
	}


	/**
	 * 项目群组成员
     */
	@Override
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
	 */
	@Override
	public ResponseBean saveOrUpdateProjectNew(ProjectDTO dto) throws Exception {
		String currentCompanyId = dto.getAppOrgId();
		AjaxMessage ajaxMessage = this.validateSaveOrUpdateProject(dto);
		if (!"0".equals(ajaxMessage.getCode())) {
			return ResponseBean.responseError((String) ajaxMessage.getInfo());
		}
		CompanyUserEntity currentUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
		if(currentUser==null){
			return ResponseBean.responseError("参数错误");
		}
		//如果建设单位id为null，则通过建设单位名查找或增加新的建设单位
		if(!StringUtil.isNullOrEmpty(dto.getEnterpriseId())) {
			//处理甲方
			String constructId = this.handleProjectConstruct(dto.getEnterpriseId(), dto.getCompanyId());
			dto.setConstructCompany(constructId);
		}else if(!StringUtil.isNullOrEmpty(dto.getConstructCompanyName())){
			String constructId = this.handleProjectConstructName(dto.getConstructCompanyName(), dto.getCompanyId());
			dto.setConstructCompany(constructId);
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

		boolean isAdd = false;
		if (StringUtil.isNullOrEmpty(dto.getId())) {
			isAdd = true;
			//新增项目信息
			String id = StringUtil.buildUUID();
			dto.setId(id);
			projectEntity.setId(id);
			projectEntity.setPstatus("0");
			projectEntity.setStatus("0");
			projectEntity.setCreateBy(accountId);
			saveProjectFunction(dto,projectEntity);
			projectDao.insert(projectEntity);

			//建立默认的自定义字段
			createDefaultProjectProperty(projectEntity);
			this.projectMemberService.saveProjectMember(projectEntity.getId(),projectEntity.getCompanyId(),null, ProjectMemberType.PROJECT_CREATOR,accountId,false,currentCompanyId);

			//创建默认磁盘
            this.projectSkyDriverService.createProjectFile(projectEntity);

			/*************处理设计阶段*************/
			this.handleProjectDesignContent(dto.getProjectDesignContentList(), projectEntity.getId(), dto.getCompanyId(), dto.getAccountId(),dto.getProjectManagerId(),isAdd);

			//保存经营负责人
			setProjectManagerForCompany(projectEntity.getCompanyId(), projectEntity.getId(), "51", accountId, true,currentCompanyId);
			//保存设计负责人
			setProjectManagerForCompany(projectEntity.getCompanyId(), projectEntity.getId(), "52", accountId, true,currentCompanyId);

			//发送动态消息
			//this.setProjectDynamicsByProject(projectEntity);
			this.noticeService.saveNoticeForProject(projectEntity.getId(), projectEntity.getCompanyId(), projectEntity.getCreateBy(), currentUser.getId(), dto.getProjectDesignContentList());

		} else {//修改
			ProjectEntity projectEntity1 = this.projectDao.selectById(dto.getId());
			companyBid = projectEntity1.getCompanyBid();
			projectEntity.setUpdateBy(accountId);
			saveProjectFunction(dto,projectEntity);
			projectDao.updateById(projectEntity);//更新全部字段
			/*************处理设计阶段*************/
			//查询经营负责人
			ProjectMemberEntity manager = this.projectMemberService.getDesignManager(projectEntity.getId(),projectEntity.getCompanyId());
			String managerId=null;
			if(manager!=null){
				managerId = manager.getCompanyUserId();
			}
			this.handleProjectDesignContent(dto.getProjectDesignContentList(), dto.getId(), dto.getCompanyId(), dto.getAccountId(),managerId,isAdd);

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
					List<String> userIds = Lists.newArrayList();
					setProjectManagerForCompany(dto.getCompanyBid(),projectEntity.getId(),"51",accountId, false,currentCompanyId);
					setProjectManagerForCompany(dto.getCompanyBid(),projectEntity.getId(),"52",accountId, false,currentCompanyId);
					this.sendMessageForPartB(projectEntity.getId(),dto.getCompanyBid(),dto.getCurrentCompanyId(),dto.getAccountId(),userIds);
				}
			}

		}
		if(partBFlag==3){//注释：郭志彬 乙方是其他组织，并且没有改变乙方组织，但是改变了乙方的经营负责人，这里也需要处理经营负责人变更，因此把partBFlag==3加上
			//保存经营负责人
				this.saveProjectManagerForPartB(projectEntity.getId(),dto.getCompanyBid(),dto.getPartBManagerId(),dto.getPartBDesignerId(),dto.getAccountId(),currentCompanyId);
		}
		//处理协同
		String projectId = dto.getId();
		/***************end******************/
		//先删除设计范围
		projectDesignRangeDao.deleteDRangeByProjectId(projectId);
		//保存设计范围
//		saveProjectRange(dto,projectEntity);
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
	 * @author  张成亮
	 * @date    2018/7/9
	 * @description     保存功能分类, 目前使用字符串形式，以后再改为项目属性列表形式
	 **/
	private ProjectEntity saveProjectFunction(ProjectDTO dto,ProjectEntity projectEntity){
		//组合功能分类保存字符串
		if ((dto.getChangedFunctionList() != null) && (dto.getChangedFunctionList().size() > 0)) {
			List<ProjectPropertyDTO> changedFunctionList = dto.getChangedFunctionList();
			StringBuilder builtTypeIdStr = new StringBuilder();
			changedFunctionList.stream()
					.forEach(bt->{
						if (StringUtils.isEmpty(bt.getId())) {
							UpdateConstDTO request = new UpdateConstDTO();
							request.setProjectId(dto.getId());
							request.setTitle(bt.getName());
							request.setClassicId(ConstService.CONST_TYPE_BUILT_TYPE);
							String id = constService.insertConst(request);
							builtTypeIdStr.append(id).append(",");
						} else if (isSelected(bt)) {
							builtTypeIdStr.append(bt.getId()).append(",");
							if (isSelected(bt)) {
								UpdateConstDTO request = new UpdateConstDTO();
								request.setId(bt.getId());
								request.setTitle(bt.getName());
								constService.updateConst(request);
							}
						} else if (isTemplate(bt)){
							constService.deleteConst(bt.getId());
						}
					});
			projectEntity.setBuiltType(builtTypeIdStr.toString());
		}
		return projectEntity;
	}

	private boolean isSelected(ProjectPropertyDTO pty){
		return "1".equals(pty.getIsSelected());
	}

	private boolean isTemplate(ProjectPropertyDTO pty){
		return "1".equals(pty.getIsTemplate());
	}

	/**
	 * @author  张成亮
	 * @date    2018/7/9
	 * @description     保存设计范围, 目前保存到设计范围列表，以后会改为保存到项目属性列表
	 **/
	private ProjectEntity saveProjectRange(ProjectDTO dto,ProjectEntity projectEntity){
		if ((dto.getChangedRangeList() != null) && (dto.getChangedRangeList().size() > 0)) {
			List<ProjectPropertyDTO> changedRangeList = dto.getChangedRangeList();
			StringBuilder builtTypeIdStr = new StringBuilder();
			int maxSeq = 1;
			changedRangeList.stream()
					.forEach(bt->saveRange(bt,projectEntity,dto.getAccountId(),maxSeq));
		}
		return projectEntity;
	}

	private void saveRange(ProjectPropertyDTO property,ProjectEntity projectEntity,String accountId,int maxSeq){
		//添加自定义项
		if (StringUtils.isEmpty(property.getId())) {
			UpdateConstDTO request = new UpdateConstDTO();
			request.setProjectId(projectEntity.getId());
			request.setTitle(property.getName());
			request.setClassicId(ConstService.CONST_TYPE_BUILT_RANGE);
			String id = constService.insertConst(request);
			ProjectDesignRangeEntity projectDesignRangeEntity = new ProjectDesignRangeEntity();
			projectDesignRangeEntity.setId(id);
			try {
				BaseDTO.copyFields(property, projectDesignRangeEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			projectDesignRangeEntity.setProjectId(projectEntity.getId());
			projectDesignRangeEntity.setCreateBy(accountId);
			projectDesignRangeEntity.setUpdateBy(accountId);
			projectDesignRangeEntity.setSeq(maxSeq++);
			projectDesignRangeDao.insert(projectDesignRangeEntity);
		} else if (isSelected(property)) {
			if (isTemplate(property)) {
				UpdateConstDTO request = new UpdateConstDTO();
				request.setId(property.getId());
				request.setTitle(property.getName());
				constService.updateConst(request);
			}
			ProjectDesignRangeEntity projectDesignRangeEntity = projectDesignRangeDao.selectById(property.getId());
			projectDesignRangeEntity.setStatus("0");
			projectDesignRangeEntity.setDesignRange(property.getName());
			projectDesignRangeDao.updateById(projectDesignRangeEntity);
		} else if (isTemplate(property)){
			constService.deleteConst(property.getId());
			projectDesignRangeDao.deleteById(property.getId());
		}
	}

	/**
	 * 方述：建立新增项目的默认自定义属性
	 * 作者：ZCL
	 * 日期：2017/9/6
	 *
	 * @param project 要添加的项目对象
	 */
	public void createDefaultProjectProperty(ProjectEntity project) {
		List<CustomProjectPropertyDTO> companyPropertyList = companyPropertyDao.listCompanyProperty(project.getCompanyId());
		if (CollectionUtils.isEmpty(companyPropertyList)) {
			companyPropertyList = createDefaultList(project.getCompanyId());
		}
		if (!CollectionUtils.isEmpty(companyPropertyList)) {
			projectPropertyDao.insertDefaultProperty(companyPropertyList, project.getId(), project.getCreateBy());
		}
	}

	/**
	 * 方法：添加公司默认的自定义属性，并返回公司默认自定义属性列表
	 * 作者：Zhangchengliang
	 * 日期：2017/8/17
	 */
	private List<CustomProjectPropertyDTO> createDefaultList(String companyId) {
		List<CustomProjectPropertyDTO> defaultList = new ArrayList<>();
		List<CompanyPropertyEntity> commonDefaultList = companyPropertyDao.listCommonDefaultProperty();
		if (!CollectionUtils.isEmpty(commonDefaultList)) {
			companyPropertyDao.insertDefaultProperty(companyId, commonDefaultList);
			for (CompanyPropertyEntity entity : commonDefaultList) {
				CustomProjectPropertyDTO dto = new CustomProjectPropertyDTO(entity);
				defaultList.add(dto);
			}
		}
		return defaultList;
	}

	/**
	 * 设置某公司某项目的管理者
	 * @param companyId 公司编号
	 * @param projectId 项目编号
	 * @param permissionId 权限编号，经营负责人：51，设计负责人：52
	 * @param accountId 立项人用户编号
	 * @param isCreator 是否立项方
	 */
	private String setProjectManagerForCompany(String companyId, String projectId, String permissionId, String accountId, Boolean isCreator,String currentCompanyId) throws Exception {
		ProjectMemberEntity pm2 = (isSettingIssueManager(permissionId) ?
				projectMemberService.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_OPERATOR_MANAGER,null) :
				projectMemberService.getProjectMember(projectId,companyId,ProjectMemberType.PROJECT_DESIGNER_MANAGER,null));
		String userId = null;
		String companyUserId = null;
		if (pm2 == null) {
			//签发到的公司中选择具备相应权限的人员中选择第一个填入项目经营负责人位置
			Map<String, Object> map = new HashMap<>();
			map.put("permissionId", permissionId);//相应权限id
			map.put("companyId", companyId);

			List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if (companyUserList.size() > 0) {
				CompanyUserDataDTO cut = null;
				if(isCreator){
					for(CompanyUserDataDTO userTableDTO:companyUserList){
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
					userId = cue.getUserId();
					companyUserId = cue.getId();
				}
			}
			if ((companyUserId == null) || (userId == null)) return null;
			//给其推送任务或消息
			if (isSettingIssueManager(permissionId)) {
				if(isCreator){//此处加上 && isCreator 区分立项还是乙方设置，添加乙方经营负责人不需要推送任务
					this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,userId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,!accountId.equals(userId),currentCompanyId);
				}else {//如果是乙方，则只推送消息
					//只推送消息
					//this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,!isCreator);
					this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,false,currentCompanyId);
				}
			} else {
				//如果立项人不是设计负责人时，才推送消息
				//this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,( !isCreator ||  !accountId.equals(userId)));
				this.projectMemberService.saveProjectMember(projectId,companyId,companyUserId,(isSettingIssueManager(permissionId) ? ProjectMemberType.PROJECT_OPERATOR_MANAGER : ProjectMemberType.PROJECT_DESIGNER_MANAGER),accountId,false,currentCompanyId);
			}
		}

		return userId;
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
//		if(CollectionUtils.isEmpty(dto.getProjectDesignContentList())){
//			return new AjaxMessage().setCode("1").setInfo("设计阶段不能为空");
//		}

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
	private void handleProjectDesignContent(List<ProjectDesignContentDTO> dtoList, String projectId, String companyId, String accountId,String managerId,boolean isAdd) throws Exception {
		int seq = 1;
		String notDeleteIds = "";
		List<ProjectDesignContentEntity> oldTaskList = this.projectDesignContentDao.getProjectDesignContentByProjectId(projectId);
		for (ProjectDesignContentDTO contentDTO : dtoList) {

			contentDTO.setAppOrgId(companyId);
			//处理设计阶段信息
			if (StringUtil.isNullOrEmpty(contentDTO.getId())) {
				//如果是新增，则需要保持设计内容
				if(isAdd){
					saveTask(projectId,companyId,contentDTO.getContentName(),(seq++),contentDTO.getStartTime(),contentDTO.getEndTime(),accountId);
				}
				//保持设计阶段信息
				saveDesignContent(projectId,companyId,contentDTO.getContentName(),(seq++),contentDTO.getStartTime(),contentDTO.getEndTime(),accountId);
			} else {
				for (ProjectDesignContentEntity entity : oldTaskList) {
					if (entity.getId().equals(contentDTO.getId())) {
						ProjectDesignContentEntity contentEntity= this.projectDesignContentDao.selectById(entity.getId());
                        //如果有时间则添加到ProjectProcessTimeEntity中
                        if(contentDTO.getFlag()==1 && !StringUtil.isNullOrEmpty(contentDTO.getStartTime()) && !StringUtil.isNullOrEmpty(contentDTO.getEndTime())) {
                            this.saveProjectTime(contentDTO.getStartTime(),contentDTO.getEndTime(),entity.getId(),companyId,contentDTO.getMemo());
                        }
						contentEntity.setContentName(contentDTO.getContentName());
						contentEntity.setSeq((seq++));
						this.projectDesignContentDao.updateById(contentEntity);
						notDeleteIds += entity.getId() + ",";
					}
				}
			}
		}
		//删除以前，而现在没有的
		for (ProjectDesignContentEntity entity : oldTaskList) {
			if (notDeleteIds.indexOf(entity.getId()) < 0) {
			    //把设计阶段下面的所以任务都设置为无效
				projectDesignContentDao.deleteById(entity.getId());
				messageDao.deleteMessage(entity.getId());
			}
		}
	}


	//保存任务信息（首次任务Id为阶段Id）
	//保存签发
	private String saveTask(String projectId, String companyId, String taskName, int seq, String startTime, String endTime, String accountId) throws Exception {
		ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
		projectTaskEntity.setId(StringUtil.buildUUID());
		projectTaskEntity.setFromCompanyId(companyId);
		projectTaskEntity.setCompanyId(companyId);
		projectTaskEntity.setProjectId(projectId);
		projectTaskEntity.setSeq(seq);
		projectTaskEntity.setTaskLevel(1);
		projectTaskEntity.setTaskName(taskName);
		projectTaskEntity.setTaskStatus(SystemParameters.TASK_STATUS_MODIFIED);//新增的时候为未发布状态
		projectTaskEntity.setTaskPath(projectTaskEntity.getId());
		projectTaskEntity.setTaskType(SystemParameters.TASK_TYPE_MODIFY);
		projectTaskEntity.setStartTime(startTime);
		projectTaskEntity.setEndTime(endTime);
		projectTaskEntity.setCreateBy(accountId);
		projectTaskEntity.setIsOperaterTask(0);
		this.projectTaskService.insert(projectTaskEntity);
		//如果时间不为null的话
		if(!StringUtil.isNullOrEmpty(startTime) || !StringUtil.isNullOrEmpty(endTime)){
			this.saveProjectTime(startTime,endTime,projectTaskEntity.getId(),companyId,null);
		}
		return projectTaskEntity.getId();
	}

	//保存任务信息（首次任务Id为阶段Id）
	//项目信息保存
	private String saveDesignContent(String projectId, String companyId, String taskName, int seq, String startTime, String endTime, String accountId) throws Exception {
		ProjectDesignContentEntity projectDesignContentEntity=new ProjectDesignContentEntity();
		projectDesignContentEntity.setId(StringUtil.buildUUID());
		projectDesignContentEntity.setProjectId(projectId);
		projectDesignContentEntity.setCompanyId(companyId);
		projectDesignContentEntity.setSeq(seq);
		projectDesignContentEntity.setContentName(taskName);
		this.projectDesignContentDao.insert(projectDesignContentEntity);
		//如果时间不为null的话
		if(!StringUtil.isNullOrEmpty(startTime) || !StringUtil.isNullOrEmpty(endTime)){
			this.saveProjectTime(startTime,endTime,projectDesignContentEntity.getId(),companyId,null);
		}
		return projectDesignContentEntity.getId();
	}


	//保存设定的设计阶段的时间
	private void saveProjectTime(String startTime,String endTime,String targetId,String companyId,String memo){
		ProjectProcessTimeEntity projectProcessTimeEntity = new ProjectProcessTimeEntity();
		projectProcessTimeEntity.setTargetId(targetId);
		projectProcessTimeEntity.setId(StringUtil.buildUUID());
		projectProcessTimeEntity.setType(1);
		projectProcessTimeEntity.setCompanyId(companyId);
		projectProcessTimeEntity.setStartTime(startTime);
		projectProcessTimeEntity.setEndTime(endTime);
		projectProcessTimeEntity.setMemo(memo);
		projectProcessTimeDao.insert(projectProcessTimeEntity);
	}


	private void saveProjectManagerForPartB(String projectId,String companyId,String managerId,String designerId,String accountId,String currentCompanyId) throws Exception {
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
				this.projectMemberService.saveProjectMember(projectId,companyId,managerId,null,ProjectMemberType.PROJECT_OPERATOR_MANAGER,accountId,currentCompanyId);
			}
		}
	}


	/**
	 * 方法描述：处理甲方
	 * 作者：MaoSF
	 * 日期：2016/12/29
	 * constructCompanyId:从工商局获取的id（长度36位）
	 */
	private String handleProjectConstruct(String constructCompanyId,String companyId) throws Exception {
		EnterpriseSearchQueryDTO query = new EnterpriseSearchQueryDTO();
		query.setEnterpriseId(constructCompanyId);
		query.setCompanyId(companyId);
		query.setSave(true);
		Response res = null;
		try {
			res = OkHttpUtils.postJson(enterpriseServer.getQueryDetail(), query);
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			logger.error("imServerRequest 请求工商数据异常", e);
			return null;
		}
		if (res.isSuccessful()) {
			try {
				ApiResult<Map<String,Object>> apiResult = JsonUtils.json2pojo(res.body().string(), ApiResult.class);
				ResponseBean responseBean = new ResponseBean();
				responseBean.setError(apiResult.getCode());
				responseBean.setMsg(apiResult.getMsg());
				String enterpriseOrgId = (String)apiResult.getData().get("enterpriseOrgId");
				return enterpriseOrgId;
			} catch (Exception e) {
				logger.error("imServerRequest 解析返回结果失败", e);
				e.printStackTrace();
			}
		} else {
			logger.info("imServerRequest 请求工商数据失败：" + res.message());
		}
		return null;
	}

	/**
	 * 方法描述：处理甲方
	 * 作者：MaoSF
	 * 日期：2016/12/29
	 * constructCompanyId:从工商局获取的id（长度36位）
	 */
	private String handleProjectConstructName(String constructCompanyName,String companyId) throws Exception {
		EnterpriseSearchQueryDTO query = new EnterpriseSearchQueryDTO();
		query.setName(constructCompanyName);
		query.setCompanyId(companyId);
		query.setSave(true);
		Response res = null;
		try {
			res = OkHttpUtils.postJson(enterpriseServer.getQueryFull(), query);
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			//logger.error("imServerRequest 请求工商数据异常", e);
			return null;
		}
		if (res.isSuccessful()) {
			try {
				ApiResult<Map<String,Object>> apiResult = JsonUtils.json2pojo(res.body().string(), ApiResult.class);
				ResponseBean responseBean = new ResponseBean();
				responseBean.setError(apiResult.getCode());
				responseBean.setMsg(apiResult.getMsg());
				String enterpriseOrgId = (String)apiResult.getData().get("enterpriseOrgId");
				return enterpriseOrgId;
			} catch (Exception e) {
				//logger.error("imServerRequest 解析返回结果失败", e);
				e.printStackTrace();
			}
		} else {
			//logger.info("自定义群失败：" + res.message());
		}
		return null;
	}

	/**
	 * 项目参与组织
	 */
	@Override
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
	 * 是否具有经营负责人 or 财务 or 企业负责人的权限
	 */
	private int handleNavigationRole(String projectId, String companyId,String accountId, String companyUserId) throws Exception{
		ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, companyId);
		if (managerEntity != null && managerEntity.getCompanyUserId().equals(companyUserId)) {
			return 1;
		}
		if (managerEntity != null && managerEntity.getAccountId().equals(accountId)) {
			return 1;
		}
		//判断是否具有财务和企业负责人的权限
		if(this.permissionService.isFinancial(companyId,accountId)){
			return 1;
		}
		return 0;
	}

	@Override
	public List<ProjectProgressDTO> getMyProjectList(Map<String, Object> param) {
    	if(StringUtil.isNullOrEmpty("companyId")){
    		param.put("companyId",param.get("appOrgId"));
		}
    	List<ProjectProgressDTO> list = projectDao.getMyProjectList(param);
    	for(ProjectProgressDTO dto:list){
    		Map<String,Object> map = new HashMap<>();
			map.put("projectId",dto.getId());
			map.put("companyId",param.get("companyId"));
    		if(dto.getRelationType()==3){//如果是立项方或是乙方，则查询所有任务(getRelationType = 1,2),如果是合作组织，则只查询与自己相关的任务(getRelationType = 3)
				map.put("isCooperator","1");
			}
			ProjectProgressDTO taskCountDTO = myTaskDao.getProjectTaskCount(map);
    		if(taskCountDTO!=null){
				dto.setCompleteCount(taskCountDTO.getCompleteCount());
				dto.setTaskCount(taskCountDTO.getTaskCount());
			}
		}
		return list;
	}

	@Override
	public List<ProjectSimpleDataDTO> getProjectListForLaborHour(Map<String, Object> param) throws Exception{
    	String accountId = (String)param.get("accountId");
    	String companyId = (String)param.get("appOrgId");
    	CompanyUserEntity companyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
		assert (companyUser != null) : "参数错误";
    	param.put("companyId",companyId);
		param.put("companyUserId",companyUser.getId());
		return projectDao.getProjectListForLaborHour(param);
	}

	@Override
	public HomeDTO getProjectForHome(Map<String, Object> param) throws Exception {
		HomeDTO data = new HomeDTO();
		String companyId = (String)param.get("appOrgId");
		String accountId = (String)param.get("accountId");
		CompanyUserEntity userEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
		if(userEntity==null){
			return data;
		}
		Map<String,Object> map = new HashMap<>();
		//获取当前人的角色，是否是企业负责人，经营负责人，设计负责人
		//判断是否具有财务和企业负责人的权限
		map.put("companyId", companyId);
		map.put("userId",accountId);
		map.put("companyUserId",userEntity.getId());
		int isHasOrg = this.permissionService.getPermissionOperator(map).getAllProjectView();
		map.clear();
		map.put("companyId", companyId);
		map.put("companyUserId",userEntity.getId());
		map.put("handlerId",userEntity.getId());
		HomeDTO projectCountData = projectDao.getProjectCountForHomeData(map);
		if(projectCountData!=null){
			data = projectCountData;
		}
		if(isHasOrg>0){
			data.setPermissionFlag(1);
		}
		return data;
	}

	@Override
	public int getProjectEditRole(String projectId, String companyId, String accountId) throws Exception{
    	ProjectEntity project = this.projectDao.selectById(projectId);
    	if(project==null){
    		return 0;
		}
		if(project.getCompanyId().equals(companyId)){
			if(accountId.equals(project.getCreateBy())){
				return 1;
			}
			ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId, project.getCompanyId());
			if (managerEntity != null && managerEntity.getAccountId().equals(accountId)) {
				return 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			//项目基本信息编辑
			map.put("permissionId", "20");//项目基本信息编辑人权限id
			map.put("companyId", project.getCompanyId());
			List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if (!CollectionUtils.isEmpty(companyUserList)) {
				for(CompanyUserDataDTO dto:companyUserList){
					if(dto.getUserId().equals(accountId)){
						return 1;
					}
				}
			}
		}
		return 0;
	}

	private void sendMessageForPartB(String projectId,String companyId,String currentCompanyId,String accountId, List<String> userIds) throws Exception{
		//推送消息给乙方经营负责人，设计负责人，企业负责人
		MessageEntity m = new MessageEntity();
		m.setMessageType(SystemParameters.MESSAGE_TYPE_PART_B);
		m.setProjectId(projectId);
		m.setUserId(null);
		m.setCompanyId(companyId);
		m.setCreateBy(accountId);
		m.setSendCompanyId(currentCompanyId);
		for(String userId:userIds){//经营负责人+设计负责人
			if(userId==null){
				continue;
			}
			m.setUserId(userId);
			this.messageService.sendMessage(m);
		}
		//查询企业负责人
		//企业负责人
		Map<String,Object> map = new HashMap<>();
		map.put("permissionId", SystemParameters.ORG_MANAGER_PERMISSION_ID);//企业负责人权限id
		map.put("companyId", companyId);
		List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
		if(!CollectionUtils.isEmpty(companyUserList)){
			m.setUserId(companyUserList.get(0).getUserId());
			this.messageService.sendMessage(m);
		}
	}


	/**
	 * 方法：根据id查询项目自定义字段详细信息
	 * 作者：Zhangchengliang
	 * 日期：2017/8/17
	 *
	 * @param query 查询条件
	 */
	@Override
	public CustomProjectPropertyEditDTO loadProjectCustomFields(ProjectCustomFieldQueryDTO query) throws Exception {
		CustomProjectPropertyEditDTO result = new CustomProjectPropertyEditDTO();

		//公司默认的自定义属性列表
		List<CustomProjectPropertyDTO> defaultList = companyPropertyDao.listDefaultProperty(query.getCompanyId());
		if (!CollectionUtils.isEmpty(defaultList)) {
			result.setBasicPropertyList(defaultList);
		} else {
			defaultList = createDefaultList(query.getCompanyId());
			if (!CollectionUtils.isEmpty(defaultList)) {
				result.setBasicPropertyList(defaultList);
			}
		}

		//单位列表
		List<Map<String,String>> unitNameList = companyPropertyDao.listUnitName();
		if (!CollectionUtils.isEmpty(unitNameList)) {
			result.setUnitNameList(unitNameList);
		}

		//公司可编辑的自定义属性列表
		List<CustomProjectPropertyDTO> customList = companyPropertyDao.listCustomProperty(query.getCompanyId());
		if (!CollectionUtils.isEmpty(customList)) {
			result.setCustomPropertyList(customList);
		}

		//项目使用到的自定义属性列表
		List<CustomProjectPropertyDTO> selectedList = projectPropertyDao.listProperty(query.getProjectId());
		if (!CollectionUtils.isEmpty(selectedList)) {
			for(CustomProjectPropertyDTO param:defaultList){
				for(CustomProjectPropertyDTO value:selectedList){
					boolean isSelected = this.isSelectedProjectCustomFields(value,param);
					if(isSelected){
						param.setIsSelected("1");
						break;
					}
				}
			}
			for(CustomProjectPropertyDTO param:customList){
				for(CustomProjectPropertyDTO value:selectedList){
					boolean isSelected = this.isSelectedProjectCustomFields(value,param);
					if(isSelected){
						param.setIsSelected("1");
						break;
					}
				}
			}
			result.setSelectedPropertyList(selectedList);
		}
		return result;
	}

	private boolean isSelectedProjectCustomFields(CustomProjectPropertyDTO value,CustomProjectPropertyDTO param){
		//m&sup2;平方米  m&sup3;立方米

		String unitName = "m²".equals(value.getUnitName())?"m&sup2;":value.getUnitName();
		String unitName2 = "m²".equals(param.getUnitName())?"m&sup2;":param.getUnitName();
		unitName = "m³".equals(unitName)?"m&sup3;":unitName;
		unitName2 = "m³".equals(unitName2)?"m&sup3;":unitName2;

		if(param.getFieldName().equals(value.getFieldName())){
			if(StringUtil.isNullOrEmpty(unitName) && StringUtil.isNullOrEmpty(unitName2)){
				return true;
			}
			if(!(StringUtil.isNullOrEmpty(unitName) && StringUtil.isNullOrEmpty(unitName2))
					&& unitName.equals(unitName2)){
				return true;
			}

		}
		return false;
	}

	/**
	 * 方法：保存项目自定义字段详细信息，包括公司自定义属性
	 * 作者：Zhangchengliang
	 * 日期：2017/8/17
	 *
	 * @param properties 自定义界面上的数据
	 */
	@Override
	public void saveProjectCustomFields(ProjectPropertyEditDTO properties) throws Exception {
		String companyId = properties.getAppOrgId();
		//todo 先查询项目原有的数据
		List<CustomProjectPropertyDTO> customList = companyPropertyDao.listCustomProperty(companyId);
		List<CustomProjectPropertyDTO> selectedList = projectPropertyDao.listProperty(properties.getProjectId());

		//保存公司自定义字段
		if (!CollectionUtils.isEmpty(properties.getProjectPropertyList())) {
			Integer n = 0;
			for (CustomProjectPropertyDTO property : properties.getProjectPropertyList()) {
				if (StringUtil.isNullOrEmpty(property.getId())) {
					n++;
					saveCompanyProperty(property, n, companyId);
					if ("1".equals(property.getIsSelected())) {
						saveProjectProperty(property, n, properties.getProjectId(), properties.getAccountId());
					}
				} else {
					boolean isSave = true;
					for (CustomProjectPropertyDTO selected : selectedList) {
						if (isSelectedProjectCustomFields(selected, property)) {
							isSave = false;
							if (!"1".equals(property.getIsSelected())) {//如果原来存在，现在不选中，则从项目中删除
								projectPropertyDao.fakeDeleteById(selected.getId());
								break;
							}
						}
					}
					if (isSave && "1".equals(property.getIsSelected())) {
						saveProjectProperty(property, n, properties.getProjectId(), properties.getAccountId());
					}
				}
			}
			//删除当前界面被删除的数据
			for(CustomProjectPropertyDTO selected:customList){
				boolean isDeleted = true;
				for (CustomProjectPropertyDTO property : properties.getProjectPropertyList()) {
					if (isSelectedProjectCustomFields(selected, property)) {
						isDeleted = false;
						break;
					}
				}
				if(isDeleted){
					//从custom中删除
					companyPropertyDao.fakeDeleteById(selected.getId());
				}
			}
		}
	}

	@Override
	public void saveProjectProfessionFields(ProjectPropertyEditDTO properties) throws Exception {
		if (!CollectionUtils.isEmpty(properties.getProjectPropertyList())) {
			for (CustomProjectPropertyDTO property : properties.getProjectPropertyList()) {
				ProjectPropertyEntity entity = new ProjectPropertyEntity();
				entity.setId(property.getId());
				entity.setFieldValue(StringUtil.isNullOrEmpty(property.getFieldValue())?"":property.getFieldValue());
				projectPropertyDao.updateById(entity);
			}
		}
	}

	private String saveCompanyProperty(CustomProjectPropertyDTO property,Integer seq,String companyId){
		CompanyPropertyEntity entity = new CompanyPropertyEntity(property);
		entity.setCompanyId(companyId);
		entity.setBeDefault(false);
		entity.setBeSelected(false);
		entity.setSequencing(seq);
		entity.resetId();
		entity.resetCreateDate();
		companyPropertyDao.insert(entity);
		return entity.getId();
	}

	private String saveProjectProperty(CustomProjectPropertyDTO property,Integer seq,String projectId,String operator){
		ProjectPropertyEntity entity = new ProjectPropertyEntity(property);
		entity.setProjectId(projectId);
		entity.setDeleted(new Short("0"));
		entity.setSequencing(seq);
		entity.setCreateBy(operator);
		entity.resetId();
		entity.resetCreateDate();
		projectPropertyDao.insert(entity);
		return entity.getId();
	}
}
