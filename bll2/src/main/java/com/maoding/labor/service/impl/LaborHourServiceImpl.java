package com.maoding.labor.service.impl;

import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.labor.dao.LaborHourDao;
import com.maoding.labor.dto.LaborHourDTO;
import com.maoding.labor.dto.LaborHourDataDTO;
import com.maoding.labor.dto.QueryLaborHourDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.labor.entity.LaborHourEntity;
import com.maoding.labor.service.LaborHourService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.schedule.dto.ScheduleAndLaborDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("laborHourService")
public class LaborHourServiceImpl implements LaborHourService {

    @Autowired
    private LaborHourDao laborHourDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Override
    public ResponseBean saveLaborHour(SaveLaborHourDTO dto) throws Exception {
        String companyId = dto.getAppOrgId();
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        assert (user!=null):"参数错误";
        List<LaborHourEntity> laborList = laborHourDao.getLaborHourByDate(user.getId(),dto.getLaborDate());
        String ids = "";
        for(LaborHourDTO labor:dto.getLaborList()){
            if(StringUtil.isNullOrEmpty(labor.getId())){//新增
                LaborHourEntity entity = new LaborHourEntity();
                entity.setLaborDate(dto.getLaborDate());
                entity.setProjectId(labor.getProjectId());
                entity.setLaborHours(labor.getLaborHours());
                entity.setCompanyId(companyId);
                entity.setCompanyUserId(user.getId());
                entity.setDeleted(0);
                entity.setCreateBy(dto.getAccountId());
                entity.initEntity();
                laborHourDao.insert(entity);
                ids += entity.getId()+",";
            }else {
                LaborHourEntity entity = new LaborHourEntity();
                entity.setId(labor.getId());
                entity.setProjectId(labor.getProjectId());
                entity.setLaborHours(labor.getLaborHours());
                entity.setCompanyId(companyId);
                entity.setCompanyUserId(user.getId());
                entity.setUpdateBy(dto.getAccountId());
                laborHourDao.updateById(entity);
                ids += entity.getId()+",";
            }
        }
        for(LaborHourEntity labor:laborList) {
            if(!ids.contains(labor.getId())){
                labor.setDeleted(1);
                laborHourDao.updateById(labor);
            }
        }
        //查询工时记录
        return ResponseBean.responseSuccess("操作成功");
    }

    @Override
    public LaborHourDataDTO getLaborHour(SaveLaborHourDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        assert (user!=null):"参数错误";
        LaborHourDataDTO dataDTO = new LaborHourDataDTO();
        dataDTO.setTotalLaborHours("0");
        List<LaborHourDTO> laborList = laborHourDao.getLaborHourDataByDate(user.getId(),dto.getLaborDate());
        laborList.stream().forEach(c->{
            dataDTO.setTotalLaborHours((Float.parseFloat(dataDTO.getTotalLaborHours())+Float.parseFloat(c.getLaborHours()==null?"0":c.getLaborHours()))+"");
        });
        dataDTO.setLaborDate(dto.getLaborDate());
        dataDTO.setLaborList(laborList);
        return dataDTO;
    }

    @Override
    public List<String> getLaborDate(QueryLaborHourDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
        dto.setCompanyUserId(user.getId());
        return laborHourDao.getLaborDate(dto);
    }

    @Override
    public List<ScheduleAndLaborDTO> getLaborHourByDate(QueryLaborHourDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
        dto.setCompanyUserId(user.getId());
        //查询工时
        List<LaborHourDTO> laborList = laborHourDao.getLaborHourDataByDate(user.getId(), DateUtils.str2Date(dto.getDate()));
        List<ScheduleAndLaborDTO> list = new ArrayList<>();
        laborList.stream().forEach(c->{
            ScheduleAndLaborDTO labor = new ScheduleAndLaborDTO();
            labor.setId(c.getId());
            labor.setContent(c.getProjectName());
            labor.setLaborHours(c.getLaborHours());
            labor.setScheduleType(3);
            list.add(labor);
        });
        return list;
    }

    @Override
    public ResponseBean deleteLaborHour(SaveLaborHourDTO dto) throws Exception {
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if (user==null) throw new NullPointerException("数据错误");
        dto.setCompanyUserId(user.getId());
        int i = laborHourDao.deleteLaborHour(dto);
        return i>0?ResponseBean.responseSuccess("操作成功"):ResponseBean.responseError("操作失败");
    }
}
