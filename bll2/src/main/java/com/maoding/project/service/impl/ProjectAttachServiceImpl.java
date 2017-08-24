package com.maoding.project.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.common.service.UploadService;
import com.maoding.core.util.StringUtil;
import com.maoding.project.dao.ProjectAttachDao;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dto.FileDTO;
import com.maoding.project.entity.ProjectAttachEntity;
import com.maoding.project.service.ProjectAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：项目Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectAttachService")
public class ProjectAttachServiceImpl extends GenericService< ProjectAttachEntity>  implements ProjectAttachService {
	//项目dao
	@Autowired
	private ProjectDao projectDao;
	//数据字典dao
	//项目附件
	@Autowired
	private ProjectAttachDao projectAttachDao;
	//上传
	@Autowired
	private UploadService uploadService;//上传

	@Value("${fastdfs.url}")
	protected String fastdfsUrl;

	@Override
	public int insertAttach(ProjectAttachEntity entity) {
		List<ProjectAttachEntity> attachEntity =  projectAttachDao.selectByType(entity.getProjectId(), entity.getFileType());
		String path[] = new String[1];
		if(null!=attachEntity){
			path[0] = attachEntity.get(0).getFilePath();
		}

		projectAttachDao.deleteByProIdAndType(entity.getProjectId(), entity.getFileType());
		int result = projectAttachDao.insert(entity);
		if(result==1){
			if(path[0]!=null && !"".equals(path[0])){
				uploadService.delFileByFdfs(attachEntity.get(0).getFilePath());
			}
		}
		return result;
	}

	/**
	 * 方法描述：项目 设计依据文件上传
	 * 作者：MaoSF
	 * 日期：2016/7/29
	 *
	 * @param imgObj
	 * @param:
	 * @return:
	 */
	@Override
	public AjaxMessage uploadBasisFile(MultipartFile imgObj) throws Exception {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		String fileName=imgObj.getOriginalFilename();
//		fileName = new String(fileName.getBytes("utf-8"),"utf-8");
//		fileName = URLDecoder.decode(fileName,"UTF-8");
		String filePath = uploadService.uploadFileByFdfs(imgObj);
		if(filePath!=null){
			FileDTO dto = new FileDTO();
			dto.setFileName(fileName);
			dto.setFilePath(filePath);
			dto.setFastdfsUrl(fastdfsUrl);
			return new AjaxMessage().setCode("0").setInfo("上传成功").setData(dto);
		}
		return new AjaxMessage().setCode("1").setInfo("上传失败");
	}

	/**
	 * 方法描述：文件上传
	 * 作   者：LY
	 * 日   期：2016/8/11 19:37
	 */
	public AjaxMessage uploadFile(MultipartFile imgObj) throws Exception {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		String fileName=imgObj.getOriginalFilename();
//		fileName = new String(fileName.getBytes("utf-8"),"utf-8");
//		fileName = URLDecoder.decode(fileName,"UTF-8");
		String filePath = uploadService.uploadFileByFdfs(imgObj);
		if(filePath!=null){
			FileDTO dto = new FileDTO();
			dto.setFileName(fileName);
			dto.setFilePath(filePath);
			dto.setFastdfsUrl(fastdfsUrl);
			return new AjaxMessage().setCode("0").setInfo("上传成功").setData(dto);
		}
		return new AjaxMessage().setCode("1").setInfo("上传失败");
	}

	/**
	 * 方法描述：合同附件上传（修改该项目中）
	 * 作者：MaoSF
	 * 日期：2016/7/29
	 *
	 * @param file
	 * @param dto
	 * @param:
	 * @return:
	 */
	@Override
	public AjaxMessage uploadContractFile(MultipartFile file,FileDTO dto) throws Exception {

		String  filePath="";
		String fileName=file.getOriginalFilename();
		String currUserId = dto.getAccountId();
		String currCompanyId = dto.getParam().get("companyId");

		String filename=file.getOriginalFilename();
		String sourceExtendName = filename.substring(filename.lastIndexOf('.') + 1);//原始文件的扩展名(不包含“.”)

		String[] path = uploadService.getWorksResourceUrl(sourceExtendName, dto.getPathUrl());
		filePath = uploadService.uploadImgWithByFdfs(file, path, null);
		String fileNewName = fileName;
//		fileNewName = new String(fileNewName.getBytes("utf-8"),"utf-8");
//		fileNewName = URLDecoder.decode(fileNewName,"UTF-8");

		if(null!=filePath&&!"".equals(filePath)){
			dto.setFilePath(filePath);
			dto.setFileName(fileNewName);

			String projectId = dto.getParam().get("projectId");
			//如果projectId不为空，则是修改，如果为空，立项中上传，则不做数据更新处理
			if(!StringUtil.isNullOrEmpty(projectId)){
				ProjectAttachEntity attach=new ProjectAttachEntity();
				attach.setFileType("1");
				attach.setFilePath(filePath);
				attach.setProjectId(projectId);
				attach.setCompanyId(currCompanyId);
				attach.setFileName(fileNewName);
				attach.setCreateBy(currUserId);
				attach.setId(StringUtil.buildUUID());
				this.insertAttach(attach);
			}

			return new AjaxMessage().setCode("0").setInfo("上传成功").setData(dto);
		}
		return new AjaxMessage().setCode("1").setInfo("上传失败");
	}

}
