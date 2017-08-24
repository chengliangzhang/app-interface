package com.maoding.v2.org.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.MD5Helper;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.*;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.role.dto.InterfaceGroupAndRoleDTO;
import com.maoding.role.service.PermissionService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.system.dto.DataDictionaryDTO;
import com.maoding.system.service.DataDictionaryService;
import com.maoding.user.dto.AccountDTO;
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


    private final String th0 = "240X250"; //内部管理，log
    private final String th1 = "300X45"; //内部管理，log
    private final String th2 = "150X180";//公司组织log 最大尺寸150X180
    private final String th3 = "200X35";//精英组织图标logo尺寸：最大200 x 35
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
    private PermissionService permissionService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ImService imService;
    @Autowired
    private OrgDynamicService orgDynamicService;
    @Autowired
    private UserAttachService userAttachService;

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
//        //更新所有的根组织
//        imGroupService.updateImGroupRootOrgIds();
//        map.put("fastdfsUrl", fastdfsUrl);
//        map.put("userId", map.get("accountId"));
//        //查询所有的公司群
//        List<ImGroupEntity> lsAll = imGroupService.selectByParameter(map);
//        //所有的根的公司
//        String rootIds = "";
//        int j = 0;
//        for (ImGroupEntity imGroupEntity : lsAll) {
//            if (imGroupEntity != null && !rootIds.contains(imGroupEntity.getRootOrgId())) {
//                if (j == 0) {
//                    rootIds = imGroupEntity.getRootOrgId();
//                } else {
//                    rootIds = rootIds + "," + imGroupEntity.getRootOrgId();
//                }
//                j++;
//            }
//        }
//        String[] rootIdsAttr = rootIds.split(",");
//        //返回数据
//        List<Object> returnList = new ArrayList<>();
//        for (String rId : rootIdsAttr) {
//            List<ImGroupEntity> ls = new ArrayList<>();
//            for (ImGroupEntity imGroupEntity : lsAll) {
//                if (rId.equals(imGroupEntity.getRootOrgId())) {
//                    if (!imGroupEntity.getOrgId().equals(imGroupEntity.getRootOrgId())) {
//                        CompanyEntity companyEntity = companyService.selectById(imGroupEntity.getRootOrgId());
//                        if (null != companyEntity) {
//                            imGroupEntity.setName(imGroupEntity.getName());
//                        }
//                    }
//
//                    ls.add(imGroupEntity);
//                }
//            }
//            List<ImGroupDTO> lsDtoReturn = new ArrayList<>();
//            List<ImGroupDTO> lsDto = new ArrayList<>();
//            if (ls != null) {
//                lsDto = BaseDTO.copyFields(ls, ImGroupDTO.class);
//                for (ImGroupDTO et : lsDto) {
//                    CompanyEntity companyEntity = companyService.selectById(et.getRootOrgId());
//                    if (null != companyEntity) {
//                        et.setRootCompanyName(companyEntity.getCompanyName());
//                    }
//                    Map<String, Object> mapPass = new HashMap<String, Object>();
//                    mapPass.put("orgId", et.getOrgId());
//                    mapPass.put("url", fastdfsUrl);
//                    List<Map<String, Object>> members = imGroupService.selectNewGroupMembers(mapPass);
//
//                    et.setMemberInfo(members);
//                    et.setIsCompany("0");
//                    et.setMembers(members.size());
//                    map.put("orgId", et.getOrgId());
//                    List<ImGroupEntity> departGroupList = imGroupService.selectDepartGroupList(map);
//                    List<ImGroupDTO> departGroupDtoList = new ArrayList<>();
//                    if (departGroupList != null) {
//                        departGroupDtoList = BaseDTO.copyFields(departGroupList, ImGroupDTO.class);
//                        et.setImGroupNewDTOList(departGroupDtoList);
//                        if (departGroupDtoList == null) {
//                            List<ImGroupDTO> listNull = new ArrayList<>();
//                            et.setImGroupNewDTOList(listNull);
//                        }
//                        for (ImGroupDTO newet : departGroupDtoList) {
//                            List<Map<String, Object>> listDepartMemebers = imGroupService.selectNewDepartGroupMembers(objectMap("orgId", newet.getOrgId(), "url", fastdfsUrl, "companyId", et.getOrgId()));
//                            newet.setMembers(listDepartMemebers.size());
//                            newet.setMemberInfo(listDepartMemebers);
//                            newet.setIsCompany("1");
//                            newet.setName(newet.getName());
//                        }
//                    } else {
//                        departGroupDtoList = new ArrayList<>();
//                        et.setImGroupNewDTOList(departGroupDtoList);
//                        if (departGroupDtoList == null) {
//                            List<ImGroupDTO> listNull = new ArrayList<>();
//                            et.setImGroupNewDTOList(listNull);
//                        }
//                    }
//                    if (lsDto.size() > 0) {
//                        lsDtoReturn.add(et);
//                    }
//                    if (departGroupDtoList.size() > 0) {
//                        lsDtoReturn.addAll(departGroupDtoList);
//                    }
//                }
//            }
//            returnList.add(lsDtoReturn);
//        }
//        map.remove("orgId");
//        //查询有关的项目群
//        List<ImGroupEntity> projectGroupList = imGroupService.selectProjectGroupByParameter(map);
//        //查询有关的自定义群
//        map.remove("companyId");//customGroupList需要把所有的自定义群组都查询出来，所有需要移除companyId（如果有的话）
//        List<ImGroupEntity> customGroupList = imGroupService.selectCustomGroupByParameter(map);
//
//        List<ImGroupDTO> resultProjectGroupList = new ArrayList<>();
//        List<ImGroupDTO> resultProjectGroupListDto = new ArrayList<>();
//        if (projectGroupList != null) {
//            resultProjectGroupList = BaseDTO.copyFields(projectGroupList, ImGroupDTO.class);
//            for (ImGroupDTO et : resultProjectGroupList) {
//                Map<String, Object> mapPass = new HashMap<String, Object>();
//                mapPass.put("orgId", et.getOrgId());
//                mapPass.put("url", fastdfsUrl);
//                //改变，不加company条件
//                mapPass.put("projectGroup", "projectGroup");
//                List<Map<String, Object>> members = imGroupService.selectNewGroupMembers(mapPass);
//                et.setMemberInfo(members);
//                et.setIsCompany("3");
//                et.setMembers(members.size());
//                resultProjectGroupListDto.add(et);
//            }
//        }
//
//        List<ImGroupDTO> resultCustomGroupList = new ArrayList<>();
//        List<ImGroupDTO> resultCustomGroupListDto = new ArrayList<>();
//        if (null != customGroupList) {
//            resultCustomGroupList = BaseDTO.copyFields(customGroupList, ImGroupDTO.class);
//            for (ImGroupDTO et : resultCustomGroupList) {
//                Map<String, Object> mapPass = new HashMap<String, Object>();
//                mapPass.put("orgId", et.getOrgId());
//                mapPass.put("url", fastdfsUrl);
//                List<Map<String, Object>> members = imGroupService.selectNewGroupMembers(mapPass);
//                et.setMemberInfo(members);
//                et.setIsCompany("2");
//                et.setMembers(members.size());
//                resultCustomGroupListDto.add(et);
//            }
//        }

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
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", dto.get("accountId"));
            param.put("companyId", cdto.getId());
            String roleCodes = permissionService.getPermissionCodeByUserId(param);
            cdto.setRoleCodes(roleCodes);
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
        for (CompanyDTO dto : returList) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", mapass.get("accountId"));
            param.put("companyId", dto.getId());
            String roleCodes = permissionService.getPermissionCodeByUserId(param);
            dto.setRoleCodes(roleCodes);
            CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, dto.getId());
            dto.setCompanyUserId(companyUser.getId());
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
     * 方法描述：通讯录人员
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getOrgUsers")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgUsers(@RequestBody Map<String, Object> dto) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        if (null != dto.get("orgId")) {
            param.put("orgId", dto.get("orgId"));
        }
        if (null != dto.get("pageSize")) {
            param.put("pageSize", dto.get("pageSize"));
        }
        if (null != dto.get("pageIndex")) {
            param.put("pageNumber", dto.get("pageIndex"));
        }
        param.put("auditStatus", "1");
        List<CompanyUserTableDTO> data = companyUserService.getCompanyUserByOrgIdOfWork(param);
        for (CompanyUserTableDTO companyUserTableDTO : data) {
            Map<String, Object> paramPass = new HashMap<String, Object>();
            paramPass.put("userId", companyUserTableDTO.getUserId());
            paramPass.put("attachType", "5");
            List<UserAttachDTO> list = userAttachService.getAttachByTypeToDTO(paramPass);
            if (list != null && list.size() > 0) {
                String img = list.get(0).getAttachPath();
                String lastStr = img.substring(img.lastIndexOf('.') + 1);
                String firstStr = img.substring(0, img.lastIndexOf('.'));
                String newImg = "";
                if (firstStr.endsWith("_cut")) {
                    newImg = img;
                } else {
                    newImg = firstStr + "_cut" + "." + lastStr;
                }
                companyUserTableDTO.setHeadImg(newImg);
            }
        }
        int totalNumber = companyUserService.getCompanyUserByOrgIdCountOfWork(param);
        param.clear();
        param.put("data", data);
        param.put("total", totalNumber);
        return responseSuccess().addData(param);
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
        List<AccountDTO> list = accountService.selectV2AllPersonByParam(map);
        return responseSuccess().addData("list", list);
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
        param.put("type", NetFileType.COMPANY_BANNER_ATTACH);
        param.put("status", "0");
        //没有存公司过来，则不查询出来
        if (StringUtil.isNullOrEmpty(param.get("companyId"))) {
            return ResponseBean.responseSuccess("").addData("status", "0").addData("list", new ArrayList<>());
        }
        List<ProjectSkyDriveEntity> list = projectSkyDriverService.getNetFileByParam(param);
        return ResponseBean.responseSuccess("").addData("status", "0").addData("list", list);

    }

    /**
     * 方法描述：获取企业登录（组织选择列表） 无
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getAdminOfCompanyByUserId")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getAdminOfCompanyByUserId(@RequestBody Map<String, String> dto) throws Exception {

        List<CompanyDTO> list = companyService.getAdminOfCompanyByUserId(dto.get("accountId"));
        return responseSuccess().addData("companyList", list);
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

    /**
     * 方法描述： 查询分公司或合作伙伴
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getPartenerOrsubcompany")
    @ResponseBody
    public ResponseBean getPartenerOrsubcompany(@RequestBody Map<String, String> paraMap) throws Exception {
        String companyId = paraMap.get("companyId");
        String selectType = paraMap.get("selectType");
        if (null == paraMap.get("companyId")) {
            companyId = paraMap.get("appOrgId");
        }
        List<CompanyEntity> companyList = new ArrayList<CompanyEntity>();
        List<CompanyEntity> companyes = companyService.getAllChilrenCompanyWs(companyId);
        for (CompanyEntity c : companyes) {
            if ("2".equals(c.getCompanyType()) && selectType.equals("1")) {//如果是分公司
                companyList.add(c);
            }
            if ("3".equals(c.getCompanyType()) && selectType.equals("2")) {//如果是合作伙伴
                companyList.add(c);
            }
        }
        return responseSuccess().addData("companyList", companyList);
    }

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
        CompanyDTO company = companyService.getCompanyById(companyId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", companyId);
        param.put("type", "0");
        List<DepartDTO> list = departService.getDepartByCompanyIdWS(param);
        for (DepartDTO departDTO : list) {
            departDTO.setType("depart");
        }
        DepartDTO dto = new DepartDTO();
        dto.setId(companyId);
        dto.setDepartName(company.getCompanyName());
        list.add(0, dto);
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
        List<DepartDTO> list = departService.getDepartByCompanyId(param, null);

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
    public ResponseBean createBusinessPartner(@RequestBody BusinessPartnerDTO dto) throws Exception {
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            String companyId = dto.getAppOrgId();
            dto.setCompanyId(companyId);
            dto.setAccountId(dto.getAccountId());
            dto.setClearlyAdminPassword(dto.getAdminPassword());
            dto.setAdminPassword(MD5Helper.getMD5For32(dto.getAdminPassword()));
            return returnResponseBean(companyService.createBusinessPartner(dto));
        } else {
            return returnResponseBean(companyService.updateBusinessPartner(dto));
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
    @AuthorityCheckable
    public ResponseBean getCompanyUser(@RequestBody CompanyUserTableDTO dto) throws Exception {
        CompanyUserTableDTO companyUser = companyUserService.getCompanyUserByIdInterface(dto.getId());
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", companyUser.getUserId());
        param.put("attachType", "5");
        List<UserAttachDTO> list = userAttachService.getAttachByTypeToDTO(param);
        if (list != null && list.size() > 0) {
            companyUser.setFilePath(fastdfsUrl + list.get(0).getFileGroup() + "/" + list.get(0).getAttachPath());
            companyUser.setFileGroup(list.get(0).getFileGroup());
        }
        return responseSuccess().addData("companyUser", companyUser);
    }

    /**
     * 方法描述：组织成员列表信息【组织界面-组织人员列表，人员选择列表】
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getOrgUser")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgUser(@RequestBody Map<String, Object> paraMap) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("orgId", paraMap.get("orgId"));
            param.put("pageSize", paraMap.get("pageSize"));
            param.put("pageNumber", paraMap.get("pageIndex"));
            param.put("auditStatus", "1");
            if (null == param.get("orgId")) {
                param.put("orgId", paraMap.get("appOrgId"));
            }
            List<CompanyUserTableDTO> data = companyUserService.getCompanyUserByOrgIdOfAdmin(param);
            for (CompanyUserTableDTO companyUserTableDTO : data) {
                Map<String, Object> paramRoleCodes = new HashMap<String, Object>();
                paramRoleCodes.put("userId", companyUserTableDTO.getUserId());
                paramRoleCodes.put("companyId", companyUserTableDTO.getCompanyId());
                String roleCodes = permissionService.getPermissionCodeByUserId(paramRoleCodes);
                if (null != roleCodes && !"".equals(roleCodes)) {
                    companyUserTableDTO.setRoleCodes(roleCodes);
                }
            }

            return responseSuccess().addData("userList", data);
        } catch (Exception e) {
            log.error("查询组织员工失败", e);
            return ResponseBean.responseError("查询组织员工失败！");
        }
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
     * 方法描述：批量添加员工
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/batchAddPersonal")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean batchAddPersonal(@RequestBody BatchCompanyUserDTO batchCompanyUserDTO) throws Exception {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("orgId", batchCompanyUserDTO.getOrgId());
            param.put("auditStatus", "1");
            if (null == param.get("orgId")) {
                param.put("orgId", batchCompanyUserDTO.getAppOrgId());
            }
            List<CompanyUserTableDTO> existUser = companyUserService.getCompanyUserByOrgIdOfAdmin(param);
            List<CompanyUserTableDTO> importUser = batchCompanyUserDTO.getCompayUserList();
            Map<String, Object> successImport = new HashMap<String, Object>();
            List<String> successCellphone = new ArrayList<>();
            Map<String, Object> alreadyExit = new HashMap<String, Object>();
            List<String> alreadyCellphone = new ArrayList<>();
            for (CompanyUserTableDTO cuImport : importUser) {
                String cellphone = cuImport.getCellphone();
                boolean flag = false;
                outer:
                for (CompanyUserTableDTO cuExist : existUser) {
                    if (cellphone.equals(cuExist.getCellphone())) {
                        flag = true;
                        break outer;
                    }
                }
                if (flag) {
                    alreadyCellphone.add(cellphone);
                } else {
                    successCellphone.add(cellphone);
                }
            }
            successImport.put("successImport", successCellphone);
            alreadyExit.put("alreadyExit", alreadyCellphone);
            List<CompanyUserTableDTO> compayUserList = new ArrayList<CompanyUserTableDTO>();
            if (null != batchCompanyUserDTO.getCompayUserList()) {
                compayUserList = batchCompanyUserDTO.getCompayUserList();
            }
            for (CompanyUserTableDTO dto : compayUserList) {
                String userId = batchCompanyUserDTO.getAccountId();
                CompanyDTO company = companyService.getCompanyById(batchCompanyUserDTO.getAppOrgId());
                dto.setCompanyName(company.getCompanyName());
                dto.setAccountId(userId);
                AjaxMessage ajax = companyUserService.saveCompanyUser(dto);
                if (ajax.getCode().equals("1")) {
                    return ResponseBean.responseError(ajax.getInfo().toString());
                }
            }


            return ResponseBean.responseSuccess("批量导入成功").addData("successImport", successImport).addData("alreadyExit", alreadyExit);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("批量导入失败");
        }
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
        //Map<String,Object> map = new HashMap<String,Object>();
        //map.put("companyStatus", companyStatus);
        map.put("cid", map.get("appOrgId"));
        map.put("isAudit", "1");//表示只查询待审核的且只是自己审核的数据
        map.put("auditStatus", 2);
        map.put("fastdfsUrl", this.fastdfsUrl);
        List<CompanyRelationAuditDTO> relationAuditList = companyRelationAuditService.getCompanyRelationAuditByParam(map);
//        for(CompanyRelationAuditDTO companyRelationAuditDTO :relationAuditList){
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("companyId", companyRelationAuditDTO.getOrgId());
//            param.put("fileType", "4");
//            List<CompanyAttachEntity> list = companyAttachService.getCompanyAttachByParamer(param);
//            if (list.size()>0) {
//                companyRelationAuditDTO.setFilePath(fastdfsUrl+list.get(0).getFileGroup()+"/"+list.get(0).getFilePath());
//                companyRelationAuditDTO.setFileGroup(list.get(0).getFileGroup());
//            }
//        }

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
//        Map<String, Object> map = new HashMap<String, Object>();
//        if(null != paraMap.get("keyword")){
//            map.put("keyword", paraMap.get("keyword"));
//        }
//        if(null != paraMap.get("pageSize") && null != paraMap.get("pageNumber")){
//            map.put("pageSize", paraMap.get("pageSize"));
//            map.put("pageNumber", paraMap.get("pageNumber"));
//        }
//        if(null != paraMap.get("appOrgId")){
//            map.put("orgId", paraMap.get("appOrgId"));
//        }
//
//        List<CompanyDTO> orgList = companyService.getCompanyByUserId(paraMap.get("accountId").toString());
//        String orgIdList = "";
//        for(int i = 0 ; i < orgList.size();i++){
//            if(i==0){
//                orgIdList = orgList.get(i).getId();
//            }else{
//                orgIdList = orgIdList + "," +orgList.get(i).getId();
//            }
//        }
//        if(orgIdList.length()>0){
//            map.put("orgIdList",orgIdList.split(","));
//        }
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
        List<DepartDTO> departList = departService.getDepartByCompanyId(map);
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


            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("companyId", companyId);
            String roleCodes = permissionService.getPermissionCodeByUserId(param);
            interfaceGroupAndRoleDTO.setRoleCodes(roleCodes);
            interfaceGroupAndRoleDTOList.add(interfaceGroupAndRoleDTO);
          /* List<RoleEntity> roleList = roleService.getRoleByUser(userId,companyId);
            if(roleList !=null){
                List<InterfaceRoleDTO> interfaceRoleDTOList =  BaseDTO.copyFields(roleList,InterfaceRoleDTO.class);
                interfaceGroupAndRoleDTO.setInterfaceRoleDTOList(interfaceRoleDTOList);
                interfaceGroupAndRoleDTOList.add(interfaceGroupAndRoleDTO);
            }else{
                List<InterfaceRoleDTO> interfaceRoleDTOList = new ArrayList<>();
                interfaceGroupAndRoleDTO.setInterfaceRoleDTOList(interfaceRoleDTOList);
                interfaceGroupAndRoleDTOList.add(interfaceGroupAndRoleDTO);
            }*/

        }
        return responseSuccess()
                .addData("interfaceGroupAndRoleDTOList", interfaceGroupAndRoleDTOList);
    }

    /**
     * 方法描述：组织架构树(数据）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/newGetcompanyDatabac")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean newGetcompanyDatabac(@RequestBody Map<String, Object> mapass) throws Exception {
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(mapass.get("accountId").toString());
        //所在的公司id
        String inCompanyIds = "";
        int i = 0;
        for (CompanyDTO companyDto : orgList) {
            if (i == 0) {
                inCompanyIds = companyDto.getId();
            } else {
                inCompanyIds = inCompanyIds + "," + companyDto.getId();
            }
            i++;
        }
        String[] rootIdsAttr = inCompanyIds.split(",");
        //返回数据
        List<Object> returnList = new ArrayList<>();
        //已经存在的公司
        String exitCompanyIds = "";

        for (String rId : rootIdsAttr) {
            if (!exitCompanyIds.contains(rId)) {
                //根公司
                CompanyEntity companyEntity = companyService.selectById(rId);
                //所有子公司
                List<CompanyEntity> list = companyService.getAllChilrenCompany(rId);
                list.add(0, companyEntity);
                List<CompanyDTO> listDto = BaseDTO.copyFields(list, CompanyDTO.class);
                //查询权限
                for (CompanyDTO dto : listDto) {
                    //加入到存在的公司
                    exitCompanyIds = exitCompanyIds + "," + dto.getId();
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("userId", mapass.get("accountId"));
                    param.put("companyId", dto.getId());
                    String roleCodes = permissionService.getPermissionCodeByUserId(param);
                    //设置权限
                    dto.setRoleCodes(roleCodes);
                    if (inCompanyIds.contains(dto.getId())) {
                        //1：在公司，0不在公司
                        dto.setIsInCompanyFlag(1);
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orgId", dto.getId());
                    map.put("auditStatus", "1");
                    //公司用户
                    List<CompanyUserTableDTO> data = companyUserService.getCompanyUserByOrgIdOfAdmin(map);
                    //设置公司用户数量
                    dto.setCompanyUserNum(data.size());
                }
                returnList.add(listDto);
            }
        }
        return responseSuccess().addData("companyData", returnList);
    }


    /****************************************v2*****************************************************/

    /**
     * 方法描述：组织架构树(数据）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/newGetcompanyData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean newGetcompanyData(@RequestBody Map<String, Object> mapass) throws Exception {
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(mapass.get("accountId").toString());
        //所有的根的公司
        String rootIds = "";
        //根公司id临时变量
        String rootId = "";
        //所在的公司id
        String inCompanyIds = "";
        for (int i = 0; i < orgList.size(); i++) {
            if (i == 0) {
                rootId = companyService.getRootCompanyId(orgList.get(i).getId());
                rootIds = rootId;
                inCompanyIds = orgList.get(i).getId();
            } else {

                rootId = companyService.getRootCompanyId(orgList.get(i).getId());
                inCompanyIds = inCompanyIds + "," + orgList.get(i).getId();
                if (!rootIds.contains(rootId)) {
                    rootIds = rootIds + "," + rootId;
                }
            }
        }
        String[] rootIdsAttr = rootIds.split(",");
        //返回数据
        List<Object> returnList = new ArrayList<>();
        for (String rId : rootIdsAttr) {
            //根公司
            CompanyEntity companyEntity = companyService.selectById(rId);
            //所有子公司
            List<CompanyEntity> list = companyService.getAllChilrenCompany(rId);
            list.add(0, companyEntity);
            List<CompanyDTO> listDto = BaseDTO.copyFields(list, CompanyDTO.class);

            //公司，分公司
            List<CompanyDTO> companyFilialeList = new ArrayList<>();
            //排序，把事业合伙人放到最后
            List<CompanyDTO> businessPartnerList = new ArrayList<>();
            //查询权限
            for (CompanyDTO dto : listDto) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("userId", mapass.get("accountId"));
                param.put("companyId", dto.getId());
                String roleCodes = permissionService.getPermissionCodeByUserId(param);
                //设置权限
                dto.setRoleCodes(roleCodes);
                if (inCompanyIds.contains(dto.getId())) {
                    //1：在公司，0不在公司
                    dto.setIsInCompanyFlag(1);
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orgId", dto.getId());
                map.put("auditStatus", "1");
                //公司用户
                List<CompanyUserTableDTO> data = companyUserService.getCompanyUserByOrgIdOfAdmin(map);
                //设置公司用户数量
                dto.setCompanyUserNum(data.size());

                if ("3".equals(dto.getCompanyType())) {
                    businessPartnerList.add(dto);
                } else {
                    companyFilialeList.add(dto);
                }
            }
            //拼在一起
            companyFilialeList.addAll(businessPartnerList);
            returnList.add(companyFilialeList);
        }
        return responseSuccess().addData("companyData", returnList);
    }

    /**
     * 方法描述：组织架构树(数据）
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getV2CompanyData")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getV2CompanyData(@RequestBody Map<String, Object> mapass) throws Exception {
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(mapass.get("accountId").toString());
        //所有的根的公司
        String rootIds = "";
        //根公司id临时变量
        String rootId = "";
        //所在的公司id
        String inCompanyIds = "";
        for (int i = 0; i < orgList.size(); i++) {
            if (i == 0) {
                rootId = companyService.getRootCompanyId(orgList.get(i).getId());
                rootIds = rootId;
                inCompanyIds = orgList.get(i).getId();
            } else {

                rootId = companyService.getRootCompanyId(orgList.get(i).getId());
                inCompanyIds = inCompanyIds + "," + orgList.get(i).getId();
                if (!rootIds.contains(rootId)) {
                    rootIds = rootIds + "," + rootId;
                }
            }
        }
        String[] rootIdsAttr = rootIds.split(",");
        //返回数据
        List<Object> returnList = new ArrayList<>();
        for (String rId : rootIdsAttr) {
            //根公司
            CompanyEntity companyEntity = companyService.selectById(rId);
            //所有子公司
            List<CompanyEntity> list = companyService.getAllChilrenCompany(rId);
            list.add(0, companyEntity);
            List<CompanyDTO> listDto = BaseDTO.copyFields(list, CompanyDTO.class);

            //公司，分公司
            List<CompanyDTO> companyFilialeList = new ArrayList<>();
            //排序，把事业合伙人放到最后
            List<CompanyDTO> businessPartnerList = new ArrayList<>();
            //查询权限
            for (CompanyDTO dto : listDto) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("userId", mapass.get("accountId"));
                param.put("companyId", dto.getId());
                String roleCodes = permissionService.getPermissionCodeByUserId(param);
                //设置权限
                dto.setRoleCodes(roleCodes);
                if (inCompanyIds.contains(dto.getId())) {
                    //1：在公司，0不在公司
                    dto.setIsInCompanyFlag(1);
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orgId", dto.getId());
                map.put("auditStatus", "1");
                //公司用户
                List<CompanyUserTableDTO> data = companyUserService.getCompanyUserByOrgIdOfAdmin(map);
                //设置公司用户数量
                dto.setCompanyUserNum(data.size());

                if ("3".equals(dto.getCompanyType())) {
                    businessPartnerList.add(dto);
                } else {
                    companyFilialeList.add(dto);
                }
            }
            //拼在一起
            companyFilialeList.addAll(businessPartnerList);
            returnList.add(companyFilialeList);
        }
        return responseSuccess().addData("companyData", returnList);
    }

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
    @AuthorityCheckable
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
        List<AccountDTO> list = accountService.selectV2AllPersonByParam(dto);
        return responseSuccess().addData("list", list);
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
     * @param:
     * @return:
     */
    @RequestMapping("/getCompanyUserForSelect")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyUserForSelect(@RequestBody Map<String, Object> map) throws Exception {
        List<CompanyUserAppDTO> userList = this.companyUserService.getCompanyUser((String) map.get("appOrgId"));
        if ("1".equals(map.get("type"))) {
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

        Map<String, Object> companyAttachMap = new HashMap<>();
        companyAttachMap.put("type", NetFileType.COMPANY_BANNER_ATTACH);
        companyAttachMap.put("companyId", map.get("appOrgId").toString());
        List<ProjectSkyDriveEntity> list = projectSkyDriverService.getNetFileByParam(companyAttachMap);
        for (ProjectSkyDriveEntity netFile : list) {
            if (!StringUtil.isNullOrEmpty(netFile.getFilePath())) {
                netFile.setFilePath(fastdfsUrl + netFile.getFileGroup() + "/" + netFile.getFilePath());
            }
        }
        return ResponseBean.responseSuccess("查询成功")
                .addData("companyAttachList", list)
                .addData("companyInfo", dto)
                .addData(paraMap);
    }

    /**
     * 方法描述：组织架构树(数据）通知公告使用
     * 作        者：MaoSF
     * 日        期：2016年7月11日-上午11:58:59
     */
    @RequestMapping("/getOrgTreeForNotice")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getOrgTreeForNotice(@RequestBody Map<String, Object> mapPara) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyId", mapPara.get("appOrgId"));
        OrgTreeDTO dataMap = companyService.v2GetOrgTreeForNotice(map);
        return ResponseBean.responseSuccess("查询成功")
                .addData("orgTree", dataMap);
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
     * @param:
     * @return:
     */
    @RequestMapping("/inviteParent")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean inviteParent(@RequestBody InvitatParentDTO dto) throws Exception {
        return this.companyService.inviteParent(dto);
    }


    /**
     * 方法描述：设置事业合伙人的别名
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:
     * @return:
     */
    @RequestMapping("/setBusinessPartnerNickName")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean setBusinessPartnerNickName(@RequestBody BusinessPartnerDTO dto) throws Exception {
        return this.companyService.setBusinessPartnerNickName(dto);
    }

    /**
     * 方法描述：获取与当前组织相关的组织人员（本公司、分公司、事业合伙人）
     * 作者：GuoZhiBin
     * 日期：2017/4/1
     * @param:
     * @return:
     */
    @RequestMapping("/getCompanyUserForCustomGroup")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getCompanyUserForCustomGroup(@RequestBody Map<String, Object> param) throws Exception {
        List<OrgUserForCustomGroupDTO> returnData = this.companyService.getCompanyForCustomGroupSelect((String) param.get("appOrgId"), (String) param.get("accountId"));
        return ResponseBean.responseSuccess("查询成功").addData("companyUserList", returnData);
    }


}