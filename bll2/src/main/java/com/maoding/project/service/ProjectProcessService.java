package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.ProjectProcessDTO;
import com.maoding.project.entity.ProjectProcessEntity;

/**
 * Created by Wuwq on 2016/10/27.
 */
public interface ProjectProcessService{

    /**新建或更新流程*/
    ResponseBean saveOrUpdateProcess(ProjectProcessDTO dto) throws Exception;


    /**
     * 方法描述：根据taskId删除流程
     * 作者：MaoSF
     * 日期：2016/10/31
     * @param:
     * @return:
     */
    ResponseBean deleteProcessByTaskId(String taskId) throws Exception;

    /**
     * 方法描述：删除节点
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    void deleteProcessNodeById(String id) throws Exception;

    /**
     * 方法描述：设校审节点完成
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    ResponseBean completeProjectProcessNode(String projectId, String companyId, String nodeId, String taskId, String accountId) throws Exception;

    /**
     * 方法描述：保存流程节点
     * 作者：MaoSF
     * 日期：2017/6/22
     */
    String saveProjectProcessNode(String projectId,int seq,int nodeSeq,String taskId,String companyUserId) throws Exception;
}
