package com.maoding.role.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.dto.DepartDataDTO;
import com.maoding.org.entity.TeamOperaterEntity;
import com.maoding.org.service.DepartService;
import com.maoding.org.service.TeamOperaterService;
import com.maoding.role.dto.OrgRoleTypeDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dao.RoleDao;
import com.maoding.role.dao.RolePermissionDao;
import com.maoding.role.dao.RoleUserDao;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.dto.RoleDTO;
import com.maoding.role.dto.RoleDataDTO;
import com.maoding.role.entity.RoleEntity;
import com.maoding.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RoleService
 * 类描述：角色权限Service
 * 作    者：MaoSF
 * 日    期：2016年7月11日-下午3:28:54
 */
@Service("roleService")
public class RoleServiceImpl  extends GenericService<RoleEntity>  implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleUserDao roleUserDao;

	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private TeamOperaterService teamOperaterService;

	@Autowired
	private DepartService departService;

	@Value("${fastdfs.url}")
	protected String fastdfsUrl;

	/**
	 * 方法描述：保存自定义角色
	 * 作者：MaoSF
	 * 日期：2016/11/2
	 */
	@Override
	public AjaxMessage saveRole(RoleDTO dto) throws Exception {
		RoleEntity roleEntity = new RoleEntity();
		BaseDTO.copyFields(dto,roleEntity);
		if(StringUtil.isNullOrEmpty(dto.getId())){
			dto.setId(StringUtil.buildUUID());
			roleEntity.setId(dto.getId());
			roleDao.insert(roleEntity);
		}else {
			roleDao.updateById(roleEntity);
		}

		return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
	}

	/**
	 * 方法描述：删除权限
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	@Override
	public AjaxMessage deleteRole(String id, String companyId) throws Exception {
		//1.删除角色
		this.roleDao.deleteById(id);

		//2.删除角色与权限的关联
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("roleId", id);
		map.put("companyId", companyId);
		this.rolePermissionDao.deleteByRoleId(map);//先删除权限与角色之间的关系

		//3.删除角色与人员之间的关联
		//删除所有的角色
		map.clear();
		map.put("companyId", companyId);
		map.put("roleId", id);
		this.roleUserDao.deleteUserRole(map);

		return new AjaxMessage().setCode("0").setInfo("删除成功成功");
	}

	@Override
	public List<RoleDataDTO> getCompanyRoleDTO(String companyId) throws Exception{
		return roleDao.getCompanyRoleDTO(companyId);
	}

	/**
	 * 方法描述：获取当前公司角色-权限-人员总览数据
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 */
	@Override
	public List<RoleDataDTO> getRolePermissionUser(String companyId) throws Exception {
		List<RoleDataDTO> roleList = this.getCompanyRoleDTO(companyId);
		String typeId = companyService.getOrgTypeId(companyId);
		if(!CollectionUtils.isEmpty(roleList)){
			for(RoleDataDTO role:roleList){
				//获取所有的权限
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId",companyId);
				map.put("roleId",role.getId());
				map.put("fastdfsUrl",this.fastdfsUrl);
				if(!StringUtil.isNullOrEmpty(typeId)){
					map.put("typeId",typeId);
				}
				List<PermissionDTO> permissionList = permissionDao.getPermissionByRole2(map);
				role.setPermissionList(permissionList);
			}
		}
		return roleList;
	}

	@Override
	public List<RoleDataDTO> getRolePermissionUserAndSysManager(String companyId) throws Exception {
		List<RoleDataDTO> roleList = getRolePermissionUser(companyId);
		roleList.add(0,getSystemManager(companyId,null));
		return roleList;
	}


	private RoleDataDTO getSystemManager(String companyId,String userId){
		CompanyUserAppDTO companyUser = teamOperaterService.getSystemManager(companyId);
		if(!StringUtil.isNullOrEmpty(userId)){
			if(companyUser==null || !userId.equals(companyUser.getUserId() )){
				return null;
			}
		}
		//获取系统管理员
		RoleDataDTO sysManager = new RoleDataDTO();
		sysManager.setName("系统管理员");
		sysManager.setIsSingle(1);
		if(!StringUtil.isNullOrEmpty(userId)){
			sysManager.setPermissionList(null);
		}else {
			PermissionDTO permission = new PermissionDTO();
			permission.setName("");
			permission.setIsSingle(1);
			permission.getCompanyUserList().add(companyUser);
			sysManager.getPermissionList().add(permission);
		}

		return sysManager;
	}
	/**
	 * 方法描述：
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 */
	@Override
	public List<RoleDataDTO> getRolePermissionByUserId(String companyId, String userId) throws Exception {
		List<RoleDataDTO> roleList = this.getCompanyRoleDTO(companyId);
		List<RoleDataDTO> newRoleList = new ArrayList<RoleDataDTO>();
		String typeId = companyService.getOrgTypeId(companyId);
		if(!CollectionUtils.isEmpty(roleList)){
			for(RoleDataDTO role:roleList){
				//获取userId的所有的权限
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId",companyId);
				map.put("roleId",role.getId());
				map.put("userId",userId);
				if(!StringUtil.isNullOrEmpty(typeId)){
					map.put("typeId",typeId);
				}
				List<PermissionDTO> permissionList = permissionDao.getPermissionByRoleAndUserForApp(map);
				if(!CollectionUtils.isEmpty(permissionList)){
					role.setPermissionList(permissionList);
					newRoleList.add(role);
				}
			}
		}
		return newRoleList;
	}

	@Override
	public List<OrgRoleTypeDTO> getOrgRoleList() {
		List<OrgRoleTypeDTO> list = new ArrayList<>();
		list.add(new OrgRoleTypeDTO("1","独立组织型","拥有财务管理权限，可下设分支机构"));
		list.add(new OrgRoleTypeDTO("2","独立财务型","拥有财务管理权限，不可下设分支机构"));
		list.add(new OrgRoleTypeDTO("3","非独立组织型","无财务管理权限，不可下设分支机构"));
		return list;
	}

	@Override
	public OrgRoleTypeDTO getOrgRoleByType(String typeId) {
		List<OrgRoleTypeDTO> list = this.getOrgRoleList();
		for(OrgRoleTypeDTO dto:list){
			if(dto.getRoleType().equals(typeId)){
				return dto;
			}
		}
		return null;
	}

	@Override
	public List<RoleDTO> getRolePermissionByType(String type) throws Exception {
		List<RoleDTO> roleList = this.roleDao.getRolePermissionByType(type);
		return roleList;
	}

	@Override
	public List<Object> getCompanyDepartAndPermission(String userId,String companyId) throws Exception{
		List<CompanyDTO> orgList = new ArrayList<>();
		if(StringUtil.isNullOrEmpty(companyId)){
			//所在的所有公司
			orgList = companyService.getCompanyByUserId(userId);
		}else {
			CompanyDTO dto = companyService.selectCompanyById(companyId);
			if(dto!=null){
				orgList.add(dto);
			}
		}
		List<Object> list = new ArrayList<>();
		for (CompanyDTO companyDTO : orgList) {
			Map<String, Object> map = new HashMap<>();
			map.put("companyName", companyDTO.getCompanyName());
			map.put("companyId", companyDTO.getId());
			map.put("companyShortName", companyDTO.getCompanyShortName());

			map.put("userId", userId);
			List<DepartDataDTO> departList = departService.getDepartByUserIdContentCompany(map);
			List<RoleDataDTO> roleDtoList = new ArrayList<>();
			//查询系统管理员
			RoleDataDTO systemManager = this.getSystemManager(companyDTO.getId(), userId);
			if(systemManager!=null){
				roleDtoList.add(systemManager);
			}
			roleDtoList.addAll(getRolePermissionByUserId(companyDTO.getId(), userId));
			List<Map<String, Object>> departReturnList = new ArrayList<>();
			for (DepartDataDTO departDto : departList) {
				Map<String, Object> returnMap = new HashMap<>();
				returnMap.put("departName", departDto.getDepartName());
				returnMap.put("serverStation", departDto.getServerStation());
				departReturnList.add(returnMap);
			}
			map.put("departList", departReturnList);
			map.put("roleList", roleDtoList);
			list.add(map);
		}
		return list;
	}

}
