package com.maoding.financial.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.financial.dao.ExpCategoryDao;
import com.maoding.financial.entity.ExpCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpCategoryDaoImpl
 * 描    述 : 报销类别DaoImpl
 * 作    者 : MaoSF
 * 日    期 : 2016/10/09-15:52
 */

@Service("expCategoryDao")
public class ExpCategoryDaoImpl extends GenericDao<ExpCategoryEntity> implements ExpCategoryDao {


    @Override
    public int insert(ExpCategoryEntity entity){
        if(0==entity.getSeq())
        {
            int seq = this.getmaxExpCategorySeq();
            entity.setSeq(seq);
        }

        return super.insert(entity);
    }

    /**
     * 方法描述：根据相关参数查找
     * 作        者：MaoSF
     * 日        期：2016年10月09日-下午2:46:28
     *
     * @param map
     * @return
     */
    @Override
    public List<ExpCategoryEntity> getDataByParemeter(Map<String, Object> map) {
        return this.sqlSession.selectList("ExpCategoryEntityMapper.selectByParemeter",map);
    }

    /**
     * 方法描述：根据父id和那么查询
     * 作者：MaoSF
     * 日期：2016/10/13
     *
     * @param pid
     * @param name
     * @param:
     * @return:
     */
    @Override
    public ExpCategoryEntity selectByName(String pid, String name) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("pid",pid);
        map.put("name",name);
        return this.sqlSession.selectOne("ExpCategoryEntityMapper.selectByName",map);
    }

    /**
     * 方法描述：获取最大的seq值
     * 作者：MaoSF
     * 日期：2016/10/9
     *
     * @param:
     * @return:
     */
    @Override
    public int getmaxExpCategorySeq() {
        return this.sqlSession.selectOne("ExpCategoryEntityMapper.getmaxExpCategorySeq");
    }

    /**
     * 方法描述：删除子类别
     * 作   者：LY
     * 日   期：2016/7/27 9:46
     * @return
     *
     */
    public int deleteByPId(Map<String, Object> map){
        return this.sqlSession.delete("ExpCategoryEntityMapper.deleteByPid", map);
    }

    /**
     * 方法描述：初始化报销类别基础数据
     * 作   者：CZJ
     * 日   期：2016/10/11 9:46
     * @return
     *
     */
    public void initInsertCategory(String companyId){

         this.sqlSession.insert("ExpCategoryEntityMapper.initInsertCategory", companyId);
    }
}
