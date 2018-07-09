package com.maoding.labor.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.labor.dto.LaborHourDTO;
import com.maoding.labor.dto.QueryLaborHourDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.labor.entity.LaborHourEntity;
import com.maoding.schedule.dto.QueryScheduleDTO;

import java.util.Date;
import java.util.List;

public interface LaborHourDao extends BaseDao<LaborHourEntity> {

    List<LaborHourEntity> getLaborHourByDate(String companyUserId, Date laborDate);

    List<LaborHourDTO> getLaborHourDataByDate(String companyUserId, Date laborDate);

    List<String> getLaborDate(QueryLaborHourDTO queryLaborHourDTO);

    int deleteLaborHour(SaveLaborHourDTO dto);

}
