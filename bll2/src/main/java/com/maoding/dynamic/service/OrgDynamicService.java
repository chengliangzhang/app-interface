package com.maoding.dynamic.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.dynamic.dto.OrgDynamicDataDTO;
import com.maoding.dynamic.entity.OrgDynamicEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2017/2/23.
 */
public interface OrgDynamicService extends BaseService<OrgDynamicEntity> {


    /**
     * 创建立项动态
     * @param projectId
     * @param companyId
     * @param createPersonId
     * @return
     */
    AjaxMessage combinationDynamicForProject(String projectId, String companyId, String createPersonId) throws Exception;

    /***
     * 创建通知公告动态
     * @param noticeId
     * @param companyId
     * @param createPersonId
     * @return
     */
    AjaxMessage combinationDynamicForNotice(String noticeId, String companyId, String createPersonId) throws Exception;


    /**
     * 查询公司的动态
     * @param paraMap
     * @return
     */
    List<OrgDynamicDataDTO> getOrgDynamicListByCompanyId(Map<String, Object> paraMap) throws Exception;

    /**
     * 方法描述：最新动态
     * 作者：MaoSF
     * 日期：2017/3/10
     * @param:
     * @return:
     */
    List<OrgDynamicEntity> getLastOrgDynamicListByCompanyId(Map<String, Object> paraMap);




}
