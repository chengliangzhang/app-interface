package com.maoding.v2.project.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.partner.dto.PartnerQueryDTO;
import com.maoding.partner.service.PartnerService;
import com.maoding.project.dto.*;
import com.maoding.project.service.ProjectService;
import com.maoding.projectmember.dto.ProjectMemberGroupDTO;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.system.dto.DataDictionaryDTO;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.v2.project.dto.V2ProjectTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：OrgController
 * 类描述：组织controller
 * 作    者：MaoSF
 * 日    期：2016年7月8日-下午3:12:45
 */
@Controller
@RequestMapping("/v2/project")
public class V2ProjectController extends BaseWSController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    PartnerService partnerService;

    @Value("${project}")
    private String projectUrl;


    /**
     * 方法描述：项目类别
     * 作者：MaoSF
     * 日期：2016/7/28
     *
     * @param:[id]
     * @return:com.maoding.core.bean.AjaxMessage
     */
    @RequestMapping("/getProjectType")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectType(@RequestBody Map<String, Object> map) throws Exception {
        List<DataDictionaryDTO> projectTypeList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_Type);//项目类别
        return ResponseBean.responseSuccess("查询成功").addData("projectTypeList", projectTypeList);
    }

    /**
     * 方法描述：设计依据
     * 作者：MaoSF
     * 日期：2016/7/28
     *
     * @param:[id]
     * @return:com.maoding.core.bean.AjaxMessage
     */
    @RequestMapping("/getDesignBasicList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDesignBasicList(@RequestBody Map<String, Object> map) throws Exception {
        List<DataDictionaryDTO> designBasicList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_DESIGNBASIC);//设计依据
        return ResponseBean.responseSuccess("查询成功").addData("designBasicList", designBasicList);
    }

    /**
     * 方法描述：设计阶段
     * 作者：MaoSF
     * 日期：2016/7/28
     */
    @RequestMapping("/getDesignContentList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDesignContentList(@RequestBody Map<String, Object> map) throws Exception {
        List<DataDictionaryDTO> designContentList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_DESIGNCONTENT);//设计阶段
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = null;
        for (DataDictionaryDTO dto : designContentList) {
            map1 = new HashMap<String, String>();
            map1.put("contentName", dto.getName());
            map1.put("contentId", dto.getId());
            list.add(map1);
        }
        return ResponseBean.responseSuccess("查询成功").addData("designContentList", list);
    }


    /**
     * 方法描述：设计范围
     * 作者：MaoSF
     * 日期：2016/7/28
     */
    @RequestMapping("/getDesignRangeList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDesignRangeList(@RequestBody Map<String, Object> map) {
        try {
            List<DataDictionaryDTO> designRangeList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_DESIGNRANGE);//设计范围
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> map1 = null;
            for (DataDictionaryDTO dto : designRangeList) {
                map1 = new HashMap<String, String>();
                map1.put("designRange", dto.getName());
                list.add(map1);
            }
            return ResponseBean.responseSuccess("查询成功").addData("designRangeList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("查询设计范围失败！");
        }
    }


    /**
     * 方法描述：功能分类
     * 作者：MaoSF
     * 日期：2016/7/28
     */
    @RequestMapping("/getProjectFunctionList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectFunctionList(@RequestBody Map<String, Object> map) {
        return realGetProjectFunctionList(map);
    }

    /**
     * @author  张成亮
     * @date    2018/7/10
     * @description     获取功能分类
     **/
    @RequestMapping("/devGetProjectFunctionList")
    @ResponseBody
    public ResponseBean devGetProjectFunctionList(@RequestBody Map<String, Object> map) {
        return realGetProjectFunctionList(map);
    }

    private ResponseBean realGetProjectFunctionList(Map<String, Object> map) {
        List<ProjectPropertyDTO> list = projectService.getProjectBuildType((String)map.get("projectId"));
        return ResponseBean.responseSuccess("查询成功").addData("projectFunctionList", list);
    }

    /**
     * @author  张成亮
     * @date    2018/7/10
     * @description     获取可选项目状态列表
     **/
    @RequestMapping("/listProjectStatus")
    @ResponseBody
    public ResponseBean listProjectStatus() {
        List<ProjectStatusDTO> list = new ArrayList<>();
        SystemParameters.PROJECT_STATUS.entrySet()
                .forEach(entry->list.add(new ProjectStatusDTO(entry.getKey(),entry.getValue())));
        return ResponseBean.responseSuccess("查询成功").addData("statusList",list);
    }





/********************************************************v2***************************************/

    /**
     * 方法描述：删除项目
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @RequestMapping("deleteProject")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteProject(@RequestBody ProjectDTO dto) throws Exception {
        AjaxMessage ajsx = projectService.deleteProjectById(dto.getId());
        if (ajsx.getCode().equals("0")) {
            return ResponseBean.responseSuccess("项目删除成功！");
        }
        return ResponseBean.responseError("项目删除失败！");
    }

    /**
     * 方法描述：项目详情
     * 作者：MaoSF
     * 日期：2016/7/28
     */
    @RequestMapping("/getProject")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProject(@RequestBody Map<String, Object> map) throws Exception {
        return realGetProject(map);

    }

    /**
     * @author  张成亮
     * @date    2018/7/9
     * @description     用于测试获取项目详情接口，正式版应当删除
     **/
    @RequestMapping("/devGetProject")
    @ResponseBody
    public ResponseBean devGetProject(@RequestBody Map<String, Object> map) throws Exception {
        return realGetProject(map);
    }

    private ResponseBean realGetProject(Map<String, Object> map) throws Exception {
        String id = (String) map.get("id");
        Map<String, Object> returnMap = projectService.getProjectDetail(id, map.get("appOrgId").toString(), map.get("accountId").toString());
        return ResponseBean.responseSuccess("查询成功").setData(returnMap);
    }

    /**
     * @author  张成亮
     * @date    2018/7/11
     * @description     获取专业信息接口
     **/
    @RequestMapping("/listMeasure")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean listMeasure(@RequestBody ProjectDetailQueryDTO query) throws Exception {
        List<CustomProjectPropertyDTO> list = projectService.listMeasure(query);
        return ResponseBean.responseSuccess("查询成功").addData("data",list);
    }

    /**
     * @author  张成亮
     * @date    2018/7/11
     * @description     获取合同信息接口
     **/
    @RequestMapping("/getContractInfo")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getContractInfo(@RequestBody ProjectDetailQueryDTO query) throws Exception {
        ProjectContractInfoDTO contractInfo = projectService.getContractInfo(query);
        return ResponseBean.responseSuccess("查询成功").addData("data",contractInfo);
    }


    /**
     * 方法描述：获取添加项目的基础数据
     * 作者：MaoSF
     * 日期：2016/12/7
     */
    @RequestMapping("/getAddProjectBaseData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getAddProjectBaseData(@RequestBody Map<String, Object> param) throws Exception {
        String companyId = null;
        if (null != param.get("appOrgId")) {
            companyId = (String) param.get("appOrgId");
        } else {
            return ResponseBean.responseError("查询失败").addData(param);
        }
        param.clear();
        param.put("projectManagerList", this.projectService.getAddProjectOfBaseData(companyId).getCompanyUserList());
        return ResponseBean.responseSuccess("查询成功").addData(param);
    }


    /**
     * 方法描述：项目立项(必要参数)
     * 作者：MaoSF
     * 日期：2016/12/7
     */
    @RequestMapping("/saveNewProject")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveNewProject(@RequestBody ProjectEditDTO dto) throws Exception {
        return realSaveNewProject(dto);
    }

    /**
     * @author  张成亮
     * @date    2018/7/9
     * @description     用于测试项目立项接口，正式版应该删除
     **/
    @RequestMapping("/devSaveNewProject")
    @ResponseBody
    public ResponseBean devSaveNewProject(@RequestBody ProjectEditDTO dto) throws Exception {
        return realSaveNewProject(dto);
    }

    private ResponseBean realSaveNewProject(ProjectEditDTO dto) throws Exception {
        dto.setCompanyId(dto.getAppOrgId());
        return this.projectService.saveOrUpdateProjectNew(dto);
    }

    /**
     * 方法描述：v2项目列表
     * 作者：chenzhujie
     * 日期：2016/12/24
     */
    @RequestMapping("/v2GetProjectList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean v2GetProjectList(@RequestBody Map<String,Object> paraMap){
        try{
            List<V2ProjectTableDTO>  projectList = projectService.getV2ProjectList(paraMap);
         //   int totalCount = projectService.getProjectListByConditionCount(paraMap);
            return  ResponseBean.responseSuccess("查询成功").addData("projectList",projectList);
                    //.addData("totalCount",totalCount);
        }catch (Exception e){
            return ResponseBean.responseError("查询失败");
        }
    }

    /**
     * 方法描述：项目参与人员
     * 作者：chenzhujie
     * 日期：2016/12/24
     */
    @RequestMapping("/getProjectParticipants")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectParticipants(@RequestBody Map<String, Object> paraMap) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", paraMap.get("appOrgId"));
        param.put("projectId", paraMap.get("projectId"));
        param.put("accountId", paraMap.get("accountId"));
        //查看的组织
        param.put("orgId", paraMap.get("orgId"));
        //List<Map<String,Object>> projectParticipantsList=projectService.getProjectParticipants(param);
        List<ProjectMemberGroupDTO> projectParticipantsList = this.projectMemberService.listProjectMember((String) paraMap.get("projectId"), (String) paraMap.get("appOrgId"), (String) paraMap.get("accountId"));
        return ResponseBean.responseSuccess("查询成功").addData("projectParticipantsList", projectParticipantsList);

    }

    /**
     * 方法描述：项目参与组织
     * 作者：chenzhujie
     * 日期：2016/12/26
     */
    @RequestMapping("/getProjectTaskCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectTaskCompany(@RequestBody Map<String, Object> paraMap) throws Exception {
        List<Map<String, Object>> list = projectService.getProjectTaskCompany(paraMap.get("projectId").toString());
        return ResponseBean.responseSuccess("查询成功")
                .addData("projectTaskCompanyList", list);
    }


    /**
     * 方法描述：项目综合数据
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @RequestMapping(value = {"/getProjectInfo"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectInfo(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addDataFromObject(this.projectService.getProjectInfo((String)paraMap.get("projectId"),
                (String)paraMap.get("appOrgId"),
                (String)paraMap.get("accountId")));
    }



    /**
     * 方法描述：项目成员
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @RequestMapping(value = {"/listProjectAllMember"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean listProjectAllMember(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("projectMembers",this.projectMemberService.listProjectAllMember(paraMap));
    }

    /**
     * 方法描述：项目综合数据
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @RequestMapping(value = {"/getProjectListForLaborHour"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectListForLaborHour(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("projectList",projectService.getProjectListForLaborHour(paraMap));
    }

    /**
     * 方法描述：首页--项目板块数据
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @RequestMapping(value = {"/getProjectForHome"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectForHome(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("homeData",projectService.getProjectForHome(paraMap));
    }

    /**
     * 方法描述：外部合组织列表
     * 作者：MaoSF
     * 日期：2017/5/9
     */
    @RequestMapping(value = "/getProjectPartnerList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectPartnerList(@RequestBody PartnerQueryDTO dto) throws Exception {
        dto.setFromCompanyId(dto.getAppOrgId());
        List<ProjectPartnerDTO> list = partnerService.getProjectPartnerList(dto);
        int editFlag = this.projectService.getProjectEditRole(dto.getProjectId(),dto.getAppOrgId(),dto.getAccountId());
        return ResponseBean.responseSuccess("查询成功").addData("partnerList",list).addData("editFlag",editFlag);

    }

    /**
     * 方法：获取项目基础信息的自定义字段
     * 作者：Zhangchengliang
     * 日期：2017/8/15
     */
    @RequestMapping(value = "/loadProjectCustomFields", method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean loadProjectCustomFields(@RequestBody ProjectCustomFieldQueryDTO query) throws Exception {
        if (query.getCompanyId() == null) {
            query.setCompanyId(query.getAppOrgId());
        }
        CustomProjectPropertyEditDTO result = projectService.loadProjectCustomFields(query);
        result.setCompanyId(query.getCompanyId());
        result.setProjectId(query.getProjectId());
        result.setOperatorId(query.getAccountId());
        return ResponseBean.responseSuccess("查询成功").addDataFromObject(result);
    }

    @RequestMapping(value = "/saveProjectCustomFields", method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveProjectCustomFields(@RequestBody ProjectPropertyEditDTO query) throws Exception {
        projectService.saveProjectCustomFields(query);
        return ResponseBean.responseSuccess("操作成功");
    }

    @RequestMapping(value = "/saveProjectProfessionFields", method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveProjectProfessionFields(@RequestBody ProjectPropertyEditDTO query) throws Exception {
        projectService.saveProjectProfessionFields(query);
        return ResponseBean.responseSuccess("操作成功");
    }

    @RequestMapping(value = "/saveProjectContract", method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveProjectContract(@RequestBody ProjectEditDTO dto) throws Exception {
        dto.setCompanyId(dto.getAppOrgId());
        return projectService.saveProjectDesign(dto);
    }


}



