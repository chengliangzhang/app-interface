package com.maoding.financial.service.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dao.ExpCategoryDao;
import com.maoding.financial.dao.ExpCategoryRelationDao;
import com.maoding.financial.dto.*;
import com.maoding.financial.entity.ExpCategoryEntity;
import com.maoding.financial.entity.ExpCategoryRelationEntity;
import com.maoding.financial.service.ExpCategoryService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.service.ProjectService;
import com.maoding.system.dao.DataDictionaryDao;
import com.maoding.system.dto.DataDictionaryDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpCategoryServiceImpl
 * 描    述 : 报销类别ServiceImpl
 * 作    者 : MaoSF
 * 日    期 : 2016/10/09-15:52
 */

@Service("expCategoryService")
public class ExpCategoryServiceImpl extends GenericDao<ExpCategoryEntity> implements ExpCategoryService {

    @Autowired
    private ExpCategoryDao expCategoryDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Autowired
    private ExpCategoryRelationDao expCategoryRelationDao;

    private String otherIncome = "gdfy_qtywsr";
    private String directCost = "bx_ywfy";

    /**
     * 方法描述：根据相关参数查找
     * 作        者：MaoSF
     * 日        期：2016年10月09日-下午2:46:28
     */
    @Override
    public List<ExpCategoryEntity> getDataByParemeter(Map<String, Object> map) {
        return expCategoryDao.getDataByParemeter(map);
    }

    @Override
    public List<ExpCategoryEntity> getParentExpCategory(String companyId) {
        Map<String,Object> param = new HashMap<>();
        param.put("companyId", companyId);
        List<ExpCategoryEntity> list=expCategoryDao.getParentExpCategory(param);
        return list;
    }

    @Override
    public AjaxMessage saveOrUpdateCategoryBaseData(ExpTypeOutDTO dto, String companyId) throws Exception {
        List<ExpTypeDTO> listAll = dto.getExpTypeDTOList();
        ExpCategoryEntity entity = null;
        if (StringUtil.isNullOrEmpty(listAll.get(0).getParent().getCompanyId())) {
            for (int i = 0; i < listAll.size(); i++) {
                String pid = StringUtil.buildUUID();
                entity = listAll.get(i).getParent();
                entity.setUpdateBy(dto.getAccountId());
                entity.setId(pid);
                entity.setCompanyId(companyId);
                expCategoryDao.insert(entity);
                List<ExpCategoryEntity> list = listAll.get(i).getChild();
                for (ExpCategoryEntity expCategoryDTO : list) {
                    BaseDTO.copyFields(expCategoryDTO, entity);
                    entity.setPid(pid);
                    entity.setId(StringUtil.buildUUID());
                    entity.setCompanyId(companyId);
                    if (StringUtil.isNullOrEmpty(expCategoryDTO.getStatus())) {
                        entity.setStatus("0");
                    }
                    expCategoryDao.insert(entity);
                }
            }

            return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);

        } else {

            //需要删除的数据，逻辑删除
            if (dto != null && !CollectionUtils.isEmpty(dto.getDeleteExpTypeList())) {
                for (ExpCategoryEntity expCategoryDTO : dto.getDeleteExpTypeList()) {
                    expCategoryDTO.setStatus("1");
                    expCategoryDao.updateById(expCategoryDTO);
                }
            }

            //所有要更新的数据。
            for (int i = 0; i < listAll.size(); i++) {

                entity = listAll.get(i).getParent();
                String pid = entity.getId();
                entity.setUpdateBy(dto.getAccountId());
                expCategoryDao.updateById(entity);

                //删除子条目
                Map<String, Object> mapParams = new HashMap<String, Object>();
                mapParams.put("companyId", companyId);
                mapParams.put("pid", pid);
                // expCategoryDao.deleteByPId(mapParams);
                List<ExpCategoryEntity> list = listAll.get(i).getChild();
                for (ExpCategoryEntity child : list) {
                    child.setStatus("0");
                    child.setPid(pid);
                    child.setCompanyId(companyId);
                    if (StringUtil.isNullOrEmpty(child.getId())) {
                        ExpCategoryEntity categoryEntity = expCategoryDao.selectByName(pid, child.getName());
                        if (categoryEntity != null) {
                            categoryEntity.setExpTypeMemo(child.getExpTypeMemo());
                            categoryEntity.setStatus("0");
                            expCategoryDao.updateById(categoryEntity);
                            child.setId(categoryEntity.getId());
                        } else {
                            child.setId(StringUtil.buildUUID());
                            expCategoryDao.insert(child);
                        }
                    } else {
                        expCategoryDao.updateById(child);
                    }
                }
            }
        }
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }


    /**
     * 方法描述：报销类别基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     */
    @Override
    public AjaxMessage getCategoryBaseData(String companyId, String userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        Map param = new HashMap();
        param.put("pid", "");
        param.put("companyId", companyId);
        List<ExpCategoryEntity> list = expCategoryDao.getDataByParemeter(param);
        //如果该公司的报销类别尚未初始化数据。则初始化
        if (list == null || list.size() == 0) {
            param.clear();
            param.put("pid", "");
            param.put("companyId", "");
            list = expCategoryDao.getDataByParemeter(param);
            //初始化本公司的报销类别基础数据
            if (!CollectionUtils.isEmpty(list)) {
                for (ExpCategoryEntity expCategoryEntity : list) {
                    String oldId = expCategoryEntity.getId();//旧id
                    expCategoryEntity.setId(StringUtil.buildUUID());
                    expCategoryEntity.setCompanyId(companyId);
                    expCategoryDao.insert(expCategoryEntity);
                    param.clear();
                    param.put("pid", oldId);
                    List<ExpCategoryEntity> childList = expCategoryDao.getDataByParemeter(param);
                    if (!CollectionUtils.isEmpty(childList)) {
                        for (ExpCategoryEntity child : childList) {
                            child.setId(StringUtil.buildUUID());
                            child.setPid(expCategoryEntity.getId());
                            child.setCompanyId(companyId);
                            expCategoryDao.insert(child);
                        }
                    }
                }
            }
        }
        //重新获取数据
        map.put("expTypeList", getExpTypeList(null,companyId));
        return new AjaxMessage().setCode("0").setData(map);
    }

    private void insertCategoryFromRootCompany(QueryExpCategoryDTO query){
        int count = this.expCategoryRelationDao.getSelectedCategory(query.getCompanyId());
        if(count==0) {
            //复杂总公司的记录到当前组织中
            this.expCategoryRelationDao.insertCategoryFromRootCompany(query);
        }
    }
    /**
     * 方法描述：查询报销类型
     * 用于我要报销界面，报销类型选择
     * 作        者：MaoSF
     * 日        期：2015年12月7日-上午11:21:49
     */
    @Override
    public List<ExpTypeDTO> getExpTypeList(String rootCompanyId,String companyId) throws Exception{
        if(rootCompanyId==null){
            rootCompanyId = companyService.getRootCompanyId(companyId);
        }
        setDefaultExp(rootCompanyId);
        List<ExpTypeDTO> expTypes = new ArrayList<ExpTypeDTO>();
        QueryExpCategoryDTO query = new QueryExpCategoryDTO();
        query.setRootCompanyId(rootCompanyId);
        query.setCompanyId(companyId);
        query.setCategoryType(1);
        insertCategoryFromRootCompany(query);
        List<ExpCategoryDataDTO> list = this.expCategoryDao.getExpCategoryListByType(query);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(exp->{
                ExpTypeDTO typeBean = new ExpTypeDTO();
                ExpCategoryEntity parent = new ExpCategoryEntity();
                parent.setId(exp.getId());
                parent.setExpTypeMemo(exp.getExpTypeMemo());
                parent.setName(exp.getName());
                parent.setCode(exp.getCode());
                parent.setPid(exp.getPid());
                typeBean.setParent(parent);
                exp.getChildList().stream().forEach(c->{
                    ExpCategoryEntity child = new ExpCategoryEntity();
                    child.setId(c.getId());
                    child.setExpTypeMemo(c.getExpTypeMemo());
                    child.setName(c.getName());
                    child.setCode(c.getCode());
                    child.setPid(c.getPid());
                    typeBean.getChild().add((child));
                });
                expTypes.add(typeBean);
            });
        }
        return expTypes;
    }

    @Override
    public List<ExpTypeDTO> getExpCategoryTypeList(String companyId, String userId) throws Exception{
        String rootCompanyId = companyService.getRootCompanyId(companyId);
        List<ExpTypeDTO> expTypes = this.getExpTypeList(rootCompanyId,companyId);
        CompanyUserEntity u = companyUserDao.getCompanyUserByUserIdAndCompanyId(userId,companyId);
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("type","1");
        if(u!=null){
            map.put("companyUserId",u.getId());
        }
        //如果没有参加任何项目的情况下，去掉“直接项目成本”
        List<ProjectDTO> list =  this.projectService.getProjectListByCompanyId(map);
        if(CollectionUtils.isEmpty(list)){
            for(ExpTypeDTO dto:expTypes){
                if("直接项目成本".equals(dto.getParent().getName())){
                    expTypes.remove(dto);
                    return expTypes;
                }
            }
        }
        return expTypes;
    }

    public void setDefaultExp(String companyId) throws Exception {
        ExpCountDTO count = this.expCategoryDao.getExpCategoryByCompanyId(companyId);
        if(count==null || count.getExp()==0){
            //如果该公司的报销类别尚未初始化数据。则初始化
            initDefaultData(companyId,null,1);
        }
        if(count==null || count.getFix()==0){
            //如果该公司的固定支出尚未初始化数据。则初始化
            initDefaultData(companyId,null,2);
        }
        if(count==null || count.getShare()==0){
            //如果该公司的报销类别尚未初始化数据。则初始化
            initDefaultData(companyId,null,3);
        }
    }

    /**
     * 初始化默认数据
     */
    private void initDefaultData(String companyId,String userId,Integer categoryType) {
        List<DataDictionaryDataDTO> dataList = new ArrayList<>();
        String code = null;
        if(categoryType==2){
            code = "gdfy";
        }else if(categoryType==1){
            code = "bx";
        }else if(categoryType==3){
            code = "fyft";
        }
        dataList = this.dataDictionaryDao.getExpTypeList(code);
        for (DataDictionaryDataDTO d : dataList) {
            String pid = d.getId();
            if(!directCost.equals(d.getCode())){
                ExpCategoryEntity expCategoryEntity = new ExpCategoryEntity();
                expCategoryEntity.setId(StringUtil.buildUUID());
                expCategoryEntity.setCompanyId(companyId);
                expCategoryEntity.setName(d.getName());
                expCategoryEntity.setCode(d.getCode());
                expCategoryEntity.setExpTypeMemo(d.getMemo());
                expCategoryEntity.setSeq(d.getSeq());
                expCategoryEntity.setCreateBy(userId);
                expCategoryEntity.setCategoryType(categoryType);
                expCategoryEntity.setStatus("0");
                expCategoryEntity.setShowStatus(1);
                if(categoryType==3){
                    expCategoryEntity.setShowStatus(0); // 如果是类型为分摊费用，默认不选中
                }else {
                    ExpCategoryRelationEntity relation = new ExpCategoryRelationEntity();
                    relation.initEntity();
                    relation.setCompanyId(companyId);
                    relation.setCategoryTypeId(expCategoryEntity.getId());
                    expCategoryRelationDao.insert(relation); //这份可以不存，为了保存统一，还是存
                }
                expCategoryDao.insert(expCategoryEntity);
                pid = expCategoryEntity.getId();
            }
            for (DataDictionaryDataDTO dChild : d.getChildList()) {
                ExpCategoryEntity child = new ExpCategoryEntity();
                child.setId(StringUtil.buildUUID());
                child.setPid(pid);
                child.setCompanyId(companyId);
                child.setName(dChild.getName());
                child.setCode(dChild.getCode());
                child.setExpTypeMemo(dChild.getMemo());
                child.setSeq(dChild.getSeq());
                child.setCreateBy(userId);
                child.setCategoryType(categoryType);
                child.setStatus("0");
                child.setShowStatus(1);
                if(categoryType==3){
                    child.setShowStatus(0); // 如果是类型为分摊费用，默认不选中
                }else {
                    //保存一份到relation中
                    ExpCategoryRelationEntity relation = new ExpCategoryRelationEntity();
                    relation.initEntity();
                    relation.setCompanyId(companyId);
                    relation.setCategoryTypeId(child.getId());
                    expCategoryRelationDao.insert(relation);
                }
                expCategoryDao.insert(child);
            }
        }
    }
}
