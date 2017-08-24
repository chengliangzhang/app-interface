package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.TeamOperaterDTO;
import com.maoding.org.entity.TeamOperaterEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：departService
 * 类描述：部门           Service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:22:43
 */
public interface TeamOperaterService extends BaseService<TeamOperaterEntity>{

	/**
	 * 方法描述：移交管理员
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午9:13:48
	 * @param 
	 * @return
	 */
    AjaxMessage  transferSys(TeamOperaterDTO dto, String newPassword) throws Exception;


	/**
	 * 方法描述：移交企业负责人
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午9:13:48
	 * @param
	 * @return
	 */
	AjaxMessage  transferOrgManager(TeamOperaterDTO dto, String newPassword) throws Exception;

	/**
	 * 方法描述：保存系统管理员所以资料
	 * 作者：MaoSF
	 * 日期：2016/11/17
	 * @param:
	 * @return:
	 */
    AjaxMessage saveSystemManage(TeamOperaterEntity teamOperaterEntity) throws Exception;

	/**
	 * 方法描述：  根据（userId，companyId）查询
	 * 作        者：TangY
	 * 日        期：2016年7月12日-上午4:50:13
	 * @return
	 */
    TeamOperaterEntity getTeamOperaterByParam(String companyId, String userId)throws Exception;

	/**
	 * 修改管理员密码
	 * @param dto
	 * @return
	 * @throws Exception
     */
    AjaxMessage updateAdminPassword(TeamOperaterDTO dto) throws Exception;

	/**
	 * 移交管理员移动端
	 * @param dto
	 * @return
	 * @throws Exception
	 */
    AjaxMessage transferSysWS(TeamOperaterDTO dto, String newPassword) throws Exception;


	/**
	 * 方法描述：验证密码移交管理员密码
	 * 作        者：chenzhujie
	 * 日        期：2016年12/30
	 * @param
	 * @return
	 */
    ResponseBean verifySysPassword(TeamOperaterDTO dto) throws Exception;

}
