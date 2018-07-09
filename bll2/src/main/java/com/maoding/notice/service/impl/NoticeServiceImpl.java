package com.maoding.notice.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.hxIm.dto.ImSendMessageDTO;
import com.maoding.hxIm.service.ImQueueProducer;
import com.maoding.message.dto.SendMessageDataDTO;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.dao.NoticeDao;
import com.maoding.notice.dao.NoticeOrgDao;
import com.maoding.notice.dao.NoticeReadDao;
import com.maoding.notice.dto.NoticeDTO;
import com.maoding.notice.dto.NoticeDataDTO;
import com.maoding.notice.dto.NoticeOrgDTO;
import com.maoding.notice.entity.NoticeEntity;
import com.maoding.notice.entity.NoticeOrgEntity;
import com.maoding.notice.entity.NoticeReadEntity;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyUserService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectDesignContentDao;
import com.maoding.project.dto.ProjectDesignContentDTO;
import com.maoding.project.entity.ProjectDesignContentEntity;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.entity.ProjectTaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.jms.MapMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：NoticeServiceImpl
 * 类描述：通知公告ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
@Service("noticeService")
public class NoticeServiceImpl extends GenericService<NoticeEntity> implements NoticeService {

    protected final Logger log=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeOrgDao noticeOrgDao;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private OrgDynamicService orgDynamicService;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private NoticeReadDao noticeReadDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectDesignContentDao projectDesignContentDao;

    @Autowired
    private ImQueueProducer imQueueProducer;

    @Autowired
    @Qualifier("notifyJmsTemplate")
    private JmsTemplate jmsTemplate;

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 方法描述：保存通知公告验证方法
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    private AjaxMessage validateSaveNotice(NoticeDTO dto) {

        AjaxMessage ajaxMessage = new AjaxMessage();
        if (StringUtil.isNullOrEmpty(dto.getNoticeTitle())) {
            return ajaxMessage.setCode("1").setInfo("标题不能为空");
        }

        if (StringUtil.isNullOrEmpty(dto.getNoticeContent())) {
            return ajaxMessage.setCode("1").setInfo("公告内容不能为空");
        }

        return ajaxMessage.setCode("0");
    }

    /**
     * 方法描述：保存通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage saveNotice(NoticeDTO dto) throws Exception {
        AjaxMessage ajaxMessage = this.validateSaveNotice(dto);
        if ("1".equals(ajaxMessage.getCode())) {
            return ajaxMessage;
        }

        //新增
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            CompanyUserEntity companyUserEntity = companyUserService.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(), dto.getCurrentCompanyId());
            if (companyUserEntity == null) {
                return ajaxMessage.setCode("1").setInfo("保存失败");
            }
            //保存通知公告
            dto.setId(StringUtil.buildUUID());
            NoticeEntity notice = new NoticeEntity();
            BaseDTO.copyFields(dto, notice);
            notice.setCompanyId(dto.getCurrentCompanyId());
            notice.setCreateBy(dto.getAccountId());
            notice.setNoticePublisher(companyUserEntity.getId());
            this.noticeDao.insert(notice);
            //发送动态
            this.orgDynamicService.combinationDynamicForNotice(notice.getId(), notice.getCompanyId(), notice.getCreateBy());
            //如果推送信息，则发送消息
            if (dto.getIsSendMsg() == 1) {
                this.sendMessageForNotice(dto);
            }

        } else {//修改
            NoticeEntity notice = new NoticeEntity();
            BaseDTO.copyFields(dto, notice);
            this.noticeDao.updateById(notice);
        }

        //先删除组织与公告关系
        this.noticeOrgDao.deleteByNoticeId(dto.getId());

        //保存通知公告与组织之间的关系
        for (String orgId : dto.getOrgList()) {
            if (orgId.indexOf("rootId") > -1 || orgId.indexOf("subCompanyId") > -1 || orgId.indexOf("partnerId") > -1) {//如果是分公司/合作伙伴容器，则不做处理
                continue;
            }

            NoticeOrgEntity noticeOrg = new NoticeOrgEntity();
            noticeOrg.setId(StringUtil.buildUUID());
            noticeOrg.setOrgId(orgId);
            noticeOrg.setNoticeId(dto.getId());
            this.noticeOrgDao.insert(noticeOrg);
        }

        return ajaxMessage.setCode("0").setInfo("保存成功");
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2016/12/8
     * @param: dto - 要发送的公告内容
     * @return:
     */
    private void sendMessageForNotice(NoticeDTO dto) throws Exception {
        Map<String, Object> orgList = new HashMap<>();
        orgList.put("orgList", dto.getOrgList());
        List<String> userList = this.companyUserDao.getUserByOrgIdForNotice(orgList);
        if (!CollectionUtils.isEmpty(userList)) {
            //推送给web端
            SendMessageDataDTO notifyMsg = new SendMessageDataDTO();
            notifyMsg.setMessageType(SystemParameters.NOTICE_TYPE);
            notifyMsg.setReceiverList(userList);
            notifyMsg.setContent(dto.getNoticeTitle());
            this.notify(NotifyDestination.WEB, notifyMsg);
            this.notify(NotifyDestination.APP, notifyMsg);
//            //app端环信通知
//            Map<String, Object> msgMap = new HashMap<>();
//            msgMap.put("type", "txt");
//            msgMap.put("msg", "你有一条新的消息："+dto.getNoticeTitle());
//            ImSendMessageDTO imSendMessageDTO = new ImSendMessageDTO();
//            imSendMessageDTO.setFromUser(SystemParameters.NOTICE_MESSAGE_ID);//通知公告助手账号
//            String[] users = new String[userList.size()];
//            userList.toArray(users);
//            imSendMessageDTO.setToUsers(users);
//            imSendMessageDTO.setTargetType("users");//接收主体（users:个人，chatgroups：群组），如果为空，则默认为个人
//            imSendMessageDTO.setExt(null);
//            imSendMessageDTO.setMsgMap(msgMap);
//            try {
//                imQueueProducer.sendMessage(imSendMessageDTO);//推送给APP
//            } catch (Exception e) {
//                log.error("MessageServiceImpl失败 推送消息失败 app端", e);
//            }
        }
        //notify(NotifyDestination.APP, map);
    }

    @Override
    public void notify(String destination, SendMessageDataDTO map) {
        try{
            jmsTemplate.convertAndSend(destination, map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getNoticeCountByParam(Map<String, Object> paraMap) {
        return this.noticeDao.getNoticeCountByParam(paraMap);
    }

    @Override
    public List<NoticeDTO> getNoticeByParam(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<NoticeDTO> list = this.noticeDao.getNoticeByParam(param);
        Map<String,String> logos = new HashMap<>();
        for(NoticeDTO dto:list){
            if(logos.containsKey(dto.getCompanyId())){
                dto.setFileFullPath(logos.get(dto.getCompanyId()));
            }else {
                dto.setFileFullPath(projectSkyDriverService.getCompanyLogo(dto.getCompanyId()));
                logos.put(dto.getCompanyId(),dto.getFileFullPath());
            }
        }
        readNotice(param);
        return list;
    }

    /**
     * 方法描述：删除公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteNotice(String id) throws Exception {
        NoticeEntity notice = new NoticeEntity();
        notice.setId(id);
        notice.setNoticeStatus("1");
        this.noticeDao.updateById(notice);
        return new AjaxMessage().setCode("0").setInfo("删除成功");
    }

    /**
     * 方法描述：批量删除公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteNoticeForBatch(List<String> idList) throws Exception {
        if (!CollectionUtils.isEmpty(idList)) {
            for (String id : idList) {
                this.deleteNotice(id);
            }
        }
        return new AjaxMessage().setCode("0").setInfo("删除成功");
    }

    /**
     * 方法描述：根据id获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    @Override
    public NoticeDTO getNoticeById(String id) throws Exception {
        NoticeEntity noticeEntity = this.noticeDao.selectById(id);
        NoticeDTO dto = new NoticeDTO();
        if (noticeEntity != null) {
            BaseDTO.copyFields(noticeEntity, dto);
            List<NoticeOrgDTO> noticeOrgList = this.noticeOrgDao.getByNoticeId(id);
            dto.setNoticeOrgList(noticeOrgList);
        }

        //获取附件
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",id);
        map.put("type", NetFileType.NOTICE_ATTACH);
        dto.setAttachList(projectSkyDriverService.getAttachList(map));
        return dto;
    }

    /**
     * 方法描述：根据id获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    @Override
    public NoticeDTO getNoticeByIdForDynamics(String id) throws Exception {
        NoticeEntity noticeEntity = this.noticeDao.selectById(id);
        NoticeDTO dto = new NoticeDTO();
        if (noticeEntity != null) {
            BaseDTO.copyFields(noticeEntity, dto);
            dto.setNoticeContent((dto.getNoticeContent()));
            CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(noticeEntity.getNoticePublisher());
            if (companyUserEntity != null) {
                dto.setNoticePublisherName(companyUserEntity.getUserName());
            }
        }
        return dto;
    }

    /**
     * 方法描述：根据id获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    @Override
    public List<NoticeDTO> getNoticeByCompanyId(Map<String, Object> param) throws Exception {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<NoticeDTO> list = noticeDao.getNoticeByCompanyId(param);
        if (!CollectionUtils.isEmpty(list)) {
            for (NoticeDTO dto : list) {
                List<NoticeOrgDTO> noticeOrgList = this.noticeOrgDao.getByNoticeId(dto.getId());
                dto.setNoticeOrgList(noticeOrgList);
            }
        }
        return list;
    }

    /**
     * 方法描述：获取本公司发布的公告数量
     * 作        者：MaoSF
     * 日        期：2016年11月30日-下午3:33:39
     */
    @Override
    public int getNoticeCountByCompanyId(Map<String, Object> param) throws Exception {
        return noticeDao.getNoticeCountByCompanyId(param);
    }

    /**
     * 方法描述：获取公告，并且按照时间分组
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    @Override
    public List<NoticeDataDTO> getNoticeGroupTime(Map<String, Object> param) throws Exception {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<NoticeDataDTO> list = noticeDao.getNoticeGroupTime(param);
        readNotice(param);
        return list;
    }

    private void readNotice(Map<String, Object> param) {
        param.put("accountId",param.get("userId"));
        List<NoticeDTO> list = this.noticeDao.getNotReadNotice(param);
        for (NoticeDTO dto : list) {
            NoticeReadEntity noticeRead = new NoticeReadEntity();
            noticeRead.initEntity();
            noticeRead.setNoticeId(dto.getId());
            noticeRead.setCompanyId(dto.getCompanyId());
            noticeRead.setUserId((String) param.get("accountId"));
            this.noticeReadDao.insert(noticeRead);
        }
    }


    /**
     * 立项动态生成
     */
    @Override
    public void saveNoticeForProject(String projectId, String companyId, String accountId, String currentCompanyUserId, List<ProjectDesignContentDTO> designContent) throws Exception {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        //生成公告
        CompanyEntity c = this.companyDao.selectById(companyId);
        String companyName = (c == null ? "" : c.getAliasName());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setCompanyId(companyId);
        noticeDTO.setCurrentCompanyId(companyId);
        noticeDTO.setNoticeTitle("项目立项 - " + projectEntity.getProjectName());
        noticeDTO.setNoticePublisher(currentCompanyUserId);
        noticeDTO.setAccountId(accountId);
        if (designContent != null) {
            noticeDTO.setNoticeContent("立项组织：" + companyName + "<br/><br/>" + "设计任务：" + getDesignContent(designContent));
        } else {
            noticeDTO.setNoticeContent("立项组织：" + companyName + "<br/><br/>" + "设计任务：" + getDesignContent(projectId));
        }
        List<String> orgList = new ArrayList<String>();
        orgList.add(companyId);
        noticeDTO.setOrgList(orgList);
        noticeDTO.setNoticeType(0);
        this.saveNotice(noticeDTO);
    }

    private String getDesignContent(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return "";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("projectId", id);
        map.put("taskType", 1);//只查询根任务
        List<ProjectDesignContentEntity> list = this.projectDesignContentDao.getProjectDesignContentByProjectId(id);
        if (list == null) {
            return "";
        }
        StringBuffer content = new StringBuffer();
        for (ProjectDesignContentEntity e : list) {
            if (content.length() > 0) {
                content.append(",");
            }
            content.append(e.getContentId());
        }
        return content.toString();
    }

    private String getDesignContent(List<ProjectDesignContentDTO> designContent) {
        if (designContent == null) {
            return "";
        }

        StringBuffer content = new StringBuffer();
        for (ProjectDesignContentDTO d : designContent) {
            if (content.length() > 0) {
                content.append(",");
            }
            content.append(d.getContentName());
        }
        return content.toString();
    }
}
