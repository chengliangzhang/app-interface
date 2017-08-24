package com.maoding.v2.project.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.projectcost.dto.ProjectCostDTO;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointDTO;
import com.maoding.projectcost.dto.ProjectCostPointDetailDTO;
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
    public ResponseBean saveOrUpdateContract(@RequestBody ProjectCostDTO projectCostDto) throws Exception {
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
    @AuthorityCheckable
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
        return this.projectCostService.deleteProjectCostPaymentDetail(projectCostPaymentDetailDTO.getId(), projectCostPaymentDetailDTO.getAccountId());
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
    @AuthorityCheckable
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

}



