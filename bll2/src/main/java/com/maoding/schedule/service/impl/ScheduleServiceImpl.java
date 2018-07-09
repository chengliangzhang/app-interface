package com.maoding.schedule.service.impl;


import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.dto.ImSendMessageDTO;
import com.maoding.hxIm.service.ImQueueProducer;
import com.maoding.labor.dao.LaborHourDao;
import com.maoding.labor.dto.LaborHourDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.schedule.dao.ScheduleDao;
import com.maoding.schedule.dao.ScheduleMemberDao;
import com.maoding.schedule.dto.*;
import com.maoding.schedule.entity.ScheduleEntity;
import com.maoding.schedule.entity.ScheduleMemberEntity;
import com.maoding.schedule.service.ScheduleService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    protected final Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleMemberDao scheduleMemberDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private LaborHourDao laborHourDao;

    @Autowired
    private MessageService messageService;


    ResponseBean validateSaveSchedule(SaveScheduleDTO dto){

        if (StringUtil.isNullOrEmpty(dto.getScheduleType()))  return  ResponseBean.responseError("参数错误");
        if(dto.getScheduleType()==1){
            if (StringUtil.isNullOrEmpty(dto.getContent())) return  ResponseBean.responseError("内容不能为空");
        }else {
            if (StringUtil.isNullOrEmpty(dto.getContent())){
                return  ResponseBean.responseError("标题不能为空");
            }
        }

        if (CollectionUtils.isEmpty(dto.getMemberList())) return  ResponseBean.responseError("至少选择一个成员");
        for(CompanyUserAppDTO user:dto.getMemberList()){
            if (StringUtil.isNullOrEmpty(user.getId())) return  ResponseBean.responseError("参数错误");
            if (StringUtil.isNullOrEmpty(user.getUserId()))  return  ResponseBean.responseError("参数错误");
        }
        return null;
    }


    @Override
    public ResponseBean saveSchedule(SaveScheduleDTO dto) throws Exception {
        ResponseBean responseBean = validateSaveSchedule(dto);
        if(responseBean!=null){
            return responseBean;
        }
        CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (u==null) throw new NullPointerException("数据错误");
        ScheduleEntity entity = new ScheduleEntity();
        BaseDTO.copyFields(dto,entity);
        List<String> userIds = new ArrayList<>();
        if(StringUtil.isNullOrEmpty(dto.getId())){
            entity.setDeleted(0);
            entity.initEntity();
            if(!StringUtil.isNullOrEmpty(dto.getTargetId())){
                entity.setId(dto.getTargetId());
            }
            entity.setCompanyId(dto.getAppOrgId());
            entity.setPublishId(u.getId());
            entity.setStatus(0);
            scheduleDao.insert(entity);
            for(CompanyUserAppDTO user:dto.getMemberList()){
                if(userIds.contains(user.getUserId())){
                    continue;
                }
                userIds.add(user.getUserId());
                saveScheduleMember(entity.getId(),entity.getReminderTime(),user,u.getId());
            }
        }else {//修改
            scheduleDao.updateById(entity);
            //查询人员
            String memberIds = "";
            List<ScheduleMemberDTO> oldMemberList = this.scheduleMemberDao.listMemberByScheduleId(entity.getId());
            for(CompanyUserAppDTO user:dto.getMemberList()){
                if(userIds.contains(user.getUserId())){
                    continue;
                }
                userIds.add(user.getUserId());
                memberIds += user.getId()+",";
                boolean isExist = false;
                for(ScheduleMemberDTO memberEntity:oldMemberList){
                    if(memberEntity.getMemberId().equals(user.getId())){
                        ScheduleMemberEntity member = new ScheduleMemberEntity();
                        member.setId(memberEntity.getScheduleMemberId());
                        member.setReminderTime(dto.getReminderTime());
                        scheduleMemberDao.updateById(member);
                        isExist = true;
                        continue;
                    }
                }
                if(!isExist){
                    saveScheduleMember(entity.getId(),entity.getReminderTime(),user,u.getId());
                }
            }

            //删除该次没有的成员（原来添加的成员）
            List<ScheduleMemberDTO> deleteMemberList = new ArrayList<>();
            for(ScheduleMemberDTO memberEntity:oldMemberList){
                if(!memberIds.contains(memberEntity.getMemberId())){
                    ScheduleMemberEntity member = new ScheduleMemberEntity();
                    member.setId(memberEntity.getScheduleMemberId());
                    member.setDeleted(1);
                    scheduleMemberDao.updateById(member);
                    deleteMemberList.add(memberEntity);
                }
            }
            //TODO 消息通知
            if(!CollectionUtils.isEmpty(deleteMemberList)){
                this.sendMessage2(entity.getId(),deleteMemberList,dto.getAccountId(),3);
            }
        }

        //推送信息
        if(dto.getReminderMode()!=null){
            if (dto.getReminderMode()==1){
                this.sendMessage(entity.getId(),dto.getMemberList(),dto.getAccountId(),1);
            }
            if(dto.getReminderMode()==2){
                userIds = userIds.stream().filter(c->!dto.getAccountId().equals(c)).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(userIds)){
                    sendMsgForSms(userIds,u.getUserName(),entity.getId());
                }
            }
        }else {
            this.sendMessage(entity.getId(),dto.getMemberList(),dto.getAccountId(),1);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    private void saveScheduleMember(String scheduleId,Integer reminderTime,CompanyUserAppDTO user,String currentCompanyUserId){
        ScheduleMemberEntity scheduleMember = new ScheduleMemberEntity();
        scheduleMember.setMemberId(user.getId());
        scheduleMember.setScheduleId(scheduleId);
        scheduleMember.setStatus(0);
        if(user.getId().equals(currentCompanyUserId)){
            scheduleMember.setStatus(1);
        }
        scheduleMember.setReminderTime(reminderTime);
        scheduleMember.setDeleted(0);
        scheduleMember.initEntity();
        scheduleMemberDao.insert(scheduleMember);
    }

    @Override
    public ScheduleDTO getScheduleById(QueryScheduleDTO dto) throws Exception {
        CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (u==null) throw new NullPointerException("数据错误");
        dto.setCompanyUserId(u.getId());
        //TODO 数据查询
        ScheduleDTO data = scheduleDao.getScheduleById(dto);
        if(data==null){
            throw new NullPointerException("数据错误");
        }
        //设置结束状态（如果是参加的成员，当前的时间如果已经大于结束的时间，则设置为已结束）
        setScheduleStatus(data);
        data.setMemberList(this.getScheduleMember(dto.getId()));
        //查询附件
        Map<String,Object> param = new HashMap<>();
        param.put("targetId", dto.getId());
        param.put("type", NetFileType.SCHEDULE_ATTACH);
        data.setAttachList(this.projectSkyDriverService.getAttachDataList(param));
        return data;
    }

    private void setScheduleStatus(ScheduleDTO dto){
        if(dto!=null && dto.getStatus()!=null && dto.getStatus()==1){
            if(!StringUtil.isNullOrEmpty(dto.getEndTime())){
               if(DateUtils.daysOfTwo(DateUtils.getDate(),dto.getEndTime())>0){
                   dto.setStatus(3);//已结束
                }
            }
        }
    }
    @Override
    public ResponseBean handleSchedule(HandleScheduleDTO dto) throws Exception {
        if(dto.getType()==1){ //删除
            CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
            if (u==null) throw new NullPointerException("数据错误");
            ScheduleMemberEntity member = scheduleMemberDao.getScheduleMember(dto.getId(),u.getId());
            //删除只删除自己的数据
            member.setDeleted(1);
            this.scheduleMemberDao.updateById(member);
        }else { //取消
            //TODO 删除逻辑
            ScheduleEntity entity = scheduleDao.selectById(dto.getId());
            if (entity==null) throw new NullPointerException("数据错误");
            entity.setStatus(1);
            entity.setCancelReason(dto.getCancelReason());
            this.scheduleDao.updateById(entity);
            //TODO 消息通知
            this.sendMessage2(dto.getId(),this.getScheduleMember(dto.getId()),dto.getAccountId(),3);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    @Override
    public ResponseBean handleScheduleMember(HandleScheduleMemberDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
        ScheduleMemberEntity member = scheduleMemberDao.getScheduleMember(dto.getScheduleId(),user.getId());
        if (member==null) throw new NullPointerException("数据错误");
        member.setStatus(dto.getStatus()); //1:参加，2：不参加
        member.setRefuseReason(dto.getRefuseReason());
        this.scheduleMemberDao.updateById(member);
        //TODO 消息通知
        sendMessage3(dto.getScheduleId(),dto.getAppOrgId(),dto.getAccountId(),member.getId(),dto.getStatus());
        return ResponseBean.responseSuccess("操作成功");

    }

    @Override
    public List<ScheduleAndLaborDTO> getScheduleByDate(QueryScheduleDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
       // dto.setCompanyUserId(user.getId());
        //查询日程,查询会议
        List<ScheduleDTO> scheduleList = scheduleDao.getScheduleByDate(dto);//此接口包含了日程和会议
        List<ScheduleAndLaborDTO> list = BaseDTO.copyFields(scheduleList,ScheduleAndLaborDTO.class);
        return list;
    }

    @Override
    public List<String> getScheduleDate(QueryScheduleDTO query) {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if (user==null){
            throw new NullPointerException("数据错误");
        }
        //query.setCompanyUserId(user.getId());
        List<ScheduleDTO> list = scheduleDao.getScheduleDate(query);
        List<String> dateList = new ArrayList<>();
        list.stream().forEach(c->{
            if(!StringUtil.isNullOrEmpty(c.getStartTime())){
                dateList.addAll(DateUtils.getBetweenDates(c.getStartTime(),c.getEndTime()));
            }
        });
        return dateList;
    }


    @Override
    public List<ScheduleMemberDTO> getScheduleMember(String scheduleId) {
        return scheduleMemberDao.listMemberByScheduleId(scheduleId);
    }

    @Override
    public List<MeetingScheduleDTO> getScheduleMemberForMeeting(String scheduleId) {
        List<ScheduleMemberDTO> list = this.getScheduleMember(scheduleId);
        MeetingScheduleDTO notParty = new MeetingScheduleDTO(2);
        MeetingScheduleDTO party = new MeetingScheduleDTO(1);
        MeetingScheduleDTO notResponse = new MeetingScheduleDTO(0);
        for(ScheduleMemberDTO memberDTO:list){
            if(memberDTO.getStatus()==0){
                notResponse.setTotal(notResponse.getTotal()+1);
                notResponse.getMemberList().add(memberDTO);
            }
            if(memberDTO.getStatus()==1){
                party.setTotal(party.getTotal()+1);
                party.getMemberList().add(memberDTO);
            }
            if(memberDTO.getStatus()==2){
                notParty.setTotal(notParty.getTotal()+1);
                notParty.getMemberList().add(memberDTO);
            }
        }
        List<MeetingScheduleDTO> dataList = new ArrayList<>();
        dataList.add(party);
        dataList.add(notParty);
        dataList.add(notResponse);
        return dataList;
    }

    @Override
    public List<ScheduleGroupDataDTO> getTodayAndFutureSchedule(QueryScheduleDTO query) throws Exception {
        query.setDate(DateUtils.date2Str(DateUtils.date_sdf));
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
        query.setCompanyUserId(user.getId());
        List<ScheduleDTO> list = scheduleDao.getTodayAndFutureSchedule(query);
        Map<String,List<ScheduleDTO>> map = new HashMap<>();
        Set<String> dateList = new TreeSet<>();
        for(ScheduleDTO c:list){
            handleSchedule(c,map,dateList);
        }
        List<ScheduleGroupDataDTO> data = new ArrayList<>();
        //重新组装数据
        for(String date:dateList){
            ScheduleGroupDataDTO d = new ScheduleGroupDataDTO();
            d.setDate(date);
            d.setScheduleList(map.get(date));
            data.add(d);
        }
        return data;
    }

    private void handleSchedule(ScheduleDTO c,Map<String,List<ScheduleDTO>> dataMap,Set<String> dateList){
        List<String> date = null;
        if(!StringUtil.isNullOrEmpty(c.getStartTime())){
            date = DateUtils.getBetweenDates(c.getStartTime(),c.getEndTime());
            dateList.addAll(date);
        }
        for(String d:date){
            if(!dataMap.containsKey(d)){
                dataMap.put(d,new ArrayList<>());
            }
            dataMap.get(d).add(c);
        }
    }
    /**
     * 短信提醒
     */
    public void sendMsgForSms(List<String> userIds,String sendUserName,String id) throws Exception {
        ScheduleEntity entity = this.scheduleDao.selectById(id);
        String content = null;
        if(entity.getScheduleType()==1){
            content = SystemParameters.SCHEDULE_MSG;
        }else {
            content = SystemParameters.MEETING_MSG;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userIds",userIds);
        List<AccountEntity> accountList =accountDao.listAccountByIds(map);
        Sms sms = new Sms();
        accountList.stream().forEach(account->{
            sms.addMobile(account.getCellphone());
        });
        if(!CollectionUtils.isEmpty(sms.getMobile())){
            sms.setMsg(StringUtil.format(content,sendUserName,entity.getContent(),
                    DateUtils.date2Str(entity.getStartTime(),DateUtils.time_sdf_slash),
                    DateUtils.date2Str(entity.getEndTime(),DateUtils.time_sdf_slash)));
            smsSender.send(sms);
        }
    }

    /**
     *type:1=新增会议/日程,2=修改会议/日程，3=取消会议/日程
     */
    private void sendMessage(String id,List<CompanyUserAppDTO> userList,String accountId,Integer type) throws Exception{
        ScheduleEntity entity = this.scheduleDao.selectById(id);
        MessageEntity message = new MessageEntity();
        Integer messageType = 0;
        if(type==1){
            if(entity.getScheduleType()==1){
                messageType = SystemParameters.MESSAGE_TYPE_701;
            }
            if(entity.getScheduleType()==2){
                messageType = SystemParameters.MESSAGE_TYPE_704;
            }
        }
        if(type==2){
            if(entity.getScheduleType()==1){
                messageType = SystemParameters.MESSAGE_TYPE_702;
            }
            if(entity.getScheduleType()==2){
                messageType = SystemParameters.MESSAGE_TYPE_705;
            }
        }
        if(type==3){
            if(entity.getScheduleType()==1){
                messageType = SystemParameters.MESSAGE_TYPE_703;
            }
            if(entity.getScheduleType()==2){
                messageType = SystemParameters.MESSAGE_TYPE_706;
            }
        }

        message.setTargetId(entity.getId());
        message.setMessageType(messageType);
        message.setSendCompanyId(entity.getCompanyId());
        message.setCreateBy(accountId);
        for(CompanyUserAppDTO user:userList){
            if(!user.getUserId().equals(accountId)){
                message.setUserId(user.getUserId());
                message.setCompanyId(user.getCompanyId());
                messageService.sendMessage(message);
            }
        }
    }

    private void sendMessage2(String id,List<ScheduleMemberDTO> userList,String accountId,Integer type) throws Exception{
        this.sendMessage(id,BaseDTO.copyFields(userList,CompanyUserAppDTO.class),accountId,type);
    }

    private void sendMessage3(String id,String currentCompanyId,String accountId,String param1,Integer type) throws Exception{
        ScheduleEntity entity = this.scheduleDao.selectById(id);
        MessageEntity message = new MessageEntity();
        Integer messageType = 0;
        if(type==1){
            messageType = SystemParameters.MESSAGE_TYPE_707;
        }
        if(type==2){
            messageType = SystemParameters.MESSAGE_TYPE_708;
        }
        CompanyUserEntity u = companyUserDao.selectById(entity.getPublishId());
        message.setCompanyId(u.getCompanyId());
        message.setUserId(u.getUserId());
        message.setTargetId(id);
        message.setMessageType(messageType);
        message.setSendCompanyId(currentCompanyId);
        message.setCreateBy(accountId);
        message.setParam1(param1);
        messageService.sendMessage(message);
    }
 }
