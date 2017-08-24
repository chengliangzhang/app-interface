package com.maoding.hxIm.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.hxIm.dto.ImGroupMemberDataDTO;
import com.maoding.hxIm.dto.ImGroupMemberQuery;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.entity.ImGroupEntity;
import com.maoding.hxIm.entity.ImGroupMemberEntity;
import com.maoding.org.dto.CompanyUserGroupDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 2017/8/7.
 */
public interface ImGroupMemberDao extends BaseDao<ImGroupMemberEntity> {


    /**方法描述：新的im群成员
     * 作        者：Chenxj
     * 日        期：2016年6月8日-下午3:07:11
     */
    List<ImGroupMemberDataDTO> selectNewGroupMembers(ImGroupMemberQuery query);

//
//    /**方法描述：
//     * 作        者：Chenxj
//     * 日        期：2016年6月8日-下午3:07:11
//     */
//    List<ImGroupMemberDataDTO> selectGroupMembers(ImGroupMemberQuery query);

    /**方法描述：新的im群成员
     * 作        者：Chenxj
     * 日        期：2016年6月8日-下午3:07:11
     */
    List<ImGroupMemberDataDTO> selectCustomGroupMembers(ImGroupMemberQuery query);


    /**方法描述：
     * 作        者：Chenxj
     * 日        期：2016年6月8日-下午3:07:11
     */
    List<ImGroupMemberDataDTO> selectNewDepartGroupMembers(ImGroupMemberQuery query);

    /**
     * 获取自定义群组成员列表，并且按照公司分组
     */
    List<CompanyUserGroupDTO> listCustomerImGroupMember(ImGroupMemberQuery query);
}
