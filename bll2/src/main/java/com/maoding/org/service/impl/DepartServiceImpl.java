package com.maoding.org.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dao.*;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.dto.DepartRoleDTO;
import com.maoding.org.entity.*;
import com.maoding.org.service.DepartService;
import com.maoding.role.dao.UserPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("departService")
public class DepartServiceImpl extends GenericService<DepartEntity> implements DepartService {

    @Autowired
    private DepartDao departDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private OrgUserDao orguserDao;

    @Autowired
    private CompanyUserDao companyUserDao;


    @Autowired
    private ImService imService;

    @Autowired
    private UserPermissionDao userPermissionDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<DepartDTO> getDepartByCompanyId(Map<String, Object> paraMap) throws Exception {
        List<DepartDTO> departList = departDao.getDepartByCompanyId(paraMap);
        return departList;
    }

    /**
     * 方法描述：根据companyId和userId查询Departs（部门）
     * 作        者：TangY
     * 日        期：2016年7月8日-下午3:32:16
     */
    public List<DepartDTO> getDepartByCompanyIdAndUserId(Map<String, Object> paraMap) throws Exception {
        List<DepartEntity> departList = departDao.getDepartByCompanyIdAndUserId(paraMap);
        List<DepartDTO> departDtoList = new ArrayList<DepartDTO>();
        departDtoList = BaseDTO.copyFields(departList, DepartDTO.class);
        return departDtoList;
    }

    /**
     * 方法描述：使用递归查询公司部门
     * 作者：MaoSF
     * 日期：2016/9/18
     * @param:
     * @return:
     */
    @Override
    public List<DepartDTO> getDepartByCompanyId(Map<String, Object> paraMap, List<DepartDTO> departDTOList) throws Exception {
        if (!paraMap.containsKey("pid")) {
            paraMap.put("pid", paraMap.get("companyId"));
        }
        if (departDTOList == null) {
            departDTOList = new ArrayList<DepartDTO>();
        }
        List<DepartDTO> list = this.getDepartByCompanyId(paraMap);
        for (int i = 0; i < list.size(); i++) {
            departDTOList.add(list.get(i));
            paraMap.put("pid", list.get(i).getId());
            departDTOList.addAll(getDepartByCompanyId(paraMap, null));
        }
        return departDTOList;
    }


    /**
     * 方法描述：根据companyId和userId查询Departs（部门）包含公司
     * 作        者：TangY
     * 日        期：2016年7月8日-下午3:32:16
     * @param paraMap （companyId（公司ID）,userId（用户Id））
     */
    @Override
    public List<DepartDTO> getDepartByUserIdContentCompany(Map<String, Object> paraMap) throws Exception {
        List<DepartEntity> departList = departDao.getDepartByUserIdContentCompany(paraMap);
        List<DepartDTO> departDtoList = new ArrayList<DepartDTO>();
        departDtoList = BaseDTO.copyFields(departList, DepartDTO.class);
        return departDtoList;
    }

    /**
     * 方法描述：根据companyId和userId查询Departs（部门）包含公司
     * getDepartByUserIdContentCompanyInterface
     * 作        者：TangY
     * 日        期：2016年7月8日-下午3:32:16
     * @param paraMap （companyId（公司ID）,userId（用户Id））
     */
    @Override
    public List<DepartDTO> getDepartByUserIdContentCompanyInterface(Map<String, Object> paraMap) throws Exception {
        List<DepartEntity> departList = departDao.getDepartByUserIdContentCompanyInterface(paraMap);
        List<DepartDTO> departDtoList = new ArrayList<DepartDTO>();
        departDtoList = BaseDTO.copyFields(departList, DepartDTO.class);
        return departDtoList;
    }

    public ResponseBean validateSaveOrUpdateDepart(DepartDTO dto) throws Exception {

        if (StringUtil.isNullOrEmpty(dto.getCompanyId())) {
            return ResponseBean.responseError("请选择组织");
        }
        CompanyEntity company = companyDao.selectById(dto.getCompanyId());
        if (company == null) {
            return ResponseBean.responseError("请选择组织");
        }
        if (StringUtil.isNullOrEmpty(dto.getCompanyId())) {
            return ResponseBean.responseError("请选择组织");
        }
        if (StringUtil.isNullOrEmpty(dto.getDepartName())) {
            return ResponseBean.responseError("部门名称不能为空");
        }
        if (StringUtil.isNullOrEmpty(dto.getPid())) {
            return ResponseBean.responseError("请选择父部门");
        }
        return null;
    }

    @Override
    public ResponseBean saveOrUpdateDepart(DepartDTO dto) throws Exception {
        ResponseBean ajax = validateSaveOrUpdateDepart(dto);
        if (ajax != null) {
            return ajax;
        }
        String accountId = dto.getAccountId();
        DepartEntity entity = new DepartEntity();
        BaseDTO.copyFields(dto, entity);
        String pid = dto.getPid();
        DepartEntity parent = null;
        boolean isFirstLevelDepart = true;//是否是一级部门
        parent = this.departDao.selectById(pid);
        if (parent != null) {
            isFirstLevelDepart = false;
        }

        String id = "";
        //如果是新增
        if (null == entity.getId() || "".equals(entity.getId())) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("companyId", entity.getCompanyId());
            param.put("pid", entity.getPid());
            param.put("departName", entity.getDepartName());
            DepartEntity isExitEntity = departDao.getByDepartNameAndPid(param);

            //判断在同一目录下是否存在名字相同的部门
            if (isExitEntity != null) {
                if ("0".equals(isExitEntity.getStatus())) {
                    return ResponseBean.responseError("该部门已经存在");
                } else {
                    isExitEntity.setStatus("0");
                    departDao.updateById(isExitEntity);
                    entity = isExitEntity;
                }
            } else {
                id = StringUtil.buildUUID();
                entity.setId(id);
                if (isFirstLevelDepart) {
                    entity.setDepartPath(id);
                    entity.setDepartLevel("1");
                } else {
                    entity.setDepartPath(parent.getDepartPath() + "-" + id);
                    entity.setDepartLevel(StringUtil.isNullOrEmpty(parent.getDepartLevel()) ? "1" : (Integer.parseInt(parent.getDepartLevel()) + 1) + "");
                }
                entity.setOrgType("0");
                entity.setCreateBy(accountId);
                entity.setCompanyId(dto.getAppOrgId());
                //保存OrgEntity,组织基础表
                OrgEntity org = new OrgEntity();
                org.setId(id);//基础表的id和部门表的id一致
                org.setOrgType("1");
                org.setOrgStatus("0");
                org.setCreateBy(entity.getCreateBy());
                orgDao.insert(org);
                //保存部门
                departDao.insert(entity);
            }
            BaseDTO.copyFields(entity, dto);
            return ResponseBean.responseSuccess("保存成功");

        } else {
            return updateDepart(dto, isFirstLevelDepart, parent);
        }
    }

    /**
     * 方法描述：修改部门信息
     * 作        者：MaoSF
     * 日        期：2016年7月14日-上午11:58:30
     */
    public ResponseBean updateDepart(DepartDTO dto, boolean isFirstLevelDepart, DepartEntity parent) throws Exception {
        DepartEntity entity = new DepartEntity();
        BaseDTO.copyFields(dto, entity);
        String id = entity.getId();
        DepartEntity dbEntity = departDao.selectById(id);//获取数据库中的信息
        if (!dbEntity.getDepartName().equals(entity.getDepartName())) {//如果修改了部门名称，则判断
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("companyId", entity.getCompanyId());
            param.put("pid", dto.getPid());
            param.put("departName", entity.getDepartName());
            DepartEntity isExitEntity = departDao.getByDepartNameAndPid(param);
            //判断在同一目录下是否存在名字相同的部门
            if (isExitEntity != null) {
                return ResponseBean.responseError("该部门已经存在");
            }
        }

        if (isFirstLevelDepart) {
            entity.setDepartPath(id);
            entity.setDepartLevel("1");
        } else {
            entity.setDepartPath(parent.getDepartPath() + "-" + id);
            entity.setDepartLevel(StringUtil.isNullOrEmpty(parent.getDepartLevel()) ? "1" : (Integer.parseInt(parent.getDepartLevel()) + 1) + "");
        }

        if (!dbEntity.getPid().equals(entity.getPid())) {//如果改变当前的父id，则修改当前
            //算出修改部门的移动的级别差，所有子部门的departLevel+level
            int dbLevel = 1;
            if (!StringUtil.isNullOrEmpty(dbEntity.getDepartLevel())) {
                dbLevel = Integer.parseInt(dbEntity.getDepartLevel());
            }
            int nowEntityLevel = 1;
            if (!StringUtil.isNullOrEmpty(entity.getDepartLevel())) {
                nowEntityLevel = Integer.parseInt(entity.getDepartLevel());
            }
            int level = nowEntityLevel - dbLevel;
            //加上"-"代表只差子部门，否则连自己也查询出来
            List<DepartEntity> departList = departDao.getDepartsByDepartPath(dbEntity.getDepartPath() + "-");
            if (null != departList && departList.size() > 0) {
                for (int i = 0; i < departList.size(); i++) {
                    DepartEntity d = departList.get(i);
                    String departPath = d.getDepartPath();
                    departPath = departPath.replaceAll(dbEntity.getDepartPath(), entity.getDepartPath());
                    d.setDepartLevel((Integer.parseInt(d.getDepartLevel()) + level) + "");
                    d.setDepartPath(departPath);
                    d.setUpdateBy(dto.getAccountId());
                    departDao.updateById(d);
                }
            }
        }
        entity.setUpdateBy(dto.getAccountId());
        departDao.updateById(entity);

        //创建一级部门群组
        if ("1".equals(entity.getDepartLevel())) {
            //修改群组
            this.updatDeartGroup(entity);
        }
        return ResponseBean.responseSuccess("保存成功");
    }

    private void updatDeartGroup(DepartEntity entity) throws Exception {
        //修改群组
        imService.updateImGroup(entity.getId(), entity.getDepartName(), 1);
    }

    @Override
    public AjaxMessage deleteDepart(String id) throws Exception {
        DepartEntity d = departDao.selectById(id);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("orgId", id);
        paraMap.put("companyId", d.getCompanyId());
        paraMap.put("auditStatus", "1");
        int count = companyUserDao.getCompanyUserByOrgIdCount(paraMap);
        if (count > 0) {
            return new AjaxMessage().setCode("1").setInfo("该组织下有员工，请先移除员工再删除");
        }
        paraMap.put("pid", id);
        List<DepartDTO> dList = this.getDepartByCompanyId(paraMap);
        if (dList != null && dList.size() > 0) {
            return new AjaxMessage().setCode("1").setInfo("该组织下有子部门，请先移除子部门再删除");
        }
        this.deleteById(id);
        return new AjaxMessage().setCode("0").setInfo("操作成功");
    }

    /**
     * 方法描述：删除部门（递归删除）【删除部门及所有的子部门和人员】
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public AjaxMessage deleteDepartById(String id, String accountId) throws Exception {
        //1.查询要删除的部门
        DepartEntity departEntity = this.selectById(id);
        //当前用户（管理员）如果管理员在部门中，则不删除管理员的记录
        CompanyUserEntity admin = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, departEntity.getCompanyId());
        //查询所有的子部门
        List<DepartEntity> list = departDao.getDepartsByDepartPath(departEntity.getDepartPath() + "-");

        Map<String, Object> param = new HashMap<String, Object>();

        //2.把所有部门的id放在orgIds中
        List<String> orgIds = new ArrayList<String>();
        orgIds.add(id);
        for (int i = 0; list != null && i < list.size(); i++) {
            orgIds.add(list.get(i).getId());
        }
        param.put("orgIds", orgIds);
        //3.根据orgIds查询所有的orgUserEntity数据
        List<OrgUserEntity> orgUserEntityList = orguserDao.selectByParam(param);

        //4.把orgUserEntityList中的cuId保存到cuIdList 中，以便删除部门后，是否要删除员工数据
        List<String> cuIdList = new ArrayList<String>();
        for (int j = 0; orgUserEntityList != null && j < orgUserEntityList.size(); j++) {
            cuIdList.add(orgUserEntityList.get(j).getCuId());
        }
        //5.删除所有的部门与部门成员之间的关系
        orguserDao.deleteOrgUserByParam(param);
        //6.删除所有此根节点下的部门（包含自己）
        departDao.deleteByDepartPath(departEntity.getDepartPath());
        //7.逐条查询cuIdList下的人员，是否还有部门，若无，删除（状态改为离职状态）
        String info = "删除成功!";
        for (String cuId : cuIdList) {

            param.clear();
            param.put("cuId", cuId);
            List<OrgUserEntity> cuOrgList = orguserDao.selectByParam(param);
            if (cuOrgList == null || cuOrgList.size() == 0) {//如果不存在其他部门信息，则删除
                if (admin == null || !cuId.equals(admin.getId()))//不是管理员才删除，否则不设为离职
                {
                    CompanyUserEntity companyUserEntity = companyUserDao.selectById(cuId);
                    if (companyUserEntity != null) {
                        companyUserEntity.setAuditStatus("4");
                        companyUserEntity.setUpdateBy(accountId);
                        companyUserDao.updateById(companyUserEntity);

                        //删除所有的权限
                        param.clear();
                        param.put("userId", companyUserEntity.getUserId());
                        param.put("companyId", companyUserEntity.getCompanyId());
                        this.userPermissionDao.deleteByUserId(param);
                    }

                } else {
                    //如果是管理员，则默认添加到公司下
                    OrgUserEntity adminOrgEntity = new OrgUserEntity();
                    adminOrgEntity.setId(StringUtil.buildUUID());
                    adminOrgEntity.setCompanyId(departEntity.getCompanyId());
                    adminOrgEntity.setOrgId(departEntity.getCompanyId());
                    adminOrgEntity.setUserId(accountId);
                    adminOrgEntity.setCuId(admin.getId());
                    orguserDao.insert(adminOrgEntity);
                    info += "提示：管理员移至当前公司下面";
                }
            }

        }
        //删除一级部门，就应删除一级部门群
        if ("1".equals(departEntity.getDepartLevel())) {
            this.imService.deleteImGroup(departEntity.getId(), 1);
        }
        return new AjaxMessage().setCode("0").setInfo(info);
    }

    @Override
    public int getDepartByCompanyIdCount(String companyId) throws Exception {
        return departDao.getDepartByCompanyIdCount(companyId);
    }

    /**
     * 方法描述：获取具有某些角色的部门（当前人在当前组织下的部门）
     * 作者：MaoSF
     * 日期：2016/8/16
     * @param:
     * @return:
     */
    @Override
    public List<DepartRoleDTO> getDepartByRole(Map<String, Object> paraMap) {
        return departDao.getDepartByRole(paraMap);
    }

    /**
     * 方法描述：获取所有组织的角色（在当前公司下）
     * 作者：MaoSF
     * 日期：2016/8/16
     * @param:
     * @return:
     */
    @Override
    public List<DepartRoleDTO> getOrgRole(Map<String, Object> paraMap) {
        return departDao.getOrgRole(paraMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DepartDTO> getDepartByCompanyIdWS(Map<String, Object> paraMap) throws Exception {
        List<DepartEntity> departList = departDao.getDepartByCompanyIdWS(paraMap);
        List<DepartDTO> departDtoList = new ArrayList<DepartDTO>();
        departDtoList = BaseDTO.copyFields(departList, DepartDTO.class);
        return departDtoList;
    }

    @Override
    public List<DepartEntity> selectNotCreateGroupDepart(Map<String, Object> paraMap) throws Exception {
        return departDao.selectNotCreateGroupDepart(paraMap);
    }

    @Override
    public List<DepartEntity> selectCreateGroupDepart(Map<String, Object> paraMap) throws Exception {
        return departDao.selectCreateGroupDepart(paraMap);
    }

    /**
     * 方法描述：查询部门下所有的子部门及人员
     * 作者：MaoSF
     * 日期：2017/2/6
     * @param:
     * @return:
     */
    @Override
    public ResponseBean getDepartAndGroup(Map<String, Object> mapass) throws Exception {
        //路径id集合
        DepartEntity departEntity = this.selectById(mapass.get("departId"));
        if (departEntity == null) {
            return ResponseBean.responseError("查询失败");
        }
        List<String> idList = new ArrayList<>();
        idList.add(departEntity.getCompanyId());
        idList.addAll(CollectionUtils.arrayToList(departEntity.getDepartPath().split("-")));
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", mapass.get("companyId").toString());
        map.put("pid", mapass.get("departId").toString());
        List<DepartDTO> listDepart = this.getDepartByCompanyIdWS(map);
        map.clear();
        map.put("orgId", mapass.get("departId").toString());

        Map<String, Object> paramCompanyUser = new HashMap<String, Object>();
        if (null != mapass.get("departId").toString()) {
            paramCompanyUser.put("orgId", mapass.get("departId"));
        }
        paramCompanyUser.put("auditStatus", "1");
        paramCompanyUser.put("fastdfsUrl", mapass.get("fastdfsUrl"));
        //公司人员
        List<CompanyUserDataDTO> companyUserList = companyUserDao.getUserByOrgId(paramCompanyUser);
        //部门全路径
        String departFullName = this.departDao.getDepartFullName((String) mapass.get("departId"));
        //获取所有父级部门名称
        List<DepartEntity> departs = this.departDao.getParentDepartsByDepartPath(departEntity.getDepartPath());
        String aliasName = "";
        CompanyEntity companyEntity = this.companyDao.selectById(departEntity.getCompanyId());
        if (companyEntity != null) {
            aliasName = companyEntity.getAliasName();
        }
        departFullName = companyEntity.getAliasName() + "-" + departFullName;
        //获取整条路径下父级的部门
        List<Map<String, String>> parentDepartList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", departEntity.getCompanyId());
        map1.put("departName", aliasName);
        parentDepartList.add(map1);
        for (DepartEntity d : departs) {
            map1 = new HashMap<>();
            map1.put("id", d.getId());
            map1.put("departName", d.getDepartName());
            parentDepartList.add(map1);
        }

        //获取父部门
        String parentDepartName = "";
        if (departEntity.getDepartPath().contains("-")) {
            DepartEntity parentDepartEntity = this.selectById(departEntity.getPid());
            if (parentDepartEntity != null) {
                parentDepartName = parentDepartEntity.getDepartName();
            }
        } else {
            CompanyEntity company = this.companyDao.selectById(departEntity.getPid());
            if (company != null) {
                parentDepartName = companyEntity.getCompanyShortName();
            }
        }
        //返回值
        return ResponseBean.responseSuccess("查询成功")
                .addData("departList", listDepart)
                .addData("parentDepartList", parentDepartList)
                .addData("departUserList", companyUserList)
                .addData("idList", idList)
                .addData("departFullName", departFullName)
                .addData("parentDepartName", parentDepartName)
                .addData("departName", departEntity.getDepartName());
    }


    @Override
    public List<DepartEntity> selectChildDepartEntity(String departPath) throws Exception {
        return departDao.getDepartsByDepartPath(departPath);
    }
}
