package com.maoding.v2.role.controller;


import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.org.service.DepartService;
import com.maoding.role.dto.*;
import com.maoding.role.service.*;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v2/role")
public class V2RoleController extends BaseWSController {

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private DepartService departService;

    @Autowired
    private UserPermissionService userPermissionService;


    /**
     * 方法描述  获取公司角色
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyRole")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCompanyRole(@RequestBody Map<String, Object> paraMap) throws Exception {
        String companyId = "";
        if (null == paraMap.get("companyId") || "".equals(paraMap.get("companyId"))) {
            companyId = paraMap.get("appOrgId").toString();
        } else {
            companyId = paraMap.get("companyId").toString();
        }
        return responseSuccess().addData("roleList", roleService.getCompanyRole(companyId));
    }

    /**
     * 方法描述：根据查找角色权限列表
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getPermissionList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getPermissionList(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", paraMap.get("roleId"));
        map.put("companyId", paraMap.get("companyId"));
        List<PermissionDTO> permissionList = permissionService.getPermissionByRole(map);

        return responseSuccess().addData("permissionList", permissionList);
    }

    /**
     * 方法描述：根据角色获取人员
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyUserByRoleId")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCompanyUserByRoleId(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", paraMap.get("roleId"));
        map.put("companyId", paraMap.get("companyId"));
        return responseSuccess().addData("companyUserList", companyUserService.getCompanyUserByRoleId(map));
    }

    /**
     * 方法描述：根据权限获取人员
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/getCompanyUserByPermissionId")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCompanyUserByPermissionId(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("permissionId", paraMap.get("permissionId"));
        map.put("companyId", paraMap.get("companyId"));
        return responseSuccess().addData("companyUserList", companyUserService.getCompanyUserByPermissionId(map));
    }


    /**
     * 方法描述：添加自定义角色
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/addOrUptCompanyRole")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean addOrUptCompanyRole(@RequestBody RoleDTO dto) throws Exception {
        String companyId = dto.getCompanyId();
        //当前登录用户Id
        String userId = dto.getAccountId();
        dto.setCompanyId(companyId);
        dto.setAccountId(userId);
        AjaxMessage ajaxMessage = roleService.saveRole(dto);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功").addData("roleObj", dto);
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }


    /**
     * 方法描述：删除自定义角色
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/deletRole")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deletRole(@RequestBody RoleDTO dto) throws Exception {
        AjaxMessage ajaxMessage = roleService.deleteRole(dto.getId(), dto.getCompanyId());
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("删除成功");
        } else {
            return ResponseBean.responseError("删除失败");
        }
    }


    /**
     * 方法描述：保存角色权限
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/saveRolePermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveRolePermission(@RequestBody SaveRolePermissionDTO dto) throws Exception {
        AjaxMessage ajaxMessage = rolePermissionService.saveRolePermission(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：添加成员
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("addRoleMember")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean addRoleMember(@RequestBody SaveRoleUserDTO dto) throws Exception {

        AjaxMessage ajaxMessage = roleUserService.saveOrUserRole(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：删除角色中的成员
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("delRoleMember")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean delRoleMember(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", paraMap.get("companyId"));
        param.put("roleId", paraMap.get("roleId"));
        param.put("userId", paraMap.get("userId"));
        AjaxMessage ajaxMessage = roleUserService.deleteRoleUser(param);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

//    /**
//     * 方法描述：根据角色获取人员
//     * 作    者 : ChenZhuJie
//     * 日    期 : 2016/12/23
//     */
//    @RequestMapping("/getUserPermission")
//     @AuthorityCheckable     @ResponseBody
//    public ResponseBean getUserPermission(@RequestBody Map<String,Object> paraMap)  throws Exception{
//        String userId = String.valueOf(paraMap.get("userId"));
//        String companyId = String.valueOf(paraMap.get("companyId"));
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("userId",userId);
//        map.put("companyId",companyId);
//        List<PermissionDTO> permissionList = permissionService.getPermissionByUserId(map);
//        CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId,companyId);
//        List<RoleEntity> roleList= roleService.getRoleByUser(userId,companyId);
//        String userRoleIds = "";
//        if(null!=roleList && roleList.size()>0){
//            for (int i=0;i<roleList.size();i++){
//                userRoleIds += roleList.get(i).getId()+",";
//            }
//        }
//        map.clear();
//        map.put("permissionList",permissionList);
//        map.put("companyUser",companyUser);
//        map.put("roleList",roleList);
//        List<RoleDataDTO> allRoleList = roleService.getCompanyRoleDTO(companyId);
//        if (null!=allRoleList && allRoleList.size()>0){
//            for (int i=0;i<allRoleList.size();i++){
//                if(!"".equals(userRoleIds) && userRoleIds.indexOf(allRoleList.get(i).getId())>-1){//存在
//                    allRoleList.get(i).setIsWithRole("1");
//                }
//                map.clear();
//                map.put("roleId",String.valueOf(allRoleList.get(i).getId()));
//                map.put("companyId",companyId);
//                List<PermissionDTO> rolePermissionList = permissionService.getPermissionByRole(map);
//                allRoleList.get(i).setPermissionList(rolePermissionList);
//            }
//        }
//
//        return responseSuccess().addData("permissionList",permissionList).addData("companyUser",companyUser).addData("allRoleList",allRoleList);
//    }


    /**
     * 方法描述：人员－添加角色
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("addUserRole")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean addUserRole(@RequestBody SaveRoleUserDTO dto) throws Exception {

        AjaxMessage ajaxMessage = roleUserService.saveOrUserRole(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：人员-删除角色
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("delUserRole")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean delUserRole(@RequestBody Map<String, Object> paraMap) throws Exception {

        AjaxMessage ajaxMessage = roleUserService.deleteRoleUser(paraMap);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：人员－保存角色权限
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("saveUserPermissionOld")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveUserPermissionOld(@RequestBody SaveUserPermissionDTO dto) throws Exception {
        AjaxMessage ajaxMessage = userPermissionService.saveUserPermission(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：人员－保存角色权限
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("saveUserRolePermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveUserRolePermission(@RequestBody Map<String, Object> paraMap) throws Exception {


        AjaxMessage ajaxMessage = userPermissionService.saveUserRolePermission(paraMap);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：保存角色权限
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("rolePermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean rolePermission(@RequestBody SaveRolePermissionDTO dto) throws Exception {


        AjaxMessage ajaxMessage = rolePermissionService.saveRolePermission(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }


    /**
     * 方法描述：保存用户权限(从权限中选择人员保存)
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("saveUserPermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveUserPermission(@RequestBody SaveUserPermission2DTO dto) throws Exception {
        // dto.setCurrentCompanyId(this.currentCompanyId);
        // dto.setAccountId(this.currentUserId);
        AjaxMessage ajaxMessage = userPermissionService.saveUserPermission2(dto);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }


    /**
     * 方法描述：保存用户权限(批量)
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("userPermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean userPermission(@RequestBody SaveUserPermissionDTO dto) throws Exception {
        // dto.setCurrentCompanyId(this.currentCompanyId);
        // dto.setAccountId(this.currentUserId);
        AjaxMessage ajaxMessage = userPermissionService.saveUserPermission(dto);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }

    /**
     * 方法描述：删除用户权限(从权限中删除人员)
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("deleteUserPermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteUserPermission(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", paraMap.get("accountId"));
        map.put("companyId", paraMap.get("appOrgId"));
        List<String> permissionList = new ArrayList<String>();
        permissionList.add(paraMap.get("permissionId").toString());
        map.put("permissionList", permissionList);
        AjaxMessage ajaxMessage = userPermissionService.deleteUserPermission2(map);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("删除成功");
        } else {
            return ResponseBean.responseError("删除失败");
        }
    }

    @Autowired
    private CompanyService companyService;

    /**
     * 方法描述：查询人员所在的公司,部门职位及权限
     * 作    者 : ChenZhuJie
     * 日    期 : 2017/1/10
     */
    @RequestMapping("getCompanyDepartAndPermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getCompanyDepartAndPermission(@RequestBody Map<String, Object> paraMap) throws Exception {
        String userId = paraMap.get("accountId").toString();
        //所在的所有公司
        List<CompanyDTO> orgList = companyService.getCompanyByUserId(userId);
        List<Object> list = new ArrayList<>();
        for (CompanyDTO companyDTO : orgList) {
            Map<String, Object> map = new HashMap<>();
            map.put("companyName", companyDTO.getCompanyName());
            map.put("companyId", companyDTO.getId());
            map.put("companyShortName", companyDTO.getCompanyShortName());

            map.put("userId", userId);
            List<DepartDTO> departList = departService.getDepartByUserIdContentCompany(map);
            List<RoleDataDTO> roleDtoList = roleService.getRolePermissionByUserId(companyDTO.getId(), userId);
            List<Map<String, Object>> departReturnList = new ArrayList<>();
            for (DepartDTO departDto : departList) {
                Map<String, Object> returnMap = new HashMap<>();
                returnMap.put("departName", departDto.getDepartName());
                returnMap.put("serverStation", departDto.getServerStation());
                departReturnList.add(returnMap);
            }
            map.put("departList", departReturnList);
            map.put("roleList", roleDtoList);
            list.add(map);
        }

        return responseSuccess().addData("list", list);
    }

    /**
     * 方法描述：获取公司角色-权限-人员总览
     * 作    者 : ChenZhuJie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("getRoleUserPermission")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getRoleUserPermission(@RequestBody Map<String, Object> paraMap) throws Exception {
        List<RoleDataDTO> list = roleService.getRolePermissionUser(paraMap.get("appOrgId").toString());
        String projectEidtUserNames = "";
        String projectTaskIssueUserNames = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (RoleDataDTO dto : list) {
                for (PermissionDTO permissionDTO : dto.getPermissionList()) {
                    if ("project_eidt".equals(permissionDTO.getCode())) {
                        for (CompanyUserAppDTO companyUserAppDTO : permissionDTO.getCompanyUserList()) {
                            projectEidtUserNames += companyUserAppDTO.getUserName() + "、";
                        }
                    }
                    if ("project_task_issue".equals(permissionDTO.getCode())) {
                        for (CompanyUserAppDTO companyUserAppDTO : permissionDTO.getCompanyUserList()) {
                            projectTaskIssueUserNames += companyUserAppDTO.getUserName() + "、";
                        }
                    }
                }
            }
        }
        if (!StringUtil.isNullOrEmpty(projectEidtUserNames)) {
            projectEidtUserNames = projectEidtUserNames.substring(0, projectEidtUserNames.length() - 1);
        }
        if (!StringUtil.isNullOrEmpty(projectTaskIssueUserNames)) {
            projectTaskIssueUserNames = projectTaskIssueUserNames.substring(0, projectTaskIssueUserNames.length() - 1);
        }
        return responseSuccess().addData("roleList", list)
                .addData("projectTaskIssueUserNames", projectTaskIssueUserNames)
                .addData("projectEidtUserNames", projectEidtUserNames);
    }

    /**
     * 方法描述：保存用户权限
     * 作    者 : MaoSF
     * 日    期 : 2017/02/08
     */
    @RequestMapping("saveUserPermissionList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveUserPermissionList(@RequestBody SaveUserPermissionListDTO dto) throws Exception {
        return this.userPermissionService.saveUserPermission2(dto);
    }

}
