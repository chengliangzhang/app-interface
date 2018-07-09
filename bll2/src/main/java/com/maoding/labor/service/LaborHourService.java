package com.maoding.labor.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.labor.dto.LaborHourDataDTO;
import com.maoding.labor.dto.QueryLaborHourDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.schedule.dto.QueryScheduleDTO;
import com.maoding.schedule.dto.ScheduleAndLaborDTO;

import java.util.List;

public interface LaborHourService {

    /**
     * 保存工时
     */
    ResponseBean saveLaborHour(SaveLaborHourDTO dto) throws Exception;

    /**
     * 工时查询根据日期
     */
    LaborHourDataDTO getLaborHour(SaveLaborHourDTO dto) throws Exception;

    /**
     * 获取某月份具有工时的日期
     */
    List<String> getLaborDate(QueryLaborHourDTO dto) throws Exception;

    List<ScheduleAndLaborDTO> getLaborHourByDate(QueryLaborHourDTO dto) throws Exception;

    ResponseBean deleteLaborHour(SaveLaborHourDTO dto) throws Exception;
}
