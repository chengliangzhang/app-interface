package com.maoding.v2.project.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.ProjectConstructDTO;
import com.maoding.project.dto.ProjectConstructDetailDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectConstructService;
import com.maoding.project.service.ProjectService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.user.dto.LinkManDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : V2ProjectConstructController
 * 描    述 : 建设单位Controller
 * 作    者 : ChenZhujie
 * 日    期 : 2016/12/23
 */
@Controller
@RequestMapping("/v2/projectconstruct")
public class V2ProjectConstructController extends BaseWSController {

    @Autowired
    private ProjectConstructService projectConstructService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AccountService accountService;


    /**
     * 方法描述：根据当前组织查找客户管理列表
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getConstructByCompanyId")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getConstructByCompanyId(@RequestBody Map<String, Object> paraMap) throws Exception {
        String companyId = paraMap.get("appOrgId").toString();
        List<ProjectConstructDTO> list = projectConstructService.getConstructByCompanyId(companyId);
        return responseSuccess().addData("projectConstrutList", list);
    }

    /**
     * 方法描述：保存或者修改建设单位联系人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/saveOrUpdateProjectConstruct")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateProjectConstruct(@RequestBody ProjectConstructDTO dto) throws Exception {
        dto.setCompanyId(dto.getAppOrgId());
        dto.setUpdateBy(dto.getAccountId());
        AjaxMessage ajaxMessage = projectConstructService.saveOrUpdateProjectConstruct(dto);
        if ("0".equals(ajaxMessage.getCode())) {

            ProjectConstructDTO dto1 = (ProjectConstructDTO) ajaxMessage.getData();
            Map<String, ProjectConstructDTO> projectConstructMap = projectConstructMap = new HashMap<String, ProjectConstructDTO>();

            projectConstructMap.put("projectConstructDTO", dto1);

            return ResponseBean.responseSuccess("甲方保存成功").addData("projectConstructDTO", dto1);
        }
        return ResponseBean.responseError("甲方保存失败");
    }

    /**
     * 方法描述：获取建设单位及联系人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getProjectConstruct")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectConstruct(@RequestBody ProjectConstructDTO projectConstructDTO) {
        try {
            ProjectConstructDTO dto = projectConstructService.getProjectConstructById(projectConstructDTO.getId());
            return responseSuccess().addData("projectConstructInfo", dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("查询甲方失败！");
        }
    }

    /**
     * 方法描述：项目详情（项目立项）界面，查看联系人获取数据
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getProjectConstructByIdAndOtherDetail")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectConstructByIdAndOtherDetail(@RequestBody Map<String, Object> map) throws Exception {
        try {
            String id = (String) map.get("id");
            String projectId = (String) map.get("projectId");
            ProjectConstructDTO dto = null;
            dto = projectConstructService.getProjectConstructByIdAndOtherDetail(id, map.get("appOrgId").toString(), projectId);
            // dto = projectConstructService.getProjectConstructByIdAndOtherDetailInterface(id, map.get("appOrgId").toString(), projectId);
            return responseSuccess().addData("projectConstructInfo", dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("查询甲方其他联系人失败！");
        }
    }

    /**
     * 方法描述：联系人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getProjectLinkMan")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectLinkMan(@RequestBody Map<String, Object> map) throws Exception {
        try {
            String projectId = (String) map.get("projectId");
            ProjectEntity projectEntity = projectService.selectById(projectId);
            ProjectConstructDTO dto = projectConstructService.getProjectConstructByIdAndOtherDetail(projectEntity.getConstructCompany(), map.get("appOrgId").toString(), projectId);

            List<ProjectConstructDetailDTO> proConstrDetList = dto.getProConstrDetList();
            List<LinkManDTO> listUser = new ArrayList<>();
            for (ProjectConstructDetailDTO pcd : proConstrDetList) {
                LinkManDTO userDTO = new LinkManDTO();
                if (null != pcd.getPhone()) {
                    userDTO.setCellphone(pcd.getPhone());
                }
                if (null != pcd.getName()) {
                    userDTO.setUserName(pcd.getName());
                }
                listUser.add(userDTO);
            }
            AccountEntity accountEntity = accountService.selectById(projectEntity.getCreateBy());

            List<LinkManDTO> setUpProjectUserList = new ArrayList<>();
            LinkManDTO userDTO = new LinkManDTO();
            userDTO.setUserName(accountEntity.getUserName());
            userDTO.setCellphone(accountEntity.getCellphone());
            setUpProjectUserList.add(userDTO);
            return responseSuccess().addData("projectLinkManList", listUser).addData("setUpProjectList", setUpProjectUserList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("查询联系人失败！");
        }
    }
}
