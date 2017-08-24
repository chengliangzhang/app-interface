package com.maoding.project.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectPointDao;
import com.maoding.project.entity.ProjectPointEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：设计阶段
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@SuppressWarnings("rawtypes")
@Service("projectPointDao")
public class ProjectPointDaoImpl extends GenericDao<ProjectPointEntity> implements ProjectPointDao {

	@Override
	public int insert(ProjectPointEntity entity){
		int seq = this.getProjectPointMaxSeq(entity.getProjectId());
		entity.setSeq(seq);
		return  super.insert(entity);
	}

	@Override
	public int deleteByProjectId(String projectId) {
		return this.sqlSession.delete("ProjectPointEntityMapper.deleteByProjectId", projectId);
	}

	@Override
	public List<ProjectPointEntity> getProjectPointListByProjectId(String projectId) {
		return this.sqlSession.selectList("ProjectPointEntityMapper.selectByProjectId", projectId);
	}

	@Override
	public List<ProjectPointEntity> getRPListByProjectIdFilterIds(Map map) {
		return this.sqlSession.selectList("ProjectPointEntityMapper.selectByProjectIdFilterIds", map);
	}

	/**
	 * 方法描述：获取最大的seq
	 * 作者：MaoSF
	 * 日期：2016/11/24
	 *
	 * @param projectId
	 * @param:
	 * @return:
	 */
	@Override
	public int getProjectPointMaxSeq(String projectId) {
		return this.sqlSession.selectOne("ProjectPointEntityMapper.getProjectPointMaxSeq", projectId);
	}




}
