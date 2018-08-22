package com.maoding.projectcost.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.invoice.dto.InvoiceEditDTO;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.projectcost.dto.*;
import com.maoding.projectcost.entity.ProjectCostEntity;

import java.util.List;
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
    ResponseBean saveOrUpdateProjectCost(ProjectCostEditDTO projectCostDto)throws Exception;

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
   * 项目费用申请付款
   */
    ResponseBean applyProjectCostPayFee(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception;

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
     */
    ResponseBean deleteProjectCost(String id, String accountId) throws Exception;

    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     */
    ResponseBean deleteProjectCostPoint(String id,String accountId) throws Exception;

  /**
   * 方法描述：删除发起收款明细节点
   * 作者：MaoSF
   * 日期：2017/3/2
   */
  ResponseBean deleteProjectCostPointDetail(String id, String accountId) throws Exception;

  /**
   * 方法描述：删除收款明细节点
   * 作者：MaoSF
   * 日期：2017/4/27
   */
  ResponseBean deleteProjectCostPaymentDetail(String id, String accountId,String currentCompanyId) throws Exception;

    /**
     * 方法描述：根据taskId删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     */
    ResponseBean deleteProjectCostPointByTaskId(String taskId) throws Exception;

    /**
     * 方法描述：根据taskId删除，用于设计阶段取消使用
     * 作者：MaoSF
     * 日期：2017/3/2
     */
    ResponseBean deleteProjectCostPointForDesignContent(String taskId) throws Exception;

    /**
     * 方法描述：合同乙方更改技术审查费
     * 作者：MaoSF
     * 日期：2017/3/2
     * @param:flag(1:重新添加，2.全部删除，4.先删除后添加）
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
   * 方法描述：合作设计费（projectId，companyId:当前公司id，costId）
   * 作者：chenzhujie
   * 日期：2017/3/1
   */
  ResponseBean getCooperativeDesignFeeInfoByCostId(Map<String,Object> map)throws Exception;

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
   */

  ResponseBean getProjectCostPointDetailForMyTask(String paymentDetailId, String pointDetailId, MyTaskEntity task) throws Exception;

  /**
   * 方法描述：合作设计费（技术审查费）付款，详情(合同回款，其他费用)，到款，付款
   * 作者：MaoSF
   * 日期：2017/3/9
   */

  ProjectCostPointDataForMyTaskDTO getProjectCostPointForMyTask(String paymentDetailId, String pointDetailId, MyTaskEntity task) throws Exception;
  /**
   * 方法描述：修改付款或到款明细
   * 作者：wrb
   * 日期：2017/4/26
   */
  ResponseBean updateCostPaymentDetail(ProjectCostPaymentDetailDTO dto)throws Exception;

  /**
   * 财务处理发票信息
   */
  ResponseBean saveCostPointDetailForInvoice(InvoiceEditDTO dto)throws Exception;

  /**
   * 查询项目费用汇总数据
   */
  ProjectFeeSummaryDTO getProjectFeeSummary(ProjectCostQueryDTO query) throws Exception;

  /**
   * 技术审查费汇总界面
   */
  List<ProjectCooperatorCostDTO> getProjectCooperatorFee(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   * 收款计划，付款计划列表
   */
  Map<String, Object> listProjectCost(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   *  根据costId查询收款，付款节点信息
   */
  Map<String,Object> getProjectCostPointByCostId(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   *  根据costId查询收款，付款节点信息
   */
  Map<String,Object> getProjectCostPointDetailByCostId(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   * 发起收款界面详情，财务到账确认详情，财务发票确认详情
   */
  Map<String, Object> getProjectCostPaymentDetailByPointDetailIdForReceive(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   * 发起付款界面详情，财务付款确认详情，审批人审批界面
   */
  Map<String, Object> getProjectCostPaymentDetailByPointDetailIdForPay(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   *  根据costId查询收款 付款 明细
   */
  Map<String,Object> getProjectCostReceiveByCostId(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   *  根据costId查询应收款 付款 明细
   */
  Map<String,Object> getProjectCostNotReceiveByCostId(ProjectCostQueryDTO queryDTO) throws Exception;

  /**
   * 费用付款申请  流程结束后，数据处理
   */
  ResponseBean completeProjectFeeApply(ProjectCostPointDetailDTO projectCostPointDetailDTO)throws Exception;

}
