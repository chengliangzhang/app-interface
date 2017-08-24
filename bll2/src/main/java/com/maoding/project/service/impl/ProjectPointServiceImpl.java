package com.maoding.project.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectFeeDao;
import com.maoding.project.dao.ProjectPointDao;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectPointChangeSeqDTO;
import com.maoding.project.dto.ProjectPointDTO;
import com.maoding.project.entity.ProjectPointEntity;
import com.maoding.project.service.ProjectPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectPointServiceImpl
 * 类描述：项目合同节点ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectPointService")
public class ProjectPointServiceImpl extends GenericService<ProjectPointEntity> implements ProjectPointService {

    @Autowired
    private ProjectFeeDao projectFeeDao;

    @Autowired
    private ProjectPointDao projectPointDao;

    @Autowired
    private ProjectDao projectDao;

    public  AjaxMessage validateSaveOrUpdateProjectPoint(ProjectPointDTO dto) throws Exception{
        AjaxMessage ajax = new AjaxMessage();
        if(StringUtil.isNullOrEmpty(dto.getPointName())){
            return ajax.setCode("1").setInfo("节点名称不能为空");
        }

        if(StringUtil.isNullOrEmpty(dto.getReceivableMoney().toString())) {
            return ajax.setCode("1").setInfo("金额不能为空");
        }

        if(dto.getReceivableMoney() !=null &&
                (dto.getReceivableMoney().compareTo(new BigDecimal(-99999999999999.999999))<0
                        || dto.getReceivableMoney().compareTo(new BigDecimal(99999999999999.999999))>0)){
            return new AjaxMessage().setCode("1").setInfo("金额长度过长");
        }

        return ajax.setCode("0");
    }
    /**
     * 方法描述：保存合同节点
     * 作者：MaoSF
     * 日期：2016/8/1
     * @param dto
     */
    @Override
    public AjaxMessage saveOrUpdateProjectPoint(ProjectPointDTO dto) throws Exception {
        AjaxMessage ajax = this.validateSaveOrUpdateProjectPoint(dto);
        if(!"0".equals(ajax.getCode())){
            return ajax;
        }
        ProjectPointEntity pointEntity = new ProjectPointEntity();
        if(dto.getReceivableMoney().compareTo(new BigDecimal(0))==-1){
            dto.setPointState("1");
            if(StringUtil.isNullOrEmpty(dto.getPayDate())){
                dto.setPayDate(DateUtils.date2Str(DateUtils.date_sdf));
            }
        }
        BaseDTO.copyFields(dto,pointEntity);
        if(StringUtil.isNullOrEmpty(dto.getId())){
            dto.setId(StringUtil.buildUUID());
            pointEntity.setId(dto.getId());
            pointEntity.setCreateBy(dto.getAccountId());
            this.insert(pointEntity);
        }else{
            pointEntity.setUpdateBy(dto.getAccountId());
            this.updateById(pointEntity);
        }
        return ajax.setCode("0").setInfo("保存成功").setData(dto);
    }

    /**
     * 方法描述：删除合同节点
     * 作者：MaoSF
     * 日期：2016/8/4
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteProjectPoint(String id) throws Exception {
        //更改开票及到款的状态
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pointId", id);
        map.put("status", "1");
        projectFeeDao.updateStatusByPointId(map);

        //更改合同节点的状态
        ProjectPointEntity pointEntity = this.selectById(id);
        pointEntity.setId(id);
        pointEntity.setStatus("1");
        this.updateById(pointEntity);

        //获取合同回款板块所有数据
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO = getProjectPointDetail(pointEntity.getProjectId(),projectDTO);
        return new AjaxMessage().setCode("0").setInfo("删除成功").setData(projectDTO);
    }

    /**
     * 方法描述：新增，修改，删除 合同回款 费用后返回的数据
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    @Override
    public ProjectDTO getProjectPointDetail(String projectId,ProjectDTO projectDTO) throws Exception {
        //合同回款节点
        List<ProjectPointEntity> projectPointEntityList = projectPointDao.getProjectPointListByProjectId(projectId);
//        projectDTO.setProjectPointDTOList(BaseDTO.copyFields(projectPointEntityList,ProjectPointDTO.class));
//
//        //开票明细
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("projectId",projectId);
//        map.put("type","1");
//        List<ProjectFeeDTO> invoiceDetailList = projectFeeDao.getProjectFeeDetailByParameter(map);
//        projectDTO.setInvoiceDetailList(invoiceDetailList);
//
//        //到款明细
//        map.clear();
//        map.put("projectId",projectId);
//        map.put("type","2");
//        List<ProjectFeeDTO> receivedPaymentDetailList = projectFeeDao.getProjectFeeDetailByParameter(map);
//        projectDTO.setReceivedPaymentDetailList(receivedPaymentDetailList);

        return projectDTO;
    }

    /**
     * 方法描述：交换两个对象的位置
     * 作者：MaoSF
     * 日期：2016/11/24
     *
     * @param dto
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage changeProjectPoint(ProjectPointChangeSeqDTO dto) throws Exception {
        ProjectPointEntity point1 = new ProjectPointEntity();
        point1.setId(dto.getId1());
        point1.setSeq(dto.getSeq2());
        projectPointDao.updateById(point1);

        ProjectPointEntity point2 = new ProjectPointEntity();
        point2.setId(dto.getId2());
        point2.setSeq(dto.getSeq1());
        projectPointDao.updateById(point2);
        return new AjaxMessage().setCode("0").setInfo("处理成功");
    }


}
