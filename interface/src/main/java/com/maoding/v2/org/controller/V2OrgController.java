package com.maoding.v2.org.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.MD5Helper;
import com.maoding.core.util.StringUtil;
import com.maoding.core.util.StringUtils;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.*;
import com.maoding.project.dto.NetFileDTO;
import com.maoding.project.dto.ProjectDetailQueryDTO;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.role.dto.InterfaceGroupAndRoleDTO;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.system.dto.DataDictionaryDTO;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.user.dto.RegisterCompanyDTO;
import com.maoding.user.dto.ShareInvateDTO;
import com.maoding.user.dto.UserAttachDTO;
import com.maoding.user.service.AccountService;
import com.maoding.user.service.UserAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenzhujie
 */
@Controller
@RequestMapping("/v2/org")
public class V2OrgController extends BaseWSController {

    @Autowired
    public AccountService accountService;
    @Value("${fastdfs.url}")
    protected String fastdfsUrl;
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
    private ProjectSkyDriverService projectSkyDriverService;
    @Autowired
    private CompanyUserAuditService companyUserAuditService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ImService imService;
    @Autowired
    private OrgDynamicService orgDynamicService;
    @Autowired
    private UserAttachService userAttachService;
    @Autowired
    private CompanyRelationService companyRelationService;

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
     * 方法描述：群组列表
     * 作    者 : ChenZhujie
     * 日     : 2016/12/23
     */
    @RequestMapping("/imGroupListNew")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean imGroupListNew(@RequestBody ImGroupQuery query) throws Exception {
        return responseSuccess().setData(this.imService.imGroupListNew(query));
    }

    /**
     * 方法描述：获取当前用户所在的组织（
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getMyCompanylist")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getMyCompanylist(@RequestBody Map<String, String> dto) throws Exception {
        String userId = dto.get("accountId");
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(userId);
        for (CompanyDTO cdto : orgList) {
            CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, cdto.getId());
            cdto.setCompanyUserId(companyUser.getId());
        }
        return responseSuccess().addData("companyData", orgList);
    }

    /**
     * 方法描述：组织架构树(数据）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getcompanyData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getcompanyData(@RequestBody Map<String, Object> mapass) throws Exception {
        String userId = mapass.get("accountId").toString();
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(userId);
        //所在的所有公司
        String companyIds = "";
        for (CompanyDTO companyDto : orgList) {
            companyIds = companyIds + "," + companyDto.getId();
        }

        List<CompanyDTO> returList = new ArrayList<>();

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
        for (CompanyDTO dto : returList) {
            CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, dto.getId());
            if(companyUser!=null){
                dto.setCompanyUserId(companyUser.getId());
            }
        }
        return responseSuccess().addData("companyData", returList);
    }

    /**
     * 方法描述：通讯录
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getAddressBook")
    @ResponseBody
    @AuthorityCheckable
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
     * 方法描述：通讯录
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getAllPersons")
    @ResponseBody
    public ResponseBean getAllPersons(@RequestBody Map<String, Object> map) throws Exception {
        map.put("fastdfsUrl", this.fastdfsUrl);
        return responseSuccess().addData("list", accountService.selectV2AllPersonByParam(map));
    }

    /**
     * 方法描述：获取当前用户所在的组织（组织切换）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getChoiseCompanyList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getChoiseCompanyList(@RequestBody Map<String, String> dto) throws Exception {
        String userId = dto.get("accountId");
        List<CompanyDTO> list = companyService.getCompanyByUserId(userId);
        return responseSuccess().addData("companyList", list);
    }

    /**
     * 方法描述：获取公司轮播图片 //无
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyImg")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyImg(@RequestBody Map<String, Object> param) throws Exception {
        //没有存公司过来，则不查询出来
        if (StringUtil.isNullOrEmpty(param.get("companyId"))) {
            return ResponseBean.responseSuccess("").addData("status", "0").addData("list", new ArrayList<>());
        }
        List<NetFileDTO> list = projectSkyDriverService.getCompanyBanner((String)param.get("companyId"));
        return ResponseBean.responseSuccess("").addData("status", "0").addData("list", list);

    }


    /**
     * 方法描述：解散组织
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/disbandCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean disbandCompany(@RequestBody TeamOperaterDTO dto) throws Exception {
        AjaxMessage ajax = companyService.disbandCompany(dto.getCompanyId());
        if (ajax.getCode().equals("0")) {
            //  imGroupService.removeGroup(dto.getCompanyId());
            return ResponseBean.responseSuccess("解散当前组织成功");
        } else {
            return ResponseBean.responseError(ajax.getInfo() == null ? "解散当前组织失败" : (String) ajax.getInfo());
        }
    }

    /***************组织架构树***************/

    /**
     * 方法描述：离职，删除人员
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/quit")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean quit(@RequestBody CompanyUserTableDTO dto) throws Exception {
        AjaxMessage ajax = companyUserService.quit(dto.getId());
        if (ajax.getCode().equals("0")) {
            return ResponseBean.responseSuccess("操作成功");
        } else {
            return ResponseBean.responseError("操作失败");
            //return ResponseBean.responseError("离开组织操作失败");
        }
    }

    /**
     * 方法描述：组织架构树(数据）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getOrgTree")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgTree(@RequestBody Map<String, String> dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", dto.get("appOrgId"));
        map.put("type", "2");//查询以当前公司为root的所有公司，部门信息
        OrgTreeDTO dataMap = companyService.getOrgTree(map);
        return responseSuccess().addData("treeMap", dataMap);
    }

    /**
     * 方法描述：组织架构树(数据）--搜索界面 //无
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getOrgTreeForSearch")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgTreeForSearch(@RequestBody CompanyDTO dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", dto.getId());
        map.put("type", "1");//只查询当前公司
        OrgTreeDTO dataMap = companyService.getOrgTree(map);
        return responseSuccess().addData("treeMap", dataMap);
    }

/*******************************部门信息*******************************************/


    /***********************添加修改分公司*************************/

    /**
     * 方法描述：增加，修改部门
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/saveOrUpdateDepart")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateDepart(@RequestBody DepartDTO dto) throws Exception {
        String userId = dto.getAccountId();
        dto.setAccountId(userId);
        return departService.saveOrUpdateDepart(dto);
    }

    /**
     * 方法描述：添加修改分公司
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/createSubCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean createSubCompany(@RequestBody SubCompanyDTO dto) throws Exception {
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String companyId = dto.getAppOrgId();
            dto.setCompanyId(companyId);
            dto.setAccountId(dto.getAccountId());
            dto.setClearlyAdminPassword(dto.getAdminPassword());
            dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
            dto.setType(2);
            return returnResponseBean(companyService.createSubCompany(dto));
        } else {
            return returnResponseBean(companyService.updateSubCompany(dto));
        }
    }

    /**
     * 方法描述：删除分公司
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/deleteSubCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteSubCompany(@RequestBody SubCompanyDTO dto) throws Exception {
        String companyId = dto.getAppOrgId();
        return returnResponseBean(companyService.deleteSubCompany(companyId, dto.getId()));
    }

    /**
     * 方法描述：获取当前公司的所有部门【用到的界面：添加、修改人员，添加、修改部门】
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getDepartByCompanyId")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDepartByCompanyId(@RequestBody CompanyDTO companyDTO) throws Exception {
        String companyId = companyDTO.getId();
        if (StringUtil.isNullOrEmpty(companyId)) {
            companyId = companyDTO.getAppOrgId();
        }
//        CompanyDTO company = companyService.getCompanyById(companyId);
        List<DepartDataDTO> list = departService.getDepartByCompanyId(companyId,null);
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
     * 方法描述：获取当前公司的所有部门(经营界面)
     * 作        者：MaoSF
     * 日        期：2016年7月9日-下午12:00:13
     */
    @RequestMapping("/getDepartForOperator")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDepartForOperator(@RequestBody Map<String, Object> map) throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", map.get("appOrgId"));
        param.put("type", "0");
        List<DepartDataDTO> list = departService.getDepartByCompanyId(param, null);

        return ResponseBean.responseSuccess().addData("departList", list);
    }


    /***********************创建事业合伙人*************************/

    /**
     * 方法描述：删除部门
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/deleteDepart")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteDepart(@RequestBody DepartDTO dto) throws Exception {
        return returnResponseBean(departService.deleteDepartById(dto.getId(), dto.getAccountId()));
    }

    /**
     * 方法描述：创建事业合伙人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/createBusinessPartner")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean createBusinessPartner(@RequestBody SubCompanyDTO dto) throws Exception {
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String companyId = dto.getAppOrgId();
            dto.setCompanyId(companyId);
            dto.setType(3);
            return returnResponseBean(companyService.createSubCompany(dto)); //此处共用创建分公司接口
        } else {
            BusinessPartnerDTO partner = new BusinessPartnerDTO();
            BaseDTO.copyFields(dto,partner);
            return returnResponseBean(companyService.updateBusinessPartner(partner));
        }
    }


/*******************************组织成员信息**************************************/

    /**
     * 方法描述：删除事业合伙人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/deleteBusinessPartner")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteBusinessPartner(@RequestBody BusinessPartnerDTO dto) throws Exception {
        String companyId = dto.getAppOrgId();
        return returnResponseBean(companyService.deleteBusinessPartner(companyId, dto.getId()));
    }

    /**
     * 方法描述：添加修改（组织成员）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/saveCompanyUser")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveCompanyUser(@RequestBody CompanyUserTableDTO dto) throws Exception {
        String userId = dto.getAccountId();
        CompanyDTO company = companyService.getCompanyById(dto.getAppOrgId());
        dto.setCompanyName(company.getCompanyName());
        dto.setAccountId(userId);
        return returnResponseBean(companyUserService.saveCompanyUser(dto));
    }

    /**
     * 方法描述： 查询员工信息（组织成员）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyUser")
    @ResponseBody
//    @AuthorityCheckable
    public ResponseBean getCompanyUser(@RequestBody CompanyUserTableDTO dto) throws Exception {
        CompanyUserDetailDTO companyUser = companyUserService.getCompanyUserByIdInterface(dto.getId(),dto.getNeedUserStatus());
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", companyUser.getUserId());
        param.put("attachType", "5");
        List<UserAttachDTO> list = userAttachService.getAttachByTypeToDTO(param);
        if (list != null && list.size() > 0) {
            companyUser.setFileFullPath(list.get(0).getFileGroup() + "/" + list.get(0).getAttachPath());
//            companyUser.setFilePath( list.get(0).getOssFilePath());
//            companyUser.setFileGroup(list.get(0).getFileGroup());
        }
        return responseSuccess().addData("companyUser", companyUser);
    }


    /**
     * 方法描述： 查找服务类型
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getServerTypes")
    @ResponseBody
    public ResponseBean getServerTypes() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DataDictionaryDTO> projectTypeList = dataDictionaryService.getSubDataByCodeToDTO(SystemParameters.PRO_Type);//项目类别
        map.put("serverTypeList", projectTypeList);
        return responseSuccess().addData(map);
    }

    /**
     * 方法描述：待审核成员列表信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getPendingAudiOrgUser")
    @ResponseBody
    @AuthorityCheckable
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
        if (!StringUtil.isNullOrEmpty(paraMap.get("orgId"))) {
            param.put("companyId", paraMap.get("appOrgId"));
        }
        Map returnData = companyUserAuditService.getCompanyUserAudit(param);
        return responseSuccess().addData("userList", returnData.get("data"));
    }

    /**
     * 方法描述：审核申请加入组织的人员【 同意或拒绝】(dto:id,auditStatus,companyId)
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/audiOrgUser")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean audiOrgUser(@RequestBody ShareInvateDTO dto) throws Exception {
        return returnResponseBean(companyUserAuditService.auditShareInvate(dto));
    }


    /**
     * 方法描述：申请成为分公司,合作伙伴(当前公司的id为orgId，选择公司的id为orgPid）
     * 邀请分公司,合作伙伴(当前公司的id为orgPid，选择公司的id为orgId）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/applicationSubCompany")
    @ResponseBody
    @AuthorityCheckable
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
    @RequestMapping("/invateSubCompany")
    @ResponseBody
    @AuthorityCheckable
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
    @RequestMapping("/applicationBusinessPartner")
    @ResponseBody
    @AuthorityCheckable
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
    @RequestMapping("/invateBusinessPartner")
    @ResponseBody
    @AuthorityCheckable
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
    @RequestMapping("/selectInvitedPartner")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean selectInvitedPartner(@RequestBody Map<String, Object> map) throws Exception {
        map.put("cid", map.get("appOrgId"));
        map.put("isAudit", "1");//表示只查询待审核的且只是自己审核的数据
        map.put("auditStatus", 2);
        map.put("fastdfsUrl", this.fastdfsUrl);
        List<CompanyRelationAuditDTO> relationAuditList = companyRelationAuditService.getCompanyRelationAuditByParam(map);

        return responseSuccess().addData("relationAuditList", relationAuditList);
    }

    /**
     * 方法描述：搜索未挂靠的公司（大B搜索小b，小b搜索大B）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-下午10:59:23
     */
    @RequestMapping("/getFilterCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getFilterCompany(@RequestBody Map<String, Object> paraMap) throws Exception {
        paraMap.put("orgId", paraMap.get("appOrgId"));
        paraMap.put("fastdfsUrl", this.fastdfsUrl);
        return responseSuccess().addData("companyList", companyService.getCompanyFilterbyParamForInvit(paraMap));
    }

    /********************移交管理员******************/

    /**
     * 方法描述：组织与组织之间的关系的操作（companyStatus:取消，同意，拒绝，解除）
     * 作        者：MaoSF
     * 日        期：2016年2月26日-下午4:04:43
     */
    @RequestMapping("/processingApplicationOrInvitation")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean processingApplicationOrInvitation(@RequestBody CompanyRelationAuditDTO dto) throws Exception {
        return returnResponseBean(companyRelationAuditService.processingApplicationOrInvitation(dto));
    }

    /**
     * 方法描述：移交管理员
     * 作        者：MaoSF
     * 日        期：2016年3月18日-上午9:43:08
     */
    @RequestMapping("/transfersys")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean transferSys(@RequestBody TeamOperaterDTO dto) throws Exception {
        String newAdminPassword = dto.getNewAdminPassword();
        AjaxMessage ajaxMessage = null;
        if ("1".equals(dto.getType())) {
            ajaxMessage = teamOperaterService.transferSys(dto, newAdminPassword);
        }

        if ("2".equals(dto.getType())) {
            ajaxMessage = teamOperaterService.transferOrgManager(dto, newAdminPassword);
        }

        if (ajaxMessage != null && "0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("移交成功!");
        } else {
            return ResponseBean.responseError("操作失败");
        }
    }

    /**
     * 方法描述：查询用户所在组织列表及个人信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/personalInGroupAndInfo")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean personalInGroupAndInfo(@RequestBody Map<String, Object> map) throws Exception {
        map.put("userId", map.get("accountId"));
        return responseSuccess()
                .addList(companyUserService.selectPersonalInGroupAndInfo(map));
    }

    /**
     * 方法描述：离开组织
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/liveCompany")
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
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/designOrg")
    @ResponseBody
    @AuthorityCheckable
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
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/personalInGroupAndRole")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean personalInGroupAndRole(@RequestBody Map<String, Object> map) throws Exception {
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

    /****************************************v2*****************************************************/

    /**
     * 方法描述：联系人及群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getLinkPeopleAndGroup")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getLinkPeopleAndGroup(@RequestBody Map<String, Object> mapass) throws Exception {
        return responseSuccess().setData(this.companyService.getLinkPeopleAndGroup(mapass));
    }

    /**
     * 方法描述：联系人
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getLinkPeopleList")
    @ResponseBody
   // @AuthorityCheckable
    public ResponseBean getLinkPeopleList(@RequestBody Map<String, Object> mapass) throws Exception {
        Map<String, Object> returnMap = this.companyService.getLinkPeopleAndGroup(mapass);
        return responseSuccess().setData(returnMap);
    }

    /**
     * 方法描述：群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getAllGroupList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getAllGroupList(@RequestBody ImGroupQuery query) throws Exception {
        return responseSuccess().setData(this.imService.listAllGroupByUserIdAndCompanyId(query));
    }

    /**
     * 方法描述：部门及群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getDepartAndGroup")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getDepartAndGroup(@RequestBody Map<String, Object> mapass) throws Exception {
        mapass.put("fastdfsUrl", this.fastdfsUrl);
        return this.departService.getDepartAndGroup(mapass);
    }

    /**
     * 方法描述：部门及人员
     * 作    者 : MaoSF
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getOrgData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgData(@RequestBody QueryCompanyUserDTO dto) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addDataFromObject(departService.getOrgData(dto,true));
    }

    /**
     * 方法描述：通讯录
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/v2GetAllPersons")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean v2GetAllPersons(@RequestBody Map<String, Object> dto) throws Exception {
        dto.put("fastdfsUrl", fastdfsUrl);
        dto.put("maodingAid", "maodingAid");
        return responseSuccess().addData("list", accountService.selectV2AllPersonByParam(dto));
    }

    /**
     * 方法描述：移交管理员验证密码
     * 作        者：chenzhujie
     * 日        期：2016/12/30
     */
    @RequestMapping("/verifySysPassword")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean verifySysPassword(@RequestBody TeamOperaterDTO dto) throws Exception {
        dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
        return teamOperaterService.verifySysPassword(dto);
    }

    /**
     * 方法描述： 创建组织
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/createCompany")
    @ResponseBody
    //  @AuthorityCheckable
    public ResponseBean v2CreateCompany(@RequestBody RegisterCompanyDTO dto) throws Exception {
        String userId = dto.getAccountId();
       /* if(null==dto.getAdminPassword()||"".equals(dto.getAdminPassword())){
            return ResponseBean.responseError("组织管理密码不能为空！");
        }*/
        return accountService.v2RegisterCompanyOfWork(dto, userId);
    }

    /**
     * 方法描述：增加与修改公司信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/saveOrUpdateCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateCompany(@RequestBody CompanyDTO dto) throws Exception {
        AjaxMessage ajaxMessage = companyService.saveOrUpdateCompany(dto);
        if (ajaxMessage.getCode().equals("0")) {
            return ResponseBean.responseSuccess("信息保存成功");
        } else {
            return ResponseBean.responseError("信息保存失败");
        }
    }

    /**
     * 方法描述： 根据id查找公司信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyById")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyById(@RequestBody CompanyDTO companyDTO) throws Exception {
        CompanyDTO dto = companyService.getCompanyById(companyDTO.getId());
        return responseSuccess().addData("companyInfo", dto);
    }

    /**
     * 方法描述：项目任务选择人员列表
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    @RequestMapping("/getCompanyUserForSelect")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyUserForSelect(@RequestBody Map<String, Object> map) throws Exception {
        List<CompanyUserAppDTO> userList = this.companyUserService.getCompanyUser((String) map.get("appOrgId"),(String)map.get("needUserStatus"));
        if ("1".equals(map.get("type")) && StringUtils.isEmpty((String)map.get("needUserStatus"))) {
            for (CompanyUserAppDTO companyUserAppDTO : userList) {
                if (companyUserAppDTO.getUserId().equals(map.get("accountId"))) {
                    userList.remove(companyUserAppDTO);
                }
            }
        }
        return ResponseBean.responseSuccess("查询成功").addData("userList", userList);
    }

    /**
     * 方法描述： 首页(获取公司，通知公告，项目动态)
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyNoticeProjectDynamic")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyNoticeProjectDynamic(@RequestBody Map<String, Object> map) throws Exception {
        //组织信息
        CompanyDTO dto = companyService.getCompanyById(map.get("appOrgId").toString());
        //项目动态
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("dynamicList", this.orgDynamicService.getLastOrgDynamicListByCompanyId(map));
        List<NetFileDTO> list = projectSkyDriverService.getCompanyBanner(map.get("appOrgId").toString());
        return ResponseBean.responseSuccess("查询成功")
                .addData("companyAttachList", list)
                .addData("companyInfo", dto)
                .addData(paraMap);
    }

    /**
     * 方法描述：团队切换列表（当前用户所在组织及组织中的权限）
     * 作        者：Chenxj
     * 日        期：2016年6月22日-下午4:18:46
     */
    @RequestMapping("/getMyCompanyAndRoleList")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getMyCompanyAndRoleList(@RequestBody Map<String, Object> map) throws Exception {
        String userId = (String) map.get("accountId");
        map.put("userId", map.get("accountId"));
        List<CompanyRoleDataDTO> companyRoleDataList = this.companyUserService.getCompanyRole(map);
        return responseSuccess()
                .addData("interfaceGroupAndRoleDTOList", companyRoleDataList);
    }

    /**
     * 方法描述：邀请事业合伙人这里需要返回企业负责人和企业名称和邀请码
     * 作        者：chenzhujie
     * 日        期：2017/2/27
     */
    @RequestMapping("/getInviteAbout")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getInviteAbout(@RequestBody Map<String, Object> map) throws Exception {
        String companyId = (String) map.get("appOrgId");
        map.put("companyId", companyId);
        Map<String, Object> resultMap = this.companyService.getInviteAbout(map);
        return responseSuccess()
                .addData("resultMap", resultMap);
    }


    /**
     * 方法描述：验证
     * 作        者：chenzhujie
     * 日        期：2017/2/27
     */
    @RequestMapping("/applyBusinessCompany")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean applayBusinessCompany(@RequestBody Map<String, Object> map) throws Exception {
        return this.companyService.applayBusinessCompany(map);
    }

    /**
     * 方法描述：企业负责人
     * 作        者：chenzhujie
     * 日        期：2017/2/27
     */
    @RequestMapping("/getCompanyPrincipal")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyPrincipal(@RequestBody Map<String, Object> map) throws Exception {
        map.put("userId", map.get("accountId"));
        Map<String, Object> resultMap = this.companyService.getCompanyPrincipal(map);
        return responseSuccess().setData(resultMap);
    }

    /**
     * 方法描述：邀请事业合伙人（发送邀请接口）
     * 作者：MaoSF
     * 日期：2017/4/1
     */
    @RequestMapping("/inviteParent")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean inviteParent(@RequestBody InvitatParentDTO dto) throws Exception {
        dto.setCurrentCompanyId(dto.getAppOrgId());
        return this.companyService.inviteParent(dto);
    }


    /**
     * 方法描述：设置事业合伙人的别名
     * 作者：MaoSF
     * 日期：2017/4/1
     */
    @RequestMapping("/setBusinessPartnerNickName")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean setBusinessPartnerNickName(@RequestBody BusinessPartnerDTO dto) throws Exception {
        return this.companyService.setBusinessPartnerNickName(dto);
    }

    /**
     * 方法描述：项目讨论区的@和特别提醒人员选择
     * 作者：GuoZhiBin
     * 日期：2017/4/1
     */
    @RequestMapping("/getCompanyUserForCustomGroup")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyUserForCustomGroup(@RequestBody QueryCompanyUserDTO query) throws Exception {
        query.setIsExceptMe("1");//需要过滤自己
        List<CompanyUserGroupDTO> returnData = this.companyService.getCompanyForCustomGroupSelect(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：获取整个组织架构数中的所有组织及成员
     * 作者：GuoZhiBin
     * 日期：2017/4/1
     */
    @RequestMapping("/getLinkman")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getLinkman(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserGroupDTO> returnData = this.companyService.getLinkman(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }


    /**
     * 方法描述：获取所有组织及项目参与人员（会议选择组织）
     * 作者：GuoZhiBin
     * 日期：2017/4/1
     */
    @RequestMapping("/getOrgForSchedule")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgForSchedule(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserGroupDTO> returnData = this.companyService.getOrgForSchedule(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：获取所选组织的所以成员
     * 作者：MaoSF
     * 日期：2018/4/1
     */
    @RequestMapping("/getUserByOrgId")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getUserByOrgId(@RequestBody QueryCompanyUserDTO query) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",query.getCompanyId());
        List<CompanyUserDataDTO> returnData = this.companyUserService.getUserByOrgId(map);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }


    /**
     * 方法描述：需求就是选择组织架构中的所有人员，过滤自己以及报销、费用详情审批列表中包含的人员
     * 作者：MaoSF
     * 日期：2018/5/3
     */
    @RequestMapping("/getCostAuditMember")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCostAuditMember(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserGroupDTO> returnData = this.companyService.getCostAuditMember(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：需求就是选择组织架构中的所有人员，过滤自己以及出差、请求详情审批列表中包含的人员
     * 作者：MaoSF
     * 日期：2018/5/3
     */
    @RequestMapping("/getLeaveAuditMember")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getLeaveAuditMember(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserAppDTO> returnData = this.companyService.getLeaveAuditMember(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：费用、报销，抄送人列表
     * 作者：MaoSF
     * 日期：2018/5/3
     */
    @RequestMapping("/getCostCopyMember")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCostCopyMember(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserGroupDTO> returnData = this.companyService.getCostCopyMember(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：出差、请假，抄送人列表
     * 作者：MaoSF
     * 日期：2018/5/3
     */
    @RequestMapping("/getLeaveCopyMember")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getLeaveCopyMember(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserAppDTO> returnData = this.companyService.getLeaveCopyMember(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：最近审批人列表并按 最后操作时间倒序排序
     * 作者：zcl
     * 日期：2018/4/23
     */
    @RequestMapping("/listRecentlyAuditUser")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean listRecentlyAuditUser(@RequestBody QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserAppDTO> returnData = this.companyService.listCompanyUserForCustomGroupSelect(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：自定义群组创建（人员选择）
     * 作者：GuoZhiBin
     * 日期：2017/4/1
     */
    @RequestMapping("/getCustomerGroupUser")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCustomerGroupUser(@RequestBody QueryCompanyUserDTO query) throws Exception {
        query.setIgnoreUserId(query.getAccountId());
        List<CompanyUserGroupDTO> returnData = this.companyService.getLinkman(query);
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }

    /**
     * 方法描述：根据companyUserId查询报销审批申请人列表
     * 作者：MaoSF
     * 日期：2016/12/26
     */
    @RequestMapping("/getExpTaskMembers")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getExpTaskMembers(@RequestBody Map<String, Object> paraMap) throws Exception {
        List<CompanyUserExpMemberDTO> list = companyUserService.getExpTaskMembers(paraMap);
        return ResponseBean.responseSuccess("查询成功").addData("memberList",list);
    }

    /**
     * 方法描述：抄送给我的记录的审批人列表
     * 作者：MaoSF
     * 日期：2016/12/26
     */
    @RequestMapping("/getApplyExpUserForCopy")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getApplyExpUserForCopy(@RequestBody Map<String, Object> paraMap) throws Exception {
        List<CompanyUserExpMemberDTO> list = companyUserService.getApplyExpUserForCopy(paraMap);
        return ResponseBean.responseSuccess("查询成功").addData("memberList",list);
    }

    /**
     * 方法描述：合作组织信息
     * 作者：MaoSF
     * 日期：2016/12/26
     */
    @RequestMapping("/getCooperatorData")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCooperatorData(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("relationData",companyRelationService.getCooperatorData(paraMap));
    }

    /****************常用乙方***********************/
    /**
     * 方法描述：常用乙方
     * 作者：MaoSF
     * 日期：2016/8/26
     */
    @RequestMapping(value = "/getUsedPartB", method = RequestMethod.POST)
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getUsedPartB(@RequestBody ProjectDetailQueryDTO query) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("companyList",this.companyService.getUsedPartB(query.getAppOrgId()));
    }
}