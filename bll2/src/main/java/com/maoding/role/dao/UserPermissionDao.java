package com.maoding.role.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.role.entity.UserPermissionEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserPermissionService
 * 类描述：前台角色权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
public interface UserPermissionDao extends BaseDao<UserPermissionEntity> {

    /**
     * 方法描述：userId
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId,companyId
     * @return:
     */
    int deleteByUserId(Map<String, Object> map);



    /**
     * 方法描述：userId
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId,companyId，permissionList
     * @return:
     */
    int deleteByUserIdAndPermission(Map<String, Object> map);

    /**
     * 方法描述：删除权限里面的所有人员
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId,companyId，permissionList
     * @return:
     */
    int deleteByPermissionId(Map<String, Object> map);

    /**
     * 方法描述：根据userId，roleList查询用户所在的权限
     * 作者：MaoSF
     * 日期：2016/11/10
     * @param:
     * @return:
     */
    List<String> getPermissionByUserAndRole(Map<String, Object> map);

    /**
     * 方法描述：删除权限里面的所有人员
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:companyId，permissionId
     * @return:
     */
    int deleteAllByPermissionId(String companyId, String permissionId);


    /**
     * 方法描述：查询公司具有权限的人的userId(用于保存权限，给相关人发送消息使用)
     * 作者：MaoSF
     * 日期：2017/2/17
     * @param:
     * @return:
     */
    List<String> getHasPermissionUserId(String companyId);

    /**
     * 方法描述：用户权限
     * 作者：Czj
     * 日期：2016/11/30
     * @param:map
     * @return:List<AccountEntity>
     */
    List<UserPermissionEntity> selectByParma(Map<String, Object> param);

    /**
     * 方法描述：获取最大的seq
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    int getMaxSeq(String companyId,String permissionId);
}