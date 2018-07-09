package com.maoding.enterprise.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.enterprise.dto.EnterpriseSearchQueryDTO;
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
@RequestMapping("/yongyoucloud/enterpriseSearch")
public class EnterpriseSearchController {

    @Autowired
    private EnterpriseServer enterpriseServer;

    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 模糊查询列表，更据dto.name
     */
    @RequestMapping("/queryAutoComplete")
    @ResponseBody
    public ResponseBean queryAutoComplete(@RequestBody EnterpriseSearchQueryDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getAutoComplete(),dto);
    }

    /**
     * 根据id查询详细信息
     */
    @RequestMapping("/queryDetail")
    @ResponseBody
    public ResponseBean queryDetail(@RequestBody EnterpriseSearchQueryDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getQueryDetail(),dto);
    }

    /**
     * 根据id查询详细信息
     */
    @RequestMapping("/queryEnterpriseDetail")
    @ResponseBody
    public ResponseBean queryEnterpriseDetail(@RequestBody EnterpriseSearchQueryDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getEnterpriseDetail(),dto);
    }


    /**
     * 查询当前公司的客户列表
     */
    @RequestMapping("/getEnterprise")
    @ResponseBody
    public ResponseBean getEnterprise(@RequestBody EnterpriseSearchQueryDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getEnterprise(),dto);
    }

    /**
     * 根据企业全称查询详细信息 dto.name
     */
    @RequestMapping("/queryFull")
    @ResponseBody
    public ResponseBean queryFull(@RequestBody EnterpriseSearchQueryDTO dto) throws Exception{
        return enterpriseService.getRemoteData(enterpriseServer.getQueryFull(),dto);
    }



}
