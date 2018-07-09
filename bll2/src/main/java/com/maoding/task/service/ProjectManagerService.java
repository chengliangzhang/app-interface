package com.maoding.task.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.task.dto.TransferTaskDesignerDTO;
import com.maoding.task.entity.ProjectManagerEntity;

import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectManagerService
 * 类描述：项目经营人、负责人
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
public interface ProjectManagerService {

    /**
     * 方法描述：移交经营负责人，项目负责人
     * 作者：MaoSF
     * 日期：2017/1/5
     * @param:
     * @return:
     */
    ResponseBean transferManager(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：移交设计负责人
     * 作者：MaoSF
     * 日期：2017/3/22
     * @param:
     * @return:
     */
    ResponseBean transferTaskDesinger(TransferTaskDesignerDTO dto) throws Exception;

    /**
     * 方法描述：删除经营负责人和设计负责人
     * 作者：MaoSF
     * 日期：2017/4/12
     * @param:
     * @return:
     */
    ResponseBean deleteProjectManage(String projectId,String companyId) throws Exception;

}
