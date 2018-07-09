package com.maoding.circle.service.impl;

import com.google.common.collect.Lists;
import com.maoding.attach.dto.FileDataDTO;
import com.maoding.circle.dao.MaodingCircleCommentDao;
import com.maoding.circle.dao.MaodingCircleDao;
import com.maoding.circle.dao.MaodingCircleReadDao;
import com.maoding.circle.dto.*;
import com.maoding.circle.entity.MaodingCircleCommentEntity;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.circle.entity.MaodingCircleReadEntity;
import com.maoding.circle.service.MaodingCircleService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.message.dto.SendMessageDataDTO;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
@Service("maodingCircleService")
public class MaodingCircleServiceImpl implements MaodingCircleService {

    @Autowired
    private MaodingCircleDao maodingCircleDao;

    @Autowired
    private MaodingCircleCommentDao maodingCircleCommentDao;

    @Autowired
    private MaodingCircleReadDao maodingCircleReadDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Override
    public ResponseBean publishMaodingCircle(MaodingCircleDTO dto) throws Exception {
        CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if(u==null){
            throw new Exception("参数错误");
        }else if(!"1".equals(u.getAuditStatus())){
            return ResponseBean.responseError("你已离职，不能发表话题");
        }
        //保存信息
        MaodingCircleEntity entity = new MaodingCircleEntity();
        BaseDTO.copyFields(dto,entity);
        if(StringUtil.isNullOrEmpty(dto.getId())){
            entity.initEntity();
            if(!StringUtil.isNullOrEmpty(dto.getTargetId())){
                entity.setId(dto.getTargetId());
            }
            entity.setPublishUserId(u.getId());
            entity.setCompanyId(dto.getAppOrgId());
            entity.setCreateBy(dto.getAccountId());
            entity.setDeleted(0);
            if(dto.getCircleType()==null){
                dto.setCircleType(0);
                entity.setCircleType(0);
            }
            maodingCircleDao.insert(entity);
            List<CompanyUserAppDTO> userList;
            if(dto.getCircleType()!=null && 1==dto.getCircleType()){
                userList = dto.getUserList();
            }else {
                //通知与该朋友圈相关的好友
                List<CompanyUserEntity> list = companyUserDao.getCompanyUserByCompanyId(dto.getAppOrgId());
                List<CompanyUserAppDTO> dtoList = BaseDTO.copyFields(list,CompanyUserAppDTO.class);
                userList = new ArrayList<>();
                dtoList.stream().filter((user) -> (!dto.getAccountId().equals(user.getUserId()))).forEach((user) -> {
                    userList.add(user);
                });
            }
            if(!userList.isEmpty()){
                sendMessage(userList,entity,null);//特别提醒
            }
            return ResponseBean.responseSuccess();
        }
        return ResponseBean.responseError("操作失败");
    }

    @Override
    public ResponseBean commentMaodingCircle(MaodingCircleCommentDTO dto) throws Exception {
        CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if(u==null){
            throw new Exception("参数错误");
        }
        dto.setCommentUserId(u.getId());
        dto.setCompanyId(dto.getAppOrgId());
        //保存评论
        MaodingCircleCommentEntity entity = new MaodingCircleCommentEntity();
        BaseDTO.copyFields(dto,entity);
        entity.initEntity();
        entity.setDeleted(0);
        maodingCircleCommentDao.insert(entity);
        //通知与该朋友圈相关的好友
        MaodingCircleEntity circleEntity = maodingCircleDao.selectById(entity.getCircleId());
        CompanyUserEntity publishUser = companyUserDao.selectById(circleEntity.getPublishUserId());
        List<MaodingCircleCommentDataDTO> list = maodingCircleCommentDao.getCircleCommentByCircleId(entity.getCircleId());
        List<CompanyUserAppDTO> userList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dto.getUserList())){
            userList.addAll(dto.getUserList());
        }
        if(!dto.getAccountId().equals(publishUser.getUserId())){
            userList.add((CompanyUserAppDTO)BaseDTO.copyFields(publishUser,CompanyUserAppDTO.class));
        }

        list.stream().filter((comment) -> !isContentUser(comment.getCommentAccountId(),userList) && !comment.getCommentAccountId().equals(dto.getAccountId())).forEach((comment) -> {
            userList.add(comment.getCommentUser());
        });
        if(!CollectionUtils.isEmpty(userList)){
            circleEntity.setContent(entity.getComment());//只是为了传递评论的内容
            sendMessage(userList,circleEntity,entity.getId(),dto.getUserList()); //普通提醒
        }
        return ResponseBean.responseSuccess();
    }

    boolean isContentUser(String accountId,List<CompanyUserAppDTO> userList){
        for(CompanyUserAppDTO user:userList){
            if(user.getUserId().equals(accountId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String,Object> listMaodingCircle(QueryCircleDTO query) throws Exception {
        //查询
        List<MaodingCircleDataDTO> list = maodingCircleDao.listMaodingCircle(query);
        for(MaodingCircleDataDTO dto:list){
            List<FileDataDTO> fileList = this.getCircleAttach(dto.getId());
            fileList.stream().forEach(c-> {
                dto.getAttachList().add(c.getFileFullPath());
                dto.getThumbAttachList().add(c.getCrtFilePath());
            });
            dto.setCommentCount(maodingCircleCommentDao.getCircleCommentByCircleId(dto.getId()).size());
        }
        Map<String,Object> data = new HashMap<>();
        data.put("circleList",list);
        query.setIsRead(0);
        data.put("notReadCount",maodingCircleReadDao.getNotReadCircle(query).size());
        data.put("currentUserOrgId",maodingCircleDao.getPartInCompany(query));
        return data;
    }

    private List<FileDataDTO> getCircleAttach(String id) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",id);
        return projectSkyDriverService.getAttachDataList(map);
    }

    @Override
    public MaodingCircleDataDTO getMaodingCircleById(QueryCircleDTO query) throws Exception{
        MaodingCircleDataDTO data = this.maodingCircleDao.getMaodingCircleById(query);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",data.getId());
        List<FileDataDTO> fileList = this.getCircleAttach(data.getId());
        fileList.stream().forEach(c-> {
            data.getAttachList().add(c.getFileFullPath());
            data.getThumbAttachList().add(c.getCrtFilePath());
        });
        //把当前讨论组的未读消息设置为已读
        this.maodingCircleReadDao.updateCircleReadStatus(query);
        return data;
    }

    @Override
    public ResponseBean deleteCommentMaodingCircle(MaodingCircleCommentDTO dto) throws Exception {
        MaodingCircleCommentEntity comment = this.maodingCircleCommentDao.selectById(dto.getId());
        if(comment==null){
            throw new Exception("参数错误");
        }
        CompanyUserEntity u = companyUserDao.selectById(comment.getCommentUserId());
        if(comment==null){
            throw new Exception("数据错误");
        }
        if(!comment.getCommentUserId().equals(u.getId())){
            return ResponseBean.responseError("无权限删除");
        }
        comment.setDeleted(1);
        this.maodingCircleCommentDao.updateById(comment);
        return ResponseBean.responseSuccess("操作成功");
    }

    @Override
    public ResponseBean deleteMaodingCircle(MaodingCircleDTO dto) throws Exception {
        MaodingCircleEntity circle = this.maodingCircleDao.selectById(dto.getId());
        CompanyUserEntity publishUser = companyUserDao.selectById(circle.getPublishUserId());
        if(publishUser!=null && !publishUser.getUserId().equals(dto.getAccountId())){
            return ResponseBean.responseError("无权限删除");
        }
        circle.setDeleted(1);
        this.maodingCircleDao.updateById(circle);
        return ResponseBean.responseSuccess("操作成功");
    }

    @Override
    public ResponseBean deleteMaodingCircleRead(QueryCircleDTO dto) throws Exception {
        MaodingCircleReadEntity read = new MaodingCircleReadEntity();
        read.setId(dto.getLastCircleReadId());
        read.setDeleted(1);
        read.setUpdateBy(dto.getAccountId());
        read.setUpdateDate(DateUtils.getDate());
        int i = maodingCircleReadDao.updateById(read);
        return i>0?ResponseBean.responseSuccess("操作成功"):ResponseBean.responseError("操作失败");
    }

    @Override
    public List<MaodingCircleNoticeDTO> getNotReadCircle(QueryCircleDTO query) throws Exception {
        query.setIsRead(0);//未读数据
        List<MaodingCircleReadDTO> list = maodingCircleReadDao.getNotReadCircle(query);
        List<MaodingCircleNoticeDTO> resultList = new ArrayList<>();
        for(MaodingCircleReadDTO read:list){
            MaodingCircleEntity circle = this.maodingCircleDao.selectById(read.getCircleId());
            CompanyUserEntity publishUser = companyUserDao.selectById(circle.getPublishUserId());
            if(circle!=null){
                MaodingCircleBaseDTO circleBaseDTO = new MaodingCircleBaseDTO();
                BaseDTO.copyFields(circle,circleBaseDTO);
                List<FileDataDTO> fileList = this.getCircleAttach(circleBaseDTO.getId());
                if(!CollectionUtils.isEmpty(fileList)){
                    circleBaseDTO.setFileFullPath(fileList.get(0).getFileFullPath());
                    circleBaseDTO.setCrtFilePath(fileList.get(0).getCrtFilePath());
                }
                //查询评论
                MaodingCircleCommentDataDTO comment = null;
                if(StringUtil.isNullOrEmpty(read.getCommentId())){
                    comment = new MaodingCircleCommentDataDTO();
                    comment.setCircleId(circle.getId());
                    comment.setComment(circle.getContent());
                    Map<String,Object> map = new HashMap<>();
                    map.put("companyId",circle.getCompanyId());
                    map.put("userId",publishUser.getUserId());
                    comment.setCommentUser(this.companyUserDao.getCompanyUserByUserId(map));
                }else {
                    comment = this.maodingCircleCommentDao.getCircleCommentById(read.getCommentId());
                }
                MaodingCircleNoticeDTO dto = new MaodingCircleNoticeDTO();
                dto.setCircle(circleBaseDTO);
                dto.setComment(comment);
                resultList.add(dto);
            }
        }
        return resultList;
    }

    //1：特别提醒（@），2：评论了你，4:评论了,3：谁谁谁评论谁
    @Override
    public List<MaodingCircleCommentDataDTO> getNotReadProjectCircle(QueryCircleDTO query) throws Exception {
        query.setIsRead(0);
        List<MaodingCircleReadDTO> list = maodingCircleReadDao.getNotReadCircle(query);
        List<MaodingCircleCommentDataDTO> resultList = new ArrayList<>();
        for(MaodingCircleReadDTO read:list){
            MaodingCircleCommentDataDTO data = this.getComment(read,query.getAccountId());
            if(data!=null){
                resultList.add((data));
            }
        }
        //把数据设置为已读
        this.maodingCircleReadDao.updateCircleReadStatus(query);
        return resultList;
    }

    @Override
    public MaodingCircleCommentDataDTO getComment(MaodingCircleReadDTO read, String accountId) throws Exception {
        MaodingCircleEntity circle = null;
        MaodingCircleCommentDataDTO data = this.maodingCircleCommentDao.getCircleCommentById(read.getCommentId());
        if(data!=null){
            if(read.getIsEspeciallyRemind()==1){
                data.setReplyType(1);//特别提醒
            }else if(StringUtil.isNullOrEmpty(data.getReplyUserId())){
                circle = this.maodingCircleDao.selectById(read.getCircleId());
                CompanyUserEntity publishUser = companyUserDao.selectById(circle.getPublishUserId());
                if(accountId.equals(publishUser.getUserId())){
                    data.setReplyType(2);
                }else {
                    if(data.getCommentUser().getUserId().equals(publishUser.getUserId())){
                        data.setReplyType(4);
                    }else {
                        data.setReplyUser(this.getCompanyUserByUserId(publishUser.getUserId(),circle.getCompanyId()));
                        data.setReplyType(3);
                    }
                }
            }else {
                if(accountId.equals(data.getReplyUser().getUserId())){
                    data.setReplyType(2);
                }else if(data.getCommentUser().getUserId().equals(data.getReplyUser().getUserId())) {
                    data.setReplyType(4);
                }else {
                    data.setReplyType(3);
                }
            }
            return data;
        }else {
            //发起主题，@了谁，此时的commentId为null
            circle = this.maodingCircleDao.selectById(read.getCircleId());
            CompanyUserEntity publishUser = companyUserDao.selectById(circle.getPublishUserId());
            if(circle!=null){
                MaodingCircleCommentDataDTO comment = new MaodingCircleCommentDataDTO();
                comment.setCircleId(read.getCircleId());
                comment.setComment(circle.getContent());
                comment.setReplyType(1);//特别提醒
                comment.setCreateDate(read.getCreateDate());
                comment.setCommentUser(this.getCompanyUserByUserId(publishUser.getUserId(),circle.getCompanyId()));
                return comment;
            }
        }
        return null;
    }

    private CompanyUserAppDTO getCompanyUserByUserId(String accountId,String companyId){
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("userId",accountId);
        return this.companyUserDao.getCompanyUserByUserId(map);
    }

    public void sendMessage(List<CompanyUserAppDTO> userIdList,  MaodingCircleEntity circle,String commentId) throws Exception{
        //插入到相应的数据表中
        List<String> userIds = new ArrayList<>();
        for(CompanyUserAppDTO user:userIdList){
            MaodingCircleReadEntity entity = new MaodingCircleReadEntity();
            entity.initEntity();
            entity.setCircleId(circle.getId());
            entity.setUserId(user.getUserId());
            entity.setCompanyId(user.getCompanyId());
            entity.setIsEspeciallyRemind(1);//特別提醒
            entity.setIsRead(0);
            entity.setDeleted(0);
            entity.setCommentId(commentId);
            maodingCircleReadDao.insert(entity);
            userIds.add(user.getUserId());
        }

        sendMessage(userIds,circle);
    }

    public void sendMessage(List<CompanyUserAppDTO> userIdList, MaodingCircleEntity circle,String commentId,List<CompanyUserAppDTO> especiallyRemindUserList) throws Exception{
        //插入到相应的数据表中
        List<String> especiallyUser = new ArrayList<>();
        especiallyRemindUserList.stream().forEach(u->{
            especiallyUser.add(u.getUserId());
        });
        List<String> userIds = new ArrayList<>();
        for(CompanyUserAppDTO user:userIdList){
            if(userIds.contains(user.getUserId())){
                continue;
            }
            MaodingCircleReadEntity entity = new MaodingCircleReadEntity();
            entity.initEntity();
            entity.setCircleId(circle.getId());
            entity.setUserId(user.getUserId());
            entity.setCompanyId(user.getCompanyId());
            entity.setIsRead(0);
            entity.setDeleted(0);
            entity.setCommentId(commentId);
            if(especiallyUser.contains(user.getUserId())){
                entity.setIsEspeciallyRemind(1);
            }else {
                entity.setIsEspeciallyRemind(0);
            }
            maodingCircleReadDao.insert(entity);
            userIds.add(user.getUserId());
        }

        sendMessage(userIds,circle);
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2016/12/8
     */
    public void sendMessage(List<String> userIdList,MaodingCircleEntity circle) throws Exception{
        SendMessageDataDTO notifyMsg = new SendMessageDataDTO();
        notifyMsg.setMessageType(SystemParameters.MAODING_CIRCLE_TYPE);
        notifyMsg.setReceiverList(userIdList);
        notifyMsg.setContent(circle.getContent());
        notifyMsg.getOtherContent().put("projectId",circle.getProjectId());
        notifyMsg.getOtherContent().put("projectName", this.projectDao.getProjectName(circle.getProjectId()));
        noticeService.notify(NotifyDestination.APP, notifyMsg);
    }
}
