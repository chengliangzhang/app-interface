
package com.maoding.dynamic.service.impl;


import com.beust.jcommander.internal.Maps;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.OrgDynamicDao;
import com.maoding.dynamic.dto.OrgDynamicDataDTO;
import com.maoding.dynamic.entity.OrgDynamicEntity;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.notice.dao.NoticeDao;
import com.maoding.notice.entity.NoticeEntity;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Idccapp21 on 2017/2/23.
 */


@Service("orgDynamicService")
public class OrgDynamicServiceImpl extends GenericService<OrgDynamicEntity> implements OrgDynamicService {

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    private final String SEPARATOR = " ;";

    @Autowired
    private OrgDynamicDao orgDynamicDao;


    @Value("${fastdfs.url}")
    protected String fastdfsUrl;

/**
     * 立项动态生成
     * @param projectId
     * @param companyId
     * @param createPersonId
     * @return
     */

    @Override
    public AjaxMessage combinationDynamicForProject(String projectId, String companyId, String createPersonId) throws Exception{

        OrgDynamicEntity orgDynamicEntity = new OrgDynamicEntity();
        orgDynamicEntity.setId(StringUtil.buildUUID());

        ProjectEntity projectEntity = projectDao.selectById(projectId);
        //获取设计阶段

        String dynamicParam = "";

        /***************start：此段内容的顺序请不要发生改变，因为，数据要匹配模板*************/
        //记录创建人
        dynamicParam += getCreatorName(projectEntity,createPersonId) + SEPARATOR;

        //判断项目
        if (null != projectEntity) {
            dynamicParam = dynamicParam + projectEntity.getProjectName() + SEPARATOR;
        } else {
            dynamicParam = dynamicParam + SEPARATOR;
        }

        //记录设计阶段
        dynamicParam += getDesignContent(projectId) + SEPARATOR;

        /**************************end***************************************/

        //判断经营负责人和设计负责人
       // dynamicParam = this.setDynamicParam(projectId, companyId, dynamicParam);
        orgDynamicEntity.setDynamicContent(dynamicParam);
        orgDynamicEntity.setCompanyId(companyId);
        orgDynamicEntity.setDynamicTitle(projectEntity.getProjectName());
        orgDynamicEntity.setDynamicType(SystemParameters.DYNAMIC_PROJECT );
        orgDynamicEntity.setTargetId(projectId);
        orgDynamicEntity.setCreateDate(new Date());
        orgDynamicEntity.setCreateBy(createPersonId);
        int i = orgDynamicDao.insert(orgDynamicEntity);
        if (i > 0) {
            return new AjaxMessage().setCode("0").setInfo("动态保存成功");
        } else {
            return new AjaxMessage().setCode("1").setInfo("动态保存失败");
        }
    }


    private String getCreatorName(ProjectEntity entity, String createPersonId) throws Exception{
        if (entity == null) return "";
        CompanyEntity c = this.companyDao.selectById(entity.getCompanyId());
        String name = (c==null?"":c.getAliasName());
        ProjectMemberDTO projectCreator = this.projectMemberService.getProjectCreatorDTO(entity.getId(),entity.getCompanyId());
        name += (projectCreator != null) ? "(" + projectCreator.getCompanyUserName() + ")" : "";
        return name;
    }

    private String getDesignContent(String id){
        if (StringUtil.isNullOrEmpty(id)) return "";
        List<ProjectTaskEntity> list = projectTaskService.listProjectTaskContent(id);
        if (list == null) return "";
        StringBuffer content = new StringBuffer();
        for (ProjectTaskEntity e : list) {
            if (content.length() > 0) {
                content.append(",");
            }
            content.append(e.getTaskName());
        }
        return content.toString();
    }

    private String getTaskName(ProjectTaskEntity entity){
        if (entity == null) return "";
        return projectTaskDao.getTaskParentName(entity.getId());
    }
/**
     * 经营，项目负责人参数拼接
     *
     * @param projectId
     * @param companyId
     * @param dynamicParam
     */


    private String setDynamicParam(String projectId, String companyId, String dynamicParam) throws Exception{
        Map<String, Object> param = Maps.newHashMap();

        param.put("projectId", projectId);

        param.put("companyId", companyId);

        List<ProjectMemberDTO> projectManagerEntityList = projectMemberService.listProjectManager(projectId,companyId);
        String managerPserson = "";
        String designPerson = "";
        for (ProjectMemberDTO projectManagerDTO : projectManagerEntityList) {
            if (1==projectManagerDTO.getType()) {
                managerPserson = projectManagerDTO.getCompanyUserName();
            }
            if (2==projectManagerDTO.getType()) {
                designPerson = projectManagerDTO.getCompanyUserName();
            }
        }
        if (!StringUtil.isNullOrEmpty(managerPserson)) {
            dynamicParam = dynamicParam + managerPserson + SEPARATOR;
        } else {
            dynamicParam = dynamicParam + SEPARATOR;
        }
        if (!StringUtil.isNullOrEmpty(designPerson)) {
            dynamicParam = dynamicParam + designPerson + SEPARATOR;
        } else {
            dynamicParam = dynamicParam + SEPARATOR;
        }
        return dynamicParam;
    }



/**
     * 乙方动态生成
     * @param projectId
     * @param companyId
     * @param createPersonId
     * @return
     */

    @Override
    public AjaxMessage combinationDynamicForPartyB(String projectId, String companyId, String createPersonId) throws Exception{
        OrgDynamicEntity orgDynamicEntity = new OrgDynamicEntity();
        orgDynamicEntity.setId(StringUtil.buildUUID());

        ProjectEntity projectEntity = projectDao.selectById(projectId);
        String dynamicParam = "";
        /***************start：此段内容的顺序请不要发生改变，因为，数据要匹配模板*************/

        //记录项目
        if (null != projectEntity) {
            dynamicParam = dynamicParam + projectEntity.getProjectName() + SEPARATOR;
        } else {
            dynamicParam = dynamicParam + SEPARATOR;
        }

        //记录立项公司和立项人
        dynamicParam += getCreatorName(projectEntity,createPersonId) + SEPARATOR;

        //记录设计阶段
        dynamicParam += getDesignContent(projectId) + SEPARATOR;
        dynamicParam = this.setDynamicParam(projectId, companyId, dynamicParam);
        /*******************end********************************************************/

       // dynamicParam = this.setDynamicParam(projectId, companyId, dynamicParam);
        orgDynamicEntity.setDynamicContent(dynamicParam);
        orgDynamicEntity.setCompanyId(projectEntity.getCompanyBid());
        orgDynamicEntity.setDynamicTitle(projectEntity.getProjectName());
        orgDynamicEntity.setDynamicType(SystemParameters.DYNAMIC_PARTYB);
        orgDynamicEntity.setTargetId(projectId);
        orgDynamicEntity.setCreateDate(new Date());
        orgDynamicEntity.setCreateBy(createPersonId);
        int i = orgDynamicDao.insert(orgDynamicEntity);
        if (i > 0) {
            return new AjaxMessage().setCode("0").setInfo("动态保存成功");
        } else {
            return new AjaxMessage().setCode("1").setInfo("动态保存失败");
        }
    }


/**
     *  合作伙伴动态生成
     * @param projectId
     * @param companyId
     * @param partnerId
     * @param taskId
     * @param createPersonId
     * @return
     */

    @Override
    public AjaxMessage combinationDynamicForPartner(String projectId, String companyId, String partnerId, String taskId, String createPersonId) throws Exception{
        OrgDynamicEntity orgDynamicEntity = new OrgDynamicEntity();
        orgDynamicEntity.setId(StringUtil.buildUUID());
        ProjectEntity projectEntity = projectDao.selectById(projectId);

        ProjectTaskEntity projectTaskEntity = projectTaskDao.selectById(taskId);

        String dynamicParam = "";

        /***************start：此段内容的顺序请不要发生改变，因为，数据要匹配模板*************/
        //判断项目
        if (null != projectEntity) {
            dynamicParam = dynamicParam + projectEntity.getProjectName() + SEPARATOR;
        } else {
            dynamicParam = dynamicParam + SEPARATOR;
        }

        //记录立项组织（立项人）
        dynamicParam += getCreatorName(projectEntity,createPersonId) + SEPARATOR;

        //记录任务名
        dynamicParam += getTaskName(projectTaskEntity) + SEPARATOR;

        //判断经营负责人，设计负责人
        dynamicParam = this.setDynamicParam(projectId, partnerId, dynamicParam);

        /***************end***************************************************/
        //判断经营负责人，设计负责人
       // dynamicParam = this.setDynamicParam(projectId, partnerId, dynamicParam);
        orgDynamicEntity.setDynamicContent(dynamicParam);
        orgDynamicEntity.setCompanyId(partnerId);
        orgDynamicEntity.setDynamicTitle(projectEntity.getProjectName());
        orgDynamicEntity.setDynamicType(SystemParameters.DYNAMIC_PARTNER );
        orgDynamicEntity.setTargetId(projectId);
        orgDynamicEntity.setCreateDate(new Date());
        orgDynamicEntity.setCreateBy(createPersonId);
        int i = orgDynamicDao.insert(orgDynamicEntity);
        if (i > 0) {
            return new AjaxMessage().setCode("0").setInfo("动态保存成功");
        } else {
            return new AjaxMessage().setCode("1").setInfo("动态保存失败");
        }
    }


/**
     * 通知公告动态生成
     * @param noticeId
     * @param companyId
     * @param createPersonId
     * @return
     */

    @Override
    public AjaxMessage combinationDynamicForNotice(String noticeId, String companyId, String createPersonId) {
        OrgDynamicEntity orgDynamicEntity = new OrgDynamicEntity();
        orgDynamicEntity.setId(StringUtil.buildUUID());
        NoticeEntity noticeEntity = noticeDao.selectById(noticeId);
        orgDynamicEntity.setDynamicContent(noticeEntity.getNoticeTitle());
        orgDynamicEntity.setCompanyId(companyId);
        orgDynamicEntity.setDynamicTitle("通知公告");
        orgDynamicEntity.setDynamicType(SystemParameters.DYNAMIC_NOTICE);
        orgDynamicEntity.setTargetId(noticeId);
        orgDynamicEntity.setCreateDate(new Date());
        orgDynamicEntity.setCreateBy(createPersonId);
        int i = orgDynamicDao.insert(orgDynamicEntity);
        if (i > 0) {
            return new AjaxMessage().setCode("0").setInfo("动态保存成功");
        } else {
            return new AjaxMessage().setCode("1").setInfo("动态保存失败");
        }
    }


    @Override
    public List<OrgDynamicDataDTO> getOrgDynamicListByCompanyId(Map<String,Object>paraMap){
        if (null != paraMap.get("pageIndex")) {
            int page = (Integer) paraMap.get("pageIndex");
            int pageSize = (Integer) paraMap.get("pageSize");
            paraMap.put("startPage", page * pageSize);
            paraMap.put("endPage", pageSize);
        }
        paraMap.put("companyId",paraMap.get("appOrgId"));
        List <OrgDynamicDataDTO> orgDynamicEntityList= orgDynamicDao.getOrgDynamicByParam(paraMap);
        if(!CollectionUtils.isEmpty(orgDynamicEntityList)){
            for(OrgDynamicDataDTO dto:orgDynamicEntityList){
                conversionToTemplate(dto.getDynamicList());
            }
        }
        return orgDynamicEntityList;
    }

    @Override
    public List<OrgDynamicEntity> getLastOrgDynamicListByCompanyId(Map<String,Object>paraMap){
        paraMap.put("companyId",paraMap.get("appOrgId"));
        List<OrgDynamicEntity> orgDynamicEntityList= orgDynamicDao.getLastOrgDynamicByParam(paraMap);
        conversionToTemplate(orgDynamicEntityList);
        return orgDynamicEntityList;
    }


/**
     * 模板转换
     * @param orgDynamicEntityList
     * @return
     */

    private void conversionToTemplate(List<OrgDynamicEntity> orgDynamicEntityList) {
        for(OrgDynamicEntity orgDynamicEntity:orgDynamicEntityList){
            String[] params = orgDynamicEntity.getDynamicContent().split("[;]");
            orgDynamicEntity.setDynamicContent(StringUtil.format(SystemParameters.dynamic.get(orgDynamicEntity.getDynamicType()+""), params));
        }
    }
}

