package com.maoding.partner.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.BusinessPartnerDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.partner.service.PartnerService;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 事业合伙人
 * Created by Wuwq on 2017/4/1.
 */
@Controller
@RequestMapping("/v2/partner")
public class BusinessPartnerController extends BaseWSController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PartnerService partnerService;


    /**
     * 方法描述：打开发送链接请求数据
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:
     * @return:
     */
    @RequestMapping(value = "/getCompanyByInviteUrl/{id}",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage getCompanyByInviteUrl(@PathVariable String id) throws Exception{

       // return companyService.getCompanyByInviteUrl(id);
        return null;
    }

    /**
     * 方法描述：验证身份
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:map(id,cellphone)
     * @return:
     */
    @RequestMapping(value = "/verifyIdentityForParent",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage verifyIdentityForParent(@RequestBody Map<String, Object> map) throws Exception{
      //  return companyService.verifyIdentityForParent(map);
        return null;
    }


    /**
     * 方法描述：验证身份成功后，请求数据
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:map(cellphone,id)
     * @return:
     */
    @RequestMapping(value ="/getCompanyPrincipal",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage getCompanyPrincipal(@RequestBody Map<String, Object> map) throws Exception{
        return AjaxMessage.succeed(companyService.getCompanyPrincipal(map));
    }


    /**
     * 方法描述：创建公司并加入事业合伙人或分支机构
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:map(inviteId:申请记录的id，userName,cellphone,adminPassword,companyName)
     * @return:
     */
    @RequestMapping(value ="/invitePartner",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean invitePartner(@RequestBody BusinessPartnerDTO dto) throws Exception{
        dto.setCompanyId(dto.getAppOrgId());
        return this.companyService.createBusinessPartner(dto);
    }

    /**
     * 方法描述：解除合作
     * 作者：MaoSF
     * 日期：2017/4/1
     */
    @RequestMapping(value ="/relieveRelationship")
    @ResponseBody
    public ResponseBean relieveRelationship(@RequestBody BusinessPartnerDTO dto) throws Exception{
        return partnerService.relieveRelationship(dto.getId());
    }

    /**
     * 方法描述：重新发送短信通知
     * 作者：wrb
     * 日期：2017/5/9
     */
    @RequestMapping(value = "/resendSMS")
    @ResponseBody
    public ResponseBean resendSMS(@RequestBody BusinessPartnerDTO dto) throws Exception{
        return partnerService.resendSMS(dto.getId(),dto.getAccountId(),dto.getAppOrgId());
    }

}
