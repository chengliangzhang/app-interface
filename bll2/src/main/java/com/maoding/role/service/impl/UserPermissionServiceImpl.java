package com.maoding.role.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.notice.constDefine.NotifyDestination;
import com.maoding.notice.service.NoticeService;
import com.maoding.role.dao.RoleDao;
import com.maoding.role.dao.RoleUserDao;
import com.maoding.role.dao.UserPermissionDao;
import com.maoding.role.dto.SaveRoleUserDTO;
import com.maoding.role.dto.SaveUserPermission2DTO;
import com.maoding.role.dto.SaveUserPermissionDTO;
import com.maoding.role.dto.SaveUserPermissionListDTO;
import com.maoding.role.entity.RoleEntity;
import com.maoding.role.entity.UserPermissionEntity;
import com.maoding.role.service.RoleUserService;
import com.maoding.role.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserPermissionService
 * 类描述：前台角色权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
@Service("userPermissionService")
public class UserPermissionServiceImpl extends GenericService<UserPermissionEntity> implements UserPermissionService {


    @Autowired
    private UserPermissionDao userPermissionDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private NoticeService noticeService;


    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    @Override
    public void saveUserPermission(UserPermissionEntity entity) {
        entity.setSeq(userPermissionDao.getMaxSeq(entity.getCompanyId(), entity.getPermissionId()));
        entity.setId(StringUtil.buildUUID());
        userPermissionDao.insert(entity);
    }

    /**
     * 方法描述：保存用户权限关联数据
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage saveUserPermission(SaveUserPermissionDTO dto) throws Exception {

        String companyId = dto.getCurrentCompanyId();
        String createBy = dto.getAccountId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", dto.getUserId());
        map.put("companyId", companyId);
        this.userPermissionDao.deleteByUserId(map);//先删除权限与用户之间的关系

        for (int i = 0; i < dto.getPermissionIds().size(); i++) {

            UserPermissionEntity userPermission = new UserPermissionEntity();
            //  userPermission.setId(StringUtil.buildUUID());
            userPermission.setPermissionId(dto.getPermissionIds().get(i));
            userPermission.setCompanyId(companyId);
            userPermission.setUserId(dto.getUserId());
            userPermission.setCreateBy(createBy);
            this.saveUserPermission(userPermission);
        }

        return new AjaxMessage().setCode("0").setInfo("保存成功");
    }

    /**
     * 方法描述：保存用户权限关联数据（从权限中选择人员）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage saveUserPermission2(SaveUserPermission2DTO dto) throws Exception {
        String companyId = dto.getCurrentCompanyId();
        String createBy = dto.getAccountId();
        List<String> listStr = dto.getDeleteUserIds();
        for (String str : listStr) {
            Map<String, Object> map = new HashMap<>();
            map.put("permissionId", dto.getPermissionId());
            map.put("companyId", companyId);
            map.put("userId", str);
            this.userPermissionDao.deleteByPermissionId(map);
        }
        for (int i = 0; i < dto.getUserIds().size(); i++) {
            UserPermissionEntity userPermission = new UserPermissionEntity();
            // userPermission.setId(StringUtil.buildUUID());
            userPermission.setPermissionId(dto.getPermissionId());
            userPermission.setCompanyId(companyId);
            userPermission.setUserId(dto.getUserIds().get(i));
            userPermission.setCreateBy(createBy);
            this.saveUserPermission(userPermission);
        }
        return new AjaxMessage().setCode("0").setInfo("保存成功");
    }

    /**
     * 方法描述：删除用户权限关联数据（从权限中删除人员）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteUserPermission2(Map<String, Object> map) throws Exception {
        this.userPermissionDao.deleteByUserIdAndPermission(map);
        return new AjaxMessage().setCode("0").setInfo("删除成功");
    }

    /**
     * 方法描述：批量删除用户权限关联数据
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteUserPermissionOfBatch(SaveUserPermissionDTO dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(dto.getPermissionIds())) {
            map.put("userId", dto.getUserId());
            map.put("companyId", dto.getCurrentCompanyId());
            map.put("permissionList", dto.getPermissionIds());
            this.userPermissionDao.deleteByUserIdAndPermission(map);

            if (!CollectionUtils.isEmpty(dto.getRoleIds())) {
                map.put("roleIds", dto.getRoleIds());
                this.roleUserDao.deleteUserRole(map);
            }
            return new AjaxMessage().setCode("0").setInfo("删除成功");
        }
        return new AjaxMessage().setCode("1").setInfo("删除失败");
    }

    @Override
    public AjaxMessage saveUserRolePermission(Map<String, Object> paraMap) throws Exception {

        String companyId = String.valueOf(paraMap.get("companyId"));
        String userId = String.valueOf(paraMap.get("userId"));
        Map<String, Object> map = new HashMap<String, Object>();
        List<RoleEntity> roleEntityList = roleDao.getCompanyRole(companyId);//查询当前组织所有角色

        if (null != roleEntityList && roleEntityList.size() > 0) {//删除当前组织所有角色与个人关联的关系
            for (int i = 0; i < roleEntityList.size(); i++) {
                map.clear();
                map.put("companyId", companyId);
                map.put("roleId", roleEntityList.get(i).getId());
                map.put("userId", userId);
                this.roleUserDao.deleteUserRole(map);
            }
        }
        List roleIds = (List) paraMap.get("roleIds");//添加当前组织角色与个人关联的关系
        if (null != roleIds && roleIds.size() > 0) {
            for (int i = 0; i < roleIds.size(); i++) {

                SaveRoleUserDTO dto = new SaveRoleUserDTO();
                List<String> userIds = new ArrayList<String>();
                userIds.add(userId);
                dto.setUserIds(userIds);
                dto.setCurrentCompanyId(companyId);
                dto.setRoleId(String.valueOf(roleIds.get(i)));
                this.roleUserService.saveOrUserRole(dto);
            }

        }
        SaveUserPermissionDTO dto = new SaveUserPermissionDTO();
        dto.setAccountId(String.valueOf(paraMap.get("accountId")));
        dto.setUserId(userId);
        dto.setPermissionIds((List<String>) paraMap.get("permissionIds"));
        return this.saveUserPermission(dto);
    }

    /**
     * 方法描述：批量保存用户权限（新版）
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:
     * @return:
     */
    @Override
    public ResponseBean saveUserPermission2(SaveUserPermissionListDTO dto) throws Exception {

        if (!CollectionUtils.isEmpty(dto.getUserPermissionList())) {
            List<String> userIdList = this.userPermissionDao.getHasPermissionUserId(dto.getAppOrgId());
            for (SaveUserPermission2DTO permission2DTO : dto.getUserPermissionList()) {
                //删除当前权限所以的人员权限关联信息
                this.userPermissionDao.deleteAllByPermissionId(dto.getAppOrgId(), permission2DTO.getPermissionId());
                for (int i = 0; i < permission2DTO.getUserIds().size(); i++) {
                    UserPermissionEntity userPermission = new UserPermissionEntity();
                    //   userPermission.setId(StringUtil.buildUUID());
                    userPermission.setPermissionId(permission2DTO.getPermissionId());
                    userPermission.setCompanyId(dto.getAppOrgId());
                    userPermission.setUserId(permission2DTO.getUserIds().get(i));
                    userPermission.setCreateBy(dto.getAccountId());
                    this.saveUserPermission(userPermission);

                    if (!userIdList.contains(permission2DTO.getUserIds().get(i))) {
                        userIdList.add(permission2DTO.getUserIds().get(i));
                    }
                }
            }

            //发送通知
            sendMessageForRole(userIdList, dto.getAppOrgId());
        }
        return ResponseBean.responseSuccess("保存成功");
    }

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2016/12/8
     * @param:
     * @return:
     */
    @Override
    public void sendMessageForRole(List<String> userIdList, String companyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "Operator");
        map.put("receiverList", userIdList);
        map.put("messageType", SystemParameters.ROLE_TYPE);
        map.put("noticeTitle","Permission");
        noticeService.notify(NotifyDestination.APP, map);//推送给app
//        try{
//
//            Map<String, Object> messageMap = new HashMap<String, Object>();
//            messageMap.put("messSource", companyId);
//            messageMap.put("messSourceType", "company");
//            messageMap.put("companyId", companyId);
//            messageMap.put("messServerType", SystemParameters.ROLE_TYPE);//通知公告
//            messageMap.put("noticeTitle", "Permission");
//            messageMap.put("noticeContent", "Operator");
//            messageMap.put("userIdList",userIdList);
//            messageProducer.sendSystemMessage(systemMessageDestination, messageMap);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}