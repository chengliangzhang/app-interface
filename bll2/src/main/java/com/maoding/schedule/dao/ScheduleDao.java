package com.maoding.schedule.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.schedule.dto.QueryScheduleDTO;
import com.maoding.schedule.dto.ScheduleDTO;
import com.maoding.schedule.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleDao extends BaseDao<ScheduleEntity> {

    ScheduleDTO getScheduleById(QueryScheduleDTO query) ;

    List<ScheduleDTO> getScheduleByDate(QueryScheduleDTO query);

    /**
     * 获取当前月份 包含 日程和会议 的所有日期
     */
    List<ScheduleDTO> getScheduleDate(QueryScheduleDTO query);

    /**
     * 获取今日及以后的日程数据
     */
    List<ScheduleDTO> getTodayAndFutureSchedule(QueryScheduleDTO query);
}
