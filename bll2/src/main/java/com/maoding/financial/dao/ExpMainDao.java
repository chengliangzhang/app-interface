package com.maoding.financial.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.v2.financial.dto.V2ExpMainDTO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpMainDao
 * 描    述 : 报销Dao
 * 作    者 : LY
 * 日    期 : 2016/7/26 15:13
 */
public interface ExpMainDao  extends BaseDao<ExpMainEntity> {

    /**
     * 方法描述：我的报销列表
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    public List<ExpMainDTO> getExpMainPage(Map<String,Object> param);

    /**
     * 方法描述：我的报销列表
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    public List<ExpMainDTO> getExpMainPageInterface(Map<String,Object> param);

    public int getExpMainPageCount(Map<String, Object> param);

    /**
     * 方法描述：查询报销主表记录并关联账号表
     * 作   者：LY
     * 日   期：2016/8/2 15:10
     * @param id 报销主表id
     */
    public ExpMainDTO selectByIdWithUserName(String id);

    /**
     * 方法描述：查询报销主表记录并关联账号表
     * 作   者：LY
     * 日   期：2016/8/2 15:10
     * @param id 报销主表id
     */
    public ExpMainDTO selectByIdWithUserName(Map<String, Object> param);

    /**
     * 方法描述：报销汇总list
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    public List<ExpMainDTO> getExpMainPageForSummary(Map<String, Object> param);

    /**
     * 方法描述：报销汇总listInterface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param  param 查询条件
     */
    public List<ExpMainDTO> getExpMainPageForSummaryInterface(Map<String, Object> param);

    /**
     * 方法描述：报销汇总列表数量
     * 作   者：LY
     * 日   期：2016/8/2 17:42
     *
    */
    public int getExpMainPageForSummaryCount(Map<String, Object> param);
    /**
     * 方法描述：报销详细器查询
     * 作   者：LY
     * 日   期：2016/8/2 17:42
     *
     */
    public ExpMainDTO getExpMainDetail(String mainId);

    /**
     * 方法描述：查询报销单（用于我的任务中）
     * 作者：MaoSF
     * 日期：2016/12/22
     * @param:
     * @return:
     */
    public ExpMainDTO getByMainIdForMyTask(String mainId);

    /**********************************************v2******************************************/

    /**
     * v2
     * 方法描述：我的报销列表
     * 作   者：ChenZhuJie
     * 日   期：2016/12/27
     */
    public List<V2ExpMainDTO> v2GetExpMainPage(Map<String,Object> param);

    /**
     * 方法描述：获取某组织中最大值
     * 作者：MaoSF
     * 日期：2016/8/5
     * @param:
     * @return:
     */
    public String getMaxExpNo(Map<String,Object> param) ;

    /**
     * 方法描述：查询
     * 作   者：ChenZhuJie
     * 日   期：2016/12/27
     */
    public List<ExpMainEntity> selectByParam(Map<String,Object> param);
}