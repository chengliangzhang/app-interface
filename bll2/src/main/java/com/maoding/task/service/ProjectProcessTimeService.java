package com.maoding.task.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.task.dto.ProjectProcessTimeDTO;
import com.maoding.task.entity.ProjectProcessTimeEntity;

import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskService
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
public interface ProjectProcessTimeService extends BaseService<ProjectProcessTimeEntity>{

    /**
     * 方法描述：保存变更
     * 作者：MaoSF
     * 日期：2016/12/30
     * @param:
     * @return:
     */
    public ResponseBean saveProjectProcessTime(ProjectProcessTimeDTO dto) throws Exception;

    /**
     * 方法描述：获取任务的变更时间（计划时间）
     * 作者：MaoSF
     * 日期：2016/12/30
     * @param:
     * @return:
     */
    public ResponseBean getProjectProcessTime(Map<String,Object> map) throws Exception;

    /**
     * 方法描述：删除变更
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    public ResponseBean deleteProjectProcessTime(String id) throws Exception;
}
