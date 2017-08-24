package com.maoding.org.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.common.service.UploadService;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyAttachDao;
import com.maoding.org.dto.UploadFileDTO;
import com.maoding.org.dto.UploadFileListDTO;
import com.maoding.org.entity.CompanyAttachEntity;
import com.maoding.org.service.CompanyAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
@Service("companyAttachService")
public class CompanyAttachServiceImpl extends GenericService<CompanyAttachEntity>  implements CompanyAttachService{

	@Autowired
	private CompanyAttachDao companyAttachDao;

	@Autowired
	private UploadService uploadService;


	@Value("${company}")
	private String companyUrl;

	@Value("${server.url}")
	protected String serverUrl;

	/*@Autowired
	private ImGroupService imGroupService;*/

	@Override
	public Object uploadCompanyLogo(MultipartFile file,Map<String, String> param) throws Exception {
		String result="";
		String th0 = param.get("th0");
		String companyId=param.get("companyId");
		String userId=param.get("userId");
		String companyUrl=param.get("companyUrl");
		//String oldPath=param.get("oldPath");
		
		String filename=file.getOriginalFilename();
		String sourceExtendName = filename.substring(filename.lastIndexOf('.') + 1);//原始文件的扩展名(不包含“.”)
		
		String[] path = uploadService.getWorksResourceUrl(sourceExtendName, companyUrl);
		String[] thArr = new String[1];
        thArr[0]=th0+"-0-true-true-0";//thArr(输出名前缀(widthxheight)-wh-gp-isAll-1为不加前缀) 164X164
        result = uploadService.uploadImgWithByFdfs(file, path, thArr);
		
		//上传成功后，数据保存到数据库
        if(null!=result&&!"".equals(result)){
        	//先删除原有的logo数据
			if(null!=companyId&&!"".equals(companyId)) {
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("companyId", companyId);
				paraMap.put("fileType", "4");
				companyAttachDao.delCompanyAttachByParamer(paraMap);
			}
            CompanyAttachEntity attachEntity=new CompanyAttachEntity();
            attachEntity.setId(StringUtil.buildUUID());
            attachEntity.setFileName(file.getName());
            attachEntity.setFileType("4");
            attachEntity.setFilePath(result);
            attachEntity.setCompanyId(companyId);
            attachEntity.setCreateBy(userId);
            int res = companyAttachDao.insert(attachEntity);

            Map<String,Object> reMap = new HashMap<String, Object>();
            reMap.put("fastdfsUrl", param.get("fastdfsUrl"));
            reMap.put("attachEntity", attachEntity);
			//同步更新im图片
			updateImGroupImg(companyId,attachEntity.getFilePath());
            return new AjaxMessage().setCode("0").setInfo("上传成功").setData(reMap);
        }
        return new AjaxMessage().setCode("1").setInfo("上传失败");
	}

	/**
	 * 方法描述：上传公司logo
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午4:24:03
	 *
	 * @param file
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object uploadCompanyImg(MultipartFile file, Map<String, String> param) throws Exception {
		String result="";
		String companyId=param.get("companyId");
		String userId=param.get("userId");

		String _seq =param.get("seq");
		int seq = 0;
		if(!StringUtil.isNullOrEmpty(_seq)){
			seq = Integer.parseInt(_seq);
		}

		result = uploadService.uploadFileByFdfs(file);

		//上传成功后，数据保存到数据库
		if(null!=result&&!"".equals(result)){
			//先删除原有的logo数据
			if(null!=companyId&&!"".equals(companyId)) {
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("companyId", companyId);
				paraMap.put("fileType", "6");
				paraMap.put("seq", seq);
				companyAttachDao.delCompanyAttachByParamer(paraMap);
			}
			CompanyAttachEntity attachEntity=new CompanyAttachEntity();
			attachEntity.setId(StringUtil.buildUUID());
			attachEntity.setFileName(file.getName());
			attachEntity.setFileType("6");
			attachEntity.setSeq(seq);
			//attachEntity.setFilePath(result);
			attachEntity.setFileGroup(result.substring(0,6));
			attachEntity.setFilePath(result.substring(7));
			attachEntity.setCompanyId(companyId);
			attachEntity.setCreateBy(userId);
			companyAttachDao.insert(attachEntity);

			Map<String,Object> reMap = new HashMap<String, Object>();
			reMap.put("fastdfsUrl", param.get("fastdfsUrl"));
			reMap.put("attachEntity", attachEntity);
			return new AjaxMessage().setCode("0").setInfo("上传成功").setData(reMap);
		}
		return new AjaxMessage().setCode("1").setInfo("上传失败");
	}

	/**
	 * 方法描述：上传公司轮播图片（移动端使用）v2版本
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午4:24:03
	 *
	 * @param fileListDTO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseBean uploadCompanyImg(UploadFileListDTO fileListDTO) throws Exception {

		//先删除原有的logo数据
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("companyId", fileListDTO.getAppOrgId());
		paraMap.put("fileType", "6");
		companyAttachDao.delCompanyAttachByParamer(paraMap);
		int seq=1;
		for(UploadFileDTO dto:fileListDTO.getFileList()){
			CompanyAttachEntity attachEntity=new CompanyAttachEntity();
			BaseDTO.copyFields(dto,attachEntity);
			attachEntity.setId(StringUtil.buildUUID());
			attachEntity.setCompanyId(fileListDTO.getAppOrgId());
			attachEntity.setCreateBy(dto.getAccountId());
			attachEntity.setSeq(seq);
			attachEntity.setFileType("6");
			companyAttachDao.insert(attachEntity);
			seq++;
		}
		return ResponseBean.responseSuccess("保存成功");
	}

	private void updateImGroupImg(String companyId, String filePath) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("companyId", companyId);
		paraMap.put("filePath", filePath);
		//imGroupDao.updateImGroupImg(paraMap);
	}

	public List<CompanyAttachEntity> getCompanyAttachByParamer(Map<String, Object> map){
		return  companyAttachDao.getCompanyAttachByParamer(map);
	}

	/**
	 * 方法描述：获取公司轮播图片，先更新序号，再获取
	 * 作者：MaoSF
	 * 日期：2016/10/20
	 *
	 * @param map
	 * @param:
	 * @return:
	 */
	@Override
	public List<CompanyAttachEntity> getCompanyAttachImg(Map<String, Object> map) {
		//把其他轮播图片进行排序
		List<CompanyAttachEntity> list = this. getCompanyAttachByParamer(map);
		for(int i = 0;i<list.size();i++){
			list.get(i).setSeq(i+1);
			companyAttachDao.updateById(list.get(i));
		}
		return list;
	}

	/**
	 * 方法描述：删除公司轮播图片
	 * 作者：MaoSF
	 * 日期：2016/10/20
	 *
	 * @param map
	 * @param:id(删除对象的id),companyId(当前公司di)，fileType(文件的类型）
	 * @return:
	 */
	@Override
	public Object deleteCompanyAttach(Map<String, Object> map) throws Exception{
		String id = (String)map.get("id");
		//删除公司附件
		int data = companyAttachDao.deleteById(id);

		//把其他轮播图片进行排序
		List<CompanyAttachEntity> list = this. getCompanyAttachByParamer(map);
		for(int i = 0;i<list.size();i++){
			CompanyAttachEntity attachEntity = list.get(i);
			attachEntity.setSeq(i+1);
			companyAttachDao.updateById(attachEntity);
		}
		return new AjaxMessage().setCode("0").setInfo("删除成功");
	}

	/**
	 * 方法描述：交换两个图片的位置
	 * 作        者：wangrb
	 * 日        期：2015年11月19日-下午4:48:56
	 *
	 * @param map （companyId：必传，fileType：文件类型）
	 * @return
	 */
	@Override
	public Object changeCompanyAttachSeq(Map<String, Object> map) throws Exception{
		String id1 = (String)map.get("id1");
		String id2 = (String)map.get("id2");

		String seq1 = (String)map.get("seq1");
		String seq2 = (String)map.get("seq2");

		CompanyAttachEntity attachEntity1 = new CompanyAttachEntity();
		attachEntity1.setId(id1);

		if(!StringUtil.isNullOrEmpty(seq2)){
			attachEntity1.setSeq(Integer.parseInt(seq2));
		}
		companyAttachDao.updateById(attachEntity1);

		CompanyAttachEntity attachEntity2 = new CompanyAttachEntity();
		attachEntity2.setId(id2);

		if(!StringUtil.isNullOrEmpty(seq1)){
			attachEntity2.setSeq(Integer.parseInt(seq1));
		}
		companyAttachDao.updateById(attachEntity2);

		return new AjaxMessage().setCode("0").setInfo("处理成功");
	}

	/**
	 * 方法描述：生成公司二维码
	 * 作者：MaoSF
	 * 日期：2016/11/25
	 *
	 * @param companyId
	 * @param:
	 * @return:
	 */
	@Override
	public String createCompanyQrcode(String companyId) throws Exception {
		String url = this.serverUrl+"iAdmin/sys/shareInvitation/"+companyId;
		String result = this.uploadService.createQrcode(url,this.companyUrl);

		//上传成功后，数据保存到数据库
		if(null!=result&&!"".equals(result)){
			//先删除原有的二维码数据
			if(null!=companyId&&!"".equals(companyId)) {
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("companyId", companyId);
				paraMap.put("fileType", "7");
				companyAttachDao.delCompanyAttachByParamer(paraMap);
			}
			CompanyAttachEntity attachEntity=new CompanyAttachEntity();
			attachEntity.setId(StringUtil.buildUUID());
			attachEntity.setFileName("");
			attachEntity.setFileType("7");
			attachEntity.setFileGroup(result.substring(0,6));
			attachEntity.setFilePath(result.substring(7));
			attachEntity.setCompanyId(companyId);
			companyAttachDao.insert(attachEntity);
			return result;
		}

		return null;
	}

}
