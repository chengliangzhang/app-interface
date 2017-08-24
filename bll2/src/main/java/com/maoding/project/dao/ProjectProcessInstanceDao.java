package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectProcessInstanceEntity;

import java.util.List;


/**
 * Created by Wuwq on 2016/10/27.
 */
public interface ProjectProcessInstanceDao extends BaseDao<ProjectProcessInstanceEntity> {

    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2016/10/27
     * @param:
     * @return:
     */
    public ProjectProcessInstanceEntity getProjectProcessInstanceByProcessId(String processId);

    /**
     * 方法描述：根据任务id查找流程实例
     * 作者：MaoSF
     * 日期：2016/10/27
     * @param:
     * @return:
     */
    public List<ProjectProcessInstanceEntity> getProjectProcessInstanceByTaskManageId(String taskTaskManageId);

    /**
     * 方法描述：根据流程id删除流程实例
     * 作者：MaoSF
     * 日期：2016/10/31
     * @param:
     * @return:
     */
    public int deleteByProcessId(String processId);
}
