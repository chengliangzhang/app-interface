package com.maoding.role.controller;


import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.service.CompanyUserService;
import com.maoding.role.dto.*;
import com.maoding.role.service.*;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseWSController {

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
    private UserPermissionService userPermissionService;


    /**
     * 方法描述：根据权限获取人员
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @RequestMapping("/get_companyUserByPermissionId")
    @ResponseBody
    public ResponseBean getCompanyUserByPermissionId(@RequestBody Map<String, Object> paraMap) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("permissionId", paraMap.get("permissionId"));
        map.put("companyId", paraMap.get("companyId"));
        return responseSuccess().addData("companyUserList", companyUserService.getCompanyUserByPermissionId(map));
    }


    /**
     * 方法描述：添加自定义角色
     * 作   者：wrb
     * 日   期：2016/8/9 16:18
     */
    @RequestMapping("/addOrUptCompanyRole")
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
     * 作        者：MaoSF
     * 日        期：2015年12月3日-下午3:20:20
     */
    @RequestMapping("/deletRole")
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param:
     * @return:
     */
    @RequestMapping("/save_role_permission")
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param:
     * @return:
     */
    @RequestMapping("add_role_member")
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param:
     * @return:
     */
    @RequestMapping("del_role_member")
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
//     * 作者：wrb
//     * 日期：2016/11/5
//     * @param:
//     * @return:
//     */
//    @RequestMapping("/get_user_permission")
//    @ResponseBody
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param:
     * @return:
     */
    @RequestMapping("add_user_role")
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param: {roleId, userId, companyId}
     * @return:
     */
    @RequestMapping("del_user_role")
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
     * 作者：wrb
     * 日期：2016/11/4
     * @param:
     * @return:
     */
    @RequestMapping("save_user_permission")
    @ResponseBody
    public ResponseBean saveUserPermission(@RequestBody SaveUserPermissionDTO dto) throws Exception {
        AjaxMessage ajaxMessage = userPermissionService.saveUserPermission(dto);

        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess("保存成功");
        } else {
            return ResponseBean.responseError("保存失败");
        }
    }


    /**
     * 方法描述：保存角色权限
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @RequestMapping("role_permission")
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
     * 方法描述：获取公司角色-权限-人员总览
     * 作者：CHENZHUJIE
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @RequestMapping("getRoleUserPermission")
    @ResponseBody
    public ResponseBean getRoleUserPermission(@RequestBody Map<String, Object> paraMap) throws Exception {
        List<RoleDataDTO> list = roleService.getRolePermissionUser(paraMap.get("appOrgId").toString());
        return responseSuccess().addData("roleList", list);
    }

    /**
     * 方法描述：保存用户权限(从权限中选择人员保存)
     * 作者：MaoSF
     * 日期：2016/11/16
     * @param:
     * @return:
     */
    @RequestMapping("saveUserPermission")
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
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @RequestMapping("userPermission")
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
     * 作者：MaoSF
     * 日期：2016/11/16
     * @param:
     * @return:
     */
    @RequestMapping("deleteUserPermission")
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
}
