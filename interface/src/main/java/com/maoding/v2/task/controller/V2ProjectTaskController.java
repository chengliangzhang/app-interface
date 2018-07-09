package com.maoding.v2.task.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.ProjectTaskResponsibleDTO;
import com.maoding.project.service.ProjectTaskResponsibleService;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.task.dto.*;
import com.maoding.task.service.ProjectManagerService;
import com.maoding.task.service.ProjectProcessTimeService;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2MyTaskController
 * 作   者：MaoSF
 * 日   期：2016/12/26
 */
@Controller
@RequestMapping("/v2/task")
public class V2ProjectTaskController extends BaseWSController {


    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ProjectTaskResponsibleService projectTaskResponsibleService;

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private ProjectProcessTimeService projectProcessTimeService;

    @Autowired
    private ProjectMemberService projectMemberService;

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getProjectTask")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTask(@RequestBody Map<String, Object> map) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("projectTask", projectTaskService.getProjectTask(map));
    }


    /**
     * 方法描述：经营界面模块（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getProjectTaskDataForOperater")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTaskDataForOperater(@RequestBody Map<String, Object> map) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("projectTask", projectTaskService.getProjectTaskDataForOperater(map));
    }

    /**
     * 方法描述：项目任务列表（任务分配界面），type:1：全部任务，2，我团队的任务，3，我的任务
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getTaskByType")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getTaskByType(@RequestBody Map<String, Object> map) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("taskList", projectTaskService.getProjectTaskByType(map));
    }

    /**
     * 方法描述：项目任务列表，点击合作设计单位，获取合作设计单位的经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getOtherPartner")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getOtherPartner(@RequestBody Map<String, Object> map) throws Exception {
        return projectTaskService.getProjectPartner(map);
    }


    /**
     * 方法描述：生产任务分解
     * 作者：CHENZHUJIE
     * 日期：2017/2/24
     */
    @RequestMapping("/saveProductionTask")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveProductionTask(@RequestBody SaveProjectTaskDTO dto) throws Exception {
        return this.projectTaskService.saveProjectTask2(dto);
    }

    /**
     * 方法描述：删除任务
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/deleteProjectTask")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteProjectTask(@RequestBody Map<String, Object> map) throws Exception {
        return this.projectTaskService.deleteProjectTaskById((String) map.get("id"), (String) map.get("accountId"));
    }

    /**
     * 方法描述：保存任务负责人
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:
     * @return:
     */
    @RequestMapping("/saveTaskResponsible")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveTaskResponsible(@RequestBody ProjectTaskResponsibleDTO dto) throws Exception {
        return projectTaskResponsibleService.saveTaskResponsible(dto);
    }


    /**
     * 方法描述：查询任务详情
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:map(id,appOrgId)
     * @return:
     */
    @RequestMapping("/getProjectTaskById")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTaskById(@RequestBody Map<String, Object> map) throws Exception {
        return ResponseBean.responseSuccess("查询成功")
                .addData("task", projectTaskService.getProjectTaskById((String) map.get("id"), (String) map.get("appOrgId"), (String) map.get("accountId")));
    }

    /**
     * 方法描述：经营模块查询任务详情
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:map(id,appOrgId)
     */
    @RequestMapping("/getProjectTaskByIdForOperater")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTaskDetailForOperater(@RequestBody Map<String, Object> map) throws Exception {
        return ResponseBean.responseSuccess("查询成功")
                .addData("task", projectTaskService.getProjectTaskDetailForOperater((String) map.get("id"), (String) map.get("appOrgId"), (String) map.get("accountId")));
    }


    /**
     * 方法描述：保存变更
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    @RequestMapping("/saveProjectProcessTime")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveProjectProcessTime(@RequestBody ProjectProcessTimeDTO dto) throws Exception {
        return this.projectProcessTimeService.saveProjectProcessTime(dto);
    }

    /**
     * 方法描述：查询设计阶段变更时间
     * 作者：MaoSF
     * 日期：2016/12/30
     *
     * @param:paraMap(当前公司id：companyId,任务id：targetId,类型：type（1：约定，2：计划）)
     * @return:
     */
    @RequestMapping("/getProjectDesignChangeTime")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectDesignChangeTime(@RequestBody Map<String, Object> paraMap) throws Exception {
        return projectProcessTimeService.getProjectProcessTime(paraMap);
    }

    /**
     * 方法描述：删除变更记录
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param:
     * @return:
     */
    @RequestMapping("/deleteProjectProcessTime")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteProjectProcessTime(@RequestBody Map<String, Object> paraMap) throws Exception {
        return projectProcessTimeService.deleteProjectProcessTime((String) paraMap.get("id"));
    }

    /**
     * 方法描述：移交经营负责人，项目负责人
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param:map(id,appOrgId)
     * @return:
     */
    @RequestMapping("/transferManager")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean transferManager(@RequestBody Map<String, Object> map) throws Exception {
        return this.projectManagerService.transferManager(map);

    }


    /**
     * 方法描述：移交设计负责人请求数据
     * param  （projectId)
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getProjectTaskForChangeDesigner", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTaskForChangeDesigner(@RequestBody Map<String, Object> param) throws Exception {
        return this.projectTaskService.getProjectTaskForChangeDesigner(param);
    }

    /**
     * 方法描述： 移交经营负责人或者项目负责人
     * param  （dto）
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/transferTaskDesigner", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean transferTaskDesigner(@RequestBody TransferTaskDesignerDTO dto) throws Exception {
        return projectManagerService.transferTaskDesinger(dto);
    }


    /**
     * 方法描述：验证是否已经签发给该组织
     * param  （dto)
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/validateIssueTaskCompany", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean validateIssueTaskCompany(@RequestBody Map<String, Object> param) throws Exception {
        return this.projectTaskService.validateIssueTaskCompany(param);
    }


    /**
     * 方法描述：任务签发查询公司
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getIssueTaskCompany", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getIssueTaskCompany(@RequestBody Map<String, Object> param) throws Exception {
        return this.projectTaskService.getIssueTaskCompany((String) param.get("taskId"), (String) param.get("appOrgId"));
    }

    /**
     * 方法描述：发布签发任务
     * 复制任务到正式签发任务，同时保存相应的日期更改历史、项目经营人等信息，并把状态改为已发布
     * 作   者： ZCL
     * 日   期：2017/5/15
     * param  dto  要更改的任务信息，包括任务起止时间
     */
    @RequestMapping(value = "publishIssueTask", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean publishIssueTask(@RequestBody Map<String, Object> map) throws Exception {
        return projectTaskService.publishIssueTask((List<String>) map.get("idList"), (String) map.get("accountId"), (String) map.get("appOrgId"));
    }


    /**
     * 方法描述：发布生产任务任务
     *           设计人员，计划时间等信息，并把状态改为已发布
     * 作   者： MaoSF
     * 日   期：2017/6/23
     * param  dto  要更改的任务信息，包括任务起止时间
     */
    @RequestMapping(value = "publishProductTask", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean publishProductTask(@RequestBody Map<String, Object> map) throws Exception {
        return projectTaskService.publishProductTask((List<String>) map.get("idList"), (String) map.get("accountId"), (String) map.get("appOrgId"));
    }

    /**
     * 方法描述：任务签发查询公司
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getProjectTaskDetailForEdit", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectTaskDetailForEdit(@RequestBody Map<String, Object> param) throws Exception {
        ProjectTaskForEditDTO task = this.projectTaskService.getProjectTaskDetailForEdit((String) param.get("id"), (String) param.get("appOrgId"), (String) param.get("accountId"));
        return ResponseBean.responseSuccess().addData("task", task);
    }

    /**
     * 方法描述：任务签发查询
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getProjectIssueTask", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectIssueTask(@RequestBody QueryProjectTaskDTO query) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addDataFromObject(this.projectTaskService.getProjectIssueTask(query));
    }

    /**
     * 方法描述：生产任务查询
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getProjectProductTask", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectProductTask(@RequestBody QueryProjectTaskDTO query) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addDataFromObject(this.projectTaskService.getProjectProductTask(query));
    }

    /**
     * 方法描述：生产任务，查询成员列表
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getProjectMemberByTaskId", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectMemberByTaskId(@RequestBody Map<String,Object> map) throws Exception{
        return ResponseBean.responseSuccess("查询成功").addData("memberList",projectMemberService.listDesignMember((String)map.get("taskId")));
    }

    /**
     * 方法描述：查询APP首页数据
     * param
     * 作   者： MaoSF
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping(value = "/getHomeData", method = RequestMethod.POST)
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getHomeData(@RequestBody Map<String,Object> map) throws Exception{
        return ResponseBean.responseSuccess("查询成功").addData("homeData",projectTaskService.getHomeData2(map));
    }
}
