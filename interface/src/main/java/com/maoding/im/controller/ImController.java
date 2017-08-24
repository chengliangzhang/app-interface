//package com.maoding.im.controller;
//
//import com.maoding.core.base.dto.BaseDTO;
//import com.maoding.core.bean.ResponseBean;
//import com.maoding.core.constant.SystemParameters;
//import com.maoding.core.util.StringUtil;
//import com.maoding.im.dto.BatchAddMemberDTO;
//import com.maoding.im.dto.ImGroupCustomerDTO;
//import com.maoding.im.dto.ImGroupCustomerUserDTO;
//import com.maoding.im.dto.ImGroupDTO;
//import com.maoding.im.entity.*;
//import com.maoding.im.service.ProducerService;
//import com.maoding.org.dto.CompanyUserTableDTO;
//import com.maoding.org.entity.CompanyEntity;
//import com.maoding.org.entity.CompanyUserEntity;
//import com.maoding.org.entity.DepartEntity;
//import com.maoding.org.service.CompanyService;
//import com.maoding.org.service.CompanyUserService;
//import com.maoding.org.service.DepartService;
//import com.maoding.role.entity.RoleEntity;
//import com.maoding.role.service.RoleService;
//import com.maoding.system.annotation.AuthorityCheckable;
//import com.maoding.system.controller.BaseWSController;
//import com.maoding.user.dto.AccountDTO;
//import com.maoding.user.entity.AccountEntity;
//import com.maoding.user.entity.UserAttachEntity;
//import com.maoding.user.entity.UserEntity;
//import com.maoding.user.service.AccountService;
//import com.maoding.user.service.UserAttachService;
//import com.maoding.user.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.jms.Destination;
//import java.util.*;
//
//import static com.maoding.core.util.MapUtil.objectMap;
//
///**
// * Created by Idccapp21 on 2016/8/1.
// */
//@Controller
//@RequestMapping("/im")
//public class ImController extends BaseWSController {
//
//    @Autowired
//    public UserAttachService userAttachService;
//    @Autowired
//    public UserService userService;
//    @Autowired
//    private ImChatHistoryService imChatHistoryService;
//    @Autowired
//    private ImGroupService imGroupService;
//    @Autowired
//    private DepartService departService;
//    @Autowired
//    private AccountService accountService;
//    @Autowired
//    private ProducerService producerService;
//    @Autowired
//    @Qualifier("createGroupDestination")
//    private Destination createGroupDestination;
//    @Autowired
//    @Qualifier("updateGroupDestination")
//    private Destination updateGroupDestination;
//    @Autowired
//    @Qualifier("removeGroupDestination")
//    private Destination removeGroupDestination;
//    @Autowired
//    @Qualifier("createCompanyUserDestination")
//    private Destination createCompanyUserDestination;
//    @Autowired
//    @Qualifier("removeCompanyUserDestination")
//    private Destination removeCompanyUserDestination;
//
//    @Autowired
//    @Qualifier("createUserDestination")
//    private Destination createUserDestination;
//
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private CompanyUserService companyUserService;
//    @Autowired
//    private CompanyService companyService;
//
//    /**
//     * 方法描述：获取聊天记录
//     * 作        者：Chenxj
//     * 日        期：2016年5月30日-上午10:35:23
//     */
//    @RequestMapping("/addOneLevelDepartGroup")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean addOneLevelDepartGroup(@RequestBody Map<String, Object> map) throws Exception {
//        String departId = (String) map.get("departId");
//        String departName = (String) map.get("departName");
//        String userId = (String) map.get("userId");
//        companyUserService.addOneLevelDepartGroup(departId, departName, userId);
//        return responseSuccess();
//    }
//
//    /**
//     * 方法描述：群组修改接口
//     * 作        者：Chenxj
//     * 日        期：2016年5月31日-上午11:18:00
//     */
//    @RequestMapping("/im_group_edit")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imGroupEdit(@RequestBody ImGroupDTO imGroupDTO) throws Exception {
//        ImGroupEntity entity = new ImGroupEntity();
//        BaseDTO.copyFields(imGroupDTO, entity);
//        if (imGroupDTO.getOrgId() != null) {
//            String orgId = imGroupDTO.getOrgId();
//            List<ImGroupEntity> ls = imGroupService.selectByParameter(objectMap("orgId", orgId));
//            if (ls != null && ls.size() > 0) {
//                ImGroupEntity e = ls.get(0);
//                entity.setId(e.getId());
//                entity.setOrgId(e.getOrgId());
//                entity.setGroupId(e.getGroupId());
//            } else {
//                return ResponseBean.responseError("群信息修改失败");
//            }
//        }
//        int i = imGroupService.saveOrUpdate(entity);
//        if (i > 0) {
//            ImGroupEntity group = new ImGroupEntity();
//            group.setOrgId(entity.getOrgId());
//            group.setName(entity.getName());
//            group.setImg(entity.getImg());
//            imGroupService.updateGroup(group);
//            return ResponseBean.responseSuccess();
//        } else {
//            return ResponseBean.responseError("群信息修改失败");
//        }
//    }
//
//    /**
//     * 方法描述：群组信息接口(公司群)
//     * 作        者：Chenxj
//     * 日        期：2016年6月6日-上午11:33:01
//     */
//    @RequestMapping("/old_im_group_info")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean oldImGroupInfo(@RequestBody Map<String, Object> map) throws Exception {
//        //部门
//        if ("1".equals(map.get("type"))) {
//            Map<String, Object> mapDepart = new HashMap<>();
//            mapDepart.put("url", fastdfsUrl);
//            mapDepart.put("groupId", map.get("groupId"));
//            List<ImGroupEntity> departGroupList = imGroupService.selectDepartGroupList(mapDepart);
//            if (departGroupList != null && departGroupList.size() > 0) {
//                ImGroupEntity et = departGroupList.get(0);
//                List<Map<String, Object>> listDepartMemebers = imGroupService.selectDepartGroupMembers(objectMap("orgId", et.getOrgId(), "url", fastdfsUrl));
//                //查询所有子部门
//                List<DepartEntity> listChild = departService.selectChildDepartEntity(et.getOrgId() + "-");
//                for (DepartEntity de : listChild) {
//                    List<Map<String, Object>> listDepartChildMemebers = new ArrayList<>();
//                    listDepartChildMemebers = imGroupService.selectDepartGroupMembers(objectMap("orgId", de.getId(), "url", fastdfsUrl));
//                    for (Map<String, Object> child : listDepartChildMemebers) {
//                        listDepartMemebers.add(child);
//                    }
//                }
//                et.setMemberInfo(listDepartMemebers);
//                et.setMembers(listDepartMemebers.size());
//                for (Map<String, Object> mapMember : listDepartMemebers) {
//                    List<RoleEntity> roleEntitiesList = roleService.getRoleByUser((String) mapMember.get("userId"), map.get("appOrgId").toString());
//                    mapMember.put("roleList", roleEntitiesList);
//                }
//                return responseSuccess()
//                        .addDataFromObject(et);
//            } else {
//                return responseSuccess()
//                        .addDataFromObject(new ImGroupEntity());
//            }
//        } else {
//            map.put("url", fastdfsUrl);
//            map.put("userId", map.get("accountId"));
//            List<ImGroupEntity> ls = imGroupService.selectByParameter(map);
//            if (ls != null && ls.size() > 0) {
//                ImGroupEntity et = ls.get(0);
//                List<Map<String, Object>> members = imGroupService.selectGroupMembers(objectMap(
//                        "orgId", et.getOrgId(),
//                        "url", fastdfsUrl));
//                et.setMemberInfo(members);
//                for (Map<String, Object> mapMember : members) {
//                    List<RoleEntity> roleEntitiesList = roleService.getRoleByUser((String) mapMember.get("userId"), et.getOrgId());
//                    mapMember.put("roleList", roleEntitiesList);
//                }
//                return responseSuccess()
//                        .addDataFromObject(et);
//            } else {
//                return responseSuccess()
//                        .addDataFromObject(new ImGroupEntity());
//            }
//        }
//    }
//
//    /**
//     * 方法描述：群组信息接口(公司群)
//     * 作        者：Chenxj
//     * 日        期：2016年6月6日-上午11:33:01
//     */
//    @RequestMapping("/im_group_info")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imGroupInfo(@RequestBody Map<String, Object> map) throws Exception {
//
//        //部门
//        if ("1".equals(map.get("type"))) {
//            Map<String, Object> mapDepart = new HashMap<>();
//            mapDepart.put("url", fastdfsUrl);
//            mapDepart.put("orgIdGroupId", map.get("orgId"));
//            List<ImGroupEntity> departGroupList = imGroupService.selectDepartGroupList(mapDepart);
//            if (departGroupList != null && departGroupList.size() > 0) {
//                ImGroupEntity et = departGroupList.get(0);
//                DepartEntity departEntity = departService.selectById(et.getOrgId());
//                List<Map<String, Object>> listDepartMemebers = imGroupService.selectNewDepartGroupMembers(objectMap("orgId", et.getOrgId(), "url", fastdfsUrl, "companyId", departEntity.getCompanyId()));
//                et.setMemberInfo(listDepartMemebers);
//                et.setMembers(listDepartMemebers.size());
//                for (Map<String, Object> mapMember : listDepartMemebers) {
//                    List<RoleEntity> roleEntitiesList = roleService.getRoleByUser((String) mapMember.get("userId"), map.get("appOrgId").toString());
//                    mapMember.put("roleList", roleEntitiesList);
//                }
//                return responseSuccess()
//                        .addDataFromObject(et);
//            } else {
//                return responseSuccess()
//                        .addDataFromObject(new ImGroupEntity());
//            }
//        }
//        //公司
//        if ("0".equals(map.get("type"))) {
//            map.put("url", fastdfsUrl);
//            map.put("userId", map.get("accountId"));
//            List<ImGroupEntity> ls = imGroupService.selectByParameter(map);
//            if (ls != null && ls.size() > 0) {
//                ImGroupEntity et = ls.get(0);
//                List<Map<String, Object>> members = imGroupService.selectNewGroupMembers(objectMap(
//                        "orgId", et.getOrgId(),
//                        "url", fastdfsUrl));
//                et.setMemberInfo(members);
//                et.setMembers(members.size());
//                for (Map<String, Object> mapMember : members) {
//                    List<RoleEntity> roleEntitiesList = roleService.getRoleByUser((String) mapMember.get("userId"), et.getOrgId());
//                    mapMember.put("roleList", roleEntitiesList);
//                }
//                return responseSuccess()
//                        .addDataFromObject(et);
//            } else {
//                return responseSuccess()
//                        .addDataFromObject(new ImGroupEntity());
//            }
//        }
//        //项目群
//        if ("3".equals(map.get("type"))) {
//            map.put("url", fastdfsUrl);
//            map.put("orgId", map.get("orgId"));
//            List<ImGroupEntity> ls = imGroupService.selectProjectGroupByParameter(map);
//            if (ls != null && ls.size() > 0) {
//                ImGroupEntity et = ls.get(0);
//                //改变，不加company条件
//                List<Map<String, Object>> members = imGroupService.selectNewGroupMembers(objectMap(
//                        "orgId", et.getOrgId(),
//                        "url", fastdfsUrl,
//                        "projectGroup", "projectGroup"));
//                et.setMemberInfo(members);
//                et.setMembers(members.size());
//
//                for (Map<String, Object> mapMember : members) {
//                    List<RoleEntity> roleEntitiesList = roleService.getRoleByUser((String) mapMember.get("userId"), map.get("appOrgId").toString());
//                    mapMember.put("roleList", roleEntitiesList);
//                }
//                return responseSuccess()
//                        .addDataFromObject(et);
//            } else {
//                return responseSuccess()
//                        .addDataFromObject(new ImGroupEntity());
//            }
//        }
//        return responseError("查询失败");
//    }
//
//    /**
//     * 方法描述：查询用户信息
//     * 作        者：Chenxj
//     * 日        期：2016年6月3日-上午11:57:29
//     */
//    @RequestMapping("/im_user_info")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imUserInfo(@RequestBody Map<String, Object> map) throws Exception {
//        map.put("userId", map.get("accountId"));
//        UserEntity personal = userService.selectById(map.get("uid"));
//        String headImg = "";
//        if (personal == null) {
//            personal = new UserEntity();
//        } else {
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("userId", map.get("uid"));
//            param.put("attachType", "5");//个人头像
//            List<UserAttachEntity> userAttachList = userAttachService.getAttachByType(param);
//            headImg = userAttachList.size() > 0 ? (fastdfsUrl + userAttachList.get(0).getFileGroup() + "/" + userAttachList.get(0).getAttachPath()) : "";
//        }
//        return responseSuccess()
//                .addData("name", personal.getUserName())
//                .addData("img", headImg)
//                .addData("sex", personal.getSex());
//    }
//
//    /**
//     * 方法描述：会话列表
//     * 作        者：Chenxj
//     * 日        期：2016年6月6日-下午4:11:31
//     */
//    @RequestMapping("/im_session_list")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imSessionList(@RequestBody Map<String, String> map) throws Exception {
//        map.put("userId", map.get("accountId"));
//        List<Map<String, Object>> ls = imChatHistoryService.selectSessionListByUserId(map.get("userId"));
//        if (ls != null) {
//            return responseSuccess()
//                    .addList(ls);
//        } else {
//            return responseSuccess()
//                    .addList(new ArrayList<Map<String, Object>>());
//        }
//    }
//
//    /**
//     * 方法描述：文件列表
//     * 作        者：Chenxj
//     * 日        期：2016年6月6日-下午5:48:27
//     */
//    @RequestMapping("/im_file_list")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imFileList(@RequestBody Map<String, Object> map) throws Exception {
//        map.put("userId", map.get("accountId"));
//        List<Map<String, Object>> ls = imChatHistoryService.selectFileListByParameter(map);
//        return responseSuccess()
//                .addList(ls);
//    }
//
//    /**
//     * 方法描述：获取群组列表
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("/im_group_list")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean imGroupList(@RequestBody Map<String, Object> map) throws Exception {
//        List<ImGroupEntity> ls = imGroupService.getImGroupEntityByGroupType(map);
//        return responseSuccess().addList(ls);
//    }
//
//    /**
//     * 方法描述：新增自定义群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("saveImgroup")
//    @ResponseBody
//    public ResponseBean saveImgroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
//        try {
//            //新增
//            if (null == dto.getOrgId()) {
//                AccountEntity account = accountService.selectById(dto.getAccountId());
//                if (null == account) {
//                    return ResponseBean.responseError("群组不存在系统中,操作失败!");
//                }
//                ImUserEntity imUserEntity = new ImUserEntity();
//                imUserEntity.setFlag("0");
//                imUserEntity.setId(account.getId());
//                imUserEntity.setUserName(account.getUserName());
//                imUserEntity.setPassword(account.getPassword());
//                try {
//                    producerService.sendUserMessage(createUserDestination, imUserEntity);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                ImGroupEntity group = new ImGroupEntity();
//                String id = StringUtil.buildUUID();
//                group.setOrgId(id);
//                group.setAdmin(dto.getAccountId());
//                group.setName(dto.getName());
//                group.setGroupType(2);
//                int num = new Random().nextInt(6);
//                String img = SystemParameters.DEPART_DEFAULT_IMG[num];
//                group.setImg(img);
//                producerService.sendGroupMessage(createGroupDestination, group);
//                List<ImGroupCustomerUserDTO> list = dto.getImGroupCustomerUserList();
//                for (int i = 0; i < list.size(); i++) {
//                    ImGroupCustomerUserDTO imGroupCustomerUserDTO = list.get(i);
//                    ImCompanyUserEntity imCompanyUserEntity = new ImCompanyUserEntity();
//                    imCompanyUserEntity.setCompanyId(id);
//                    imCompanyUserEntity.setUserId(imGroupCustomerUserDTO.getAccountId());
//                    producerService.sendCompanyUserMessage(createCompanyUserDestination, imCompanyUserEntity);
//                }
//                //修改
//            } else {
//                ImGroupEntity group = new ImGroupEntity();
//                group.setOrgId(dto.getOrgId());
//                group.setName(dto.getName());
//                group.setImg(dto.getImg());
//                producerService.sendGroupMessage(updateGroupDestination, group);
//            }
//        } catch (Exception ex) {
//            return ResponseBean.responseError("自定义群组操作失败");
//        }
//        return ResponseBean.responseSuccess("自定义群组操作成功");
//    }
//
//    /**
//     * 方法描述：修改自定义群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("updateImgroup")
//    @ResponseBody
//    public ResponseBean updateImgroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
//        try {
//            ImGroupEntity group = new ImGroupEntity();
//            group.setOrgId(dto.getOrgId());
//            group.setName(dto.getName());
//            if (null != dto.getImg()) {
//                group.setImg(dto.getImg());
//            }
//            producerService.sendGroupMessage(updateGroupDestination, group);
//
//        } catch (Exception ex) {
//            return ResponseBean.responseError("自定义群组操作失败");
//        }
//        return ResponseBean.responseSuccess("自定义群组操作成功");
//    }
//
//    /**
//     * 方法描述：查询自定义群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("getImGroups")
//    @ResponseBody
//    public ResponseBean getImGroups(@RequestBody Map<String, Object> map) throws Exception {
//
//        List<ImGroupEntity> list = imGroupService.getImGroupsByParam(map);
//        if (null == list) {
//            list = new ArrayList<>();
//        }
//        return responseSuccess().addData("imGroupsList", list);
//    }
//
//    /**
//     * 方法描述：查询创建失败群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("getErrorImGroups")
//    @ResponseBody
//    public ResponseBean getErrorImGroups(@RequestBody Map<String, Object> map) throws Exception {
//
//        List<ImGroupErrorEntity> list = imGroupService.getErrorImGroupsByParam(map);
//        if (null == list) {
//            list = new ArrayList<>();
//        }
//        return responseSuccess().addData("errorImGroupsList", list);
//    }
//
//    /**
//     * 方法描述：查询群组成员
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("getImGroupsMembers")
//    @ResponseBody
//    public ResponseBean getImGroupsMembers(@RequestBody Map<String, Object> map) throws Exception {
//
//        List<ImGroupUserEntity> list = imGroupService.getImGroupsMembersByParam(map);
//        if (null == list) {
//            list = new ArrayList<>();
//        }
//        return responseSuccess().addData("imGroupsMembersList", list);
//    }
//
//    /**
//     * 方法描述：查询群组失败成员
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("getImGroupsErrorMembers")
//    @ResponseBody
//    public ResponseBean getImGroupsErrorMembers(@RequestBody Map<String, Object> map) throws Exception {
//
//        List<ImGroupErrorUserEntity> list = imGroupService.getImGroupsErrorMembersByParam(map);
//        if (null == list) {
//            list = new ArrayList<>();
//        }
//        return responseSuccess().addData("imGroupsErrorMembersList", list);
//    }
//
//    /**
//     * 方法描述：删除自定义群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("deleteImgroup")
//    @ResponseBody
//    public ResponseBean deleteImgroup(@RequestBody ImGroupDTO dto) throws Exception {
//        //删除群组
//        ImGroupEntity imGroupEntity = new ImGroupEntity();
//        imGroupEntity.setOrgId(dto.getOrgId());
//        try {
//            producerService.sendGroupMessage(removeGroupDestination, imGroupEntity);
//        } catch (Exception e) {
//            return ResponseBean.responseError("群组删除失败");
//        }
//
//        return ResponseBean.responseSuccess("群组删除成功");
//    }
//
//    /**
//     * 方法描述：批量添加人员到群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("batchAddmemberImgroup")
//    @ResponseBody
//    public ResponseBean batchAddmemberImgroup(@RequestBody BatchAddMemberDTO dto) throws Exception {
//        List<AccountDTO> list = dto.getAccountList();
//        for (AccountDTO accountDTO : list) {
//            ImCompanyUserEntity imCompanyUserEntity = new ImCompanyUserEntity();
//            imCompanyUserEntity.setCompanyId(dto.getOrgId());
//            imCompanyUserEntity.setUserId(accountDTO.getAccountId());
//            producerService.sendCompanyUserMessage(createCompanyUserDestination, imCompanyUserEntity);
//        }
//        return ResponseBean.responseSuccess("添加成员操作成功");
//    }
//
//    /**
//     * 方法描述：添加人员到群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("addmemberImgroup")
//    @ResponseBody
//    public ResponseBean addmemberImgroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            ImCompanyUserEntity imCompanyUserEntity = new ImCompanyUserEntity();
//            imCompanyUserEntity.setCompanyId(dto.getOrgId());
//            imCompanyUserEntity.setUserId(dto.getAccountId());
//            producerService.sendCompanyUserMessage(createCompanyUserDestination, imCompanyUserEntity);
//        } catch (Exception e) {
//            return ResponseBean.responseError("添加成员操作失败");
//        }
//        return ResponseBean.responseSuccess("添加成员操作成功");
//    }
//
//    /**
//     * 方法描述：从群组中把人删除掉
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("deletememberImgroup")
//    @ResponseBody
//    public ResponseBean deletememberImgroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            ImCompanyUserEntity imCompanyUserEntity = new ImCompanyUserEntity();
//            imCompanyUserEntity.setCompanyId(dto.getOrgId());
//            imCompanyUserEntity.setUserId(dto.getAccountId());
//            producerService.sendCompanyUserMessage(removeCompanyUserDestination, imCompanyUserEntity);
//        } catch (Exception e) {
//            return ResponseBean.responseError("删除成员失败");
//        }
//        return ResponseBean.responseSuccess("删除成员成功");
//    }
//
//    /**
//     * 方法描述：批量踢人
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("batchDeletememberImgroup")
//    @ResponseBody
//    public ResponseBean batchDeletememberImgroup(@RequestBody BatchAddMemberDTO dto) throws Exception {
//        List<AccountDTO> list = dto.getAccountList();
//        for (AccountDTO accountDTO : list) {
//            ImCompanyUserEntity imCompanyUserEntity = new ImCompanyUserEntity();
//            imCompanyUserEntity.setCompanyId(dto.getOrgId());
//            imCompanyUserEntity.setUserId(accountDTO.getAccountId());
//            producerService.sendCompanyUserMessage(removeCompanyUserDestination, imCompanyUserEntity);
//        }
//        return ResponseBean.responseSuccess("删除成员成功");
//    }
//
//    /**
//     * 方法描述：转让群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("updategroup_owner")
//    @ResponseBody
//    public ResponseBean updateGroupOwner(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            imGroupService.updateGroupOwner(dto);
//        } catch (Exception e) {
//            return ResponseBean.responseError("转让群组失败");
//        }
//        return ResponseBean.responseSuccess("转让群组成功");
//    }
//
//    /**
//     * 方法描述：创建公司群，(20161126检查)
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("createCompanyGroup")
//    @ResponseBody
//    public ResponseBean createCompanyGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            if (null != dto.getCompanyId()) {
//                String companyId = dto.getCompanyId();
//                CompanyEntity c = companyService.selectById(companyId);
//                //查询是否存在群组
//                List<ImGroupEntity> ls = imGroupService.selectByParameter(objectMap("orgId", companyId));
//
//                List<CompanyUserEntity> lsUsers = companyUserService.getCompanyUserByCompanyId(companyId);
//                String groupId = "";
//                if (ls.size() == 0 && lsUsers.size() > 0) {
//                    groupId = companyUserService.createCompanyGroup(c, lsUsers.get(0).getUserId());
//                }
//
//            } else {
//                //没有创建成功的生效的公司
//                List<CompanyEntity> listCompanyEntity = companyService.getAllCompanyIm();
//                for (CompanyEntity c : listCompanyEntity) {
//                    String companyId = c.getId();
//                    //查询是否存在群组
//                    List<ImGroupEntity> ls = imGroupService.selectByParameter(objectMap("orgId", companyId));
//
//                    List<CompanyUserEntity> lsUsers = companyUserService.getCompanyUserByCompanyId(companyId);
//                    String groupId = "";
//                    if (ls.size() == 0 && lsUsers.size() > 0) {
//                        groupId = companyUserService.createCompanyGroup(c, lsUsers.get(0).getUserId());
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            return ResponseBean.responseError("创建公司群失败");
//        }
//        return ResponseBean.responseSuccess("创建公司群成功");
//    }
//
//
//    /**
//     * 方法描述：加人到公司群，
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("addUserCompanyGroup")
//    @ResponseBody
//    public ResponseBean addUserCompanyGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            if (null != dto.getCompanyId()) {
//                String companyId = dto.getCompanyId();
//                CompanyEntity c = companyService.selectById(companyId);
//                List<ImGroupEntity> ls = imGroupService.selectByParameter(objectMap("orgId", companyId));
//                List<CompanyUserEntity> lsUsers = companyUserService.getCompanyUserByCompanyId(companyId);
//                if (ls.size() == 1 && lsUsers.size() > 0) {
//                    for (int j = 0; j < lsUsers.size(); j++) {
//                        companyUserService.addUserToGroup(lsUsers.get(j).getUserId(), null, companyId);
//                    }
//                }
//
//            } else {
//                //获取所有创建成功公司群的公司
//                List<CompanyEntity> listCompanyEntity = companyService.getImAllCompany();
//                for (CompanyEntity c : listCompanyEntity) {
//                    String companyId = c.getId();
//                    List<ImGroupEntity> ls = imGroupService.selectByParameter(objectMap("orgId", companyId));
//                    List<CompanyUserEntity> lsUsers = companyUserService.getCompanyUserByCompanyId(companyId);
//                    if (ls.size() == 1 && lsUsers.size() > 0) {
//                        for (int j = 0; j < lsUsers.size(); j++) {
//                            companyUserService.addUserToGroup(lsUsers.get(j).getUserId(), null, companyId);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseBean.responseError("加人到公司群失败");
//        }
//        return ResponseBean.responseSuccess("加人到公司群成功");
//    }
//
//    /**
//     * 方法描述：处理失败群组
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("handleErrorGroup")
//    @ResponseBody
//    public ResponseBean handleErrorGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            List<ImGroupErrorEntity> listImGroupErrorGroups = companyUserService.selectImGroupErrorGroup();
//            for (ImGroupErrorEntity entity : listImGroupErrorGroups) {
//                if (0 == entity.getGroupType()) {
//                    companyUserService.createGroup(entity.getOrgId(), entity.getUserId(), 0);
//                } else if (1 == entity.getGroupType()) {
//                    companyUserService.createGroup(entity.getOrgId(), entity.getUserId(), 1);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseBean.responseError("处理失败人员(加群失败)失败");
//        }
//        return ResponseBean.responseSuccess("处理失败人员(加群失败)成功");
//    }
//
//
//    /**
//     * 方法描述：处理失败人员(加群失败)
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("addErrorUserToGroup")
//    @ResponseBody
//    public ResponseBean addErrorUserToGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            List<ImGroupErrorUserEntity> listImGroupErrorUsers = companyUserService.selectImGroupErrorUsers();
//            for (ImGroupErrorUserEntity entity : listImGroupErrorUsers) {
//                int flag = imGroupService.addUserToGroup(entity.getUserId(), entity.getOrgId());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseBean.responseError("处理失败人员(加群失败)失败");
//        }
//        return ResponseBean.responseSuccess("处理失败人员(加群失败)成功");
//    }
//
//    /**
//     * 方法描述：创建部门群
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("createOneLevelDepartGroup")
//    @ResponseBody
//    public ResponseBean createOneLevelDepartGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            if (null != dto.getCompanyId()) {
//                String companyId = dto.getCompanyId();
//                CompanyEntity c = companyService.selectById(companyId);
//                //查询没有创建群组的一级部门群
//                Map<String, Object> params = new HashMap<String, Object>();
//                params.put("companyId", companyId);
//                List<DepartEntity> list = departService.selectNotCreateGroupDepart(params);
//                for (DepartEntity departEntity : list) {
//                    params.clear();
//                    params.put("departId", departEntity.getId());
//                    String groupId = null;
//                    //查询部门用户
//                    List<CompanyUserEntity> userList = companyUserService.getUserByDepartId(params);
//                    //查询所有子部门
//                    List<DepartEntity> listChild = departService.selectChildDepartEntity(departEntity.getId() + "-");
//                    for (DepartEntity de : listChild) {
//                        params.clear();
//                        params.put("departId", de.getId());
//                        List<CompanyUserEntity> newUserList = new ArrayList<>();
//                        newUserList = companyUserService.getUserByDepartId(params);
//                        for (CompanyUserEntity cue : newUserList) {
//                            userList.add(cue);
//                        }
//                    }
//
//                    if (userList.size() > 0) {
//                        String userId = userList.get(0).getUserId();
//                        groupId = companyUserService.addOneLevelDepartGroup(departEntity.getId(), departEntity.getDepartName(), userId);
//                    }
//                }
//            } else {
//                //获取所有创建成功公司群的公司
//                List<CompanyEntity> listCompanyEntity = companyService.getImAllCompany();
//                for (CompanyEntity c : listCompanyEntity) {
//                    String companyId = c.getId();
//                    //查询没有创建群组的一级部门群
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    params.put("companyId", companyId);
//                    List<DepartEntity> list = departService.selectNotCreateGroupDepart(params);
//                    for (DepartEntity departEntity : list) {
//                        params.clear();
//                        params.put("departId", departEntity.getId());
//                        String groupId = null;
//                        //查询部门用户
//                        List<CompanyUserEntity> userList = companyUserService.getUserByDepartId(params);
//                        //查询所有子部门
//                        List<DepartEntity> listChild = departService.selectChildDepartEntity(departEntity.getId() + "-");
//                        for (DepartEntity de : listChild) {
//                            params.clear();
//                            params.put("departId", de.getId());
//                            List<CompanyUserEntity> newUserList = new ArrayList<>();
//                            newUserList = companyUserService.getUserByDepartId(params);
//                            for (CompanyUserEntity cue : newUserList) {
//                                userList.add(cue);
//                            }
//                        }
//
//                        if (userList.size() > 0) {
//                            String userId = userList.get(0).getUserId();
//                            groupId = companyUserService.addOneLevelDepartGroup(departEntity.getId(), departEntity.getDepartName(), userId);
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            return ResponseBean.responseError("创建部门群失败");
//        }
//        return ResponseBean.responseSuccess("创建部门群成功");
//    }
//
//    /**
//     * 方法描述：添加人到部门群
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("addUserToOneLevelDepartGroup")
//    @ResponseBody
//    public ResponseBean addUserToOneLevelDepartGroup(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            if (null != dto.getCompanyId()) {
//                String companyId = dto.getCompanyId();
//                CompanyEntity c = companyService.selectById(companyId);
//                //查询创建群组的一级部门群
//                Map<String, Object> params = new HashMap<String, Object>();
//                params.put("companyId", companyId);
//                List<DepartEntity> list = departService.selectCreateGroupDepart(params);
//                for (DepartEntity departEntity : list) {
//                    params.clear();
//                    params.put("departId", departEntity.getId());
//                    String groupId = null;
//                    //查询部门用户
//                    List<CompanyUserEntity> userList = companyUserService.getUserByDepartId(params);
//                    //查询所有子部门
//                    List<DepartEntity> listChild = departService.selectChildDepartEntity(departEntity.getId() + "-");
//                    for (DepartEntity de : listChild) {
//                        params.clear();
//                        params.put("departId", de.getId());
//                        List<CompanyUserEntity> newUserList = new ArrayList<>();
//                        newUserList = companyUserService.getUserByDepartId(params);
//                        for (CompanyUserEntity cue : newUserList) {
//                            userList.add(cue);
//                        }
//                    }
//                    if (userList.size() > 0) {
//                        String userId = userList.get(0).getUserId();
//                        groupId = companyUserService.selectOneLevelDepartGroupId(departEntity.getId(), departEntity.getDepartName(), userId);
//                    }
//                    if (null != groupId && userList.size() > 1) {
//                        for (int j = 0; j < userList.size(); j++) {
//                            System.out.println("userList.get(j).getUserId()-->" + userList.get(j).getUserId() + "===" + groupId);
//                            companyUserService.addUserToGroup(userList.get(j).getUserId(), groupId, departEntity.getId());
//                        }
//
//                    }
//                }
//            } else {
//                //获取所有创建成功公司群的公司
//                List<CompanyEntity> listCompanyEntity = companyService.getImAllCompany();
//                for (CompanyEntity c : listCompanyEntity) {
//                    String companyId = c.getId();
//                    //查询创建群组的一级部门群
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    params.put("companyId", companyId);
//                    List<DepartEntity> list = departService.selectCreateGroupDepart(params);
//                    for (DepartEntity departEntity : list) {
//                        params.clear();
//                        params.put("departId", departEntity.getId());
//                        String groupId = null;
//                        //查询部门用户
//                        List<CompanyUserEntity> userList = companyUserService.getUserByDepartId(params);
//                        //查询所有子部门
//                        List<DepartEntity> listChild = departService.selectChildDepartEntity(departEntity.getId() + "-");
//                        for (DepartEntity de : listChild) {
//                            params.clear();
//                            params.put("departId", de.getId());
//                            List<CompanyUserEntity> newUserList = new ArrayList<>();
//                            newUserList = companyUserService.getUserByDepartId(params);
//                            for (CompanyUserEntity cue : newUserList) {
//                                userList.add(cue);
//                            }
//                        }
//                        if (userList.size() > 0) {
//                            String userId = userList.get(0).getUserId();
//                            groupId = companyUserService.selectOneLevelDepartGroupId(departEntity.getId(), departEntity.getDepartName(), userId);
//                        }
//                        if (null != groupId && userList.size() > 1) {
//                            for (int j = 0; j < userList.size(); j++) {
//                                System.out.println("userList.get(j).getUserId()-->" + userList.get(j).getUserId() + "===" + groupId);
//                                companyUserService.addUserToGroup(userList.get(j).getUserId(), groupId, departEntity.getId());
//                            }
//
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            return ResponseBean.responseError("加人到部门群失败");
//        }
//        return ResponseBean.responseSuccess("加人到部门群成功");
//    }
//
//    /**
//     * 方法描述：处理系统中失败im用户
//     * 作        者：ChenZJ
//     * 日        期：2016年9月13日-下午5:48:27
//     */
//    @RequestMapping("addImUser")
//    @ResponseBody
//    public ResponseBean addImUser(@RequestBody ImGroupDTO dto) throws Exception {
//        try {
//            companyUserService.addImUser();
//        } catch (Exception e) {
//            return ResponseBean.responseError("处理系统中失败im用户失败");
//        }
//        return ResponseBean.responseSuccess("处理系统中失败im用户成功");
//    }
//
//    /**
//     * 方法描述：查询用户信息
//     * 作        者：Chenxj
//     * 日        期：2016年6月3日-上午11:57:29
//     */
//    @RequestMapping("/newImUserInfo")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean newImUserInfo(@RequestBody Map<String, Object> map) throws Exception {
//        map.put("userId", map.get("accountId"));
//        UserEntity personal = userService.selectById(map.get("uid"));
//        String headImg = "";
//        if (personal == null) {
//            personal = new UserEntity();
//        } else {
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("userId", map.get("uid"));
//            param.put("attachType", "5");//个人头像
//            List<UserAttachEntity> userAttachList = userAttachService.getAttachByType(param);
//            headImg = userAttachList.size() > 0 ? (fastdfsUrl + userAttachList.get(0).getFileGroup() + "/" + userAttachList.get(0).getAttachPath()) : "";
//        }
//        CompanyUserTableDTO companyUser = companyUserService.getCompanyUserByIdInterface(map.get("id").toString());
//        return ResponseBean.responseSuccess("查询成功")
//                .addData("name", personal.getUserName())
//                .addData("img", headImg)
//                .addData("sex", personal.getSex())
//                .addData("companyUser", companyUser);
//    }
//
//
//}
