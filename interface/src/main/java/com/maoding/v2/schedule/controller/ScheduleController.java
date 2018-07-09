package com.maoding.v2.schedule.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.schedule.dto.HandleScheduleDTO;
import com.maoding.schedule.dto.HandleScheduleMemberDTO;
import com.maoding.schedule.dto.QueryScheduleDTO;
import com.maoding.schedule.dto.SaveScheduleDTO;
import com.maoding.schedule.service.ScheduleService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/v2/schedule/")
@Controller
public class ScheduleController extends BaseWSController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 保存会议/日程
     */
    @RequestMapping("/saveSchedule")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveSchedule(@RequestBody SaveScheduleDTO dto) throws Exception {
        return scheduleService.saveSchedule(dto);
    }

    /**
     * 获取会议/日程
     */
    @RequestMapping("/getScheduleById")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getScheduleById(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("schedule",scheduleService.getScheduleById(dto));
    }

    /**
     * 删除或许取消会议/日程 type = 1:删除，type = 2 取消
     */
    @RequestMapping("/handleSchedule")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean handleSchedule(@RequestBody HandleScheduleDTO dto) throws Exception {
        return scheduleService.handleSchedule(dto);
    }

    /**
     * 是否参加会议 status = 1:参加，status = 2 不参加
     */
    @RequestMapping("/handleScheduleMember")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean handleScheduleMember(@RequestBody HandleScheduleMemberDTO dto) throws Exception {
        return scheduleService.handleScheduleMember(dto);
    }

    /**
     * 获取会议，日程，工时
     */
    @RequestMapping("/getScheduleByDate")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getScheduleByDate(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("schedule",scheduleService.getScheduleByDate(dto));
    }


    /**
     * 获取会议，日程，工时
     */
    @RequestMapping("/getScheduleDate")
    @ResponseBody
    public ResponseBean getScheduleDate(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("dateList",scheduleService.getScheduleDate(dto));
    }

    /**
     * 获取日程成员
     */
    @RequestMapping("/getScheduleMember")
    @ResponseBody
    public ResponseBean getScheduleMember(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("memberList",scheduleService.getScheduleMember(dto.getId()));
    }

    /**
     * 获取会议成员
     */
    @RequestMapping("/getScheduleMemberForMeeting")
    @ResponseBody
    public ResponseBean getScheduleMemberForMeeting(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("memberList",scheduleService.getScheduleMemberForMeeting(dto.getId()));
    }


    /**
     * 获取会议成员
     */
    @RequestMapping("/getTodayAndFutureSchedule")
    @ResponseBody
    public ResponseBean getTodayAndFutureSchedule(@RequestBody QueryScheduleDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("scheduleList",scheduleService.getTodayAndFutureSchedule(dto));
    }

}
