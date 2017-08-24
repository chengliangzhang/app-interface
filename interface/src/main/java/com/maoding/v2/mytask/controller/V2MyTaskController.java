package com.maoding.v2.mytask.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2MyTaskController
 * 作   者：MaoSF
 * 日   期：2016/12/26
 */
@Controller
@RequestMapping("/v2/mytask")
public class V2MyTaskController extends BaseWSController {


    @Autowired
    private MyTaskService myTaskService;

    /**
     * 方法描述：项目动态 列表
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getMyTask")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getMyTask(@RequestBody Map<String, Object> paraMap) throws Exception {
        return myTaskService.getMyTaskDTO(paraMap);
    }

    /**
     * 方法描述：任务 列表
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getMyTaskList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getMyTaskList(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("myTaskList", myTaskService.getMyTaskList(paraMap));
    }

    /**
     * 方法描述：根据projectId查询列表（报销特殊，projectId为null）
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:（projectId，accountId，appOrgId）
     * @return:
     */
    @RequestMapping("/getMyTaskByProjectId")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getMyTaskByProjectId(@RequestBody Map<String, Object> paraMap) throws Exception {
        return myTaskService.getMyTaskByProjectId(paraMap);
    }

    /**
     * 方法描述：获取任务详情
     * 作者：MaoSF
     * 日期：2017/1/11
     *
     * @param:
     * @return:
     */
    @RequestMapping("/getMyTaskDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getMyTaskDetail(@RequestBody Map<String, Object> paraMap) throws Exception {
        return this.myTaskService.getMyTaskDetail((String) paraMap.get("id"), (String) paraMap.get("accountId"));
    }

    /**
     * 方法描述：处理任务
     * 作者：MaoSF
     * 日期：2017/1/11
     *
     * @param:
     * @return:
     */
    @RequestMapping("/handleMyTask")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean handleMyTask(@RequestBody Map<String, Object> paraMap) throws Exception {
        if (paraMap.get("paidDate") != null) {
            return this.myTaskService.handleMyTask((String) paraMap.get("id"), (String) paraMap.get("result"), (String) paraMap.get("status"), (String) paraMap.get("accountId"), (String) paraMap.get("paidDate"));
        }
        return this.myTaskService.handleMyTask((String) paraMap.get("id"), (String) paraMap.get("result"), (String) paraMap.get("status"), (String) paraMap.get("accountId"));
    }

}
