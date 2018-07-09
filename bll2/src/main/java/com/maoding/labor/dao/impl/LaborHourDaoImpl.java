package com.maoding.labor.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.labor.dao.LaborHourDao;
import com.maoding.labor.dto.LaborHourDTO;
import com.maoding.labor.dto.QueryLaborHourDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.labor.entity.LaborHourEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("laborHourDao")
public class LaborHourDaoImpl extends GenericDao<LaborHourEntity> implements LaborHourDao {

    @Override
    public List<LaborHourEntity> getLaborHourByDate(String companyUserId, Date laborDate) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyUserId",companyUserId);
        map.put("laborDate",laborDate);
        return this.sqlSession.selectList("LaborHourEntityMapper.getLaborHourByDate",map);
    }

    @Override
    public List<LaborHourDTO> getLaborHourDataByDate(String companyUserId, Date laborDate) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyUserId",companyUserId);
        map.put("laborDate",laborDate);
        return this.sqlSession.selectList("LaborHourEntityMapper.getLaborHourDataByDate",map);
    }

    @Override
    public List<String> getLaborDate(QueryLaborHourDTO queryLaborHourDTO) {
        return this.sqlSession.selectList("LaborHourEntityMapper.getLaborDate",queryLaborHourDTO);
    }

    @Override
    public int deleteLaborHour(SaveLaborHourDTO dto) {
        return this.sqlSession.update("LaborHourEntityMapper.deleteLaborHour",dto);
    }
}
