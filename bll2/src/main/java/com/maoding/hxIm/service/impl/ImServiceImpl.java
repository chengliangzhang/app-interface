package com.maoding.hxIm.service.impl;

import com.google.common.collect.Maps;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.NewBaseService;
import com.maoding.core.bean.ApiResult;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.JsonUtils;
import com.maoding.core.util.OkHttpUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.constDefine.*;
import com.maoding.hxIm.dao.ImAccountDao;
import com.maoding.hxIm.dao.ImGroupDao;
import com.maoding.hxIm.dao.ImGroupMemberDao;
import com.maoding.hxIm.dto.*;
import com.maoding.hxIm.entity.ImAccountEntity;
import com.maoding.hxIm.entity.ImGroupEntity;
import com.maoding.hxIm.service.ImQueueProducer;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dao.DepartDao;
import com.maoding.org.dto.CompanyUserGroupDTO;
import com.maoding.org.entity.DepartEntity;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by sandy on 2017/8/7.
 */
@Service("imService")
public class ImServiceImpl extends NewBaseService implements ImService {

    private static final Logger logger = LoggerFactory.getLogger(ImServiceImpl.class);

    @Value("${fastdfs.url}")
    private String fastdfsUrl;

    @Autowired
    private ImGroupDao imGroupDao;

    @Autowired
    private DepartDao departDao;

    @Autowired
    private ImGroupMemberDao imGroupMemberDao;

    @Autowired
    private ImQueueProducer imQueueProducer;

    @Autowired
    private ImAccountDao imAccountDao;

    /**
     * 创建im账号
     */
    @Override
    public void createImAccount(String userId, String userName, String password) throws Exception {
        ImAccountDTO dto = new ImAccountDTO();
        dto.setAccountId(userId);
        dto.setAccountName(userName);
        dto.setPassword(password);
        ((ImService) AopContextCurrentProxy()).insertImAccount(dto);
        imQueueProducer.account_create(dto);
    }

    /**
     * 修改im密码
     */
    @Override
    public void updateImAccount(String userId, String password) throws Exception {
        ImAccountDTO dto = new ImAccountDTO();
        dto.setAccountId(userId);
        dto.setPassword(password);
        imQueueProducer.account_modifyPassword(dto);
    }

    /****************************************************************************************/


    /**
     * 方法描述：创建群组
     * 作者：MaoSF
     * 日期：2016/11/29
     * param：此处的groupId是组织id
     */
    public void createImGroup(String groupId, String admin, String companyName, Integer groupType) throws Exception {
        ImGroupDTO dto = new ImGroupDTO();
        dto.setGroupOwner(admin);
        dto.setGroupName(companyName);
        dto.setOrgId(groupId);
        dto.setGroupType(groupType);
        dto.setGroupId(groupId);//由于orgId在im_group中是唯一的，所有用orgId设置为id，方便查找,
        insertImGroup(dto);
        imQueueProducer.group_create(dto);
    }

    /**
     * 方法描述：修改群组
     * 作者：MaoSF
     * 日期：2016/11/29
     */
    public void updateImGroup(String groupId, String companyName, Integer groupType) throws Exception {
        ImGroupDTO dto = new ImGroupDTO();
        dto.setGroupName(companyName);
        dto.setOrgId(groupId);
        dto.setGroupId(groupId);
        dto.setGroupType(groupType);
        imQueueProducer.group_modifyGroupName(dto);
    }

    /**
     * 方法描述：删除群组
     * 作者：MaoSF
     * 日期：2016/11/29
     */
    public void deleteImGroup(String groupId, int groupType) throws Exception {
        ImGroupDTO dto = new ImGroupDTO();
        dto.setOrgId(groupId);
        dto.setGroupId(groupId);
        dto.setGroupType(groupType);
        imQueueProducer.group_delete(dto);
    }

    /****************************************************************************************/

    @Override
    public void addMembers(String groupId, String userId) throws Exception {
        ImGroupDTO dto = new ImGroupDTO();
        dto.setOrgId(groupId);
        dto.setGroupId(groupId);
        dto.setMembers(Arrays.asList(new ImGroupMemberDTO(userId, null)));
        imQueueProducer.group_addMembers(dto);
    }

    @Override
    public void deleteMembers(String groupId, String userId) throws Exception {
        ImGroupDTO dto = new ImGroupDTO();
        dto.setOrgId(groupId);
        dto.setGroupId(groupId);
        dto.setMembers(Arrays.asList(new ImGroupMemberDTO(userId, null)));
        imQueueProducer.group_deleteMembers(dto);
    }

    /****************************************************************************************/

    @Override
    public ImGroupDataDTO imGroupInfo(ImGroupQuery query) throws Exception {
        Integer type = query.getType();
        query.setUrl(this.fastdfsUrl);
        query.setFastdfsUrl(this.fastdfsUrl);
        ImGroupMemberQuery memberQuery = null;
        ImGroupDataDTO data = new ImGroupDataDTO();
        //部门
        if (type == ImGroupType.DEPARTMENT) {
            query.setOrgIdGroupId(query.getOrgId());
            List<ImGroupDataDTO> departGroupList = this.imGroupDao.selectDepartGroupList(query);
            if (departGroupList != null && departGroupList.size() > 0) {
                data = departGroupList.get(0);
                DepartEntity departEntity = departDao.selectById(data.getOrgId());
                if (departEntity != null) {
                    memberQuery = new ImGroupMemberQuery();
                    memberQuery.setOrgId(data.getOrgId());
                    memberQuery.setUrl(this.fastdfsUrl);
                    memberQuery.setCompanyId(departEntity.getCompanyId());
                    List<ImGroupMemberDataDTO> listDepartMemebers = imGroupMemberDao.selectNewDepartGroupMembers(memberQuery);
                    data.setMemberInfo(listDepartMemebers);
                }
            }
        }
        //公司
        if (type == ImGroupType.COMPANY) {
            query.setUserId(query.getAccountId());
            List<ImGroupDataDTO> ls = imGroupDao.selectCompanyGroupByParameter(query);
            if (ls != null && ls.size() > 0) {
                data = ls.get(0);
                memberQuery = new ImGroupMemberQuery();
                memberQuery.setOrgId(data.getOrgId());
                memberQuery.setUrl(this.fastdfsUrl);
                List<ImGroupMemberDataDTO> members = imGroupMemberDao.selectNewGroupMembers(memberQuery);
                data.setMemberInfo(members);
            }
        }
        //项目群
        if (type == ImGroupType.PROJECT) {
            List<ImGroupDataDTO> ls = imGroupDao.selectProjectGroupByParameter(query);
            if (ls != null && ls.size() > 0) {
                data = ls.get(0);
                memberQuery = new ImGroupMemberQuery();
                memberQuery.setOrgId(data.getOrgId());
                memberQuery.setUrl(this.fastdfsUrl);
                memberQuery.setProjectGroup("projectGroup");
                //改变，不加company条件
                List<ImGroupMemberDataDTO> members = imGroupMemberDao.selectNewGroupMembers(memberQuery);
                data.setMemberInfo(members);
            }
        }
        //自定义群
        if (type == ImGroupType.CUSTOM) {
            query.setCompanyId(null);//customGroupList需要把所有的自定义群组都查询出来，所有需要移除companyId（如果有的话）
            List<ImGroupDataDTO> ls = imGroupDao.selectCustomGroupByParameter(query);
            if (ls != null && ls.size() > 0) {
                data = ls.get(0);
                memberQuery = new ImGroupMemberQuery();
                memberQuery.setUrl(this.fastdfsUrl);
                memberQuery.setGroupId(query.getGroupId());
                //改变，不加company条件
                List<ImGroupMemberDataDTO> members = imGroupMemberDao.selectCustomGroupMembers(memberQuery);
                data.setMemberInfo(members);
            }
        }
        return data;
    }

    @Override
    public List<ImGroupDataDTO> listAllGroupByUserId(ImGroupQuery query) throws Exception {
        query.setFastdfsUrl(this.fastdfsUrl);
        query.setUrl(this.fastdfsUrl);
        if (StringUtil.isNullOrEmpty(query.getUserId())) {
            query.setUserId(query.getAccountId());
        }
        return imGroupDao.listAllGroupByUserId(query);
    }

    @Override
    public List<CompanyUserGroupDTO> listCustomerImGroupMember(String groupId) throws Exception {
        ImGroupMemberQuery memberQuery = new ImGroupMemberQuery();
        memberQuery.setFastdfsUrl(this.fastdfsUrl);
        memberQuery.setGroupId(groupId);
        return imGroupMemberDao.listCustomerImGroupMember(memberQuery);
    }

    @Override
    public Map<String, Object> imGroupListNew(ImGroupQuery query) throws Exception {
        List<List<ImGroupDataDTO>> returnList = new ArrayList<>();
        List<ImGroupDataDTO> resultProjectGroupListDto = new ArrayList<>();
        List<ImGroupDataDTO> resultCustomGroupList = new ArrayList<>();
        List<ImGroupDataDTO> list = listAllGroupByUserId(query);
        returnList.add(new ArrayList<>());
        for (ImGroupDataDTO dto : list) {
            if (dto.getGroupType() == ImGroupType.COMPANY || dto.getGroupType() == ImGroupType.DEPARTMENT) {
                returnList.get(0).add(dto);
            }

            if (dto.getGroupType() == ImGroupType.CUSTOM) {
                resultCustomGroupList.add(dto);
            }

            if (dto.getGroupType() == ImGroupType.PROJECT) {
                resultProjectGroupListDto.add(dto);
            }
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("companyImData", returnList);
        returnMap.put("projectGroupList", resultProjectGroupListDto);
        returnMap.put("customGroupList", resultCustomGroupList);
        return returnMap;
    }

    @Override
    public Map<String, Object> listAllGroupByUserIdAndCompanyId(ImGroupQuery query) throws Exception {
        query.setFastdfsUrl(this.fastdfsUrl);
        query.setUrl(this.fastdfsUrl);
        query.setUserId(query.getAccountId());
        List<ImGroupDataDTO> companyGroupList = new ArrayList<>();
        List<ImGroupDataDTO> departGroupList = new ArrayList<>();
        List<ImGroupDataDTO> customGroupList = new ArrayList<>();
        List<ImGroupDataDTO> list = this.imGroupDao.listAllGroupByUserIdAndCompanyId(query);
        for (ImGroupDataDTO dto : list) {
            if (dto.getGroupType() == ImGroupType.COMPANY) {
                companyGroupList.add(dto);
            }

            if (dto.getGroupType() == ImGroupType.DEPARTMENT) {
                departGroupList.add(dto);
            }

            if (dto.getGroupType() == ImGroupType.CUSTOM) {
                customGroupList.add(dto);
            }
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("companyGroupList", companyGroupList);
        returnMap.put("departGroupList", departGroupList);
        returnMap.put("customGroupList", customGroupList);
        return returnMap;
    }

    @Override
    public ResponseBean saveCustomGroup(ImGroupCustomerDTO dto) throws Exception {
        //新增
        if (null == dto.getOrgId()) {
            ImGroupDTO group = new ImGroupDTO();
            group.setRootOrgId(dto.getAppOrgId());
            String id = StringUtil.buildUUID();
            group.setGroupId(id);
            group.setOrgId(id);
            group.setGroupOwner(dto.getAccountId());
            group.setGroupName(dto.getName());
            group.setGroupType(ImGroupType.CUSTOM);
            List<ImGroupCustomerUserDTO> list = dto.getImGroupCustomerUserList();
            for (int i = 0; i < list.size(); i++) {
                ImGroupCustomerUserDTO imGroupCustomerUserDTO = list.get(i);
                ImGroupMemberDTO memberDTO = new ImGroupMemberDTO();
                BaseDTO.copyFields(imGroupCustomerUserDTO, memberDTO);
                group.getMembers().add(memberDTO);
            }

            ImQueueDTO queueDTO = new ImQueueDTO();
            queueDTO.setIgnoreFix(true);
            queueDTO.setTargetId(id);
            queueDTO.setContent(JsonUtils.obj2json(group));
            queueDTO.setOperation(ImOperation.GROUP_CREATE);
            queueDTO.setQueueNo(imQueueProducer.generateQueueNo());
            queueDTO.setRetry(0);

            //新起事务插入
            ((ImService) AopContextCurrentProxy()).insertImGroup(group);

            ResponseBean responseBean = imServerRequest(queueDTO, "saveCustomGroup");
            if (responseBean.getError().equalsIgnoreCase("0"))
                return ResponseBean.responseSuccess("创建自定义成功");
        }
        return ResponseBean.responseError("创建自定义群失败");
    }

    /**
     *
     * @param dto(老接口：groupId，环信id)
     */
    @Override
    public ResponseBean updateCustomGroup(ImGroupCustomerDTO dto) throws Exception {
        // TODO 查询表，获取orgId
        Map<String, Object> map = Maps.newHashMap();
        map.put("groupNo", dto.getGroupId());// (新表中的group_no,环信id)
        List<ImGroupEntity> groupList = imGroupDao.getImGroupsByParam(map);
        if (!CollectionUtils.isEmpty(groupList)) {
            ImGroupDTO group = new ImGroupDTO();
            group.setGroupId(groupList.get(0).getOrgId());//im_group表中，id=orgId，groupId表示的是im_group中的id
            group.setOrgId(groupList.get(0).getOrgId());
            group.setGroupOwner(dto.getAdmin());
            group.setGroupName(dto.getName());
            group.setGroupType(ImGroupType.CUSTOM);

            ImQueueDTO queueDTO = new ImQueueDTO();
            queueDTO.setIgnoreFix(true);
            queueDTO.setTargetId(groupList.get(0).getOrgId());
            queueDTO.setContent(JsonUtils.obj2json(group));
            queueDTO.setQueueNo(imQueueProducer.generateQueueNo());
            queueDTO.setRetry(0);
            if (!StringUtil.isNullOrEmpty(dto.getAdmin())) {
                queueDTO.setOperation(ImOperation.GROUP_TRANSFER_GROUP_OWNER);
            } else {
                queueDTO.setOperation(ImOperation.GROUP_MODIFY_GROUP_NAME);
            }
            return imServerRequest(queueDTO, "updateCustomGroup");
        }

        return ResponseBean.responseError("更新自定义群失败");

    }

    @Override
    public ResponseBean deleteGroup(ImGroupCustomerDTO dto) throws Exception {
        // TODO 查询表，获取orgId
        Map<String, Object> map = Maps.newHashMap();
        map.put("groupNo", dto.getGroupId());// (新表中的group_no,环信id)
        List<ImGroupEntity> groupList = imGroupDao.getImGroupsByParam(map);
        if (!CollectionUtils.isEmpty(groupList)) {
            ImGroupDTO group = new ImGroupDTO();
            group.setGroupId(groupList.get(0).getOrgId());//im_group表中，id=orgId，groupId表示的是im_group中的id
            group.setOrgId(groupList.get(0).getOrgId());
            group.setGroupType(ImGroupType.CUSTOM);

            ImQueueDTO queueDTO = new ImQueueDTO();
            queueDTO.setIgnoreFix(true);
            queueDTO.setContent(JsonUtils.obj2json(group));
            queueDTO.setQueueNo(imQueueProducer.generateQueueNo());
            queueDTO.setTargetId(groupList.get(0).getOrgId());
            queueDTO.setRetry(0);
            queueDTO.setOperation(ImOperation.GROUP_DELETE);
            return imServerRequest(queueDTO, "updateCustomGroup");
        }
        return ResponseBean.responseError("删除自定义群失败");
    }

    @Override
    public ResponseBean addMemberToGroup(ImGroupCustomerDTO dto) throws Exception {
        ImGroupDTO group = new ImGroupDTO();
        group.setGroupId(dto.getOrgId());
        group.setOrgId(dto.getOrgId());
        group.setGroupType(ImGroupType.CUSTOM);
        List<ImGroupCustomerUserDTO> list = dto.getImGroupCustomerUserList();
        for (int i = 0; i < list.size(); i++) {
            ImGroupCustomerUserDTO imGroupCustomerUserDTO = list.get(i);
            ImGroupMemberDTO memberDTO = new ImGroupMemberDTO();
            BaseDTO.copyFields(imGroupCustomerUserDTO, memberDTO);
            group.getMembers().add(memberDTO);
        }

        ImQueueDTO queueDTO = new ImQueueDTO();
        queueDTO.setIgnoreFix(true);
        queueDTO.setTargetId(dto.getOrgId());
        queueDTO.setContent(JsonUtils.obj2json(group));
        queueDTO.setOperation(ImOperation.GROUP_MEMBER_ADD);
        queueDTO.setQueueNo(imQueueProducer.generateQueueNo());
        queueDTO.setRetry(0);
        return imServerRequest(queueDTO, "addMemberToGroup");
    }

    @Override
    public ResponseBean deleteMemberFromGroup(ImGroupUserDTO dto) throws Exception {
        ImGroupDTO group = new ImGroupDTO();
        group.setGroupId(dto.getOrgId());
        group.setOrgId(dto.getOrgId());
        group.setGroupType(ImGroupType.CUSTOM);
        String[] userIds = dto.getUserIds().split(",");
        for (int i = 0; i < userIds.length; i++) {
            ImGroupMemberDTO memberDTO = new ImGroupMemberDTO();
            memberDTO.setAccountId(userIds[i]);
            group.getMembers().add(memberDTO);
        }

        ImQueueDTO queueDTO = new ImQueueDTO();
        queueDTO.setIgnoreFix(true);
        queueDTO.setTargetId(dto.getOrgId());
        queueDTO.setContent(JsonUtils.obj2json(group));
        queueDTO.setOperation(ImOperation.GROUP_MEMBER_DELETE);
        queueDTO.setQueueNo(imQueueProducer.generateQueueNo());
        queueDTO.setRetry(0);
        return imServerRequest(queueDTO, "deleteMemberFromGroup");
    }


    private ResponseBean imServerRequest(ImQueueDTO queueDTO, String method) throws Exception {
        Response res = null;
        try {
            logger.info("ImServiceImpl {} {} {}", method, ImServer.URL_GROUP_HANDLE, JsonUtils.obj2json(queueDTO));
            res = OkHttpUtils.postJson(ImServer.URL_GROUP_HANDLE, queueDTO);
        } catch (IOException e) {
            logger.error("imServerRequest 请求发生异常", e);
            return ResponseBean.responseError("自定义群失败");
        } catch (Exception e) {
            logger.error("imServerRequest 请求发生异常", e);
            return ResponseBean.responseError("自定义群失败");
        }
        if (res.isSuccessful()) {
            try {
                ApiResult apiResult = JsonUtils.json2pojo(res.body().string(), ApiResult.class);
                ResponseBean responseBean = new ResponseBean();
                responseBean.setError(apiResult.getCode());
                responseBean.setMsg(apiResult.getMsg());
                return responseBean;
            } catch (Exception e) {
                logger.error("imServerRequest 解析返回结果失败", e);
            }
        } else {
            logger.info("自定义群失败：" + res.message());
        }
        return ResponseBean.responseError("自定义群失败");
    }

    private void insertAccount(ImAccountDTO dto) throws Exception {
        ImAccountEntity imAccount = new ImAccountEntity();
        BaseDTO.copyFields(dto, imAccount);
        imAccount.initEntity();
        imAccount.setId(dto.getAccountId());
        imAccount.setAccountStatus(ImAccountStatus.WAIT_CREATE);
        imAccount.setUpVersion(0L);
        imAccount.setDeleted(false);
        imAccount.setLastQueueNo(0L);
        imAccountDao.insert(imAccount);
    }


    /**
     * 插入imAccount
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertImAccount(ImAccountDTO dto) throws Exception {
        insertAccount(dto);
    }

    /**
     * 批量插入imAccount
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertImAccountBatch(List<ImAccountDTO> list) throws Exception {
        for (ImAccountDTO dto : list)
            insertAccount(dto);
    }

    /**
     * 插入imGroup
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertImGroup(ImGroupDTO dto) throws Exception {
        ImGroupEntity imGroup = new ImGroupEntity();
        BaseDTO.copyFields(dto, imGroup);
        imGroup.initEntity();
        imGroup.setId(dto.getGroupId());
        if (StringUtil.isNullOrEmpty(dto.getGroupStatus())) {
            imGroup.setGroupStatus(ImGroupStatus.WAIT_CREATE);
        }
        imGroup.setUpVersion(0L);
        imGroup.setDeleted(false);
        imGroup.setLastQueueNo(0L);
        imGroupDao.insert(imGroup);
    }
}
