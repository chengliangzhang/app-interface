package com.maoding.v2.finance.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dto.*;
import com.maoding.financial.service.ExpDetailService;
import com.maoding.financial.service.ExpMainService;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.org.service.DepartService;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.v2.financial.dto.V2ExpMainDTO;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maoding.core.util.MapUtil.objectMap;

/**
 * @Author ZhujieChen
 * @Description:
 * @Date: Created in 20:08 2016/12/22
 * @Modified By:
 */
@Controller
@RequestMapping("/v2/finance")
public class V2FinancialController extends BaseWSController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExpMainService expMainService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private DepartService departService;
    @Autowired
    private ExpDetailService expDetailService;


    /**
     * 方法描述：自定义报销类别查询
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpCategory")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpCategory(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String userId = paraMap.get("accountId");
            AjaxMessage ajax = expMainService.getCategoryBaseData(companyId, userId);
            Map<String, Object> map = (Map<String, Object>) ajax.getData();
            return ResponseBean.responseSuccess("查询成功").addData("expTypeList", map.get("expTypeList"));
        } catch (Exception e) {
            logger.error("=========V2FinancialController.getExpCategory()方法出现异常==========", e);
            return ResponseBean.responseError("查询报销类别失败");
        }
    }

    /**
     * 方法描述：自定义报销类别增加或者修改
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/saveOrUpdateExpCategory")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveOrUpdateExpCategory(@RequestBody ExpTypeOutDTO dto) {
        try {
            String companyId = dto.getAppOrgId();
            AjaxMessage ajax = expMainService.saveOrUpdateCategoryBaseData(dto, companyId);
            if ("0".equals(ajax.getCode())) {
                return ResponseBean.responseSuccess("保存成功");
            }
            return ResponseBean.responseError("保存失败");
        } catch (Exception e) {
            log.error("=========V2FinancialController.saveOrUpdateExpCategory()方法出现异常==========", e);
            return ResponseBean.responseError("操作失败");
        }
    }


    /**
     * 方法描述：获取报销类型
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpTypeList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpTypeList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            List<ExpTypeDTO> expType = expMainService.getExpTypeList(companyId);
            return ResponseBean.responseSuccess("查询成功").addData("expTypeList", expType);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpTypeList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

/*******************************************************************v2*****************************************/

    /**
     * v2
     * 方法描述：获取相关项目列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getProjectList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String searchVal = paraMap.get("searchVal");
            Map<String, Object> map = new HashMap<String, Object>();
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setCompanyId(companyId);
            //  projectDTO.setSearchVal(searchVal);
            map.put("companyId", companyId);
            if (!StringUtil.isNullOrEmpty(searchVal)) {
                map.put("searchVal", searchVal);
            }
            map.put("projectList", expMainService.getProjectListWS(map));
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getProjectList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }


    /**
     * 方法描述：获取和自己有关的公司和部门列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getCompanyWithDepartList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCompanyWithDepartList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String userId = paraMap.get("accountId");
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("companyId", companyId);
            mapParams.put("userId", userId);
            map.put("departList", departService.getDepartByUserIdContentCompanyInterface(mapParams));
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getCompanyWithDepartList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：获取部门列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getDepartList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getDepartList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String userId = paraMap.get("accountId");
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("companyId", companyId);
            mapParams.put("userId", userId);
            CompanyDTO company = companyService.getCompanyById(companyId);
            List<DepartDTO> list = departService.getDepartByCompanyId(mapParams);
            DepartDTO dto = new DepartDTO();
            dto.setId(companyId);
            dto.setDepartName(company.getCompanyShortName());
            list.add(0, dto);
            map.put("departListByCompanyId", list);
            List<DepartDTO> newList = new ArrayList<DepartDTO>();
            newList = BaseDTO.copyFields(list, DepartDTO.class);
            for (DepartDTO departDTO : newList) {
                String strDepartName = departDTO.getDepartName();
                if (strDepartName.indexOf("/") != -1) {
                    String[] arrStr = strDepartName.split("/");
                    strDepartName = arrStr[arrStr.length - 1];
                    departDTO.setDepartName(strDepartName);
                }
            }
            map.put("departListByCompanyIdWithOutParentDepart", newList);
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getDepartList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：获取报销人列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpUserList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpUserList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String userId = paraMap.get("accountId");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyUserList", companyUserService.getCompanyUserByCompanyId(companyId));
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpUserList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：查询当前组织所有人员
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getUserList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getUserList(@RequestBody Map<String, String> paraMap) {
        try {
            String companyId = paraMap.get("appOrgId");
            String orgId = paraMap.get("orgId");
            return ResponseBean.responseSuccess("查询成功").addData("userList", expMainService.getUserList(companyId, orgId));
        } catch (Exception e) {
            log.error("=======V2FinancialController.getUserList()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：我的报销列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpMainPage")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpMainPage(@RequestBody Map<String, Object> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("approveStatus", paraMap.get("approveStatus"));
            param.put("departId", paraMap.get("departId"));
            param.put("startDate", paraMap.get("startDate"));
            param.put("endDate", paraMap.get("endDate"));
            param.put("pageSize", paraMap.get("pageSize"));
            param.put("pageNumber", paraMap.get("pageNumber"));
            param.put("companyId", paraMap.get("appOrgId"));
            param.put("userId", paraMap.get("accountId"));
            if (!StringUtil.isNullOrEmpty(paraMap.get("expNo"))) {
                param.put("expNo", paraMap.get("expNo"));
            }
            List<ExpMainDTO> data = expMainService.getExpMainPage(param);
            for (ExpMainDTO expMainDTO : data) {
                List<ExpDetailDTO> list = expDetailService.getExpDetailDTO(expMainDTO.getId());
                String expPName = "";
                for (int i = 0; i < list.size(); i++) {
                    String str = list.get(i).getExpAllName();
                    String[] strAttr = str.split("-");
                    if (i == 0) {
                        expPName = strAttr[0];
                    } else {
                        if (expPName.indexOf(strAttr[0]) == -1) {
                            expPName = expPName + "," + strAttr[0];
                        }
                    }
                }
                expMainDTO.setExpTypeParentName(expPName);
            }
            return ResponseBean.responseSuccess("查询成功").addData("expMainList", data);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpMainPage()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：待我审核列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpMainPageForAudit")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpMainPageForAudit(@RequestBody Map<String, Object> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("departId", paraMap.get("departId"));
            param.put("startDate", paraMap.get("startDate"));
            param.put("endDate", paraMap.get("endDate"));
            param.put("pageSize", paraMap.get("pageSize"));
            param.put("pageNumber", paraMap.get("pageNumber"));
            param.put("companyId", paraMap.get("appOrgId"));
            if (StringUtil.isNullOrEmpty(paraMap.get("approveStatus"))) {
                param.put("defaultApproveStatus", "('0','1','2','5')");
            }
            if ("0".equals(paraMap.get("approveStatus"))) {
                param.put("defaultApproveStatus", "('0','5')");
                param.put("waitCheck", "1");
                param.put("isHave", "have");
            } else if ("1".equals(paraMap.get("approveStatus"))) {
                param.put("defaultApproveStatus", "('1','5')");
                param.put("waitCheck", "1");
            } else {
                param.put("approveStatus", paraMap.get("approveStatus"));
            }

            param.put("auditPerson", paraMap.get("accountId"));
            if (!StringUtil.isNullOrEmpty(paraMap.get("expNo"))) {
                param.put("expNo", paraMap.get("expNo"));
            }
            List<ExpMainDTO> data = expMainService.getExpMainPageInterface(param);
            int totalNumber = expMainService.getExpMainPageCount(param);
            for (ExpMainDTO expMainDTO : data) {
                List<ExpDetailDTO> list = expDetailService.getExpDetailDTO(expMainDTO.getId());
                String expPName = "";
                for (int i = 0; i < list.size(); i++) {
                    String str = list.get(i).getExpAllName();
                    String[] strAttr = str.split("-");
                    if (i == 0) {
                        expPName = strAttr[0];
                    } else {
                        if (expPName.indexOf(strAttr[0]) == -1) {
                            expPName = expPName + "," + strAttr[0];
                        }
                    }
                }
                expMainDTO.setExpTypeParentName(expPName);
            }
            return ResponseBean.responseSuccess("查询成功").addData("expMainList", data).addData("total", totalNumber);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpMainPageForAudit()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：撤回报销 报销单id  type--状态(3撤回)
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */

    @RequestMapping("/recallExpMain")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean recallExpMain(@RequestBody ExpAuditDTO dto) {
        try {
            int i = expMainService.recallExpMain(dto);
            if (i > 0) {

                return ResponseBean.responseSuccess("报销退回成功");
            } else {
                return ResponseBean.responseError("报销退回失败");
            }
        } catch (Exception e) {
            log.error("=======V2FinancialController.recallExpMain()方法出现异常=========", e);
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：报销详情 报销单id
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpMainPageDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpMainPageDetail(@RequestBody ExpMainDTO expMainDTO) {
        try {
            ExpMainDTO dto = expMainService.selectExpMainDetail(expMainDTO.getId());
            return ResponseBean.responseSuccess("查询成功").addData("expMainInfo", dto);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpMainPageDetail()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：删除报销报销单id
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/deleteExpMain")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteExpMain(@RequestBody ExpMainDTO expMainDTO) {
        try {
            int i = expMainService.deleteExpMain(expMainDTO.getId(), expMainDTO.getVersionNum() + "");
            if (i > 0) {
                return ResponseBean.responseSuccess("删除成功!");
            } else {
                return ResponseBean.responseError("删除失败！");
            }
        } catch (Exception e) {
            log.error("=======V2FinancialController.deleteExpMain()方法出现异常=========", e);
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：同意报销报销单id
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/agreeExpMain")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean agreeExpMain(@RequestBody ExpMainDTO expMainDTO) {
        try {
            int i = expMainService.agreeExpMain(expMainDTO.getId(), expMainDTO.getCompanyUserId(), expMainDTO.getVersionNum() + "");
            if (i > 0) {
                return ResponseBean.responseSuccess("操作成功");
            } else {
                return ResponseBean.responseError("操作失败");
            }
        } catch (Exception e) {
            log.error("=======V2FinancialController.agreeExpMain()方法出现异常=========", e);
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：同意报销并转移审批人 报销单id auditPerson--新审批人
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/agreeAndTransAuditPerExpMain")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean agreeAndTransAuditPerExpMain(@RequestBody ExpMainDTO expMainDTO) {
        try {
            int i = expMainService.agreeAndTransAuditPerExpMain(expMainDTO.getId(), expMainDTO.getCompanyUserId(), expMainDTO.getAuditPerson(), expMainDTO.getVersionNum() + "",expMainDTO.getAccountId());
            if (i > 0) {
                return ResponseBean.responseSuccess("转发成功");
            } else {
                return ResponseBean.responseError("报销转发失败");
            }
        } catch (Exception e) {
            log.error("=======V2FinancialController.agreeAndTransAuditPerExpMain()方法出现异常=========", e);
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：报销详情与审批记录 报销单id
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpMainDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpMainDetail(@RequestBody ExpMainDTO expMainDTO) {
        try {
            Map<String, Object> map = expMainService.getExpMainDetail(expMainDTO.getId());
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpMainDetail()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：报销汇总
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getExpMainPageForSummary")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpMainPageForSummary(@RequestBody Map<String, Object> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("departId", paraMap.get("departId"));//部门
            param.put("userId", paraMap.get("userId"));//报销人
            param.put("startDate", paraMap.get("startDate"));
            param.put("endDate", paraMap.get("endDate"));
            //param.put("expType",dto.getExpType());//报销类别
            //param.put("expParentType",dto.getExpParentType());//报销父类别
            param.put("expName", paraMap.get("expName"));//子
            param.put("expPName", paraMap.get("expPName"));//父
            if (null != paraMap.get("pageIndex")) {
                param.put("pageNumber", paraMap.get("pageIndex"));
            } else {
                param.put("pageNumber", paraMap.get("pageNumber"));
            }
            param.put("pageSize", paraMap.get("pageSize"));

            param.put("companyId", paraMap.get("appOrgId"));
            if (!StringUtil.isNullOrEmpty(paraMap.get("expNo"))) {
                param.put("expNo", paraMap.get("expNo"));
            }
            List<ExpMainDTO> data = expMainService.getExpMainPageForSummary(param);


            for (ExpMainDTO expMainDTO : data) {
                expMainDTO.setExpTypeParentName(expMainDTO.getExpPName());
            }
            int totalNumber = expMainService.getExpMainPageForSummaryCount(param);
            param.clear();
            param.put("expMainList", data);
            param.put("total", totalNumber);
            BigDecimal expSumAmount = new BigDecimal("0.00");
            for (ExpMainDTO expMainDTO : data) {
                expSumAmount = expSumAmount.add(expMainDTO.getExpSumAmount());
            }
            param.put("expSumAmount", expSumAmount);
            return ResponseBean.responseSuccess("查询成功").addData(param);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getExpMainPageForSummary()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }


    /*********************************************************v2************************************/

    /**
     * v2MyExpMainPage
     * 方法描述：我的报销列表
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/v2MyExpMainPage")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean v2MyExpMainPage(@RequestBody Map<String, String> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //审批状态(0:待审核，1:同意，2，退回,3:撤回,4:删除,5.审批中）
            if (!StringUtil.isNullOrEmpty(paraMap.get("approveStatus"))) {
                param.put("approveStatus", paraMap.get("approveStatus"));
            } else {
                param.put("notStatus", "1");
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("pageSize"))) {
                param.put("pageSize", Integer.parseInt(paraMap.get("pageSize")));
                param.put("pageIndex", Integer.parseInt(paraMap.get("pageIndex")));
            }
            param.put("companyId", paraMap.get("appOrgId"));
            //我报销的
            if (!StringUtil.isNullOrEmpty(paraMap.get("companyUserId"))) {
                param.put("userId", paraMap.get("companyUserId"));
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("expUse"))) {
                param.put("expUse", paraMap.get("expUse"));
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("expNo"))) {
                param.put("expNo", paraMap.get("expNo"));
            }
            List<V2ExpMainDTO> data = expMainService.v2GetExpMainPage(param);
            return ResponseBean.responseSuccess("查询成功").addData("expMainList", data);
        } catch (Exception e) {
            log.error("=======V2FinancialController.v2GetExpMainPage()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }


    /**
     * v2
     * 方法描述：我审批的报销
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/v2MyAuditExpMainPage")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean v2MyAuditExpMainPage(@RequestBody Map<String, String> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //审批状态(0:待审核，1:同意，2，退回,3:撤回,4:删除,5.审批中）
            if (!StringUtil.isNullOrEmpty(paraMap.get("approveStatus"))) {
                int approveStatus = Integer.parseInt(paraMap.get("approveStatus"));
                if (approveStatus == 6) {
                    String str = "1,2";
                    param.put("myAuditApproveStatus", str.split(","));
                } else if (approveStatus == 7) {
                    String str = "2";
                    param.put("myAuditApproveStatus", str.split(","));
                } else if (approveStatus == 1) {
                    String s = "1";
                    param.put("myAuditApproveStatus", s.split(","));
                    param.put("isHave", "isHave");
                } else {
                    param.put("approveStatus", paraMap.get("approveStatus"));
                }
            } else {
                //默认等待审核，审批中
                String sta = "0,5";
                param.put("myAuditApproveStatus", sta.split(","));
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("pageSize"))) {
                param.put("pageSize", Integer.parseInt(paraMap.get("pageSize")));
                param.put("pageIndex", Integer.parseInt(paraMap.get("pageIndex")));
            }
            param.put("companyId", paraMap.get("appOrgId"));

            //我审核的
            if (!StringUtil.isNullOrEmpty(paraMap.get("companyUserId"))) {
                param.put("auditPerson", paraMap.get("companyUserId"));
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("expUse"))) {
                param.put("expUse", paraMap.get("expUse"));
            }
            if (!StringUtil.isNullOrEmpty(paraMap.get("expNo"))) {
                param.put("expNo", paraMap.get("expNo"));
            }

            List<V2ExpMainDTO> data = expMainService.v2GetExpMainPage(param);
            return ResponseBean.responseSuccess("查询成功").addData("expMainList", data);
        } catch (Exception e) {
            log.error("=======V2FinancialController.v2GetExpMainPage()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：报销增加或者修改
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/v2SaveOrUpdateExpMainAndDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean v2SaveOrUpdateExpMainAndDetail(@RequestBody V2ExpMainDTO dto) {
        try {
            String userId = dto.getAccountId();
            String companyId = dto.getAppOrgId();
            dto.setAppOrgId(null);
            dto.setAccountId(null);
            AjaxMessage ajax = expMainService.v2SaveOrUpdateExpMainAndDetail(dto, userId, companyId);
            if (ajax.getCode().equals("0")) {
                V2ExpMainDTO v2dto = (V2ExpMainDTO) ajax.getData();
                Map<String, Object> map = new HashedMap();
                map.put("expNo", v2dto.getExpNo());
                return ResponseBean.responseSuccess("报销保存成功").setData(map);
            }
            return ResponseBean.responseError("报销保存失败");
        } catch (Exception e) {
            log.error("=========V2FinancialController.saveOrUpdateExpMainAndDetail()方法出现异常==========", e);
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：报销详情与审批记录 报销单id
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/v2GetExpMainDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean v2GetExpMainDetail(@RequestBody ExpMainDTO expMainDTO) {
        try {
            Map<String, Object> map = expMainService.getExpMainDetail(expMainDTO.getId());

            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.v2GetExpMainDetail()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：获取最大组织expNo + 1
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @RequestMapping("/getMaxExpNo")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getMaxExpNo(@RequestBody ExpMainDTO expMainDTO) {
        try {
            Map<String, Object> map = expMainService.getMaxExpNo(objectMap("companyId", expMainDTO.getAppOrgId()));
            return ResponseBean.responseSuccess("查询成功").addData(map);
        } catch (Exception e) {
            log.error("=======V2FinancialController.getMaxExpNo()方法出现异常=========", e);
            return ResponseBean.responseError("查询失败");
        }
    }

}
