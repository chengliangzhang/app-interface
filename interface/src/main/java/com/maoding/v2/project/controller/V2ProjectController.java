package com.maoding.v2.project.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectDesignContentDTO;
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
        List<DataDictionaryDTO> projectTypeList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_CONSTRUCTFUNCTION);//项目类别
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
     *
     * @param:[id]
     * @return:com.maoding.core.bean.AjaxMessage
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
     *
     * @param:[id]
     * @return:com.maoding.core.bean.AjaxMessage
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


/********************************************************v2***************************************/

    /**
     * 方法描述：删除项目
     * 作者：MaoSF
     * 日期：2016/7/29
     *
     * @param:
     * @return:
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
     *
     * @param:[id]
     * @return:
     */
    @RequestMapping("/getProject")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProject(@RequestBody Map<String, Object> map) throws Exception {
        String id = (String) map.get("id");
        Map<String, Object> returnMap = projectService.getProjectDetail(id, map.get("appOrgId").toString(), map.get("accountId").toString());
        return ResponseBean.responseSuccess("查询成功").setData(returnMap);

    }

    /**
     * 方法描述：项目相关信息详情
     * 作者：Chenzhujie
     * 日期：2016/12/13
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getProjectAbouts")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectAbouts(@RequestBody Map<String, Object> param) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData(projectService.getProjectsAbouts(param));
    }


    /**
     * 方法描述：获取添加项目的基础数据
     * 作者：MaoSF
     * 日期：2016/12/7
     *
     * @param:
     * @return:
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
     *
     * @param:
     * @return:
     */
    @RequestMapping("/saveNewProject")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveNewProject(@RequestBody ProjectDTO dto) throws Exception {
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
    public ResponseBean v2GetProjectList(@RequestBody Map<String,Object> paraMap){
        try{
            List<V2ProjectTableDTO>  projectList = projectService.getV2ProjectList(paraMap);
            int totalCount = projectService.getProjectListByConditionCount(paraMap);
            return  ResponseBean.responseSuccess("查询成功").addData("projectList",projectList).addData("totalCount",totalCount);
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
     * 方法描述：获取项目菜单权限
     * 作者：MaoSF
     * 日期：2017/03/26
     */
    @RequestMapping("/projectNavigationRoleInterface")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean projectNavigationRoleInterface(@RequestBody Map<String, Object> paraMap) throws Exception {
        return projectService.projectNavigationRoleInterface(paraMap);
    }

    /**
     * 方法描述：保存设计阶段
     * 作者：MaoSF
     * 日期：2016/7/29
     *
     * @param:
     * @return:
     */
    @RequestMapping(value = {"/saveOrUpdateProjectDesign"}, method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateProjectDesign(@RequestBody ProjectDesignContentDTO dto) throws Exception {
        dto.setCompanyId(dto.getAppOrgId());
        return this.projectService.saveOrUpdateProjectDesign(dto);
    }
}



