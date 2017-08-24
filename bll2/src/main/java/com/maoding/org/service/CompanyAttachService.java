package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.UploadFileListDTO;
import com.maoding.org.entity.CompanyAttachEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyAttachEntity
 * 类描述：（公司附件）Service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:22:43
 */
public interface CompanyAttachService extends BaseService<CompanyAttachEntity>{
	
	/**
	 * 方法描述：上传公司logo
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午4:24:03
	 * @return
	 * @throws Exception
	 */
	public Object uploadCompanyLogo(MultipartFile file,Map<String,String> param) throws Exception;


	/**
	 * 方法描述：上传公司轮播图片（移动端使用）
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午4:24:03
	 * @return
	 * @throws Exception
	 */
	public Object uploadCompanyImg(MultipartFile file,Map<String,String> param) throws Exception;


	/**
	 * 方法描述：上传公司轮播图片（移动端使用）v2版本
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午4:24:03
	 * @return
	 * @throws Exception
	 */
	public ResponseBean uploadCompanyImg(UploadFileListDTO fileListDTO) throws Exception;

	/**
	 * 方法描述：根据参数查询附件
	 * 作        者：wangrb
	 * 日        期：2015年11月19日-下午4:48:56
	 * @param map（companyId：必传，fileType：文件类型）
	 * @return
	 */
	public List<CompanyAttachEntity> getCompanyAttachByParamer(Map<String, Object> map);

	/**
	 * 方法描述：获取公司轮播图片，先更新序号，再获取
	 * 作者：MaoSF
	 * 日期：2016/10/20
	 * @param:
	 * @return:
	 */
	public List<CompanyAttachEntity> getCompanyAttachImg(Map<String, Object> map);


	/**
	 * 方法描述：删除公司轮播图片
	 * 作者：MaoSF
	 * 日期：2016/10/20
	 * @param:id(删除对象的id),companyId(当前公司di)，fileType(文件的类型）
	 * @return:
	 */
	public Object deleteCompanyAttach(Map<String, Object> map) throws Exception;


	/**
	 * 方法描述：交换两个图片的位置
	 * 作        者：wangrb
	 * 日        期：2015年11月19日-下午4:48:56
	 * @param map（companyId：必传，fileType：文件类型）
	 * @return
	 */
	public Object changeCompanyAttachSeq(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：生成公司二维码
	 * 作者：MaoSF
	 * 日期：2016/11/25
	 * @param:
	 * @return:
	 */
	public String createCompanyQrcode(String companyId) throws Exception;
}