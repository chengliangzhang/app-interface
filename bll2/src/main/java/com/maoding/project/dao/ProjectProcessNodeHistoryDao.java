package com.maoding.project.dao;
import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectProcessNodeHistoryEntity;

import java.util.List;


/**
 * Created by Wuwq on 2016/10/27.
 */
public interface ProjectProcessNodeHistoryDao extends BaseDao<ProjectProcessNodeHistoryEntity> {

    /**
     * 方法描述：根据nodeId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     * @param:
     * @return:
     */
    public  ProjectProcessNodeHistoryEntity getLastUpdateProcessNode(String processNodeId);


    /**
     * 方法描述：根据processId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     * @param:
     * @return:
     */
    public  ProjectProcessNodeHistoryEntity getLastProjectProcessNodeHistory(String processId);


    /**
     * 方法描述：根据nodeId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     * @param:
     * @return:
     */
    public  int  getMaxSeqByProcessInstanceId(String processInstanceId);


    public ProjectProcessNodeHistoryEntity getLastUpdateProcessInstanceId(String processInstanceId);

    public int deleteByProcessInstanceId(String processInstanceId);

    /**
     * 方法描述：根据流程实例获取流程历史记录
     * 作者：MaoSF
     * 日期：2016/11/1
     * @param:
     * @return:
     */
    public List<ProjectProcessNodeHistoryEntity> getByProcessInstanceId(String processInstanceId);

}
