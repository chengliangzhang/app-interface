package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectProcessNodeEntity;

import java.util.List;
import java.util.Map;


/**
 * Created by Wuwq on 2016/10/27.
 */
public interface ProjectProcessNodeDao extends BaseDao<ProjectProcessNodeEntity> {

    List<ProjectProcessNodeEntity> selectByProcessId(String id);

    List<ProjectProcessNodeEntity> selectByParam(Map<String, Object> paraMap);


    /**
     * 方法描述：获取节点（taskId）
     * 作者：MaoSF
     * 日期：2017/5/20
     */
    List<ProjectProcessNodeEntity> getProcessNodeByParam(Map<String, Object> paraMap);


    /**
     * 方法描述：获取所有的数据
     * 作者：MaoSF
     * 日期：2016/11/6
     */
    List<ProjectProcessNodeEntity> selectNodeByTaskManageId(String taskManageId);

    /**
     * 方法描述：根据processId,companyUserId,seq,查询节点（一个节点，一个companyUserId只会产生一条数据）
     * 作者：MaoSF
     * 日期：2017/2/24
     */
    ProjectProcessNodeEntity getNodeByCompanyUserAndSeq(String processId, String companyUserId, int seq);

    /**
     * 方法描述：设置完成时间
     * 作者：MaoSF
     * 日期：2017/3/11
     */
    int updateProcessNodeComplete(String id);

}
