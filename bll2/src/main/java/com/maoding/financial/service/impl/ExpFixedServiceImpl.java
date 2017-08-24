package com.maoding.financial.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dao.ExpFixedDao;
import com.maoding.financial.entity.ExpFixedEntity;
import com.maoding.financial.service.ExpFixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpFixedServiceImpl
 * 描    述 : 固定支出ServiceImpl
 * 作    者 : LY
 * 日    期 : 2016/8/4-11:11
 */
@Service("expFixedService")
public class ExpFixedServiceImpl extends GenericService<ExpFixedEntity> implements ExpFixedService {

    @Autowired
    private ExpFixedDao expFixedDao;


    /**
     * 方法描述：保存或者编辑固定支出
     * 作   者：LY
     * 日   期：2016/8/4 14:17
     * @param
     */
    public int saveOrUpdateExpFixed(Map<String, Object> paraMapk, String companyId, String userId){
        String expDate = paraMapk.get("expDate").toString();
        Map<String, String> ids = (Map<String, String>)paraMapk.get("ids");
        Map<String, String> expTypes = (Map<String, String>)paraMapk.get("expTypes");
        Map expAmounts = (Map)paraMapk.get("expAmounts");
        Map<String, String> remarks = (Map<String, String>)paraMapk.get("remarks");
        //加上，如果存在记录，则不进行新增
        int count = expFixedDao.getExpDateCount(paraMapk);
        int j=0;
        for(int i=1;i<15;i++){
            //user_name depart_id
            ExpFixedEntity entity = new ExpFixedEntity();
            String expAmount = expAmounts.get("expAmount"+i) == null ? null : expAmounts.get("expAmount"+i).toString();
            entity.setExpType(expTypes.get("expType"+i));
            entity.setExpDate(expDate);
            if(StringUtil.isNullOrEmpty(expAmount)){
                entity.setExpAmount(new BigDecimal("0"));
            }else{
                entity.setExpAmount(new BigDecimal(expAmount));
            }
            entity.setUserId(userId);
            entity.setCompanyId(companyId);
            entity.setRemark(remarks.get("remark"+i));
            entity.setId(ids.get("id"+i));
            entity.setUpdateBy(userId);
            if(StringUtil.isNullOrEmpty(entity.getId())){
                entity.setId(StringUtil.buildUUID());
                entity.setCreateBy(userId);
               // if(count==0){
                    j=expFixedDao.insert(entity);
               // }
            }else{
                j=expFixedDao.updateById(entity);
            }
        }
        return j;
    }


    /**
     * 方法描述：查询选择月的固定支出
     * 作   者：LY
     * 日   期：2016/8/4 15:25
     * @param  expDate 月份
     */
    public Map<String,Object> getExpFixedByExpDate(String companyId, String expDate){
        List<ExpFixedEntity> list=null;
        Map ids=new HashMap();
        Map expTypes=new HashMap();
        Map expAmounts=new HashMap();
        Map remarks=new HashMap();
        if(!StringUtil.isNullOrEmpty(expDate)){
            list = expFixedDao.getExpFixedByExpDate(companyId, expDate);
        }
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ids.put("id"+(i+1), list.get(i).getId());
                expTypes.put("expType"+(i+1), list.get(i).getExpType());
                expAmounts.put("expAmount"+(i+1), list.get(i).getExpAmount());
                remarks.put("remark"+(i+1), list.get(i).getRemark());
            }
            Map returnMap=new HashMap();
            returnMap.put("ids", ids);
            returnMap.put("expTypes", expTypes);
            returnMap.put("expAmounts", expAmounts);
            returnMap.put("remarks", remarks);
//            returnMap.put("isSave", 1);
//            List<ExpStatisticEntity> expStatistic=financeService.getExpStatisticByYear(companyId, expDate);
//            if(expStatistic !=null && expStatistic.size()>0){
//                returnMap.put("isSave", 0);
//            }
            return returnMap;
        }else{
            return null;
        }
    }
    /**
     * 方法描述：查询选择月的固定支出(接口)
     * 作   者：LY
     * 日   期：2016/8/4 15:25
     * @param  expDate 月份
     */
    public Map<String,Object> getExpFixedByExpDateInterface(String companyId, String expDate){
        List<ExpFixedEntity> list=null;
        Map ids=new HashMap();
        Map expTypes=new HashMap();
        Map expAmounts=new HashMap();
        Map remarks=new HashMap();
        if(!StringUtil.isNullOrEmpty(expDate)){
            list = expFixedDao.getExpFixedByExpDate(companyId, expDate);
        }
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ids.put("id"+(i+1), list.get(i).getId());
               // expTypes.put("expType"+(i+1), list.get(i).getExpType());
                expAmounts.put("expAmount"+(i+1), list.get(i).getExpAmount());
                if(i==0 || i == 13){
                    remarks.put("remark"+(i+1), list.get(i).getRemark());
                }
            }
            Map returnMap=new HashMap();
            returnMap.put("ids", ids);
           // returnMap.put("expTypes", expTypes);
            returnMap.put("expAmounts", expAmounts);
            returnMap.put("remarks", remarks);
//            returnMap.put("isSave", 1);
//            List<ExpStatisticEntity> expStatistic=financeService.getExpStatisticByYear(companyId, expDate);
//            if(expStatistic !=null && expStatistic.size()>0){
//                returnMap.put("isSave", 0);
//            }
            return returnMap;
        }else{
            return null;
        }
    }

}
