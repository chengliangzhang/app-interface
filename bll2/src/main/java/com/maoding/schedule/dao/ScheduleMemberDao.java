package com.maoding.schedule.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.schedule.dto.ScheduleMemberDTO;
import com.maoding.schedule.entity.ScheduleMemberEntity;

import java.util.List;

public interface ScheduleMemberDao extends BaseDao<ScheduleMemberEntity> {

    ScheduleMemberEntity getScheduleMember(String scheduleId,String memberId) ;

    List<ScheduleMemberEntity> getMemberByScheduleId(String scheduleId) ;

    List<ScheduleMemberDTO> listMemberByScheduleId(String scheduleId) ;
}
