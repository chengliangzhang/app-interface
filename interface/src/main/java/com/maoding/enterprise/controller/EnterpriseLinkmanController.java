package com.maoding.enterprise.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.enterprise.dto.OperateLinkmanDTO;
import com.maoding.enterprise.service.EnterpriseService;
import com.maoding.project.constDefine.EnterpriseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 企业公商信息查询
 */
@Controller
@RequestMapping("/yongyoucloud/enterpriseLinkman")
public class EnterpriseLinkmanController {

    @Autowired
    private EnterpriseServer enterpriseServer;

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 保存联系人
     */
    @RequestMapping("/saveLinkman")
    @ResponseBody
    public ResponseBean saveLinkman(@RequestBody OperateLinkmanDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getSaveLinkman(),dto);
    }

    /**
     * 删除联系人
     */
    @RequestMapping("/deleteLinkman")
    @ResponseBody
    public ResponseBean deleteLinkman(@RequestBody OperateLinkmanDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getDeleteLinkman(),dto);
    }

}
