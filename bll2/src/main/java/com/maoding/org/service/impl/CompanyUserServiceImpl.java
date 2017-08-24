package com.maoding.org.service.impl;

import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.SecretUtil;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.dao.ImGroupDao;
import com.maoding.hxIm.entity.ImGroupEntity;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dao.*;
import com.maoding.org.dto.*;
import com.maoding.org.entity.*;
import com.maoding.org.service.CompanyUserService;
import com.maoding.projectmember.service.ProjectMemberService;

import com.maoding.role.dao.UserPermissionDao;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyUserServiceImpl
 * 类描述：组织（公司）人员 ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("companyUserService")
public class CompanyUserServiceImpl extends GenericService<CompanyUserEntity>  implements CompanyUserService{
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SmsSender smsSender;
	
	@Autowired
    private CompanyUserDao companyUserDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProjectMemberService projectMemberService;

	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private OrgUserDao orgUserDao;

	@Autowired
	private CompanyUserAuditDao companyUserAuditDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private DepartDao departDao;

	@Autowired
	private UserPermissionDao userPermissionDao;

	@Autowired
	private CollaborationService collaborationService;

	@Autowired
	private ImService imService;

	@Autowired
	private ImGroupDao imGroupDao;

	@Value("${server.url}")
	protected String serverUrl;

	@Value("${android.url}")
	protected String androidUrl;

	@Value("${ios.url}")
	protected String iosUrl;

	@Value("${fastdfs.url}")
	protected String fastdfsUrl;
	
	@Override
	public CompanyUserEntity getCompanyUserByUserIdAndCompanyId(String userId, String companyId) throws Exception {
		return companyUserDao.getCompanyUserByUserIdAndCompanyId(userId, companyId);
	}

	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	public List<CompanyUserEntity> getCompanyUserByCompanyId(String companyId){
		return companyUserDao.getCompanyUserByCompanyId(companyId);
	}


	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public CompanyUserTableDTO getCompanyUserById(String id) throws Exception {
		CompanyUserTableDTO dto = companyUserDao.getCompanyUserById(id);
		List<UserDepartDTO> departList = companyUserDao.getCompanyUserDepartRole(dto.getUserId(),dto.getCompanyId());
		dto.setDepartList(departList);
		return dto;
	}

	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public CompanyUserTableDTO getCompanyUserByIdInterface(String id) throws Exception {
		CompanyUserTableDTO dto = companyUserDao.getCompanyUserById(id);
		List<UserDepartDTO> departList = companyUserDao.getCompanyUserDepartRoleInterface(dto.getUserId(),dto.getCompanyId());
		dto.setDepartList(departList);
		return dto;
	}


	/**
	 * 方法描述：组织人员查询（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	@Override
	public List<CompanyUserTableDTO> getCompanyUserByOrgIdOfAdmin(Map<String, Object> param) throws Exception {
		if(null!=param.get("pageNumber")){
			int page=(Integer)param.get("pageNumber");
			int pageSize=(Integer) param.get("pageSize");
			param.put("startPage",page*pageSize);
			param.put("endPage",pageSize);
		}
		List<CompanyUserTableDTO> list = companyUserDao.getCompanyUserByOrgIdOfAdmin(param);

		for(CompanyUserTableDTO c:list){
			List<UserDepartDTO> departList = companyUserDao.getCompanyUserDepartRole(c.getUserId(),c.getCompanyId());
			c.setDepartList(departList);
		}
		return list;
	}


	/**
	 * 方法描述：组织人员查询（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	@Override
	public List<CompanyUserTableDTO> getCompanyUserByOrgIdOfWork(Map<String, Object> param) throws Exception {
		if(null!=param.get("pageNumber")){
			int page=(Integer)param.get("pageNumber");
			int pageSize=(Integer) param.get("pageSize");
			param.put("startPage",page*pageSize);
			param.put("endPage",pageSize);
		}
		List<CompanyUserTableDTO> list = companyUserDao.getCompanyUserByOrgIdOfWork(param);

		for(CompanyUserTableDTO c:list){
			List<UserDepartDTO> departList = companyUserDao.getCompanyUserDepartRole(c.getUserId(),c.getCompanyId());
			c.setDepartList(departList);
		}
		return list;
	}

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId)【orgId组织Id，companyId 公司Id】
	 */
	public List<CompanyUserDataDTO> getUserByOrgId (Map<String,Object> param) throws Exception{
		return companyUserDao.getUserByOrgId(param);
	}

	/**
	 * 方法描述：组织人员条数（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 *
	 * @param param (orgId,keyword)【orgId必传】
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCompanyUserByOrgIdCountOfWork(Map<String, Object> param) throws Exception {
		return companyUserDao.getCompanyUserByOrgIdCountOfWork(param);
	}

	/**
	 * 方法描述：验证添加人员（saveCompanyUser）
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-上午11:24:22
	 * @param dto
	 * @return
	 */
	public AjaxMessage validateSaveCompanyUser(CompanyUserTableDTO dto){
		if (StringUtil.isNullOrEmpty(dto.getCompanyId())){
			return new AjaxMessage().setCode("1").setInfo("请选择组织");//20161215组织
		}
		CompanyEntity company = companyDao.selectById(dto.getCompanyId());
		if(company==null){
			return new AjaxMessage().setCode("1").setInfo("请选择组织");//20161215组织
		}
		if (StringUtil.isNullOrEmpty(dto.getCompanyId())){
			return new AjaxMessage().setCode("1").setInfo("请选择组织");//20161215组织
		}

		if(StringUtil.isNullOrEmpty(dto.getCellphone())){
			return new AjaxMessage().setCode("1").setInfo("手机号不能为空");
		}
		if(StringUtil.isNullOrEmpty(dto.getUserName())){
			return new AjaxMessage().setCode("1").setInfo("姓名不能为空");
		}
		if(null !=dto.getDepartList() && dto.getDepartList().size()>0){
			for(int i = 0;i<dto.getDepartList().size();i++){
				if(StringUtil.isNullOrEmpty(dto.getDepartList().get(i).getDepartId())){
					return new AjaxMessage().setCode("1").setInfo("部门不能为空");
				}
			}
		}
		return new AjaxMessage().setCode("0");
	}

	@Override
	public AjaxMessage saveCompanyUser(CompanyUserTableDTO dto) throws Exception{
		AjaxMessage ajax = validateSaveCompanyUser(dto);
		if(!"0".equals(ajax.getCode())){
			return ajax;
		}
		AccountEntity account= null;
		String cellphone=dto.getCellphone();
		CompanyUserEntity cUser =null;
		/************如果没有存公司，首先胖的是否已经注册，若没有注册，先注册，然后加入公司，如果注册，直接加入公司*************/
		if(dto.getId()==null){//新增人
			account=accountDao.getAccountByCellphoneOrEmail(cellphone);
			boolean isNewUser=false;
			//没有人员，先注册
			if(account==null){
				account = this.register(dto);
				isNewUser= true;
			}//账号已经存在

			cUser = this.getCompanyUserByUserIdAndCompanyId(account.getId(), dto.getCompanyId());

			if(cUser !=null){//判断是否已经存在公司
				cUser.setAuditStatus("1");
				cUser.setUpdateBy(dto.getAccountId());
				cUser.setUserName(dto.getUserName());
				cUser.setUserId(account.getId());
				cUser.setEntryTime(dto.getEntryTime());
				cUser.setEmail(dto.getEmail());
				cUser.setPhone(dto.getPhone());
				/****************更新******************/
				companyUserDao.updateById(cUser);
			}
			//入股不存在该公司
			if(cUser ==null){
				/****************添加公司人员*****************/
				cUser = new CompanyUserEntity();
				BaseDTO.copyFields(dto, cUser);
				cUser.setId(StringUtil.buildUUID());
				cUser.setUserId(account.getId());
				cUser.setAuditStatus("1");
				cUser.setRelationType("2");//受邀请
				cUser.setCreateBy(dto.getAccountId());
				this.saveCompanyUser(cUser,dto.getCellphone());
			}
			//处理部门
			this.handleDepartForAdd(dto,cUser);
			//新增的时候才发送短信
			this.sendMsg(isNewUser,dto.getCompanyName(),dto.getCellphone(),dto.getAccountId(),dto.getCompanyId());
		}else{
			/****************修改公司人员信息*****************/
			cUser = new CompanyUserEntity();
			BaseDTO.copyFields(dto, cUser);
			cUser.setCreateBy(dto.getAccountId());
			companyUserDao.updateById(cUser);
			//处理部门
			this.handleDepartForEdit(dto,cUser);
		}
		//处理人员在群组的信息
		this.sendCompanyUserMessageFun(cUser.getCompanyId(),cUser.getUserId(),cUser.getAuditStatus());

		//删除没有成员的一级部门群
		this.handleDeleteNoMemberGroup(dto.getCompanyId());

		//通知协同
		this.collaborationService.pushSyncCMD_CU(dto.getCompanyId());

		return new AjaxMessage().setCode("0").setInfo("保存成功").setData(this.getCompanyUserById(cUser.getId()));
	}

	private void handleDeleteNoMemberGroup(String companyId) throws Exception{
		Map<String,Object> params = new HashMap<>();
		params.put("companyId",companyId);
		List<DepartEntity> departEntityList = departDao.selectStairDepartCompanyId(params);
		for(DepartEntity departEntity : departEntityList){
			Map<String,Object> mapPass = new HashMap<>();
			mapPass.put("orgId",departEntity.getId());
			List<OrgUserEntity> orgUserEntityList = orgUserDao.selectByParam(mapPass);
			if(orgUserEntityList.size()==0){
				imService.deleteImGroup(departEntity.getId(),1);
			}
		}
	}


	/**
	 * 方法描述：修改人员，部门处理
	 * 作者：MaoSF
	 * 日期：2016/9/20
	 * @param:
	 * @return:
	 */
	private void handleDepartForEdit(CompanyUserTableDTO dto,CompanyUserEntity cUser) throws Exception{
		//添加部门与成员之间的关系
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", dto.getCompanyId());
		param.put("cuId", cUser.getId());
		List<OrgUserEntity> orgUserEntityList = orgUserDao.selectByParam(param);
		String notDeleteIds ="";
		if(dto.getDepartList() !=null && dto.getDepartList().size()>0){
			for(int j=0;j<dto.getDepartList().size();j++){
				UserDepartDTO userDepartDTO=dto.getDepartList().get(j);
				OrgUserEntity orgUser = new OrgUserEntity();
				orgUser.setCompanyId(cUser.getCompanyId());
				orgUser.setUserId(cUser.getUserId());
				orgUser.setCuId(cUser.getId());
				orgUser.setOrgId(userDepartDTO.getDepartId());
				orgUser.setServerStation(userDepartDTO.getServerStation());
				//加多判断，因为没有在app端传orgUserId过来
				param.put("orgId",userDepartDTO.getDepartId());
				List<OrgUserEntity> orgUserList = orgUserDao.selectByParam(param);
				if(orgUserList.size()>0){
					userDepartDTO.setId(orgUserList.get(0).getId());
				}
				if(StringUtil.isNullOrEmpty(userDepartDTO.getId()))
				{
					orgUser.setId(StringUtil.buildUUID());
					orgUser.setCreateBy(dto.getAccountId());
					orgUserDao.insert(orgUser);
				}else{
					orgUser.setId(userDepartDTO.getId());
					orgUser.setUpdateBy(dto.getAccountId());
					//orgUserDao.updateById(orgUser);
					orgUserDao.updateById(orgUser);
					notDeleteIds+=orgUser.getId();
				}
				//查询部门，如果是一级部门，则进行加入群组
				this.handleDepartGroup(userDepartDTO,cUser);
			}
		}

		//删除原来有，现在没有的部门
		if(!StringUtil.isNullOrEmpty(notDeleteIds)){
			if(orgUserEntityList!=null){
				for(OrgUserEntity orgUserEntity:orgUserEntityList){
					if(notDeleteIds.indexOf(orgUserEntity.getId())<0){
						//删除部门群用户
						this.handleDeleteGroupMemberFromOneDepartGroup(orgUserEntity.getOrgId(),cUser);
						orgUserDao.deleteById(orgUserEntity.getId());
					}
				}
			}
		}
	}

	//删除部门群用户
	private void handleDeleteGroupMemberFromOneDepartGroup(String orgId,CompanyUserEntity cUser) {
		//查询部门
		DepartEntity departEntity = departDao.selectById(orgId);
		//存在
		if(null != departEntity){
			String oneDepartId = departEntity.getDepartPath();
			String[] oneDepartIds = oneDepartId.split("-");
			//查询出一级部门群
			departEntity = departDao.selectById(oneDepartIds[0]);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orgId",departEntity.getId());
			map.put("groupType","1");
			//查询是否已经存在一级部门群
			List<ImGroupEntity> listDepartGroup = imGroupDao.getImGroupsByParam(map);
			if(listDepartGroup.size()>0){
				this.sendCompanyUserMessageFun(orgId,cUser.getUserId(),"4");
			}
		}
	}

	/**
	 * 方法描述：添加/移除用户到组织群,
	 * 作者：MaoSF
	 * 日期：2016/11/29
	 * @param:orgId(组织id，公司或许部门），userId，auditStatus：成员的状态（1有效，4：离职）
	 */
	@Override
	public void sendCompanyUserMessageFun(String orgId ,String userId,String auditStatus){
		/************添加到组织群，暂时屏蔽，后期添加*********/
		try {
			if ("1".equals(auditStatus)) {
				this.imService.addMembers(orgId, userId);

			} else if ("4".equals(auditStatus)) {
				this.imService.deleteMembers(orgId, userId);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 方法描述：增加人员，部门处理
	 * 作者：MaoSF
	 * 日期：2016/9/20
	 */
	private void handleDepartForAdd(CompanyUserTableDTO dto,CompanyUserEntity cUser) throws Exception{
		//添加部门与成员之间的关系
		if(dto.getDepartList() !=null && dto.getDepartList().size()>0){
			Map<String,Object> param = new HashMap<String,Object>();
			//查询以前的部门
			param.put("companyId", dto.getCompanyId());
			param.put("cuId", cUser.getId());
			List<OrgUserEntity> orgUserEntityList = orgUserDao.selectByParam(param);
			//把OrgUserEntity放入map中，一遍更加orgId获取
			Map<String,OrgUserEntity> departOrgMap = new HashMap<String,OrgUserEntity>();
			if(orgUserEntityList!=null){
				for(OrgUserEntity orgUserEntity:orgUserEntityList){
					departOrgMap.put(orgUserEntity.getOrgId(),orgUserEntity);
				}
			}

			//循环传递过来的部门信息
			for(int j=0;j<dto.getDepartList().size();j++){
				UserDepartDTO userDepartDTO=dto.getDepartList().get(j);
				OrgUserEntity orgUser = new OrgUserEntity();
				orgUser.setCompanyId(cUser.getCompanyId());
				orgUser.setUserId(cUser.getUserId());
				orgUser.setCuId(cUser.getId());
				orgUser.setOrgId(userDepartDTO.getDepartId());
				orgUser.setServerStation(userDepartDTO.getServerStation());
				if(StringUtil.isNullOrEmpty(userDepartDTO.getId()))
				{
					if(departOrgMap.containsKey(userDepartDTO.getDepartId())){
						orgUser.setId(departOrgMap.get(userDepartDTO.getDepartId()).getId());
						orgUser.setUpdateBy(dto.getAccountId());
						orgUserDao.updateById(orgUser);
					}else {
						orgUser.setId(StringUtil.buildUUID());
						orgUser.setCreateBy(dto.getAccountId());
						orgUserDao.insert(orgUser);
					}
				}else{
					orgUser.setId(userDepartDTO.getId());
					orgUser.setUpdateBy(dto.getAccountId());
					orgUserDao.updateById(orgUser);
				}
				//处理部门群组
				this.handleDepartGroup(userDepartDTO,cUser);
			}
		}
	}

	/**
	 * 方法描述：处理部门群组
	 * 作者：MaoSF
	 * 日期：2016/11/29
	 */
	private void handleDepartGroup(UserDepartDTO userDepartDTO,CompanyUserEntity cUser) throws Exception{
		//查询部门
		DepartEntity departEntity = departDao.selectById(userDepartDTO.getDepartId());
		//存在
		if(null != departEntity){
			String oneDepartId = departEntity.getDepartPath();
			String[] oneDepartIds = oneDepartId.split("-");
			//查询出一级部门群
			departEntity = departDao.selectById(oneDepartIds[0]);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orgId",departEntity.getId());
			map.put("groupType","1");
			//查询是否已经存在一级部门群
			List<ImGroupEntity> listDepartGroup = imGroupDao.getImGroupsByParam(map);
			if(listDepartGroup.size()==0){
				//创建部门群组
				this.createGroupIm(departEntity.getId(),cUser.getUserId(),departEntity.getDepartName(),1,true);
			}else{
				//添加人到一级部门群组(String userId,String groupId,String departId)
				addUserToGroup(cUser.getUserId(),departEntity.getId(),departEntity.getId());
			}
		}
	}

	/**
	 * 方法描述：创建群组
	 * 作者：MaoSF
	 * 日期：2016/11/29
	 */
	private void createGroupIm(String id,String userId,String name,int type,boolean isSetDefaultImg) throws Exception{
		this.imService.createImGroup(id,userId,name,1);
	}

	/**
	 * 方法描述：添加人员（若没注册，先注册）【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午5:20:59
	 */
	public AccountEntity register(CompanyUserTableDTO dto) throws Exception{
		AccountEntity account= this.accountService.createAccount(dto.getUserName(),SecretUtil.MD5("123456"),dto.getCellphone());

		if(!StringUtil.isNullOrEmpty(dto.getSex()))
		{
			if("男".equals(dto.getSex().trim()) || "女".equals(dto.getSex().trim()))
			{
				UserEntity user=new UserEntity();
				user.setId(account.getId());
				user.setSex(dto.getSex().trim());
			}
		}

		dto.setUserId(account.getId());
		return account;
	}

	/**
	 * 方法描述：保存公司成员【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午5:37:42
	 * @param entity
	 * @return
	 */
	public CompanyUserEntity saveCompanyUser(CompanyUserEntity entity,String cellphone) throws Exception{
		//保存OrgEntity,组织基础表
		orgDao.deleteById(entity.getId());//线删除基础表中的数据（如果存在）
		OrgEntity orgUserEntity=new OrgEntity();
		orgUserEntity.setId(entity.getId());//基础表id和人员表id一致
		orgUserEntity.setOrgType("4");
		orgUserEntity.setOrgStatus("0");
		orgUserEntity.setCreateBy(entity.getCreateBy());
		orgDao.insert(orgUserEntity);
		companyUserDao.insert(entity);

		//添加了成员，查询companyUserAudit表中是否有该人的申请信息， 如果有，则删除
		companyUserAuditDao.deleteByCellphoneAndCompanyId(cellphone,entity.getCompanyId());
		/************添加到组织群*********/
		//imGroupService.addUserToGroup(entity.getUserId(), entity.getCompanyId());
		return entity;
	}
	
	/**
	 * 方法描述：发送短信【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午8:05:44
	 * @param companyName
	 * @param cellphone
	 */
	public void sendMsg(boolean isNewUser,String companyName,String cellphone,String accountId,String companyId){
		String accountName ="";
		CompanyUserEntity companyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
		if(companyUser!=null){
			accountName = companyUser.getUserName();
		}
		Sms sms=new Sms();
		sms.addMobile(cellphone);
		//暂时屏蔽短信
		if(isNewUser)
		{
			sms.setMsg(StringUtil.format(SystemParameters.ADD_COMPANY_USER_MSG_1, accountName,companyName,this.serverUrl,this.iosUrl,this.androidUrl));
		}else {
			sms.setMsg(StringUtil.format(SystemParameters.ADD_COMPANY_USER_MSG_2, accountName,companyName));
		}
		smsSender.send(sms);
	}
	

	@Override
	public AjaxMessage quit(String id) throws Exception{

		//暂时屏蔽删除权限，过后添加
		//this.deleteRole(map1);//删除所在公司的权限
		CompanyUserEntity cuser = this.selectById(id);
		AccountEntity account=accountDao.selectById(cuser.getUserId());
		if(account.getDefaultCompanyId() !=null && cuser.getCompanyId().equals(account.getDefaultCompanyId())){
			//把默认组织设置为null
			account.setDefaultCompanyId(null);
			accountDao.updateById(account);
		}
		cuser.setAuditStatus("4");//离职状态
		int i=companyUserDao.updateById(cuser);
		if(i>0){

			//后期处理
			//删除所有部门信息
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("companyId", cuser.getCompanyId());
			param.put("cuId", cuser.getId());

			//退出部门群
			handleQuitOneLevelDepartGroup(param);
			orgUserDao.deleteOrgUserByParam(param);

			//删除所有的权限
			param.clear();
			param.put("userId",cuser.getUserId());
			param.put("companyId",cuser.getCompanyId());
			this.userPermissionDao.deleteByUserId(param);

			//退出群处理
			this.sendCompanyUserMessageFun(cuser.getCompanyId(),cuser.getUserId(),cuser.getAuditStatus());

			//通知协同
			this.collaborationService.pushSyncCMD_CU(cuser.getCompanyId());

			return new AjaxMessage().setCode("0").setInfo("操作成功");
		}
		return new AjaxMessage().setCode("1").setInfo("操作失败");
	}

	private void handleQuitOneLevelDepartGroup(Map<String, Object> param) {
		//退出部门群
		List<OrgUserEntity> orgUserEntityList = orgUserDao.selectByParam(param);
		for(OrgUserEntity orgUserEntity : orgUserEntityList){
			//查询部门
			DepartEntity departEntity = departDao.selectById(orgUserEntity.getOrgId());
			//存在
			if(null != departEntity){
				String oneDepartId = departEntity.getDepartPath();
				String[] oneDepartIds = oneDepartId.split("-");
				//查询出一级部门群
				departEntity = departDao.selectById(oneDepartIds[0]);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orgId",departEntity.getId());
				map.put("groupType","1");
				//查询是否已经存在一级部门群
				List<ImGroupEntity> listDepartGroup = imGroupDao.getImGroupsByParam(map);
				if(listDepartGroup.size()==1){
					//退出部门群处理
					this.sendCompanyUserMessageFun(departEntity.getId(),orgUserEntity.getUserId(),"4");
				}
			}
		}
	}

	/**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2016/8/5
     */
    @Override
    public AjaxMessage orderCompanyUser(CompanyUserTableDTO dto1,CompanyUserTableDTO dto2,String orgId) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("cuId",dto1.getId());
        map.put("orgId",orgId);
        List<OrgUserEntity> orgUserEntityList1 = orgUserDao.selectByParam(map);
        map.clear();
        map.put("cuId",dto2.getId());
        map.put("orgId",orgId);
        List<OrgUserEntity> orgUserEntityList2 = orgUserDao.selectByParam(map);
        if(orgUserEntityList1.isEmpty() || orgUserEntityList1.size()>1 || orgUserEntityList2.isEmpty() || orgUserEntityList2.size()>1){
            return new AjaxMessage().setCode("1").setInfo("操作失败");
        }
        OrgUserEntity orgUserEntity1 = orgUserEntityList1.get(0);
        OrgUserEntity orgUserEntity2 = orgUserEntityList2.get(0);
		int seq2= orgUserEntity2.getSeq();
		int seq1= orgUserEntity1.getSeq();
        orgUserEntity1.setSeq(seq2);
        orgUserEntity2.setSeq(seq1);
        orgUserDao.updateById(orgUserEntity1);
        orgUserDao.updateById(orgUserEntity2);
        return new AjaxMessage().setCode("0").setInfo("操作成功");
    }

    @Override
    public List<Map<String, Object>>selectPersonalInGroupAndInfo(Map<String, Object>map)throws Exception{
        return companyUserDao.selectPersonalInGroupAndInfo(map);
    }

	/**
	 * 方法描述：根据id集合查询人员名字（任务分解--负责人，设计人）使用
	 * 作        者：MaoSF
	 * 日        期：2016年4月23日-下午6:02:15
	 * @param param
	 * @return
	 */
	@Override
	public List<CompanyUserEntity> getPersonByIds(Map<String,Object> param){
		return companyUserDao.getPersonByIds(param);
	}


	public void addUserToGroup(String userId,String groupId,String departId) throws Exception{

		AccountEntity account = accountDao.selectById(userId);
		if(null != account){
			this.imService.createImAccount(account.getId(),account.getUserName(),account.getPassword());
			this.sendCompanyUserMessageFun(departId,userId,"1");
		}
	}


	/**
	 * 方法描述：根据角色id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	@Override
	public List<CompanyUserTableDTO> getCompanyUserByRoleId(Map<String, Object> param) {
		if(null!=param.get("pageNumber")){
			int page=(Integer)param.get("pageNumber");
			int pageSize=(Integer) param.get("pageSize");
			param.put("startPage",page*pageSize);
			param.put("endPage",pageSize);
		}
		List<CompanyUserTableDTO> list = this.companyUserDao.getCompanyUserByRoleId(param);
		if(!CollectionUtils.isEmpty(list)){
			for(CompanyUserTableDTO c:list){
				List<UserDepartDTO> departList = companyUserDao.getCompanyUserDepartRole(c.getUserId(),c.getCompanyId());
				c.setDepartList(departList);
			}
		}
		return list;
	}

	/**
	 * 方法描述：根据权限id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	@Override
	public List<CompanyUserTableDTO> getCompanyUserByPermissionId(Map<String, Object> param) {
		if(null!=param.get("pageNumber")){
			int page=(Integer)param.get("pageNumber");
			int pageSize=(Integer) param.get("pageSize");
			param.put("startPage",page*pageSize);
			param.put("endPage",pageSize);
		}
		List<CompanyUserTableDTO> list = this.companyUserDao.getCompanyUserByPermissionId(param);
		return list;
	}


	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public List<CompanyUserAppDTO> getCompanyUser(String companyId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId",companyId);
		map.put("fastdfsUrl",fastdfsUrl);
		List<CompanyUserAppDTO> dtoList = this.companyUserDao.getCompanyUser(map);
		return dtoList;
	}
	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 *
	 * @param companyId
	 * @return
	 */
	@Override
	public List<CompanyUserAppDTO> getCompanyUserExceptMe(String companyId,String accountId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId",companyId);
		map.put("userId",accountId);
		map.put("fastdfsUrl",fastdfsUrl);
		List<CompanyUserAppDTO> dtoList = this.companyUserDao.getCompanyUserExceptMe(map);
		return dtoList;
	}

	/**
	 * 方法描述：获取当前用户所在的团队及团对中的权限（用于组织切换）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 *
	 * @param param
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyRoleDataDTO> getCompanyRole(Map<String, Object> param) throws Exception {
		param.put("fastdfsUrl",fastdfsUrl);
		return this.companyUserDao.getCompanyRole(param);
	}

	@Override
	public ImUserInfoDTO getImUserInfo(ImUserInfoQueryDTO query) throws Exception {
		query.setFastdfsUrl(this.fastdfsUrl);
		ImUserInfoDTO imUserInfoDTO = this.companyUserDao.getImUserInfo(query);
		if(!StringUtil.isNullOrEmpty(query.getProjectId())){
			imUserInfoDTO.setTaskRoleList(this.projectMemberService.listUserPositionForProject(query.getProjectId(),query.getCompanyId(),query.getUserId()));
		}
		return imUserInfoDTO;
	}
}
