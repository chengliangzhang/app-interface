package com.maoding.user.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.user.dao.*;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.UserDTO;
import com.maoding.user.dto.UserInfoDTO;
import com.maoding.user.entity.*;
import com.maoding.user.service.UserAttachService;
import com.maoding.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserServiceImpl
 * 类描述：人员信息Service
 * 作    者：MaoSF
 * 日    期：2015年11月23日-下午3:37:01
 */
@Service("userService")
public class UserServiceImpl extends GenericService<UserEntity>  implements UserService{

	@Autowired
	private UserDao userDao;

	@Autowired
	private AppUseDao appUseDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserAwardsDao userAwardsDao;
	@Autowired
	private UserContinueEducationDao userContinueEducationDao;
	@Autowired
	private UserEducationBackGroundDao userEducationBackGroundDao;
	@Autowired
	private UserQualificationsDao userQualificationsDao;
	@Autowired
	private UserTechnicalDao userTechnicalDao;
	@Autowired
	private UserWorkExperienceDao userWorkExperienceDao;
	@Autowired
	private UserWorkPerformanceDao userWorkPerformanceDao;
	@Autowired
	private UserAttachDao userAttachDao;
	
	@Autowired
	private UserAttachService userAttachService;

	@Value("${fastdfs.url}")
	protected String fastdfsUrl;
	/**
	 * 方法描述：获取个人信息
	 * 作者：MaoSF
	 * 日期：2017/5/23
	 */
	@Override
	public UserInfoDTO getUserInfo(String id) {
		return this.userDao.getUserInfo(id,this.fastdfsUrl);
	}

	@Override
	public AjaxMessage saveOrUpdateUser(AccountDTO entity)throws Exception{
		UserEntity userEntity=new UserEntity();
		BaseDTO.copyFields(entity, userEntity);
		if(null==entity.getId() || "".equals(entity.getId())){
			userEntity.setId(StringUtil.buildUUID());
			userDao.insert(userEntity);
			return new AjaxMessage().setCode("0").setInfo("保存成功");
		}else{
			userDao.updateById(userEntity);
			AccountEntity accountEntity = new AccountEntity();
			accountEntity.setId(entity.getId());
			accountEntity.setUserName(entity.getUserName());
			accountDao.updateById(accountEntity);
			return new AjaxMessage().setCode("0").setInfo("保存成功");
		}
	}


	@Override
	public UserDTO getUserById(String id) throws Exception {
		
		UserEntity user = userDao.selectById(id);
		UserDTO dto = new UserDTO();
		BaseDTO.copyFields(user, dto);
		
		List<UserAttachEntity> list = userAttachService.getUserAttachByUserId(id);
		if(list!=null && list.size()>0){
			dto.setAttachPath(list.get(0).getAttachPath());	
		}
		return dto;
	}
	
	@Override
	public List<UserAwardsEntity> getUserAwardshonorsByUserId(
			String userId) {
		return userAwardsDao.getUserAwardshonorsByUserId(userId);
	}

	@Override
	public List<UserContinueEducationEntity> getUserContinueeducationByUserId(
			String userId) {
		return userContinueEducationDao.getUserContinueeducationByUserId(userId);
	}

	@Override
	public List<UserEducationBackGroundEntity> getUserEducationbgByUserId(String userId) {
		return userEducationBackGroundDao.getUserEducationbgByUserId(userId);
	}

	

	@Override
	public List<UserQualificationsEntity> getUserQualificationsByUserId(
			String userId) {
		return userQualificationsDao.getUserQualificationsByUserId(userId);
	}

	@Override
	public List<UserTechnicalEntity> getUserTechnicalByUserId(String userId) {
		return userTechnicalDao.getUserTechnicalByUserId(userId);
	}

	@Override
	public List<UserWorkExperienceEntity> getUserWorkexperienceByUserId(
			String userId) {
		return userWorkExperienceDao.getUserWorkexperienceByUserId(userId);
	}

	@Override
	public List<UserWorkPerformanceEntity> getUserWorkperformanceByUserId(
			String userId) {
		return userWorkPerformanceDao.getUserWorkperformanceByUserId(userId);
	}



	@Override
	public UserAwardsEntity getAwardshonorsById(String id) {
		return userAwardsDao.selectById(id);
	}

	@Override
	public UserContinueEducationEntity getContinueeducationById(String id) {
		return userContinueEducationDao.selectById(id);
	}

	@Override
	public UserEducationBackGroundEntity getEducationbgById(String id) {
		return userEducationBackGroundDao.selectById(id);
	}

	@Override
	public UserQualificationsEntity getQualificationsById(String id) {
		return userQualificationsDao.selectById(id);
	}

	@Override
	public UserTechnicalEntity getTechnicalById(String id) {
		return userTechnicalDao.selectById(id);
	}

	@Override
	public UserWorkExperienceEntity getWorkexperienceById(String id) {
		return userWorkExperienceDao.selectById(id);
	}

	@Override
	public UserWorkPerformanceEntity getWorkperformanceById(String id) {
		return userWorkPerformanceDao.selectById(id);
	}

	@Override
	public int saveOrUpdateAwardshonors(UserAwardsEntity entity)
			{
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userAwardsDao.insert(entity);
		}else{
			return userAwardsDao.updateById(entity);
		}
	}

	
	@Override
	public int saveOrUpdateContinueeducation(UserContinueEducationEntity entity)
			{
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userContinueEducationDao.insert(entity);
		}else{
			return userContinueEducationDao.updateById(entity);
		}
	}

	@Override
	public int saveOrUpdateEducationbg(UserEducationBackGroundEntity entity)
			 {
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userEducationBackGroundDao.insert(entity);
		}else{
			return userEducationBackGroundDao.updateById(entity);
		}
	}

	@Override
	public int saveOrUpdateQualifications(UserQualificationsEntity entity)
			{
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userQualificationsDao.insert(entity);
		}else{
			return userQualificationsDao.updateById(entity);
		}
		
	}

	@Override
	public int saveOrUpdateTechnical(UserTechnicalEntity entity)
			{
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userTechnicalDao.insert(entity);
		}else{
			return userTechnicalDao.updateById(entity);
		}
		
	}
	
	@Override
	public int saveOrUpdateWorkexperience(UserWorkExperienceEntity entity)
			 {
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userWorkExperienceDao.insert(entity);
		}else{
			return userWorkExperienceDao.updateById(entity);
		}
	}

	@Override
	public int saveOrUpdateWorkperformance(UserWorkPerformanceEntity entity)
			 {
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userWorkPerformanceDao.insert(entity);
		}else{
			return userWorkPerformanceDao.updateById(entity);
		}
		
	}

	
	@Override
	public int delAwardshonorsById(String id) {
		return userAwardsDao.deleteById(id);
		
	}

	@Override
	public int delContinueeducationById(String id) {
		return userContinueEducationDao.deleteById(id);
	}

	@Override
	public int delEducationbgById(String id) {
		return userEducationBackGroundDao.deleteById(id);
	}
	
	@Override
	public int delQualificationsById(String id) {
		return userQualificationsDao.deleteById(id);		
	}

	@Override
	public int delTechnicalById(String id) {
		return userTechnicalDao.deleteById(id);	
	}

	@Override
	public int delWorkexperienceById(String id) {
		return userWorkExperienceDao.deleteById(id);			
	}

	@Override
	public int delWorkperformanceById(String id) {
		return userWorkPerformanceDao.deleteById(id);		
	}

	@Override
	public List<UserAttachEntity> getAttachByType(Map<String, Object> paraMap) {
	   
		return userAttachDao.getAttachByType(paraMap);
	}

	@Override
	public int saveOrUpdateAttach(UserAttachEntity entity)  {
		if(null==entity.getId() || "".equals(entity.getId()))
		{
			entity.setId(StringUtil.buildUUID());
			return userAttachDao.insert(entity);
		}else{
			return userAttachDao.updateById(entity);
		}
		
	}

/*****************************************************v2*******************************************************/
	@Override
	public AjaxMessage v2SaveOrUpdateUser(AccountDTO entity)throws Exception{

		UserEntity userEntity=new UserEntity();
		entity.setId(entity.getAccountId());
		BaseDTO.copyFields(entity, userEntity);
		if(null==entity.getId() || "".equals(entity.getId())){
			userEntity.setId(StringUtil.buildUUID());
			userDao.insert(userEntity);
			return new AjaxMessage().setCode("0").setInfo("操作成功");
		}else{
			userDao.updateById(userEntity);
			if(null !=entity.getUserName() && !"".equals(entity.getUserName())){
				AccountEntity accountEntity = new AccountEntity();
				accountEntity.setId(entity.getId());
				accountEntity.setUserName(entity.getUserName());
				accountDao.updateById(accountEntity);
			}



			//附件保存
			if(null != entity.getFilePath() && !"".equals(entity.getFilePath())){
				UserAttachEntity attachEntity=new UserAttachEntity();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("userId", entity.getAccountId());
				map.put("attachType", "5");
				List<UserAttachEntity>attachList=this.getAttachByType(map);
				if(null !=attachList && attachList.size()>0){
					attachEntity=attachList.get(0);
					attachEntity.setAttachPath(entity.getFilePath());
					attachEntity.setFileGroup(entity.getFileGroup());
				}else{
					attachEntity.setUserId(entity.getAccountId());
					attachEntity.setAttachPath(entity.getFilePath());
					attachEntity.setFileGroup(entity.getFileGroup());
					attachEntity.setAttachType("5");
					attachEntity.setAttachName(entity.getFileName());
					attachEntity.setCreateBy(entity.getAccountId());
				}
				this.saveOrUpdateAttach(attachEntity);
			}
			return new AjaxMessage().setCode("0").setInfo("操作成功");
		}
	}
}
