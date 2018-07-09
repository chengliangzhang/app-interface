package com.maoding.user.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.UserDTO;
import com.maoding.user.dto.UserInfoDTO;
import com.maoding.user.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<UserEntity>{

	/**
	 * 方法描述：获取个人信息
	 * 作者：MaoSF
	 * 日期：2017/5/23
	 * @param:
	 * @return:
	 */
	UserInfoDTO getUserInfo(String id);

	/**
	 * 方法描述：保存人员信息
	 * 作        者：MaoSF
	 * 日        期：2015年11月26日-下午4:51:31
	 * @param entity
	 * @return
	 */
	AjaxMessage saveOrUpdateUser(AccountDTO entity)throws Exception;

	/**
	 * 方法描述：根据id获取人员信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午8:44:32
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserDTO getUserById(String id)throws Exception;
	
	/**
	 * 方法描述：根据userId员工获奖信息
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-上午11:23:03
	 * @param userId
	 * @return
	 */
	List<UserAwardsEntity> getUserAwardshonorsByUserId(String userId);
	/**
	 * 方法描述：根据userId获取个人继续教育 
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<UserContinueEducationEntity> getUserContinueeducationByUserId(String userId);
	/**
	 * 方法描述：根据userId获取员工教育背景
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<UserEducationBackGroundEntity> getUserEducationbgByUserId(String userId);
	
	/**
	 * 方法描述：根据userId获取人员资质信息
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<UserQualificationsEntity> getUserQualificationsByUserId(String userId);
	/**
	 * 方法描述：根据userId获取员工技术信息
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<com.maoding.user.entity.UserTechnicalEntity> getUserTechnicalByUserId(String userId);
	/**
	 * 方法描述：根据userId获取员工工作经历信息
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<UserWorkExperienceEntity> getUserWorkexperienceByUserId(String userId);
	/**
	 * 方法描述：根据userId获取员工工作业绩
	 * 作        者：MaoSF
	 * 日        期：2015年6月30日-下午4:38:35
	 * @param userId
	 * @return
	 */
	List<UserWorkPerformanceEntity> getUserWorkperformanceByUserId(String userId);
	
	/**
	 * 方法描述：根据id查找员工获奖信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserAwardsEntity getAwardshonorsById(String id);
	/**
	 * 方法描述：根据id查找员工继续教育信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserContinueEducationEntity getContinueeducationById(String id);

	/**
	 * 方法描述：根据id查找员工教育背景
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserEducationBackGroundEntity getEducationbgById(String id) ;
	/**
	 * 方法描述：根据id查找员工资质信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserQualificationsEntity getQualificationsById(String id);
	/**
	 * 方法描述：根据id查找员工技术信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserTechnicalEntity getTechnicalById(String id);
	
	/**
	 * 方法描述：根据id查找员工工作经历
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserWorkExperienceEntity getWorkexperienceById(String id);
	/**
	 * 方法描述：根据id查找员工业绩信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	UserWorkPerformanceEntity getWorkperformanceById(String id);
	
	/**
	 * 方法描述：保存员工获奖信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:31:03
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateAwardshonors(UserAwardsEntity entity) ;
	
	/**
	 * 方法描述：保存员工继续教育信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:31:27
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateContinueeducation(UserContinueEducationEntity entity) ;
	
	/**
	 * 方法描述：保存员工教育背景信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:32:11
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateEducationbg(UserEducationBackGroundEntity entity);
	
	
	
	
	/**
	 * 方法描述：保存员工资质信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:32:39
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateQualifications(UserQualificationsEntity entity);
	
	/**
	 * 方法描述：保存员工技术信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:32:57
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateTechnical(UserTechnicalEntity entity) ;
	/**
	 * 方法描述：保存员工工作经历信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:33:12
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateWorkexperience(UserWorkExperienceEntity entity);
	
	
	/**
	 * 方法描述：保存员工工业绩历信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:33:12
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateWorkperformance(UserWorkPerformanceEntity entity);
	
	/**
	 * 方法描述：根据id删除员工获奖信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delAwardshonorsById(String id);
	/**
	 * 方法描述：根据id删除员工继续教育信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delContinueeducationById(String id);

	/**
	 * 方法描述：根据id删除员工教育背景
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delEducationbgById(String id) ;
	/**
	 * 方法描述：根据id删除员工其他附件信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
//	public void deleteByUserId(String persoanlId,String attachType) ;
	/**
	 * 方法描述：根据id删除员工资质信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delQualificationsById(String id);
	/**
	 * 方法描述：根据id删除员工技术信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delTechnicalById(String id);
	/**
	 * 方法描述：根据id删除员工工作经历
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delWorkexperienceById(String id);
	/**
	 * 方法描述：根据id删除员工业绩信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月3日-下午6:31:43
	 * @param id
	 * @return
	 */
	int delWorkperformanceById(String id);
	
	/**
	 * 方法描述：根据类型查询关注
	 * 作        者：MaoSF
	 * 日        期：2016年3月18日-上午9:50:00
	 * @param paraMap
	 * @return
	 */
	List<UserAttachEntity>  getAttachByType(Map<String, Object> paraMap);

	/**
	 * 方法描述：保存员工其他附件信息
	 * 作        者：MaoSF
	 * 日        期：2015年7月6日-下午4:32:25
	 * @param entity
	 * @throws Exception 
	 */
	int saveOrUpdateAttach(UserAttachEntity entity);


	/*******************************v2******************************************/
	/**
	 * 方法描述：添加或修改用户信息 v2
	 * 作    者 : ChenZhujie
	 * 日    期 : 2016/12/23
	 */
	AjaxMessage v2SaveOrUpdateUser(AccountDTO accountDTO)throws Exception;





}
