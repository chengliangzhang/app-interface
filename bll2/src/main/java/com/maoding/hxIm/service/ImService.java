package com.maoding.hxIm.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.hxIm.dto.*;
import com.maoding.org.dto.CompanyUserGroupDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 2017/8/7.
 */
public interface ImService {

    /**
     * 创建im账号
     */
    void createImAccount(String userId, String userName, String password) throws Exception;

    /**
     * 修改im密码
     */
    void updateImAccount(String userId, String password) throws Exception;

    /****************************************************************************************/

    /**
     * 方法描述：创建群组
     */
    void createImGroup(String groupId, String admin, String companyName, Integer groupType) throws Exception;

    /**
     * 方法描述：修改群组
     */
    void updateImGroup(String groupId, String companyName, Integer groupType) throws Exception;

    /**
     * 方法描述：删除群组
     */
    void deleteImGroup(String groupId, int groupType) throws Exception;

    /****************************************************************************************/

    /**
     * 群组添加成员
     */
    void addMembers(String groupId, String userId) throws Exception;

    /**
     * 群组删除成员
     */
    void deleteMembers(String groupId, String userId) throws Exception;


    /****************************************************************************************/

    /**
     * 群组信息
     */
    ImGroupDataDTO imGroupInfo(ImGroupQuery query) throws Exception;

    /**
     * 查询userId所在所有群组及群组成员
     */
    List<ImGroupDataDTO> listAllGroupByUserId(ImGroupQuery query) throws Exception;

    /**
     * 查询userId所在所有群组及群组成员(老版本的接口)
     */
    Map<String, Object> imGroupListNew(ImGroupQuery query) throws Exception;

    /**
     * 获取自定义群组的成员并按照公司分组
     */
    Map<String, Object> listAllGroupByUserIdAndCompanyId(ImGroupQuery query) throws Exception;

    /**
     * 获取自定义群组的成员并按照公司分组
     */
    List<ImGroupListDTO> listGroupByUserIdAndCompanyId(ImGroupQuery query) throws Exception;

    /**
     * 获取自定义群组的成员并按照公司分组
     */
    List<CompanyUserGroupDTO> listCustomerImGroupMember(String groupId) throws Exception;


    /************************************自定义群组创建（远程接口调用，为了兼容旧版本）****************************************************/

    /**
     * 查询群组
     */
    List<ImGroupDataDTO> selectCustomGroupByParameter(ImGroupQuery query);

    /**
     * 新建自定义群
     */
    ResponseBean saveCustomGroup(ImGroupCustomerDTO dto) throws Exception;

    /**
     * 修改自定义群
     */
    ResponseBean updateCustomGroup(ImGroupCustomerDTO dto) throws Exception;

    /**
     * 删除
     */
    ResponseBean deleteGroup(ImGroupCustomerDTO dto) throws Exception;

    /**
     * 自定义群添加成员
     */
    ResponseBean addMemberToGroup(ImGroupCustomerDTO dto) throws Exception;

    /**
     * 自定义群添加成员
     */
    ResponseBean deleteMemberFromGroup(ImGroupUserDTO dto) throws Exception;

    /*************************************************************/
    /** 插入imAccount **/
    void insertImAccount(ImAccountDTO dto) throws Exception;

    /** 批量插入imAccount **/
    void insertImAccountBatch(List<ImAccountDTO> list) throws Exception;

    /** 插入imGroup **/
    void insertImGroup(ImGroupDTO dto) throws Exception;
}
