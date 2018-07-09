package com.maoding.commonModule.dao.impl;

import com.maoding.commonModule.dao.ConstDao;
import com.maoding.commonModule.dto.ContentDTO;
import com.maoding.commonModule.dto.TemplateQueryDTO;
import com.maoding.commonModule.entity.CustomConstEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * 日    期 : 2018/6/26 14:12
 * 描    述 :
 */
@Service("constDao")
public class ConstDaoImpl extends GenericDao<CustomConstEntity> implements ConstDao {
    @Override
    public List<ContentDTO> listTemplateContent(TemplateQueryDTO query) {
        return this.sqlSession.selectList("CustomConstEntityMapper.listTemplateContent", query);
    }

    /**
     * @return 可用单位列表
     * @author 张成亮
     * @date 2018/7/9
     * @description 查询可用单位
     **/
    @Override
    public List<String> listUnit() {
        return sqlSession.selectList("CustomConstEntityMapper.listUnit");
    }
}
