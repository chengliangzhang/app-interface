package com.maoding.financial.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.financial.dto.ExpAuditDTO;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.dto.ExpTypeDTO;
import com.maoding.financial.dto.ExpTypeOutDTO;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.v2.financial.dto.V2ExpMainDTO;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpMainService
 * 描    述 : 报销主表Service
 * 作    者 : LY
 * 日    期 : 2016/7/26-15:58
 */
public interface ExpMainService extends BaseService<ExpMainEntity>{



    /**
     * 方法描述：报销基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     * @return
     *
     */
    AjaxMessage getExpBaseData(String companyId, String userId) throws Exception ;

    /**
     * 方法描述：报销类别基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     * @return
     *
     */
    AjaxMessage getCategoryBaseData(String companyId, String userId) throws Exception ;


    /**
     * 方法描述：得到当前公司和当前组织下面人员
     * 作   者：LY
     * 日   期：2016/8/3 17:17
     * @param  companyId 公司Id  orgId 组织Id
     *
     */
    List<CompanyUserDataDTO> getUserList(String companyId, String orgId) throws Exception;

    /**
     * 方法描述：我的报销列表
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     * @return
     *
     */
    List<ExpMainDTO> getExpMainPage(Map<String, Object> param);

    /**
     * 方法描述：我的报销列表Interface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     * @return
     *
     */
    List<ExpMainDTO> getExpMainPageInterface(Map<String, Object> param);

    int getExpMainPageCount(Map<String, Object> param);

    /**
     * 方法描述：撤回报销
     * 作   者：LY
     * 日   期：2016/7/29 11:01
     @param id--报销单id  type--状态(3撤回)
     * @return
     *
     */
    int recallExpMain(String id, String versionNum, String type) throws Exception;


    /**
     * 方法描述：退回报销
     * 作   者：LY
     * 日   期：2016/7/29 11:01
     * @param  dto -- mainId--报销单id  approveStatus--状态(2.退回) auditMessage审批意见
     */
    int recallExpMain(ExpAuditDTO dto) throws Exception;

    /**
     * 方法描述：报销详情
     * 作   者：LY
     * 日   期：2016/7/29 10:53
     * @param id--报销单id
     *
     */
    ExpMainDTO getExpMainPageDetail(String id) throws Exception;

    /**
     * 方法描述：删除报销
     * 作   者：LY
     * 日   期：2016/7/29 10:53
     * @param id--报销单id
     */
    int deleteExpMain(String id, String versionNum) throws Exception;

    /**
     * 方法描述：同意报销
     * 作   者：LY
     * 日   期：2016/8/1 15:08
     * @param  id--报销单id
     */
    int agreeExpMain(String id, String companyUserId, String versionNum) throws Exception;


    /**
     * 方法描述：同意报销并转移审批人
     * 作   者：LY
     * 日   期：2016/8/1 15:08
     * @param  id--报销单id auditPerson--新审批人  userId用户Id
     */
    int agreeAndTransAuditPerExpMain(String id, String userId, String auditPerson, String versionNum,String accountId) throws Exception;

    /**
     * 方法描述：报销详情与审批记录
     * 作   者：LY
     * 日   期：2016/8/2 14:13
     * @param  id--报销单id
     */
    Map<String, Object> getExpMainDetail(String id) throws Exception;

    /**
     * 方法描述：报销汇总List
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    List<ExpMainDTO> getExpMainPageForSummary(Map<String, Object> param);
    /**
     * 方法描述：报销汇总ListInterface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    List<ExpMainDTO> getExpMainPageForSummaryInterface(Map<String, Object> param);
    /**
     * 方法描述：报销汇总数量
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    int getExpMainPageForSummaryCount(Map<String, Object> param);

    /**
     * 方法描述：查询报销类型
     * 用于我要报销界面，报销类型选择
     * 作        者：MaoSF
     * 日        期：2015年12月7日-上午11:21:49
     * @return
     */
    List<ExpTypeDTO> getExpTypeList(String companyId);

    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    List<ProjectDTO> getProjectList(String companyId);



    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    List<ProjectDTO> getProjectListWS(Map<String, Object> map);
    /**
     * 方法描述:根据id获取报销详情app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    ExpMainDTO selectExpMainDetail(String mainId);
    /**
     * 方法描述:saveOrUpdateCategoryBaseData
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    AjaxMessage saveOrUpdateCategoryBaseData(ExpTypeOutDTO dto, String companyId)throws Exception;
    /**
     * 方法描述:deleteCategoryBaseData
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    AjaxMessage deleteCategoryBaseData(String id)throws Exception;
    /**
     * 方法描述:初始化报销类别基础数据(每个组织初始化)
     * 作   者：CZJ
     * 日   期：2016/10/11 17:39
     * @return
     *
     */
    AjaxMessage initInsertCategory(String companyId)throws Exception;

    //===============================2.0=================================

    /*****======================================v2==================================================***/
    /**
     * 方法描述：我的报销列表
     * 作   者：CZJ
     * 日   期：2016/12/27
     */
    List<V2ExpMainDTO> v2GetExpMainPage(Map<String, Object> param)throws Exception;


    /**
     * 方法描述：报销增加或者修改
     * 作   者：CZJ
     * 日   期：2016/12/27
     * @return
     *
     */
    AjaxMessage v2SaveOrUpdateExpMainAndDetail(V2ExpMainDTO dto, String userId, String companyId) throws Exception;


    /**
     * 方法描述：获取最大组织expNo + 1
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    Map<String, Object> getMaxExpNo(Map<String, Object> param) throws Exception;

}
