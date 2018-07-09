package com.maoding.v2.role.controller;


import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.DepartService;
import com.maoding.role.dto.*;
import com.maoding.role.service.RoleService;
import com.maoding.role.service.RoleUserService;
import com.maoding.role.service.UserPermissionService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private DepartService departService;

    @Autowired
    private UserPermissionService userPermissionService;


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
        String userId = (String)paraMap.get("accountId");
        return responseSuccess().addData("list", roleService.getCompanyDepartAndPermission(userId,null));
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
        List<RoleDataDTO> list = roleService.getRolePermissionUserAndSysManager(paraMap.get("appOrgId").toString());
        String projectEidtUserNames = "";
        String projectTaskIssueUserNames = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (RoleDataDTO dto : list) {
                for (PermissionDTO permissionDTO : dto.getPermissionList()) {
                    if ("project_edit".equals(permissionDTO.getCode())) {
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

    /**
     * 方法描述：根据companyUserId查询报销审批申请人列表
     * 作者：MaoSF
     * 日期：2016/12/26
     */
    @RequestMapping("/getOrgRoleList")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getOrgRoleList(@RequestBody Map<String, Object> paraMap) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("list",roleService.getOrgRoleList());
    }


    /**
     * 方法描述：不同组织类型的权限列表展示
     * 作    者 : MaoSF
     * 日    期 : 2017/6/22
     */
    @RequestMapping(value = "/getRolePermissionByType",method= RequestMethod.POST)
    @ResponseBody
    public ResponseBean getRolePermissionByType(@RequestBody Map<String, Object> paraMap) throws Exception{
        return ResponseBean.responseSuccess().addData("roleList",roleService.getRolePermissionByType((String)paraMap.get("roleType")));
    }
}
