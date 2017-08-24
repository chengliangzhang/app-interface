package com.maoding.org.service.impl;

import com.maoding.conllaboration.SyncCmd;
import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.MD5Helper;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.*;
import com.maoding.org.entity.*;
import com.maoding.org.service.CompanyUserAuditService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.dao.UserAttachDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.ShareInvateDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.entity.UserAttachEntity;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("companyUserAuditService")
public class CompanyUserAuditServiceImpl extends GenericService<CompanyUserAuditEntity>  implements CompanyUserAuditService {

	@Autowired
	private CompanyUserAuditDao companyUserAuditDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CompanyUserDao companyUserDao;

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private OrgUserDao orgUserDao;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SmsSender smsSender;

	@Autowired
	private CollaborationService collaborationService;

	@Autowired
	private CompanyUserService companyUserService;

	@Value("${server.url}")
	protected String serverUrl;

	@Value("${android.url}")
	protected String androidUrl;

	@Value("${ios.url}")
	protected String iosUrl;

	/**
	 * 方法描述：分享邀请注册验证方法
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 */
	public AjaxMessage validateShareInvateRegister(ShareInvateDTO dto) throws Exception{
		AjaxMessage ajax = new AjaxMessage();

		if (StringUtil.isNullOrEmpty(dto.getCellphone())){
			return ajax.setCode("1").setInfo("手机号不能为空");
		}

		if (StringUtil.isNullOrEmpty(dto.getUserName())){
			return ajax.setCode("1").setInfo("姓名不能为空");
		}

		if(StringUtil.isNullOrEmpty(dto.getCompanyId())){
			return ajax.setCode("1").setInfo("处理失败");
		}

		CompanyEntity companyEntity = companyDao.selectById(dto.getCompanyId());
		if (companyEntity==null){
			return ajax.setCode("1").setInfo("处理失败");
		}
		return ajax.setCode("0");
	}

	/**
	 * 方法描述：分享邀请注册（companyId,cellphone,userName）
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 *
	 * @param dto （companyId,cellphone,userName,code,userId）
	 */
	@Override
	public AjaxMessage shareInvateRegister(ShareInvateDTO dto) throws Exception {
		AjaxMessage ajax = this.validateShareInvateRegister(dto);
		if(!"0".equals(ajax.getCode())){
			return ajax;
		}
		String companyId = dto.getCompanyId();
		String userName = dto.getUserName();
		String cellphone = dto.getCellphone();

		AccountEntity account = accountDao.getAccountByCellphoneOrEmail(cellphone);
		if(account!=null){
			//判断人员是否存在
			CompanyUserEntity companyUserEntity =  companyUserDao.getCompanyUserByUserIdAndCompanyId(account.getId(),companyId);
			if(companyUserEntity!=null && "1".equals(companyUserEntity.getAuditStatus())){
				return ajax.setCode("1").setInfo("你已经是该公司成员");
			}
		}

		CompanyUserAuditEntity companyUserAuditEntity = companyUserAuditDao.selectByCellphoneAndCompanyId(cellphone,companyId);
		//如果审核表存在记录
		if(companyUserAuditEntity !=null){
			companyUserAuditEntity.setUserName(userName);
			companyUserAuditDao.updateById(companyUserAuditEntity);
			return ajax.setCode("0").setInfo("操作成功");
		}else{
			//如果不存在，向审核表中加入数据
			companyUserAuditEntity = new CompanyUserAuditEntity();
			BaseDTO.copyFields(dto,companyUserAuditEntity);
			companyUserAuditEntity.setId(StringUtil.buildUUID());
			companyUserAuditDao.insert(companyUserAuditEntity);
			return ajax.setCode("0").setInfo("操作成功");
		}

	}


	/**
	 * 方法描述：审核邀请注册（companyId,cellphone,userName）
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 *
	 * @param dto （companyId,cellphone,userName,code,userId）
	 */
	@Override
	public AjaxMessage auditShareInvate(ShareInvateDTO dto) throws Exception {

		AjaxMessage ajax = new AjaxMessage();

		String companyId = dto.getCompanyId();
		String userName = dto.getUserName();
		String cellphone = dto.getCellphone();

		if(!"1".equals(dto.getAuditStatus())){//如果不是同意，则直接删除
			companyUserAuditDao.deleteById(dto.getId());
			return ajax.setCode("0").setInfo("操作成功");
		}
		CompanyEntity company = companyDao.selectById(companyId);
		if (company==null){
			return ajax.setCode("1").setInfo("操作失败");
		}

		AccountEntity account = accountDao.getAccountByCellphoneOrEmail(cellphone);
		AccountDTO accountDTO = new AccountDTO();
		//如果没有注册，先注册
		boolean isNewUser = false;//是否是新用户
		if(account == null){
			isNewUser=true;
			account = this.accountService.createAccount(userName,cellphone,MD5Helper.getMD5For32("123456"));
		}else{
			BaseDTO.copyFields(account, accountDTO);
		}
		String currUserId = account.getId();
		//添加公司成员信息
		CompanyUserEntity companyUserEntity =  companyUserDao.getCompanyUserByUserIdAndCompanyId(currUserId,companyId);
		String companyUserId = "";
		//如果不为空
		if(companyUserEntity!=null){
			//如果存在记录，更新数据
			companyUserEntity.setUserName(userName);
			companyUserEntity.setAuditStatus("1");
			companyUserDao.updateById(companyUserEntity);

			companyUserId = companyUserEntity.getId();
			//查询是否存在部门
			Map<String,Object> map = new HashMap<>();
			map.put("cuId",companyUserId);
			List<OrgUserEntity> departList = this.orgUserDao.selectByParam(map);
			if(CollectionUtils.isEmpty(departList)){
				//人加入组织中间表
				OrgUserEntity orgUser = new OrgUserEntity();
				orgUser.setId(StringUtil.buildUUID());
				orgUser.setOrgId(companyId);
				orgUser.setCompanyId(companyId);
				orgUser.setCuId(companyUserId);
				orgUser.setUserId(currUserId);
				orgUser.setCreateBy(dto.getAccountId());
				orgUserDao.insert(orgUser);
			}
		}else{
			companyUserEntity = new CompanyUserEntity();
			companyUserId=StringUtil.buildUUID();
			companyUserEntity.setId(companyUserId);
			companyUserEntity.setCompanyId(companyId);
			companyUserEntity.setUserId(currUserId);
			companyUserEntity.setAuditStatus("1");//受邀请的
			companyUserEntity.setRelationType("2");
			companyUserEntity.setCreateBy(dto.getAccountId());
			companyUserEntity.setUserName(userName);
			companyUserDao.insert(companyUserEntity);


			//保存OrgEntity,组织基础表
			OrgEntity orgUserEntity=new OrgEntity();
			orgUserEntity.setId(companyUserId);//基础表id和人员表id一致
			orgUserEntity.setOrgType("4");
			orgUserEntity.setOrgStatus("0");
			orgUserEntity.setCreateBy(dto.getAccountId());
			orgDao.insert(orgUserEntity);

			//如果默认组织为空，则修改默认组织
			if(StringUtil.isNullOrEmpty(accountDTO.getDefaultCompanyId())){
				account.setDefaultCompanyId(companyId);
				accountDao.updateById(account);
			}

			//人加入组织中间表
			OrgUserEntity orgUser = new OrgUserEntity();
			orgUser.setId(StringUtil.buildUUID());
			orgUser.setOrgId(companyId);
			orgUser.setCompanyId(companyId);
			orgUser.setCuId(companyUserId);
			orgUser.setUserId(currUserId);
			orgUser.setCreateBy(dto.getAccountId());
			orgUserDao.insert(orgUser);

		}
		/************添加到组织群*********/
		//加入群组
		this.companyUserService.sendCompanyUserMessageFun(companyId, currUserId,"1");
		//通知协同
		this.collaborationService.pushSyncCMD_CU(companyId);
		//最后删除审核表中的数据
		int i = companyUserAuditDao.deleteById(dto.getId());

		if(i==1){
			this.sendMsg(isNewUser,company.getCompanyName(),dto.getCellphone(),dto.getAccountId());

			return new AjaxMessage().setCode("0").setInfo("处理成功");
		}else{
			return new AjaxMessage().setCode("1").setInfo("处理失败");
		}
	}
	@Value("${fastdfs.url}")
	protected String fastdfsUrl;
	@Autowired
	private UserAttachDao userAttachDao;
	/**
	 * 方法描述：待审核列表数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 *
	 * @param param(companyId,pageNumber,pageSize)
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String,Object> getCompanyUserAudit(Map<String,Object> param) throws Exception {
		if(null!=param.get("pageNumber")){
			int page=(Integer)param.get("pageNumber");
			int pageSize=(Integer) param.get("pageSize");
			param.put("startPage",page*pageSize);
			param.put("endPage",pageSize);
		}
		Map returnMap = new HashMap();
		List<CompanyUserAuditEntity> data = companyUserAuditDao.getCompanyUserAuditByCompanyId(param);
		for(CompanyUserAuditEntity companyUserAuditEntity : data){
			AccountEntity accountEntity = accountDao.getAccountByCellphoneOrEmail(companyUserAuditEntity.getCellphone());
			if(accountEntity != null){
				Map<String, Object> paramPass = new HashMap<String, Object>();
				paramPass.put("userId", accountEntity.getId());
				paramPass.put("attachType", "5");
				List<UserAttachEntity> listAttach = userAttachDao.getAttachByType(paramPass);
				if(listAttach.size()>0){
					companyUserAuditEntity.setFilePath(this.fastdfsUrl+listAttach.get(0).getFileGroup()+"/"+listAttach.get(0).getAttachPath());
				}
			}
		}

		int totalNumber = companyUserAuditDao.getCompanyUserAuditByCompanyIdCount(param);
		returnMap.put("data",data);
		returnMap.put("total", totalNumber);
		return returnMap;
	}
	/**
	 * 方法描述：待审核列表数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 *
	 * @param param(companyId,pageNumber,pageSize)
	 */
	@Override
	public int getCompanyUserNumAudit(Map<String,Object> param) throws Exception {

		int totalNumber = companyUserAuditDao.getCompanyUserAuditByCompanyIdCount(param);
		return totalNumber;
	}

	/**
	 * 方法描述：待审核列表数据条目数
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午2:39:17
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCompanyUserAuditByCompanyIdCount(Map<String,Object> param) throws Exception {
		return companyUserAuditDao.getCompanyUserAuditByCompanyIdCount(param);
	}

	/**
	 * 方法描述：发送短信【此方法不是接口】
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午8:05:44
	 */
	public void sendMsg(boolean isNewUser,String companyName,String cellphone,String accountId){
		Sms sms=new Sms();
		sms.addMobile(cellphone);
		//暂时屏蔽短信
		if(isNewUser)
		{
			sms.setMsg(StringUtil.format(SystemParameters.SHARE_INVITE_MSG_1,companyName,this.serverUrl));
		}else {
			sms.setMsg(StringUtil.format(SystemParameters.SHARE_INVITE_MSG_2,companyName));
		}
		smsSender.send(sms);
	}

}
