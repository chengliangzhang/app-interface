package com.maoding.schedule.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.schedule.dto.*;

import java.util.List;

public interface ScheduleService {

    /**
     * 保存日程
     */
    ResponseBean saveSchedule(SaveScheduleDTO dto) throws Exception;

    /**
     * 查询日程
     */
    ScheduleDTO getScheduleById(QueryScheduleDTO dto) throws Exception;

    /**
     * 删除日程/取消日程
     */
    ResponseBean handleSchedule(HandleScheduleDTO dto) throws Exception;

    /**
     * 参加/不参加会议
     */
    ResponseBean handleScheduleMember(HandleScheduleMemberDTO dto) throws Exception;

    /**
     * 获取个人在某个日期的 日程，会议，工时
     */
    List<ScheduleAndLaborDTO> getScheduleByDate(QueryScheduleDTO dto) throws Exception;

    /**
     * 获取当前月份 包含 日程和会议 的所有日期
     */
    List<String> getScheduleDate(QueryScheduleDTO query);

    /**
     * 获取参会人
     */
    List<ScheduleMemberDTO> getScheduleMember(String scheduleId);

    /**
     * 获取参会人(会议）
     */
    List<MeetingScheduleDTO> getScheduleMemberForMeeting(String scheduleId);

    /**
     * 获取今天及以后的日程
     */
    List<ScheduleGroupDataDTO> getTodayAndFutureSchedule(QueryScheduleDTO dto) throws Exception;
}
