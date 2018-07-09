package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyDataDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.role.dao.RolePermissionDao;
import com.maoding.role.entity.RolePermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyDaoImpl
 * 类描述：类描述：组织(公司）DaoImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:44:06
 */
@Service("companyDao")
public class CompanyDaoImpl extends GenericDao<CompanyEntity> implements CompanyDao{

	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Override
	public int insert(CompanyEntity entity){
		List<RolePermissionEntity> initData = this.rolePermissionDao.getAllDefaultPermission();
		if(!CollectionUtils.isEmpty(initData)){
			for(RolePermissionEntity rolePermission:initData){
				rolePermission.setId(StringUtil.buildUUID());
				rolePermission.setCompanyId(entity.getId());
				this.rolePermissionDao.insert(rolePermission);
			}
		}
		return  super.insert(entity);
	}

	@Override
	public List<CompanyDTO> getCompanyByUserId(String userId) {
		return this.sqlSession.selectList("GetCompanyByUserIdMapper.getCompanyByUserId", userId);
	}

	/**
	 * 方法描述：获取所有公司（全部公司注册环信群，手工操作）
	 * 作者：MaoSF
	 * 日期：2016/8/23
	 */
	@Override
	public List<CompanyEntity> selectAll() {
		return this.sqlSession.selectList("CompanyEntityMapper.selectAll");
	}

	/**
	 * 方法描述：获取具有管理员权限的公司
	 * 作者：MaoSF
	 * 日期：2017/2/28
	 */
	@Override
	public List<CompanyDataDTO> getHasSystemManagerCompany(Map<String, Object> map) {
		return this.sqlSession.selectList("GetCompanyFilterbyParamMapper.getHasSystemManagerCompany", map);
	}


	@Override
	public List<Map<String, Object>> selectAllCompanyEdges(Map<String, Object> map) {
		 return this.sqlSession.selectList("QueryOrgTreeEntityMapper.selectAllCompanyEdges", map);
	}

	@Override
	public List<CompanyDTO> getCompanyFilterbyParam(Map<String, Object> map) {
		 return this.sqlSession.selectList("GetCompanyFilterbyParamMapper.getCompanyFilterbyParam", map);
	}

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）
	 * 作者：MaoSF
	 * 日期：2017/1/17
	 */
	@Override
	public List<CompanyDTO> getCompanyFilterbyParamForInvit(Map<String, Object> map) {
		return this.sqlSession.selectList("GetCompanyFilterbyParamMapper.getCompanyFilterbyParamForInvit", map);
	}

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）总条数
	 * 作者：MaoSF
	 * 日期：2017/1/17
	 */
	@Override
	public int getCompanyFilterbyParamForInvitCount(Map<String, Object> map) {
		return this.sqlSession.selectOne("GetCompanyFilterbyParamMapper.getCompanyFilterbyParamForInvitCount");
	}

	@Override
	public int getCompanyFilterbyParamCount(Map<String, Object> map) {
		Object count = this.sqlSession.selectOne("GetCompanyFilterbyParamMapper.getCompanyFilterbyParamCount", map);
		return count==null?0:Integer.parseInt(count.toString());
	}

	@Override
	public CompanyEntity getParentCompany(String id) {
		return this.sqlSession.selectOne("QueryOrgTreeEntityMapper.getParentCompany", id);
	}

	@Override
	public List<CompanyEntity> getChilrenCompany(String id) {
		return this.sqlSession.selectList("QueryOrgTreeEntityMapper.getChilrenCompany", id);
	}

	/**
	 * 方法描述：查询了公司信息及logo
	 * 作者：MaoSF
	 * 日期：2017/3/3
	 */
	@Override
	public CompanyEntity getCompanyMsgById(String id) {
		return this.sqlSession.selectOne("QueryOrgTreeEntityMapper.getCompanyMsgById",id);
	}

	/**
	 * 方法描述：获取项目的外部团队
	 * 作者：MaoSF
	 * 日期：2017/5/8
	 */
	@Override
	public List<CompanyEntity> getOuterCooperatorCompany(String companyId, String projectId) {
		Map<String,Object> map = new HashMap<>();
		map.put("companyId",companyId);
		map.put("projectId",projectId);
		return this.sqlSession.selectList("CompanyEntityMapper.getOuterCooperatorCompany",map);
	}

	/**
	 * 方法描述：获取项目合作的组织
	 * 作者：MaoSF
	 * 日期：2017/6/8
	 */
	@Override
	public List<CompanyEntity> getCompanyByProjectId(String projectId) {
		return this.sqlSession.selectList("CompanyEntityMapper.getProjectCooperator",projectId);
	}

	@Override
	public List<String> listCompanyIdByProjectId(String projectId) {
		return this.sqlSession.selectList("CompanyEntityMapper.listCompanyIdByProjectId",projectId);
	}

	@Override
	public String getCompanyName(String id) {
		List<String> names = this.sqlSession.selectList("CompanyEntityMapper.getAliasName",id);
		if(names.size()>0){
			return names.get(0);
		}
		return null;
	}
}
