package com.maoding.partner.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.partner.dto.PartnerQueryDTO;
import com.maoding.project.dto.ProjectPartnerDTO;

import java.util.List;

/**
 * Created by wrb on 2017/5/8.
 */
public interface PartnerService {
    /**
     * 获取项目合伙人列表
     */
    List<ProjectPartnerDTO> getProjectPartnerList(PartnerQueryDTO dto) throws Exception;

    /**
     * 方法描述：外部合作－解除关系
     * 作者：wrb
     * 日期：2017/5/9
     * @param:
     * @return:
     */
    ResponseBean relieveRelationship(String id);

    /**
     * 方法描述：重新发送短信通知
     * 作者：wrb
     * 日期：2017/5/9
     * @param:
     * @return:
     */
    ResponseBean resendSMS(String id, String currentUserId, String currentCompanyId);
}
