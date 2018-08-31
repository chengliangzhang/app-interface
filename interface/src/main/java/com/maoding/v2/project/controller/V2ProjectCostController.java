package com.maoding.v2.project.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.ProjectCostConst;
import com.maoding.projectcost.dto.*;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2ProjectCostController
 * 类描述：费用controller
 * 作    者：MaoSF
 * 日    期：2016年7月8日-下午3:12:45
 */
@Controller
@RequestMapping("/v2/projectCost")
public class V2ProjectCostController extends BaseWSController {

    @Autowired
    private ProjectCostService projectCostService;



    /**
     * 方法描述:获取费用类型
     * 保存费用总金额（type=1:合同总金额,type=2:技术审查费，type=3：合作设计费）
     * 作者：MaoSF
     * 日期：2018/8/10
     */
    @RequestMapping("/getCostType")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCostType(@RequestBody ProjectCostEditDTO projectCostDto) throws Exception {
        if(projectCostDto.getPayType()==null || projectCostDto.getPayType()==1){
            return ResponseBean.responseSuccess().addData("costTypeList", ProjectCostConst.COST_TYPE_RECEIVE_LIST);
        }else {
            return ResponseBean.responseSuccess().addData("costTypeList", ProjectCostConst.COST_TYPE_PAY_LIST);
        }
    }
    /*****************================合同回款================*****************/
    /**
     * 方法描述：设置修改合同总金额
     * 保存费用总金额（type=1:合同总金额,type=2:技术审查费，type=3：合作设计费（合作设计只有更新操作））
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/saveOrUpdateContract")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateContract(@RequestBody ProjectCostEditDTO projectCostDto) throws Exception {
        return this.projectCostService.saveOrUpdateProjectCost(projectCostDto);
    }

    /**
     * 方法描述：增加收付款计划
     * 保存费用总金额（type=1:合同总金额,type=2:技术审查费，type=3：合作设计费
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/saveProjectCost")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveProjectCost(@RequestBody ProjectCostEditDTO projectCostDto) throws Exception {
        return this.projectCostService.saveOrUpdateProjectCost(projectCostDto);
    }

    /**
     * 方法描述：新增/更新 费用节点（如果是合作设计费，必须传递costId（合作设计费），projectId，type）
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId，type（type=1:合同总金额,type=2:技术审查费，type=3：合作设计费，type=4：其他费用（付款），type=5:其他费用收款）
     * @return:
     */
    @RequestMapping("/saveOrUpdateCostPoint")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateCostPoint(@RequestBody ProjectCostPointDTO projectCostPointDTO) throws Exception {
        if ("4".equals(projectCostPointDTO.getType()) || "5".equals(projectCostPointDTO.getType())) {
            return this.projectCostService.saveOtherProjectCostPoint(projectCostPointDTO);
        }
        return this.projectCostService.saveOrUpdateProjectCostPoint(projectCostPointDTO);

    }


    /**
     * 方法描述：@param:pointId，fee
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/saveOrUpdateCostDetail")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateReturnMoneyDetail(@RequestBody ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        return this.projectCostService.saveOrUpdateReturnMoneyDetail(projectCostPointDetailDTO);

    }


    /**
     * 方法描述：查询合同回款(projectId)(全查)
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/getContractInfo")
    @ResponseBody
   // @AuthorityCheckable
    public ResponseBean getContractInfo(@RequestBody ProjectCostPaymentDetailDTO projectCostPaymentDetailDTO) throws Exception {
        return this.projectCostService.getContractInfo(projectCostPaymentDetailDTO);

    }

    /**
     * 方法描述：查询节点明细
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/getCostPointInfo")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getPointInfo(@RequestBody ProjectCostPointDTO projectCostPointDTO) throws Exception {
        return this.projectCostService.getPointInfo(projectCostPointDTO);

    }

    /**
     * 方法描述：删除节点
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/deletePointById")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deletePointById(@RequestBody ProjectCostPointDTO projectCostPointDTO) throws Exception {
        return this.projectCostService.deleteProjectCostPoint(projectCostPointDTO.getId(), projectCostPointDTO.getAccountId());

    }

    /**
     * 方法描述：删除节点
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @RequestMapping("/deleteProjectCostPointDetail")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteCostDetailById(@RequestBody ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        return this.projectCostService.deleteProjectCostPointDetail(projectCostPointDetailDTO.getId(), projectCostPointDetailDTO.getAccountId());

    }


    /**
     * 方法描述：删除收款费用详情
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId，type
     * @return:
     */
    @RequestMapping("/deleteProjectCostPaymentDetail")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteProjectCostPaymentDetail(@RequestBody ProjectCostPaymentDetailDTO projectCostPaymentDetailDTO) throws Exception {
        return this.projectCostService.deleteProjectCostPaymentDetail(projectCostPaymentDetailDTO.getId(), projectCostPaymentDetailDTO.getAccountId(),projectCostPaymentDetailDTO.getAppOrgId());
    }

    /**************技术审查费列表查询*************/

    /**
     * 方法描述：技术审查费界面数据
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId
     * @return:
     */
    @RequestMapping(value = {"/techicalReviewFeeInfo"}, method = RequestMethod.POST)
    @ResponseBody
   // @AuthorityCheckable
    public ResponseBean getTechicalReviewFeeInfo(@RequestBody Map<String, Object> map) throws Exception {
        return projectCostService.getTechicalReviewFeeInfo(map);
    }

    /**
     * 方法描述：合作设计费界面数据
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId
     * @return:
     */
    @RequestMapping(value = {"/cooperativeDesignFeeInfo"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCooperativeDesignFeeInfo(@RequestBody Map<String, Object> map) throws Exception {
        return projectCostService.getCooperativeDesignFeeInfo(map);
    }

    /**
     * 方法描述：合作设计费界面数据根据costId（查询某个组织与另外一个组织的合作设计费详情）
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId
     * @return:
     */
    @RequestMapping(value = {"/cooperativeDesignFeeInfoByCostId"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCooperativeDesignFeeInfoByCostId(@RequestBody Map<String, Object> map) throws Exception {
        return projectCostService.getCooperativeDesignFeeInfoByCostId(map);
    }

    /**
     * 方法描述：其他费用界面数据（project，type=4：其他费用付款，type=5:其他费用收款）
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId
     * @return:
     */
    @RequestMapping(value = {"/getOtherFee"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOtherFee(@RequestBody Map<String, Object> map) throws Exception {
        return projectCostService.getOtherFee(map);
    }


    /**
     * 方法描述：合作设计费详情界面
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:projectId
     * @return:
     */
    @RequestMapping(value = {"/getProjectCostPointDetail"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPointDetail(@RequestBody Map<String, Object> map) throws Exception {
        return this.projectCostService.getProjectCostPointDetail(map);
    }


    /**
     * 方法描述：项目费用汇总
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectFeeSummary"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectFeeSummary(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().addData("projectFee",this.projectCostService.getProjectFeeSummary(queryDTO));
    }


    /**
     * 方法描述：合作设计汇总界面
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCooperatorFee"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCooperatorFee(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().addData("cooperatorFee",this.projectCostService.getProjectCooperatorFee(queryDTO));
    }

    /**
     * 方法描述：项目计划收款，计划付款列表
     * 参数：projectId= 项目id，payType：（1：收款计划2：付款计划）
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/listProjectCost"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean listProjectCost(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.listProjectCost(queryDTO));
    }

    /**
     * 方法描述：收付款计划节点信息
     * 参数：projectId= 项目id，payType：（1：收款计划2：付款计划），costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostPointByCostId"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPointByCostId(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostPointByCostId(queryDTO));
    }

    /**
     * 方法描述：发起收付款计划节点信息
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostPointDetailByPointId"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPointDetailByCostId(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostPointDetailByCostId(queryDTO));
    }


    /**
     * 方法描述：发起收款计划节点信息
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostPaymentDetailByPointDetailIdForReceive"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPaymentDetailByPointDetailIdForReceive(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostPaymentDetailByPointDetailIdForReceive(queryDTO));
    }

    /**
     * 方法描述：发起付款计划节点信息
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostPaymentDetailByPointDetailIdForPay"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPaymentDetailByPointDetailIdForPay(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostPaymentDetailByPointDetailIdForPay(queryDTO));
    }
 /**
     * 方法描述：发起付款计划节点信息
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostPaymentDetailByMainIdForPay"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostPaymentDetailByMainIdForPay(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostPaymentDetailByMainIdForPay(queryDTO));
    }



    /**
     * 方法描述：单个计划，到账，付款 汇总
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostReceiveByCostId"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostReceiveByCostId(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostReceiveByCostId(queryDTO));
    }

    /**
     * 方法描述：单个计划，应到账，付款 汇总
     * 参数：projectId= 项目id，pointId:节点id，costId：收付款计划id
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value = {"/getProjectCostNotReceiveByCostId"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectCostNotReceiveByCostId(@RequestBody ProjectCostQueryDTO queryDTO) throws Exception {
        return ResponseBean.responseSuccess().setData(this.projectCostService.getProjectCostNotReceiveByCostId(queryDTO));
    }

    /**
     * 方法描述：经营负责人  申请付款（内部组织使用）
     * 作者：MaoSF
     * 日期：2017/3/7
     */
    @RequestMapping(value ={"/applyProjectCostPayFee"} , method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean applyProjectCostPayFee(@RequestBody ProjectCostPointDetailDTO dto) throws Exception{
        return this.projectCostService.applyProjectCostPayFee(dto);
    }
}



