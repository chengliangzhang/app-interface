package com.maoding.schedule.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.schedule.dao.ScheduleDao;
import com.maoding.schedule.dto.QueryScheduleDTO;
import com.maoding.schedule.dto.ScheduleDTO;
import com.maoding.schedule.entity.ScheduleEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleDao")
public class ScheduleDaoImpl extends GenericDao<ScheduleEntity> implements ScheduleDao {

    @Override
    public ScheduleDTO getScheduleById(QueryScheduleDTO query) {
        return this.sqlSession.selectOne("GetScheduleMapper.getScheduleById",query);
    }

    @Override
    public List<ScheduleDTO> getScheduleByDate(QueryScheduleDTO query) {
        return this.sqlSession.selectList("GetScheduleMapper.getScheduleByDate",query);
    }

    @Override
    public List<ScheduleDTO> getScheduleDate(QueryScheduleDTO query) {
        return this.sqlSession.selectList("GetScheduleMapper.getScheduleDate",query);
    }

    @Override
    public List<ScheduleDTO> getTodayAndFutureSchedule(QueryScheduleDTO query) {
        return this.sqlSession.selectList("GetScheduleMapper.getTodayAndFutureSchedule",query);
    }
}
