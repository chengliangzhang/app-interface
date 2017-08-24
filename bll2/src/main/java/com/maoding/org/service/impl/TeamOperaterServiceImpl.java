package com.maoding.org.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dao.TeamOperaterDao;
import com.maoding.org.dto.CompanyUserTableDTO;
import com.maoding.org.dto.TeamOperaterDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.entity.TeamOperaterEntity;
import com.maoding.org.service.TeamOperaterService;
import com.maoding.role.dao.RolePermissionDao;
import com.maoding.role.dao.RoleUserDao;
import com.maoding.role.dao.UserPermissionDao;
import com.maoding.role.dto.SaveRoleUserDTO;
import com.maoding.role.entity.RolePermissionEntity;
import com.maoding.role.entity.RoleUserEntity;
import com.maoding.role.entity.UserPermissionEntity;
import com.maoding.role.service.RoleUserService;
import com.maoding.role.service.UserPermissionService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("teamOperaterService")
public class TeamOperaterServiceImpl extends GenericService<TeamOperaterEntity>  implements TeamOperaterService{

	@Autowired
	private TeamOperaterDao teamOperaterDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private SmsSender smsSender;

	@Autowired
	private RoleUserService roleUserService;

	@Autowired
	private RoleUserDao roleUserDao;

	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	private UserPermissionDao userPermissionDao;

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private CompanyUserDao companyUserDao;


	
	@Override
	public AjaxMessage transferSys(TeamOperaterDTO dto,String newPassword) throws Exception{
		TeamOperaterEntity entity = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), null);
//		AccountEntity aEntity = accountDao.selectById(dto.getAccountId());
//		if(aEntity==null || !aEntity.getPassword().equals(dto.getAdminPassword())){
//			return new AjaxMessage().setCode("1").setInfo("密码错误");
//		}
		entity.setUserId(dto.getUserId());
	//	entity.setAdminPassword(dto.getNewAdminPassword());
		teamOperaterDao.updateById(entity);

		//删除当前人的系统管理员角色

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleId",SystemParameters.ADMIN_MANAGER_ROLE_ID);
		param.put("userId",dto.getAccountId());
		param.put("orgId",dto.getCompanyId());
		param.put("companyId",dto.getCompanyId());

		this.roleUserService.deleteRoleUser(param);


		//给新的管理员添加管理员角色

		//删除新管理员系统管理员角色（理论上是没有改权限）
		param.put("userId",dto.getUserId());
		this.roleUserDao.deleteUserRole(param);
		//添加系统管理员角色
		SaveRoleUserDTO saveRoleUserDTO = new SaveRoleUserDTO();
		saveRoleUserDTO.setRoleId(SystemParameters.ADMIN_MANAGER_ROLE_ID);
		List<String> users = new ArrayList<String>();
		users.add(dto.getUserId());
		saveRoleUserDTO.setUserIds(users);
		saveRoleUserDTO.setCurrentCompanyId(dto.getCompanyId());
		this.roleUserService.saveOrUserRole(saveRoleUserDTO);
		//-----------------end-----------------

		CompanyEntity companyEntity = companyDao.selectById(dto.getCompanyId());
		AccountEntity accountEntity = accountDao.selectById(dto.getUserId());
		if(companyEntity!=null && accountEntity!=null)
		{
			sendMsg(accountEntity.getCellphone(),StringUtil.format(SystemParameters.TRANSFER_ADMIN_MSG,companyEntity.getCompanyName()));
		}

		//发送消息给App端，进行权限刷新
		List<String> userIdList = new ArrayList<String>();
		userIdList.add(dto.getAccountId());
		userIdList.add(dto.getUserId());
		this.userPermissionService.sendMessageForRole(userIdList,dto.getCompanyId());
		return  new AjaxMessage().setCode("0").setInfo("移交成功");
	}
	/**
	 * 方法描述：移交企业负责人（统一在roleUserService中调用）
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午9:13:48
	 *
	 * @param dto
	 * @param newPassword
	 * @return
	 */
	@Override
	public AjaxMessage transferOrgManager(TeamOperaterDTO dto, String newPassword) throws Exception {

		CompanyUserTableDTO orgManager = null;
		if("2".equals(dto.getFlag())){//如果是设置
			//给原来企业负责人发送信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permissionId", "50");//企业负责人
			map.put("companyId", dto.getCompanyId());
			List<CompanyUserTableDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
			if(!CollectionUtils.isEmpty(companyUserList)){
				orgManager = companyUserList.get(0);
			}
		}
		//保存企业负责人权限
		SaveRoleUserDTO saveRoleUserDTO = new SaveRoleUserDTO();
		saveRoleUserDTO.setRoleId(SystemParameters.ORG_MANAGER_ROLE_ID);
		List<String> users = new ArrayList<String>();
		users.add(dto.getUserId());
		saveRoleUserDTO.setUserIds(users);
		saveRoleUserDTO.setCurrentCompanyId(dto.getCompanyId());
		this.roleUserService.saveOrUserRole(saveRoleUserDTO);

		//发送短信通知
		if(orgManager!=null){
			CompanyEntity company = this.companyDao.selectById(dto.getCompanyId());
			TeamOperaterEntity teamOperater = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), null);
			if(company!=null && teamOperater!=null){
				CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(teamOperater.getUserId(),teamOperater.getCompanyId());
				String msg = StringUtil.format(SystemParameters.TRANSFER_ORG_MSG, company.getCompanyName(),companyUserEntity.getUserName());
				this.sendMsg(orgManager.getCellphone(),msg);
			}
		}
		return AjaxMessage.succeed("设置成功");
	}



	/**
	 * 方法描述：保存系统管理员所以资料（创建企业的时候，给管理员设置系统管理员，并赋予所有权限）
	 * 作者：MaoSF
	 * 日期：2016/11/17
	 *
	 * @param teamOperaterEntity
	 * @param:
	 * @return:
	 */
	@Override
	public AjaxMessage saveSystemManage(TeamOperaterEntity teamOperaterEntity) throws Exception {

		String companyId = teamOperaterEntity.getCompanyId();
		String userId = teamOperaterEntity.getUserId();
		String roleId = SystemParameters.ADMIN_MANAGER_ROLE_ID;

		//保存teamOperaterEntity
		teamOperaterEntity.setId(StringUtil.buildUUID());
		teamOperaterDao.insert(teamOperaterEntity);

		//添加系统管理员角色


		SaveRoleUserDTO saveRoleUserDTO = new SaveRoleUserDTO();
		saveRoleUserDTO.setRoleId(roleId);
		List<String> users = new ArrayList<String>();
		users.add(teamOperaterEntity.getUserId());
		saveRoleUserDTO.setUserIds(users);
		saveRoleUserDTO.setCurrentCompanyId(companyId);
		this.roleUserService.saveOrUserRole(saveRoleUserDTO);

		//先删除权限
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId",companyId);
		map.put("roleId",roleId);
		map.put("userId",userId);
		this.roleUserDao.deleteUserRole(map);

		//添加系统管理角色
		RoleUserEntity roleUserEntity = new RoleUserEntity();
		String id = StringUtil.buildUUID();
		roleUserEntity.setId(id);
		roleUserEntity.setOrgId(companyId);
		roleUserEntity.setCompanyId(companyId);
		roleUserEntity.setUserId(userId);
		roleUserEntity.setRoleId(roleId);
		this.roleUserDao.insert(roleUserEntity);

		//添加所有权限
		List<RolePermissionEntity> permissionList = rolePermissionDao.getAllDefaultPermission();

		for(RolePermissionEntity rolePermission:permissionList){
			map.clear();
			//先删除已有数据
			List<String> permissionLists = new ArrayList<String>();
			permissionLists.add(rolePermission.getPermissionId());
			map.put("permissionList",permissionLists);
			map.put("companyId",companyId);
			map.put("userId",userId);
			userPermissionDao.deleteByUserIdAndPermission(map);

			//添加数据
			UserPermissionEntity userPermission =new UserPermissionEntity();
			//userPermission.setId(StringUtil.buildUUID());
			userPermission.setPermissionId(rolePermission.getPermissionId());
			userPermission.setCompanyId(companyId);
			userPermission.setUserId(userId);
			this.userPermissionService.saveUserPermission(userPermission);
		}

		return  new AjaxMessage().setCode("0").setInfo("保存成功");
	}

	@Override
	public AjaxMessage transferSysWS(TeamOperaterDTO dto,String newPassword) throws Exception{
		TeamOperaterEntity entity = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), dto.getAccountId());
		entity.setUserId(dto.getUserId());
		entity.setAdminPassword(dto.getAdminPassword());
		teamOperaterDao.updateById(entity);
		return  new AjaxMessage().setCode("0").setInfo("移交成功");
	}




	/**
	 * 方法描述：  根据（userId，companyId）查询
	 * 作        者：TangY
	 * 日        期：2016年7月12日-上午4:50:13
	 * @return
	 */
	@Override
	public TeamOperaterEntity getTeamOperaterByParam(String companyId,String userId)throws Exception{
		return teamOperaterDao.getTeamOperaterByCompanyId(companyId, userId);
	}

	/**
	 * 修改管理员密码
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public AjaxMessage updateAdminPassword(TeamOperaterDTO dto) throws Exception {

		if(StringUtil.isNullOrEmpty(dto.getAdminPassword())){
			return new AjaxMessage().setCode("1").setInfo("密码不能为空");
		}

		TeamOperaterEntity teamOperaterEntity = this.getTeamOperaterByParam(dto.getCompanyId(),dto.getUserId());

		if(teamOperaterEntity == null){
			return new AjaxMessage().setCode("1").setInfo("操作失败");
		}

		teamOperaterEntity.setAdminPassword(dto.getAdminPassword());
		this.updateById(teamOperaterEntity);
		return new AjaxMessage().setCode("0").setInfo("修改成功");
	}

	/**
	 * 方法描述：发送短信【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午8:05:44
	 * @param msg
	 * @param cellphone
	 */
	public void sendMsg(String cellphone,String msg){
		Sms sms=new Sms();
		sms.addMobile(cellphone);

		sms.setMsg(msg);

		smsSender.send(sms);
	}



	@Override
	public ResponseBean verifySysPassword(TeamOperaterDTO dto) throws Exception{
		//TeamOperaterEntity entity = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), dto.getAccountId());
		AccountEntity entity = accountDao.selectById(dto.getAccountId());
		if(entity==null || !entity.getPassword().equals(dto.getAdminPassword())){
			return ResponseBean.responseError("密码错误");
		}
		return ResponseBean.responseSuccess("密码正确");
	}
}
