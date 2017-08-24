package com.maoding.task.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.task.dao.ProjectTaskRelationDao;
import com.maoding.task.entity.ProjectTaskRelationEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskRelationEntity
 * 类描述：项目任务（任务签发组织之间的关系）
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
@Service("projectTaskRelationDao")
public  class ProjectTaskRelationDaoImpl extends GenericDao<ProjectTaskRelationEntity> implements ProjectTaskRelationDao {


    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/5/3
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskRelationEntity> getTaskRelationParam(Map<String, Object> param) {
        return  this.sqlSession.selectList("ProjectTaskRelationEntityMapper.selectByParam",param);
    }

    /**
     * 方法描述：根据taskId删除关系
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param taskId
     * @param:
     * @return:
     */
    @Override
    public int deleteProjectTaskRelationByTaskId(String taskId) {
        return this.sqlSession.delete("ProjectTaskRelationEntityMapper.deleteProjectTaskRelationByTaskId",taskId);
    }

    /**
     * 方法描述：根据taskId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param taskId
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskRelationEntity getProjectTaskRelationByTaskId(String taskId) {
        return this.sqlSession.selectOne("ProjectTaskRelationEntityMapper.getProjectTaskRelationByTaskId",taskId);
    }

    /**
     * 方法描述：根据projectId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskRelationEntity> getProjectTaskRelationByProjectId(String projectId) {
        return this.sqlSession.selectList("ProjectTaskRelationEntityMapper.getProjectTaskRelationByProjectId",projectId);
    }

    /**
     * 方法描述：根据projectId,fromCompanyId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param projectId
     * @param fromCompanyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskRelationEntity> getProjectTaskRelationByFromCompanyId(String projectId, String fromCompanyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("fromCompanyId",fromCompanyId);
        return this.sqlSession.selectList("ProjectTaskRelationEntityMapper.getProjectTaskRelationByFromCompanyId",map);
    }

    /**
     * 方法描述：根据taskId.list集合，更新relationStatus
     * 作者：MaoSF
     * 日期：2017/1/5
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public int updateTaskRelationStatus(Map<String, Object> map) {
        return this.sqlSession.update("ProjectTaskRelationEntityMapper.updateTaskRelationStatus",map);
    }

    /**
     * 方法描述：查询某任务整条线被签发的次数
     * 作者：MaoSF
     * 日期：2017/1/5
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public int getTaskIssueCount(Map<String, Object> map) {
        return this.sqlSession.selectOne("ProjectTaskRelationEntityMapper.getTaskIssueCount",map);
    }


    /**
     * 方法描述：用于合作设计费查询界面使用
     * 作者：MaoSF
     * 日期：2017/3/6
     *
     * @param map
     * @param:
     * @return:
     */
    @Override
    public List<Map<String,String>> getProjectRelation(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectTaskRelationEntityMapper.getProjectRelation",map);
    }


    /**
     * 方法描述：根据projectId,fromCompanyId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskRelationEntity> getProjectTaskRelationByCompanyId(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("ProjectTaskRelationEntityMapper.getProjectTaskRelationByCompanyId",map);
    }
}