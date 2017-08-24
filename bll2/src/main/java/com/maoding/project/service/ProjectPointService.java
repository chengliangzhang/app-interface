package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectPointChangeSeqDTO;
import com.maoding.project.dto.ProjectPointDTO;
import com.maoding.project.entity.ProjectPointEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectPointService
 * 类描述：项目合同节点Service
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectPointService extends BaseService<ProjectPointEntity>{

    /**
     * 方法描述：保存合同节点
     * 作者：MaoSF
     * 日期：2016/8/1
     * @param:
     * @return:
     */
    public AjaxMessage saveOrUpdateProjectPoint(ProjectPointDTO dto) throws Exception ;

    
    /**
     * 方法描述：删除合同节点
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    public AjaxMessage deleteProjectPoint(String id) throws Exception ;

    /**
     * 方法描述：新增，修改，删除 合同回款 费用后返回的数据
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    public ProjectDTO getProjectPointDetail(String projectId, ProjectDTO projectDTO) throws Exception;


    /**
     * 方法描述：交换两个对象的位置
     * 作者：MaoSF
     * 日期：2016/11/24
     * @param:
     * @return:
     */
    public AjaxMessage changeProjectPoint(ProjectPointChangeSeqDTO dto) throws Exception ;


}
