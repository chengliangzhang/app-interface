package com.maoding.projectcost.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.projectcost.dto.ProjectCostDTO;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointDTO;
import com.maoding.projectcost.dto.ProjectCostPointDetailDTO;
import com.maoding.projectcost.entity.ProjectCostEntity;

import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostService
 * 类描述：费用service
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectCostService extends BaseService<ProjectCostEntity>{


    /**
     * 方法描述：设置合同总金额
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean saveOrUpdateProjectCost(ProjectCostDTO projectCostDto)throws Exception;


    /**
     * 方法描述：添加修改收款节点
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean saveOrUpdateProjectCostPoint(ProjectCostPointDTO projectCostPointDTO)throws Exception;


  /**
   * 方法描述：其他费用添加
   * 作者：MaoSF
   * 日期：2017/3/30
   * @param:
   * @return:
   */
    ResponseBean saveOtherProjectCostPoint(ProjectCostPointDTO projectCostPointDTO)throws Exception;

    /**
     * 方法描述：发起收款
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean saveOrUpdateReturnMoneyDetail(ProjectCostPointDetailDTO projectCostPointDetailDTO)throws Exception;

    /**
     * 方法描述：发起收款
     * 作者：ZhangChengliang
     * 日期：2017/4/20
     */
    ResponseBean saveOtherCostDetail(ProjectCostPointDetailDTO projectCostPointDetailDTO)throws Exception;
    /**
     * 方法描述：查询合同回款
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean getContractInfo(ProjectCostPaymentDetailDTO projectCostPaymentDetailDTO)throws Exception;

    /**
     * 方法描述：查询节点明细
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean getPointInfo(ProjectCostPointDTO projectCostPointDTO)throws Exception;

    /**
     * 方法描述：删除费用（目前界面上没有删除操作。用于删除签发的任务时候，如果不存在签发的记录，则合作设计费删除）
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:
     * @return:
     */
    ResponseBean deleteProjectCost(String id, String accountId) throws Exception;

    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:
     * @return:
     */
    ResponseBean deleteProjectCostPoint(String id,String accountId) throws Exception;

  /**
   * 方法描述：删除发起收款明细节点
   * 作者：MaoSF
   * 日期：2017/3/2
   *
   * @param id
   * @param:
   * @return:
   */
  ResponseBean deleteProjectCostPointDetail(String id, String accountId) throws Exception;

  /**
   * 方法描述：删除收款明细节点
   * 作者：MaoSF
   * 日期：2017/4/27
   *
   * @param id
   * @param accountId
   * @param:
   * @return:
   */
  ResponseBean deleteProjectCostPaymentDetail(String id, String accountId) throws Exception;

    /**
     * 方法描述：根据taskId删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:
     * @return:
     */
    ResponseBean deleteProjectCostPointByTaskId(String taskId) throws Exception;

    /**
     * 方法描述：根据taskId删除，用于设计阶段取消使用
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:
     * @return:
     */
    ResponseBean deleteProjectCostPointForDesignContent(String taskId) throws Exception;

    /**
     * 方法描述：合同乙方更改技术审查费
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:flag(1:重新添加，2.全部删除，4.先删除后添加）
     * @return:
     */
    ResponseBean handPartBChange(String id,String accountId,int flag) throws Exception;



    /**
     * 方法描述：查询技术审查费（projectId）
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean getTechicalReviewFeeInfo(Map<String,Object> map)throws Exception;


    /**
     * 方法描述：合作设计费（projectId，companyId:当前公司id）
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean getCooperativeDesignFeeInfo(Map<String,Object> map)throws Exception;

    /**
     * 方法描述：查询合同回款(map:projectId)map.put("type"="4"：付款，5：收款);
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    ResponseBean getOtherFee(Map<String,Object> map)throws Exception;

    /**
     * 方法描述：合作设计费（技术审查费）详情
     * 作者：MaoSF
     * 日期：2017/3/9
     * @param:map(pointId,appOrgId,accountId)
     * @return:
     */
    ResponseBean getProjectCostPointDetail(Map<String,Object> map)throws Exception;


  /**
   * 方法描述：保存付款或到款明细
   * 作者：wrb
   * 日期：2017/4/26
   */
  ResponseBean saveCostPaymentDetail(ProjectCostPaymentDetailDTO dto)throws Exception;

  /**
   * 方法描述：合作设计费（技术审查费）付款，详情(合同回款，其他费用)，到款，付款
   * 作者：MaoSF
   * 日期：2017/3/9
   *
   * @param:map(pointDetailId,taskType)
   * @return:
   */

  ResponseBean getProjectCostPointDetailForMyTask(String paymentDetailId, String pointDetailId, int taskType,String companyId) throws Exception;

  /**
   * 方法描述：修改付款或到款明细
   * 作者：wrb
   * 日期：2017/4/26
   */
  ResponseBean updateCostPaymentDetail(ProjectCostPaymentDetailDTO dto)throws Exception;
}
