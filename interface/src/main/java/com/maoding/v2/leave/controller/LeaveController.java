package com.maoding.v2.leave.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.financial.dto.SaveLeaveDTO;
import com.maoding.financial.service.LeaveService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.system.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v2/leave")
public class LeaveController extends BaseWSController{

    @Autowired
    private DataDictionaryService dataDictionaryService;


    @Autowired
    private LeaveService leaveService;


    /**
     * 方法描述：获取请假类型
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/getLeaveType")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getLeaveType() throws Exception {
        return ResponseBean.responseSuccess().addData("leaveTypeList", this.dataDictionaryService.getSubDataByCode(SystemParameters.LEAVE));
    }

    /**
     * 方法描述：保存请假信息
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/saveLeave")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveLeave(@RequestBody SaveLeaveDTO dto) throws Exception {
        return leaveService.saveLeave(dto);
    }

    /**
     * 方法描述：获取请假信息
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/getLeaveDetail")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getLeaveDetail(@RequestBody SaveLeaveDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("leaveDetail",leaveService.getLeaveDetail(dto.getId()));
    }

}
