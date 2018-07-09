package com.maoding.role.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.message.dto.SendMessageDataDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.DepartDao;
import com.maoding.org.dto.UserDepartDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.role.dao.RoleDao;
import com.maoding.role.dao.RolePermissionDao;
import com.maoding.role.dao.RoleUserDao;
import com.maoding.role.dao.UserPermissionDao;
import com.maoding.role.dto.RoleUserDTO;
import com.maoding.role.dto.SaveRoleUserDTO;
import com.maoding.role.dto.UserOrgRoleDTO;
import com.maoding.role.entity.RoleEntity;
import com.maoding.role.entity.RolePermissionEntity;
import com.maoding.role.entity.RoleUserEntity;
import com.maoding.role.entity.UserPermissionEntity;
import com.maoding.role.service.RoleUserService;
import com.maoding.role.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("roleUserService")
public class RoleUserServiceImpl extends GenericService<RoleUserEntity>  implements RoleUserService{
	
    @Autowired
    private RoleUserDao  roleUserDao;
    
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    private DepartDao departDao;

	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	private UserPermissionDao userPermissionDao;

    @Autowired
    private UserPermissionService userPermissionService;

	@Autowired
    private MessageService messageService;

	@Autowired
	private NoticeService noticeService;

	/**
	 * 方法描述：保存用户权限(系统管理员，企业负责人)
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午5:23:51
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public AjaxMessage saveOrUserRole(SaveRoleUserDTO dto) throws Exception {

		if(!CollectionUtils.isEmpty(dto.getUserIds())){
			String roleId = dto.getRoleId();
			String companyId = dto.getCurrentCompanyId();

			//先删除权限
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("companyId",companyId);
			map.put("roleId",roleId);
			this.roleUserDao.deleteUserRole(map);


			List<RolePermissionEntity> permissionList = rolePermissionDao.getPermissionByRole(roleId,companyId);//保存用户权限的关系（2016-11-10权限改动添加代码）
			for(String userId:dto.getUserIds()){
				//添加
				RoleUserEntity roleUserEntity = new RoleUserEntity();
				String id = StringUtil.buildUUID();
				roleUserEntity.setId(id);
				roleUserEntity.setOrgId(companyId);
				roleUserEntity.setCompanyId(companyId);
				roleUserEntity.setUserId(userId);
				roleUserEntity.setRoleId(roleId);
				this.roleUserDao.insert(roleUserEntity);

				//保存用户权限的关系（2016-11-10权限改动添加代码）
				//此处可能保存重复的权限
				for(RolePermissionEntity rolePermission:permissionList){

					//先删除已有数据
					List<String> permissionLists = new ArrayList<String>();
					permissionLists.add(rolePermission.getPermissionId());
					map.put("permissionList",permissionLists);
					userPermissionDao.deleteByUserIdAndPermission(map);

					//添加数据
					UserPermissionEntity userPermission =new UserPermissionEntity();
				//	userPermission.setId(StringUtil.buildUUID());
					userPermission.setPermissionId(rolePermission.getPermissionId());
					userPermission.setCompanyId(companyId);
					userPermission.setUserId(userId);
					userPermissionService.saveUserPermission(userPermission);
				}
				//-----------------end-----------------
			}
			this.sendMessageForRole(dto.getUserIds(),companyId,dto.getAccountId());
		}

		return new AjaxMessage().setCode("0").setInfo("保存成功").setData(null);
	}

	public AjaxMessage saveOrgManager(SaveRoleUserDTO dto) throws Exception{
		String roleId = dto.getRoleId();
		String companyId = dto.getCurrentCompanyId();
		List<RolePermissionEntity> permissionList = rolePermissionDao.getPermissionByRole(roleId,companyId);//保存用户权限的关系（2016-11-10权限改动添加代码）
		if(!CollectionUtils.isEmpty(dto.getUserIds())){
			//1.删除原来所有的企业负责人的权限
			for(RolePermissionEntity rolePermission:permissionList){
				this.userPermissionDao.deleteAllByPermissionId(dto.getCurrentCompanyId(),rolePermission.getPermissionId());
			}
			//先删除权限
			Map<String,Object> map = new HashMap<>();
			map.put("companyId",dto.getCurrentCompanyId());
			map.put("roleId",dto.getRoleId());
			this.roleUserDao.deleteUserRole(map);
			//保存被选择人的权限
			for(String userId:dto.getUserIds()){
				//添加
				RoleUserEntity roleUserEntity = new RoleUserEntity();
				String id = StringUtil.buildUUID();
				roleUserEntity.setId(id);
				roleUserEntity.setOrgId(companyId);
				roleUserEntity.setCompanyId(companyId);
				roleUserEntity.setUserId(userId);
				roleUserEntity.setRoleId(roleId);
				this.roleUserDao.insert(roleUserEntity);

				//此处可能保存重复的权限
				for(RolePermissionEntity rolePermission:permissionList) {
					//添加数据
					UserPermissionEntity userPermission = new UserPermissionEntity();
					userPermission.setPermissionId(rolePermission.getPermissionId());
					userPermission.setCompanyId(companyId);
					userPermission.setUserId(userId);
					userPermissionService.saveUserPermission(userPermission);
				}
			}
		}
		return new AjaxMessage().setCode("0").setInfo("保存成功").setData(null);

	}

	/**
	 * 方法描述：删除用户角色（根据userId，companyId，roleId）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 *
	 * @param param
	 * @param:
	 * @return:
	 */
	@Override
	public AjaxMessage deleteRoleUser(Map<String,Object> param) throws Exception {
		this.roleUserDao.deleteUserRole(param);
		deletePermissionByRole(param); //（2016-11-10权限改动添加代码）
		return  new AjaxMessage().setCode("0").setInfo("删除成功").setData(null);
	}

	private void deletePermissionByRole(Map<String,Object> param) throws Exception{
		//删除uer对应角色所拥有的权限（2016-11-10权限改动添加代码）
		String companyId = (String)param.get("companyId");
		String roleId = (String)param.get("roleId");
		String userId = (String)param.get("userId");

		List<RoleEntity> roleList = roleDao.getUserRolesByOrgId(userId,companyId);

		if(!CollectionUtils.isEmpty(roleList)){
			List<String> roleIds = new ArrayList<String>();
			for(RoleEntity roleEntity:roleList){
				roleIds.add(roleEntity.getId());
			}
			param.put("roleIds",roleIds);
		}
		List<String> permissionList = this.userPermissionDao.getPermissionByUserAndRole(param);
		if(!CollectionUtils.isEmpty(permissionList)){
			param.put("permissionList",permissionList);
			this.userPermissionDao.deleteByUserIdAndPermission(param);
		}

		//
		List<String> userIds = Arrays.asList(userId);
		this.sendMessageForRole(userIds,companyId,(String)param.get("accountId"));
		//------------end------------------
	}

	/**
	 * 方法描述：发送消息
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 */
	@Override
	public void sendMessageForRole(List<String> userIdList, String companyId,String accountId) throws Exception{
		sendMessageForRole(userIdList,companyId);
		//推送消息
		pushMessage(userIdList, companyId, accountId);
	}

	/**
	 * 方法描述：发送消息
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 */
	@Override
	public void sendMessageForRole(List<String> userIdList, String companyId) throws Exception{
		//推送给app端
		SendMessageDataDTO notifyMsg = new SendMessageDataDTO();
		notifyMsg.setMessageType(SystemParameters.ROLE_TYPE);
		notifyMsg.setReceiverList(userIdList);
		notifyMsg.setContent("Operator");
		noticeService.notify(NotifyDestination.APP, notifyMsg);
	}

	/**
	 * 方法描述：推送消息【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午8:05:44
	 */
	public void pushMessage(List<String> userIds,String companyId,String accountId) throws Exception{
		for(String userId:userIds){
			MessageEntity m = new MessageEntity();
			m.setCompanyId(companyId);
			m.setUserId(userId);
			m.setCreateBy(accountId);
			m.setSendCompanyId(companyId);
			m.setMessageType(SystemParameters.MESSAGE_TYPE_ROLE_CHANGE);
			messageService.sendMessage(m);
		}
	}
}
