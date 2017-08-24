package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.project.dto.ProjectFeeDTO;
import com.maoding.project.dto.ProjectPointDTO;
import com.maoding.project.entity.ProjectFeeEntity;
import com.maoding.project.entity.ProjectPointEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectPointService
 * 类描述：项目合同节点Service
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectFeeService extends BaseService<ProjectFeeEntity>{

    /**
     * 方法描述：保存费用节点
     * （ProjectFeeDTO--type:1合同回款开票,2合同回款收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款））
     * 作者：MaoSF
     * 日期：2016/8/1
     * @param:
     * @return:
     */
    public AjaxMessage saveOrUpdateProjectFee(ProjectFeeDTO dto) throws Exception ;

    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    public AjaxMessage deleteProjectFee(String id, String companyId,String userId) throws Exception ;

    /**
     * 方法描述：根据节点查询费用
     * 作        者：MaoSF
     * 日        期：2015年8月10日-下午4:18:42
     * @param： pointId，type：1:开票，2：收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款
     * @return
     */
    public List<ProjectFeeEntity> getProjectFeeByPointAndType(String pointId, String type) throws Exception ;

}
