package com.maoding.project.service;
import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.project.dto.FileDTO;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectShowDTO;
import com.maoding.project.dto.ProjectTableDTO;
import com.maoding.project.entity.ProjectAttachEntity;
import com.maoding.project.entity.ProjectEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectAttachService extends BaseService<ProjectAttachEntity>{

    /**
     * 方法描述：文件记录插入
     * 作        者：wangrb
     * 日        期：2015年12月10日-下午5:05:35
     * @param entity
     * @return
     */
    public int insertAttach(ProjectAttachEntity entity);

    /**
     * 方法描述：项目 设计依据文件上传
     * 作者：MaoSF
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    public  AjaxMessage uploadBasisFile(MultipartFile imgObj) throws Exception;

    /**
     * 方法描述：文件上传
     * 作   者：LY
     * 日   期：2016/8/11 19:37
    */
    public AjaxMessage uploadFile(MultipartFile imgObj) throws Exception;

   /**
    * 方法描述：合同附件上传（修改该项目中）
    * 作者：MaoSF
    * 日期：2016/7/29
    * @param:
    * @return:
    */
    public AjaxMessage uploadContractFile(MultipartFile file, FileDTO dto) throws Exception;
}
