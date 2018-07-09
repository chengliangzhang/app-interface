package com.maoding.org.dao.impl;


import com.maoding.commonModule.dto.QueryCopyRecordDTO;
import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.util.ChineseToEnglishUtil;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyUserEntity;
import org.springframework.stereotype.Service;

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
@Service("companyUserDao")
public class CompanyUserDaoImpl extends GenericDao<CompanyUserEntity> implements CompanyUserDao{

	@Override
	public int insert(CompanyUserEntity entity){
		entity.setPinYin(ChineseToEnglishUtil.getPingYin(entity.getUserName()));
		int seq = this.sqlSession.selectOne("CompanyUserEntityMapper.getMaxCompanyUserSeq");
		entity.setSeq(seq);
		return  super.insert(entity);
	}

	@Override
	public int updateById(CompanyUserEntity entity){
		entity.setPinYin(ChineseToEnglishUtil.getPingYin(entity.getUserName()));
		return  super.updateById(entity);
	}

	@Override
	public CompanyUserEntity getCompanyUserByUserIdAndCompanyId(String userId, String companyId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("companyId", companyId);
		return this.sqlSession.selectOne("CompanyUserEntityMapper.getCompanyUserByUserIdAndCompanyId", map);
	}

	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public List<CompanyUserEntity> getCompanyUserByCompanyId(String companyId){
		return this.sqlSession.selectList("CompanyUserEntityMapper.getCompanyUserByCompanyId", companyId);
	}


	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public CompanyUserDetailDTO getCompanyUserById(String id) throws Exception {
		return this.sqlSession.selectOne("GetCompanyUserByIdMapper.getCompanyUserById", id);
	}

	/**
	 * 方法描述：根据id查询数据（带状态）
	 * 作        者：ZCL
	 * 日        期：2018-4-19
	 *
	 * @param id companyUserId
	 * @return 带状态companyUser数据
	 */
	@Override
	public CompanyUserDetailDTO getCompanyUserWithStatusById(String id) throws Exception {
		return this.sqlSession.selectOne("GetCompanyUserByIdMapper.getCompanyUserWithStatusById", id);
	}

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId)【orgId组织Id，companyId 公司Id】
	 */
	@Override
	public List<CompanyUserDataDTO> getUserByOrgId (Map<String,Object> param) throws Exception{
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getUserByOrgId", param);
	}

	@Override
	public List<CompanyUserDataDTO> getDepartAllUser (Map<String,Object> param) throws Exception{
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getDepartAllUser", param);
	}

	@Override
	public List<CompanyUserDataDTO> getCopyUser(QueryCopyRecordDTO query) {
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getCopyUser", query);
	}


	/**
	 * 方法描述：组织人员部门及权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	@Override
	public List<UserDepartDTO> getCompanyUserDepartRole(String userId, String companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId",userId);
		param.put("companyId",companyId);
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getCompanyUserDepartRole", param);
	}
	/**
	 * 方法描述：组织人员部门及权限Interface
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	@Override
	public List<UserDepartDTO> getCompanyUserDepartRoleInterface(String userId, String companyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId",userId);
		param.put("companyId",companyId);
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getCompanyUserDepartRoleInterface", param);
	}

	@Override
	public List<Map<String, Object>>selectPersonalInGroupAndInfo(Map<String, Object>map){
		return this.sqlSession.selectList("CompanyUserEntityMapper.selectPersonalInGroupAndInfo",map);
	}


	@Override
	public List<CompanyUserEntity> getCompanyUserId(String userId){
		return  this.sqlSession.selectList("CompanyUserEntityMapper.getCompanyByUserId",userId);
	}


	/**
	 * 方法描述：根据角色id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	@Override
	public List<CompanyUserDataDTO> getCompanyUserByPermissionId(Map<String, Object> param) {
		return  this.sqlSession.selectList("GetCompanyUserByRoleIdMapper.getCompanyUserByPermissionId",param);
	}

	/**
	 * 方法描述：根据orgList查询人员的账号id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 */
	@Override
	public List<String> getUserByOrgIdForNotice(Map<String, Object> param) {
		return  this.sqlSession.selectList("GetUserByOrgIdForNoticeMapper.getUserByOrgIdForNotice",param);
	}

	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 *
	 * @param param
	 * @return
	 */
	@Override
	public List<CompanyUserAppDTO> getCompanyUser(Map<String, Object> param) {
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getCompanyUser", param);
	}
	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public List<CompanyUserAppDTO> getCompanyUserExceptMe(QueryCompanyUserDTO query) {
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.getCompanyUserExceptMe", query);
	}

	/**
	 * 方法描述：查找人员
	 * 作        者：ZCL
	 * 日        期：2018-4-24
	 *
	 * @param query
	 */
	@Override
	public List<CompanyUserAppDTO> listCompanyUser(QueryCompanyUserDTO query) {
		return this.sqlSession.selectList("GetCompanyUserByOrgIdMapper.listCompanyUser", query);
	}

	/**
	 * 方法描述：查询员工简单信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public CompanyUserAppDTO getCompanyUserByUserId(Map<String, Object> param) {
		return this.sqlSession.selectOne("GetCompanyUserByOrgIdMapper.getCompanyUserByUserId", param);
	}

	/**
	 * 方法描述：获取当前用户所在的团队及团对中的权限（用于组织切换）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	@Override
	public List<CompanyRoleDataDTO> getCompanyRole(Map<String, Object> param) {
		return  this.sqlSession.selectList("CompanyUserEntityMapper.getCompanyRole",param);
	}



	@Override
	public String getUserNameByCompanyIdAndUserId(String companyId, String userId) {
		return getUserName(getCompanyUserByUserIdAndCompanyId(userId,companyId));
	}

	public String getUserName(CompanyUserEntity companyUser) {
		if (companyUser == null){
			return "";
		}
		return (StringUtil.isNullOrEmpty(companyUser.getUserName())) ? "" : companyUser.getUserName();
	}

	@Override
	public ImUserInfoDTO getImUserInfo(ImUserInfoQueryDTO queryDTO) {
		return this.sqlSession.selectOne("GetImUserInfoMapper.getImUserInfo", queryDTO);
	}

	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	@Override
	public List<CompanyUserExpMemberDTO> getExpTaskMembers(Map<String, Object> paraMap) {
		return this.sqlSession.selectList("CompanyUserEntityMapper.getExpTaskMembers", paraMap);
	}

	@Override
	public List<CompanyUserExpMemberDTO> getApplyExpUserForCopy(Map<String, Object> paraMap) {
		return this.sqlSession.selectList("CompanyUserEntityMapper.getApplyExpUserForCopy", paraMap);
	}

	@Override
	public String getUserName(String companyUserId) {
		CompanyUserEntity user = this.selectById(companyUserId);
		if(user!=null){
			return user.getUserName();
		}
		return null;
	}

	@Override
	public List<CompanyUserDataDTO> getCompanyUserByPermissionCode(Map<String, Object> param) {
		return  this.sqlSession.selectList("GetCompanyUserByRoleIdMapper.getCompanyUserByPermissionCode",param);
	}
}
