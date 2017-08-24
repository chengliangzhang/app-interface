package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dao.TeamOperaterDao;
import com.maoding.org.entity.TeamOperaterEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：TeamOperaterDaoImpl
 * 类描述：类描述：组织(公司）DaoImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:44:06
 */
@Service("teamOperaterDao")
public class TeamOperaterDaoImpl extends GenericDao< TeamOperaterEntity> implements TeamOperaterDao{

	
	@Override
	public TeamOperaterEntity getTeamOperaterByCompanyId(String companyId,String userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", companyId);
		param.put("userId", userId);
		return this.sqlSession.selectOne("TeamOperaterEntityMapper.getTeamOperaterByCompanyId", param);
	}

	/**
	 * 方法描述：根据companyId查找【一个组织一个管理员】,理论上有且只有一条数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-上午10:36:11
	 *
	 * @param companyId
	 * @return
	 */
	@Override
	public List<TeamOperaterEntity> selectTeamOperaterByCompanyId(String companyId) {
		return this.sqlSession.selectList("TeamOperaterEntityMapper.selectTeamOperaterByCompanyId", companyId);
	}

	/**
	 * 方法描述：获取整个系统的系统管理员（用户初始化云端的系统管理员角色）（2016-11-07打包，添加系统角色）
	 * 作者：MaoSF
	 * 日期：2016/11/7
	 *
	 * @param:
	 * @return:
	 */
	@Override
	public List<TeamOperaterEntity> getAllTeamOperater() {
		return this.sqlSession.selectList("TeamOperaterEntityMapper.getAllTeamOperater");
	}

	@Override
	public List<TeamOperaterEntity> selectByParam(Map<String,Object> param) {
		return this.sqlSession.selectList("TeamOperaterEntityMapper.selectByParam", param);
	}


}
