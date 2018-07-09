package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dao.DepartDao;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.dto.DepartDataDTO;
import com.maoding.org.dto.UserDepartDTO;
import com.maoding.org.entity.DepartEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartDaoImpl
 * 类描述：部门 DaoImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:44:06
 */
@Service("departDao")
public class DepartDaoImpl extends GenericDao<DepartEntity> implements DepartDao{

	@Override
	public int insert(DepartEntity entity){
		int departSeq = this.sqlSession.selectOne("DepartEntityMapper.getmaxDepartSeq");
		entity.setDepartSeq(departSeq);
		return  super.insert(entity);
	}

    @Override
	public List<DepartDataDTO> getDepartByCompanyId(Map<String,Object>paraMap){
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.selectDepartBycompanyId",paraMap);
	}

	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 *
	 * @param paraMap （companyId（公司ID）,userId（用户Id））
	 */
	@Override
	public List<DepartEntity> getDepartByUserIdContentCompany(Map<String, Object> paraMap) {
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.getDepartByUserIdContentCompany",paraMap);
	}

	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司getDepartByUserIdContentCompanyInterface
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 *
	 * @param paraMap （companyId（公司ID）,userId（用户Id））
	 */
	@Override
	public List<DepartEntity> getDepartByUserIdContentCompanyInterface(Map<String, Object> paraMap) {
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.getDepartByUserIdContentCompanyInterface",paraMap);
	}


	@Override
	public DepartEntity getByDepartNameAndPid(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return this.sqlSession.selectOne("DepartEntityMapper.selectByDepartNameAndPid",paraMap);
	}

	@Override
	public List<DepartEntity> getDepartsByDepartPath(String departPath) {
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.selectDepartsByDepartPath",departPath);
	}

	@Override
	public List<DepartEntity> getParentDepartsByDepartPath(String departPath) {
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.getParentDepartsByDepartPath",departPath);
	}

	@Override
	public List<DepartEntity> selectDepartNodesByCompanyIds(
			Map<String, Object> param) {
		return this.sqlSession.selectList("DepartEntityMapper.selectDepartNodesByCompanyIds",param);
	}

	@Override
	public List<Map<String, Object>> selectDepartEdgesByCompanyIds(
			Map<String, Object> param) {
		return this.sqlSession.selectList("DepartEntityMapper.selectDepartEdgesByCompanyIds",param);
	}


	/**
	 * 方法描述：根据departPath删除（批量删除，删除自己及所有子部门）
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午8:21:40
	 */
	@Override
	public int deleteByDepartPath(String departPath) {
		return this.sqlSession.update("DepartEntityMapper.deleteByDepartPath",departPath);
	}


	@Override
	public List<DepartEntity> getDepartByCompanyIdWS(Map<String,Object>paraMap){
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.selectDepartBycompanyIdWS",paraMap);
	}

	@Override
	public List<DepartEntity> selectDepartByParam(Map<String,Object> paraMap){
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.selectDepartByParam",paraMap);
	}

	@Override
	public List<DepartEntity> selectStairDepartCompanyId(Map<String,Object> paraMap){
		return this.sqlSession.selectList("GetDepartByCompanyIdMapper.selectStairDepartCompanyId",paraMap);
	}

	/**
	 * 方法描述：获取我所在一级部门群
	 * 作者：MaoSF
	 * 日期：2017/1/13
	 */
	@Override
	public String getOwnDepartGroupId(Map<String, Object> paraMap) {
		return this.sqlSession.selectOne("GetDepartByCompanyIdMapper.getOwnDepartGroupId",paraMap);
	}

	/**
	 * 方法描述：获取部门全路径
	 * 作者：MaoSF
	 * 日期：2017/2/6
	 */
	@Override
	public String getDepartFullName(String id) {
		return this.sqlSession.selectOne("GetDepartByCompanyIdMapper.getDepartFullName",id);
	}
}
