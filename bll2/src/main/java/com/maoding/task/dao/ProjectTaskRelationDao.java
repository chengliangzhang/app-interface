package com.maoding.task.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.task.entity.ProjectTaskRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskRelationEntity
 * 类描述：项目任务（任务签发组织之间的关系）
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
public interface ProjectTaskRelationDao extends BaseDao<ProjectTaskRelationEntity> {
    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/5/3
     * @param:
     * @return:
     */
    List<ProjectTaskRelationEntity> getTaskRelationParam(Map<String, Object> param);

    /**
     * 方法描述：根据taskId删除关系
     * 作者：MaoSF
     * 日期：2017/1/3
     * @param:
     * @return:
     */
    int deleteProjectTaskRelationByTaskId(String taskId);

    /**
     * 方法描述：根据taskId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     * @param:
     * @return:
     */
    ProjectTaskRelationEntity getProjectTaskRelationByTaskId(String taskId);

    /**
     * 方法描述：根据projectId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     * @param:
     * @return:
     */
    List<ProjectTaskRelationEntity> getProjectTaskRelationByProjectId(String projectId);


    /**
     * 方法描述：根据projectId,fromCompanyId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     * @param:
     * @return:
     */
    List<ProjectTaskRelationEntity> getProjectTaskRelationByFromCompanyId(String projectId,String fromCompanyId);

    /**
     * 方法描述：根据taskId.list集合，更新relationStatus
     * 作者：MaoSF
     * 日期：2017/1/5
     * @param:
     * @return:
     */
    int updateTaskRelationStatus(Map<String, Object> map);

    /**
     * 方法描述：查询某任务整条线被签发的次数
     * 作者：MaoSF
     * 日期：2017/1/5
     * @param:
     * @return:
     */
    int getTaskIssueCount(Map<String, Object> map);


    /**
     * 方法描述：用于合作设计费查询界面使用
     * 作者：MaoSF
     * 日期：2017/3/6
     *
     * @param map
     * @param:
     * @return:
     */
    List<Map<String,String>> getProjectRelation(Map<String, Object> map);

    /**
     * 方法描述：根据projectId,fromCompanyId获取关系
     * 作者：MaoSF
     * 日期：2017/1/4
     * @param:
     * @return:
     */
    List<ProjectTaskRelationEntity> getProjectTaskRelationByCompanyId(String projectId,String companyId);
}