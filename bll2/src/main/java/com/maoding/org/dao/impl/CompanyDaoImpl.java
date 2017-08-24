package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyInviteDao;
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

	@Autowired
	private CompanyInviteDao companyInviteDao;

	@Override
	public int insert(CompanyEntity entity){
		this.rolePermissionDao.deleteByCompanyId(entity.getId());
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
	public CompanyEntity getCompanyByCompanyName(String companyName) {
		return this.sqlSession.selectOne("CompanyEntityMapper.getCompanyByCompanyName", companyName);
	}

	/**
	 * 方法描述：根据公司简称查找公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月7日-下午4:16:44
	 *
	 * @param companyShortName
	 * @return
	 */
	@Override
	public CompanyEntity getCompanyByCompanyShortName(String companyShortName) {
		return this.sqlSession.selectOne("CompanyEntityMapper.getCompanyByCompanyShortName", companyShortName);
	}

	@Override
	public List<CompanyDTO> getAdminOfCompanyByUserId(String userId) {
		return this.sqlSession.selectList("GetAdminOfCompanyByUserIdMapper.getAdminOfCompanyByUserId", userId);
	}

	@Override
	public List<CompanyDTO> getCompanyByUserId(String userId) {
		return this.sqlSession.selectList("GetCompanyByUserIdMapper.getCompanyByUserId", userId);
	}

	@Override
	public List<CompanyDTO> getCompanyByUserIdWS(String userId) {
		return this.sqlSession.selectList("GetCompanyByUserIdWSMapper.getCompanyByUserId", userId);
	}

	/**
	 * 方法描述：获取所有公司（全部公司注册环信群，手工操作）
	 * 作者：MaoSF
	 * 日期：2016/8/23
	 *
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyEntity> selectAll() {
		return this.sqlSession.selectList("CompanyEntityMapper.selectAll");
	}

	/**
	 * 方法描述：获取所有公司（全部公司注册环信群，手工操作）
	 * 作者：MaoSF
	 * 日期：2016/8/23
	 *
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyEntity> selectAllIm() {
		return this.sqlSession.selectList("CompanyEntityMapper.selectAllIm");
	}

	@Override
	public List<CompanyEntity> getImAllCompany() {
		return this.sqlSession.selectList("CompanyEntityMapper.getImAllCompany");
	}

	/**
	 * 方法描述：获取挂靠的公司
	 * 作者：MaoSF
	 * 日期：2017/1/16
	 *
	 * @param id
	 * @param:
	 * @return:
	 */
	@Override
	public CompanyEntity getParentCompanyById(String id) {
		return this.sqlSession.selectOne("CompanyEntityMapper.getParentCompanyById",id);
	}

	/**
	 * 方法描述：获取具有管理员权限的公司
	 * 作者：MaoSF
	 * 日期：2017/2/28
	 *
	 * @param map
	 * @param:userId,fastDfs
	 * @return:
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
	 *
	 * @param map
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyDTO> getCompanyFilterbyParamForInvit(Map<String, Object> map) {
		return this.sqlSession.selectList("GetCompanyFilterbyParamMapper.getCompanyFilterbyParamForInvit", map);
	}

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）总条数
	 * 作者：MaoSF
	 * 日期：2017/1/17
	 *
	 * @param map
	 * @param:
	 * @return:
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
	public CompanyEntity getParentCompany(String id) throws Exception {
		return this.sqlSession.selectOne("QueryOrgTreeEntityMapper.getParentCompany", id);
	}

	@Override
	public List<CompanyEntity> getChilrenCompany(String id) {
		return this.sqlSession.selectList("QueryOrgTreeEntityMapper.getChilrenCompany", id);
	}

	/**
	 * 方法描述：项目统计（新增合同，合同回款）统计，项目承接人查询
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyDTO> getCompanyForProjectStatics(String companyId) {
		return this.sqlSession.selectList("GetCompanyForProjectStaticsMapper.getCompanyForProjectStatics", companyId);
	}

	/**
	 * 方法描述： 技术审查费统计(付款方向），技术审查人选项
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyDTO> getCompanyForProjectStatics2(String companyId) {
		return this.sqlSession.selectList("GetCompanyForProjectStaticsMapper.getCompanyForProjectStatics2", companyId);
	}

	/**
	 * 方法描述：  合作设计费统计(付款方向）
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyDTO> getCompanyForProjectStatics3(String companyId) {
		return this.sqlSession.selectList("GetCompanyForProjectStaticsMapper.getCompanyForProjectStatics3", companyId);
	}



	/**
	 * 方法描述：  合作设计费统计(收款方向）
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyDTO> getCompanyForProjectStatics4(String companyId) {
		return this.sqlSession.selectList("GetCompanyForProjectStaticsMapper.getCompanyForProjectStatics4", companyId);
	}

	/**
	 * 方法描述：查询了公司信息及logo
	 * 作者：MaoSF
	 * 日期：2017/3/3
	 *
	 * @param id
	 * @param:
	 * @return:
	 */
	@Override
	public CompanyEntity getCompanyMsgById(String id) {
		return this.sqlSession.selectOne("QueryOrgTreeEntityMapper.getCompanyMsgById",id);
	}

	/**
	 * 方法描述：获取项目的外部团队
	 * 作者：MaoSF
	 * 日期：2017/5/8
	 *
	 * @param companyId
	 * @param projectId
	 * @param:int
	 * @return:
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
		return this.sqlSession.selectOne("CompanyEntityMapper.getAliasName",id);
	}
}
