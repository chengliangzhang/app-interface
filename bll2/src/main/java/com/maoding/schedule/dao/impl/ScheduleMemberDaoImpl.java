package com.maoding.schedule.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.schedule.dao.ScheduleMemberDao;
import com.maoding.schedule.dto.ScheduleMemberDTO;
import com.maoding.schedule.entity.ScheduleMemberEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleMemberDao")
public class ScheduleMemberDaoImpl extends GenericDao<ScheduleMemberEntity> implements ScheduleMemberDao {

    @Override
    public ScheduleMemberEntity getScheduleMember(String scheduleId, String memberId) {
        Map<String,Object> map = new HashMap<>();
        map.put("scheduleId",scheduleId);
        map.put("memberId",memberId);
        return this.sqlSession.selectOne("ScheduleMemberEntityMapper.getScheduleMember",map);
    }

    @Override
    public List<ScheduleMemberEntity> getMemberByScheduleId(String scheduleId) {
        return this.sqlSession.selectList("ScheduleMemberEntityMapper.getMemberByScheduleId",scheduleId);
    }

    @Override
    public List<ScheduleMemberDTO> listMemberByScheduleId(String scheduleId) {
        return this.sqlSession.selectList("GetScheduleMemberMapper.listMemberByScheduleId",scheduleId);
    }
}
