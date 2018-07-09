package com.maoding.org.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.MD5Helper;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.*;
import com.maoding.role.dto.InterfaceGroupAndRoleDTO;
import com.maoding.role.service.PermissionService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.system.dto.DataDictionaryDTO;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.user.dto.ShareInvateDTO;
import com.maoding.user.service.UserAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/7/27.
 */
@Controller
@RequestMapping("/org")
public class OrgController extends BaseWSController {

    private final String th0 = "240X250"; //内部管理，log
    private final String th1 = "300X45"; //内部管理，log
    private final String th2 = "150X180";//公司组织log 最大尺寸150X180
    private final String th3 = "200X35";//精英组织图标logo尺寸：最大200 x 35
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRelationAuditService companyRelationAuditService;
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private DepartService departService;
    @Autowired
    private TeamOperaterService teamOperaterService;
    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private CompanyUserAuditService companyUserAuditService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserAttachService userAttachService;

    /**
     * 方法描述：组织架构树(数据）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     * @return
     * @throws Exception
     */
   /* @RequestMapping("/getcompanyDatabac")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getcompanyDatabac(@RequestBody Map<String, Object> mapass) throws Exception {
       *//* Map<String,Object> map = new HashMap<String, Object>();
        map.put("companyId", mapass.get("appOrgId"));
        map.put("type", "2");//查询以当前公司为root的所有公司，部门信息
        OrgTreeDTO dataMap = companyService.getOrgTree(map);*//*
       //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(mapass.get("accountId").toString());
        //所在的所有公司
        String companyIds = "";
        for(CompanyDTO companyDto : orgList){
            companyIds = companyIds + "," + companyDto.getId();
        }

        List<CompanyDTO> returList = new ArrayList<>();
        *//*CompanyEntity companyEntity = companyService.selectById(mapass.get("appOrgId"));*//*

        String idAll = "";
        outer:
        for(CompanyDTO companyDto : orgList){
            //根公司
            String id = companyService.getRootCompanyId(companyDto.getId());
            //要把

            if(idAll.indexOf(id) != -1){
                break outer;
            }
            idAll = idAll + ","+id;
            CompanyDTO cDto = companyService.getCompanyById(id);
            *//**公司开始标识:1*//*
            cDto.setCompanyStartFlag(1);
            //查询所有的子节点，
            List<CompanyEntity> listOld = companyService.getAllChilrenCompany(id);
            List<CompanyDTO> listOldList = BaseDTO.copyFields(listOld,CompanyDTO.class);

            List<CompanyDTO> list = new ArrayList<>();
            for(CompanyDTO ce : listOldList){
                if(companyIds.indexOf(ce.getId()) != -1){
                    ce.setIsInCompanyFlag(1);
                    list.add(ce);
                }else{
                    list.add(ce);
                }
            }
            if(companyIds.indexOf(cDto.getId()) != -1){
                cDto.setIsInCompanyFlag(1);
            }
            list.add(0,cDto);
            returList.addAll(list);
        }
        for(CompanyDTO dto : returList){
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("userId",mapass.get("accountId"));
            param.put("companyId",dto.getId());
            String roleCodes =permissionService.getPermissionCodeByUserId(param);
            dto.setRoleCodes(roleCodes);
        }
        //.addData("companyData", dataMap)
        return responseSuccess().addData("companyData",returList);
    }
*/

    /**
     * 方法描述：获取当前用户所在的组织（
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:14:31
     */
    @RequestMapping("/getMyCompanylist")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getMyCompanylist(@RequestBody Map<String, String> dto) throws Exception {
        String userId = dto.get("accountId");
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(userId);
        return responseSuccess().addData("companyData", orgList);
    }

    /**
     * 方法描述：组织架构树(数据）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     */
    @RequestMapping("/getcompanyData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getcompanyData(@RequestBody Map<String, Object> mapass) throws Exception {
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(mapass.get("accountId").toString());
        //所在的所有公司
        String companyIds = "";
        for (CompanyDTO companyDto : orgList) {
            companyIds = companyIds + "," + companyDto.getId();
        }

        List<CompanyDTO> returList = new ArrayList<>();
        /*CompanyEntity companyEntity = companyService.selectById(mapass.get("appOrgId"));*/

        String idAll = "";
        outer:
        for (CompanyDTO companyDto : orgList) {
            List<CompanyDTO> list = new ArrayList<>();
            String id = companyDto.getId();
            boolean flag = true;
            while (flag) {
                CompanyDTO cdto = companyService.getLineCompanyByCompanyId(id);
                if (null == cdto) {
                    flag = false;
                } else {
                    id = cdto.getId();
                    list.add(0, cdto);
                }
            }

            list.add(companyDto);
            for (int j = 0; j < list.size(); j++) {

                if (j == 0) {
                    list.get(0).setCompanyStartFlag(1);
                }
                if (companyIds.indexOf(list.get(j).getId()) != -1) {
                    list.get(j).setIsInCompanyFlag(1);
                }
            }

            returList.addAll(list);

        }
        //.addData("companyData", dataMap)
        return responseSuccess().addData("companyData", returList);
    }

    /**
     * 方法描述：通讯录
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     */
    @RequestMapping("/get_addressBook")
    @ResponseBody
    public ResponseBean getAddressBook(@RequestBody Map<String, String> dto) throws Exception {
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(dto.get("accountId").toString());
        String idAll = "";
        for (int j = 0; j < orgList.size(); j++) {
            String rootId = companyService.getRootCompanyId(orgList.get(j).getId());
            if (idAll.indexOf(rootId) == -1) {
                if (j == 0) {
                    idAll = rootId;
                } else {
                    idAll = idAll + "," + rootId;
                }
            }
        }
        String[] ids = idAll.split(",");
        List<OrgTreeDTO> list = new ArrayList<>();
        for (String id : ids) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyId", id);
            OrgTreeDTO dataMap = companyService.getOrgTree(map);
            list.add(dataMap);
        }
        return responseSuccess().addData("list", list);
    }


    /**
     * 方法描述：获取当前用户所在的组织（组织切换）
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:14:31
     */
    @RequestMapping("/get_choisecompanylist")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getChoiseCompanyByUserId(@RequestBody Map<String, String> dto) throws Exception {
        String userId = dto.get("accountId");
        List<CompanyDTO> list = companyService.getCompanyByUserId(userId);
        return responseSuccess().addData("companyList", list);
    }

    /**
     * 方法描述：增加与修改公司信息
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:35:00
     */
    @RequestMapping("/saveorupdate_company")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateCompany(@RequestBody CompanyDTO dto) throws Exception {
        AjaxMessage ajaxMessage = companyService.saveOrUpdateCompany(dto);
        CompanyDTO companyDTO = (CompanyDTO) ajaxMessage.getData();
        if (null == companyDTO) {
            return returnResponseBean(ajaxMessage);
        }

        if (ajaxMessage.getCode().equals("0")) {
            return ResponseBean.responseSuccess("公司信息保存成功！");
        } else {
            return ResponseBean.responseError("公司信息保存失败！");
        }
    }



    /**
     * 方法描述： 根据id查找公司信息
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:35:00
     */
    @RequestMapping("/get_companybyid")
    @ResponseBody
    public ResponseBean getCompanyById(@RequestBody CompanyDTO companyDTO) throws Exception {
        CompanyDTO dto = companyService.getCompanyById(companyDTO.getId());
        return responseSuccess().addData("companyInfo", dto);
    }


    /**
     * 方法描述：解散组织
     * 作        者：MaoSF
     * 日        期：2016年4月26日-下午2:24:31
     */
    @RequestMapping("/disband_company")
    @ResponseBody
    public ResponseBean disbandCompany(@RequestBody TeamOperaterDTO dto) throws Exception {
        AjaxMessage ajax = companyService.disbandCompany(dto.getCompanyId());
        if (ajax.getCode().equals("0")) {
            return ResponseBean.responseSuccess("解散当前组织成功");
        } else {
            return ResponseBean.responseError((String) ajax.getInfo());
        }
    }

    /**
     * 方法描述：离职，删除人员
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:25:03
     */
    @RequestMapping("/quit")
    @ResponseBody
    public ResponseBean quit(@RequestBody CompanyUserTableDTO dto) throws Exception {
        AjaxMessage ajax = companyUserService.quit(dto.getId());

        if (ajax.getCode().equals("0")) {
            return ResponseBean.responseSuccess();
        } else {
            return ResponseBean.responseError("离开组织操作失败");
        }
    }

    /***************组织架构树***************/
    /**
     * 方法描述：组织架构树(数据）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     */
    @RequestMapping("/get_orgtree")
    @ResponseBody
    public ResponseBean getOrgTree(@RequestBody Map<String, String> dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", dto.get("appOrgId"));
        map.put("type", "2");//查询以当前公司为root的所有公司，部门信息
        OrgTreeDTO dataMap = companyService.getOrgTree(map);
        return responseSuccess().addData("treeMap", dataMap);
    }


    /**
     * 方法描述：组织架构树(数据）--搜索界面
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     */
    @RequestMapping("/get_orgtreef_search")
    @ResponseBody
    public ResponseBean getOrgTreeForSearch(@RequestBody CompanyDTO dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", dto.getId());
        map.put("type", "1");//只查询当前公司
        OrgTreeDTO dataMap = companyService.getOrgTree(map);
        return responseSuccess().addData("treeMap", dataMap);
    }


/*******************************部门信息*******************************************/
    /**
     * 方法描述：增加，修改部门
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:57:10
     */
    @RequestMapping("/save_orupdate_depart")
    @ResponseBody
    public ResponseBean saveOrUpdateDepart(@RequestBody DepartDTO dto) throws Exception {
        String userId = dto.getAccountId();
        dto.setAccountId(userId);
        return departService.saveOrUpdateDepart(dto);
    }


    /***********************添加修改分公司*************************/
    /**
     * 方法描述：添加修改分公司
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:57:10
     */
    @RequestMapping("/create_subcompany")
    @ResponseBody
    public ResponseBean createSubCompany(@RequestBody SubCompanyDTO dto) throws Exception {
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String companyId = dto.getAppOrgId();
            dto.setCompanyId(companyId);
            dto.setAccountId(dto.getAccountId());
            // dto.setClearlyAdminPassword(dto.getAdminPassword());
            // dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
            return returnResponseBean(companyService.createSubCompany(dto));
        } else {
            return returnResponseBean(companyService.updateSubCompany(dto));
        }

    }

    /**
     * 方法描述：删除分公司
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:57:10
     */
    @RequestMapping("/delete_subcompany")
    @ResponseBody
    public ResponseBean deleteSubCompany(@RequestBody SubCompanyDTO dto) throws Exception {
        String companyId = dto.getAppOrgId();
        return returnResponseBean(companyService.deleteSubCompany(companyId, dto.getId()));
    }


    /**
     * 方法描述：获取当前公司的所有部门【用到的界面：添加、修改人员，添加、修改部门】
     * 作        者：MaoSF
     * 日        期：2016年7月9日-下午12:00:13
     */
    @RequestMapping("/get_depart_bycompanyid")
    @ResponseBody
    public ResponseBean getDepartByCompanyId(@RequestBody CompanyDTO companyDTO) throws Exception {
        String companyId = companyDTO.getId();
        if (StringUtil.isNullOrEmpty(companyId)) {
            companyId = companyDTO.getAppOrgId();
        }
        CompanyDTO company = companyService.getCompanyById(companyId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", companyId);
        param.put("type", "0");
        List<DepartDataDTO> list = departService.getDepartByCompanyIdWS(param);
        for (DepartDataDTO departDTO : list) {
            departDTO.setType("depart");
        }
//        DepartDTO dto = new DepartDTO();
//        dto.setId(companyId);
//        dto.setDepartName(company.getCompanyName());
//        list.add(0, dto);
        return responseSuccess().addData("departList", list);
    }


    /**
     * 方法描述：删除部门
     * 作        者：MaoSF
     * 日        期：2016年7月12日-上午6:08:52
     */
    @RequestMapping("/delete_depart")
    @ResponseBody
    public ResponseBean deleteDepart(@RequestBody DepartDTO dto) throws Exception {
        return returnResponseBean(departService.deleteDepartById(dto.getId(), dto.getAccountId()));
    }


    /***********************创建事业合伙人*************************/
    /**
     * 方法描述：创建事业合伙人
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:57:10
     */
    @RequestMapping("/create_businesspartner")
    @ResponseBody
    public ResponseBean createBusinessPartner(@RequestBody BusinessPartnerDTO dto) throws Exception {
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String companyId = dto.getAppOrgId();
            dto.setCompanyId(companyId);
            dto.setAccountId(dto.getAccountId());

            // dto.setClearlyAdminPassword(dto.getAdminPassword());
            // dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
            return companyService.createBusinessPartner(dto);
        } else {
            return returnResponseBean(companyService.updateBusinessPartner(dto));
        }
    }


    /**
     * 方法描述：删除事业合伙人
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:57:10
     */
    @RequestMapping("/delete_businesspartner")
    @ResponseBody
    public ResponseBean deleteBusinessPartner(@RequestBody BusinessPartnerDTO dto) throws Exception {
        String companyId = dto.getAppOrgId();
        return returnResponseBean(companyService.deleteSubCompany(companyId, dto.getId()));
    }


/*******************************组织成员信息**************************************/
    /**
     * 方法描述：添加修改（组织成员）
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:25:03
     */
    @RequestMapping("/save_companyuser")
    @ResponseBody
    public ResponseBean saveCompanyUser(@RequestBody CompanyUserTableDTO dto) throws Exception {
        String userId = dto.getAccountId();
        CompanyDTO company = companyService.getCompanyById(dto.getCompanyId());
        dto.setCompanyName(company.getCompanyName());
        dto.setAccountId(userId);
        return returnResponseBean(companyUserService.saveCompanyUser(dto));
    }


    /**
     * 方法描述： 查询员工信息（组织成员）
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:25:03
     */
    @RequestMapping("/get_companyuser")
    @ResponseBody
    public ResponseBean getCompanyUser(@RequestBody CompanyUserTableDTO dto) throws Exception {
        CompanyUserDetailDTO companyUser = companyUserService.getCompanyUserByIdInterface(dto.getId(),dto.getNeedUserStatus());
        return responseSuccess().addData("companyUser", companyUser);
    }



    /**
     * 方法描述： 查找服务类型
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:35:00
     */
    @RequestMapping("/get_serverTypes")
    @ResponseBody
    public ResponseBean getCompanyInfo() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DataDictionaryDTO> projectTypeList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_Type);//项目类别
        //  model.put("projectTypeList",projectTypeList);
        //  List<DataDictionaryEntity> serverTypeList = dataDictionaryService.getSubDataByCode("server-type");
        map.put("serverTypeList", projectTypeList);
        return responseSuccess().addData(map);
    }

    /**
     * 方法描述：待审核成员列表信息
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:25:03
     */
    @RequestMapping("/get_pendingaudiorguser")
    @ResponseBody
    public ResponseBean getPendingAudiOrgUser(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgId", paraMap.get("orgId"));
        if (null != paraMap.get("pageSize") && null != paraMap.get("pageNumber")) {
            param.put("pageSize", paraMap.get("pageSize"));
            param.put("pageNumber", paraMap.get("pageNumber"));
        }
        if (null != paraMap.get("pageSize") && null != paraMap.get("pageIndex")) {
            param.put("pageSize", paraMap.get("pageSize"));
            param.put("pageNumber", paraMap.get("pageIndex"));
        }
        if (StringUtil.isNullOrEmpty(paraMap.get("orgId"))) {
            param.put("companyId", paraMap.get("appOrgId"));
        }
        Map returnData = companyUserAuditService.getCompanyUserAudit(param);
        return responseSuccess().addData("userList", returnData.get("data"));
    }

    /**
     * 方法描述：审核申请加入组织的人员【 同意或拒绝】(dto:id,auditStatus,companyId)
     * 作        者：MaoSF
     * 日        期：2016年3月18日-上午9:43:08
     */
    @RequestMapping("/audi_orguser")
    @ResponseBody
    public ResponseBean audiOrgUser(@RequestBody ShareInvateDTO dto) throws Exception {
        return returnResponseBean(companyUserAuditService.auditShareInvate(dto));
    }



    /**
     * 方法描述：申请成为分公司,合作伙伴(当前公司的id为orgId，选择公司的id为orgPid）
     * 邀请分公司,合作伙伴(当前公司的id为orgPid，选择公司的id为orgId）
     * 作        者：MaoSF
     * 日        期：2016年3月21日-上午10:38:08
     */
    @RequestMapping("/application_subcompany")
    @ResponseBody
    public ResponseBean applicationSubCompany(@RequestBody Map<String, String> paraMap) throws Exception {
        String userId = paraMap.get("accountId");
        String companyId = paraMap.get("appOrgId");
        CompanyRelationAuditDTO dto = new CompanyRelationAuditDTO();
        //邀请分公司
        dto.setAccountId(userId);
        dto.setOrgId(companyId);
        dto.setOrgPid(paraMap.get("id"));
        dto.setType("2");
        dto.setRelationType("0");
        return returnResponseBean(companyRelationAuditService.applicationOrInvitation(dto));
    }

    /**
     * 方法描述：申请成为分公司,合作伙伴(当前公司的id为orgId，选择公司的id为orgPid）
     * 邀请分公司,合作伙伴(当前公司的id为orgPid，选择公司的id为orgId）
     * 作        者：MaoSF
     * 日        期：2016年3月21日-上午10:38:08
     */
    @RequestMapping("/invate_SubCompany")
    @ResponseBody
    public ResponseBean invateSubCompany(@RequestBody Map<String, String> paraMap) throws Exception {
        String userId = paraMap.get("accountId");
        String companyId = paraMap.get("appOrgId");
        CompanyRelationAuditDTO dto = new CompanyRelationAuditDTO();
        //邀请分公司
        dto.setAccountId(userId);
        dto.setOrgId(paraMap.get("id"));
        dto.setOrgPid(companyId);
        dto.setType("2");
        dto.setRelationType("1");
        return returnResponseBean(companyRelationAuditService.applicationOrInvitation(dto));
    }

    /**
     * 方法描述：申请成为分公司,合作伙伴(当前公司的id为orgId，选择公司的id为orgPid）
     * 邀请分公司,合作伙伴(当前公司的id为orgPid，选择公司的id为orgId）
     * 作        者：MaoSF
     * 日        期：2016年3月21日-上午10:38:08
     */
    @RequestMapping("/application_businesspartner")
    @ResponseBody
    public ResponseBean applicationBusinessPartner(@RequestBody Map<String, String> paraMap) throws Exception {
        String userId = paraMap.get("accountId");
        String companyId = paraMap.get("appOrgId");
        CompanyRelationAuditDTO dto = new CompanyRelationAuditDTO();
        //申请成为合作伙伴
        dto.setAccountId(userId);
        dto.setOrgId(companyId);
        dto.setOrgId(paraMap.get("id"));
        dto.setType("3");
        dto.setRelationType("0");

        return returnResponseBean(companyRelationAuditService.applicationOrInvitation(dto));
    }

    /**
     * 方法描述：申请成为分公司,合作伙伴(当前公司的id为orgId，选择公司的id为orgPid）
     * 邀请分公司,合作伙伴(当前公司的id为orgPid，选择公司的id为orgId）
     * 作        者：MaoSF
     * 日        期：2016年3月21日-上午10:38:08
     */
    @RequestMapping("/invate_businesspartner")
    @ResponseBody
    public ResponseBean invateBusinessPartner(@RequestBody Map<String, String> paraMap) throws Exception {
        String userId = paraMap.get("accountId");
        String companyId = paraMap.get("appOrgId");
        CompanyRelationAuditDTO dto = new CompanyRelationAuditDTO();
        //邀请合作伙伴
        dto.setAccountId(userId);
        dto.setOrgId(paraMap.get("id"));
        dto.setOrgPid(companyId);
        dto.setType("3");
        dto.setRelationType("1");
        return returnResponseBean(companyRelationAuditService.applicationOrInvitation(dto));
    }

    /**
     * 方法描述：查询正在审核和审核通过的合作伙伴
     * 作        者：MaoSF
     * 日        期：2016年6月8日-上午11:07:13
     */
    @RequestMapping("/select_invitedpartner")
    @ResponseBody
    public ResponseBean selectInvitedPartner(@RequestBody Map<String, Object> map) throws Exception {
        //Map<String,Object> map = new HashMap<String,Object>();
        //map.put("companyStatus", companyStatus);
        map.put("cid", map.get("appOrgId"));
        map.put("isAudit", "1");//表示只查询待审核的且只是自己审核的数据
        map.put("auditStatus", 2);
        List relationAuditList = companyRelationAuditService.getCompanyRelationAuditByParam(map);
        return responseSuccess().addData("relationAuditList", relationAuditList);
    }

    /**
     * 方法描述：搜索未挂靠的公司（大B搜索小b，小b搜索大B）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-下午10:59:23
     */
    @RequestMapping("/get_filtercompany")
    @ResponseBody
    public ResponseBean getFilterCompany(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != paraMap.get("keyword")) {
            map.put("keyword", paraMap.get("keyword"));
        }
        if (null != paraMap.get("pageSize") && null != paraMap.get("pageNumber")) {
            map.put("pageSize", paraMap.get("pageSize"));
            map.put("pageNumber", paraMap.get("pageNumber"));
        }
        if (null != paraMap.get("appOrgId")) {
            map.put("orgId", paraMap.get("appOrgId"));
        }

        return responseSuccess().addData("companyList", companyService.getCompanyFilterbyParamPage(map));
    }

    /**
     * 方法描述：组织与组织之间的关系的操作（companyStatus:取消，同意，拒绝，解除）
     * 作        者：MaoSF
     * 日        期：2016年2月26日-下午4:04:43
     */
    @RequestMapping("/processingApplicationOrInvitation")
    @ResponseBody
    public ResponseBean processingApplicationOrInvitation(@RequestBody CompanyRelationAuditDTO dto) throws Exception {
        return returnResponseBean(companyRelationAuditService.processingApplicationOrInvitation(dto));
    }

    /********************移交管理员******************/

    /**
     * 方法描述：移交管理员
     * 作        者：MaoSF
     * 日        期：2016年3月18日-上午9:43:08
     */
    @RequestMapping("/transfersys")
    @ResponseBody
    public ResponseBean transferSys(@RequestBody TeamOperaterDTO dto) throws Exception {
        String newAdminPassword = dto.getNewAdminPassword();
       /* dto.setAccountId(this.getFromSession("userId", String.class));
        dto.setCompanyId(this.currentCompanyId);*/
        dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
        dto.setNewAdminPassword(MD5Helper.getMD5For32(dto.getNewAdminPassword()));
        AjaxMessage ajaxMessage = teamOperaterService.transferSys(dto, newAdminPassword);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("移交成功!");
        } else {
            return ResponseBean.responseError(ajaxMessage.getInfo().toString());
        }

/*
        try {
            AccountDTO accountDTO = accountService.getAccountById(dto.getAccountId());
            if (accountDTO.getPassword().equals(dto.getUserPassword())) {

                return returnResponseBean(teamOperaterService.transferSysWS(dto,dto.getAdminPassword()));
            }
            return ResponseBean.responseError("您输入的密码和当前用户的密码不一致！");
        }
        catch (Exception e){
            log.error("移交管理员失败！",e);
            return ResponseBean.responseError("移交管理员失败!");
        }*/
    }

    /**
     * 方法描述：查询用户所在组织列表及个人信息
     * 作        者：Chenxj
     * 日        期：2016年6月22日-下午4:18:46
     */
    @RequestMapping("/personal_in_group_and_info")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean selectPersonalInGroupAndInfo(@RequestBody Map<String, Object> map) throws Exception {
        map.put("userId", map.get("accountId"));
        return responseSuccess()
                .addList(companyUserService.selectPersonalInGroupAndInfo(map));
    }

    /**
     * 方法描述：离开组织
     * 作        者：Chenxj
     * 日        期：2016年6月22日-下午4:18:46
     */
    @RequestMapping("/live_company")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean liveCompany(@RequestBody CompanyUserTableDTO companyUserTableDTO) throws Exception {
        String userId = companyUserTableDTO.getUserId();
        String companyId = companyUserTableDTO.getCompanyId();
        CompanyUserEntity companyUserEntity = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, companyId);
        AjaxMessage ajax = companyUserService.quit(companyUserEntity.getId());
        if (ajax.getCode().equals("0")) {
            return ResponseBean.responseSuccess();
        } else {
            return ResponseBean.responseError("离开组织操作失败");
        }
    }

    /**
     * 方法描述：项目签发（组织查询）所有的公司及自己的部门
     * 作者：MaoSF
     * 日期：2016/8/6
     * @param:appOrgid
     * @return:ResponseBean
     */
    @RequestMapping("/designOrg")
    @ResponseBody
    public ResponseBean getDesignOrg(@RequestBody Map<String, String> mappass) throws Exception {
        String companyId = mappass.get("appOrgId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", companyId);
        map.put("companyName", mappass.get("companyName"));
        List<CompanyDTO> list = companyService.getCompanyFilterbyParam(map);
        CompanyDTO company = companyService.getCompanyById(companyId);
        map.clear();
        map.put("companyId", companyId);
        map.put("selectVal", mappass.get("companyName"));
        List<DepartDataDTO> departList = departService.getDepartByCompanyId(map);
        company.setDepartList(departList);
        list.add(0, company);
        return responseSuccess().addData("designOrgList", list);
    }

    /**
     * 方法描述：查询用户所在组织列表及角色
     * 作        者：Chenxj
     * 日        期：2016年6月22日-下午4:18:46
     */
    @RequestMapping("/personal_in_group_and_role")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean selectPersonalInGroupAndRole(@RequestBody Map<String, Object> map) throws Exception {
        String userId = (String) map.get("accountId");
        map.put("userId", map.get("accountId"));
        List<Map<String, Object>> list = companyUserService.selectPersonalInGroupAndInfo(map);
        List<InterfaceGroupAndRoleDTO> interfaceGroupAndRoleDTOList = new ArrayList<InterfaceGroupAndRoleDTO>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> mapi = list.get(i);
            String companyId = (String) mapi.get("companyId");
            String companyName = (String) mapi.get("companyName");
            String companyShortName = (String) mapi.get("companyShortName");
            CompanyDTO company = companyService.getCompanyById(companyId);
            InterfaceGroupAndRoleDTO interfaceGroupAndRoleDTO = new InterfaceGroupAndRoleDTO();
            interfaceGroupAndRoleDTO.setCompanyId(companyId);
            CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, companyId);
            interfaceGroupAndRoleDTO.setCompanyUserId(companyUser.getId());
            if (company != null) {
                interfaceGroupAndRoleDTO.setFilePath((company.getFilePath() == null) ? "" : company.getFilePath());
            } else {
                interfaceGroupAndRoleDTO.setFilePath("");
            }
            interfaceGroupAndRoleDTO.setCompanyName(companyName);
            interfaceGroupAndRoleDTO.setCompanyShortName(companyShortName);
            interfaceGroupAndRoleDTOList.add(interfaceGroupAndRoleDTO);
        }
        return responseSuccess()
                .addData("interfaceGroupAndRoleDTOList", interfaceGroupAndRoleDTOList);
    }

}
