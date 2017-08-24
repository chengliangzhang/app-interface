package com.maoding.v2.project.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.ProjectSkyDriveDTO;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2ProjectSkyDriveController
 * 类描述：项目文档库
 * 作    者：MaoSF
 * 日    期：2016年7月8日-下午3:12:45
 */
@Controller
@RequestMapping("/v2/projectskydrive")
public class V2ProjectSkyDriveController extends BaseWSController {

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    /**
     * 方法描述：项目文档库
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:paraMap（projectId必须，pid为null，则顶级文档列表，pid不为空，查询项目路径为pid的所有子文档列表）
     * @return:
     */
    @RequestMapping("/getProjectSkyDrive")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getProjectSkyDrive(@RequestBody Map<String, Object> paraMap) throws Exception {
        return projectSkyDriverService.getSkyDrive(paraMap);
    }


    /**
     * 方法描述：创建文件夹（或许修改文件夹名）
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:
     * @return:
     */
    @RequestMapping("/saveOrUpdateFileMaster")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrUpdateFileMaster(@RequestBody ProjectSkyDriveDTO dto) throws Exception {
        return projectSkyDriverService.saveOrUpdateFileMaster(dto);
    }

    /**
     * 方法描述：删除文件夹/文件
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:
     * @return:
     */
    @RequestMapping("/deleteProjectSkyDrive")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteProjectSkyDrive(@RequestBody Map<String, Object> map) throws Exception {
        return projectSkyDriverService.deleteSysDrive((String) map.get("id"), (String) map.get("accountId"));
    }

    /**
     * 方法描述：文件上传（项目文档库）
     * 作者：MaoSF
     * 日期：2016/12/26
     *
     * @param:projectId，companyId必传，pid可以为null（如果为null，则上传到根目录下）
     * @return:
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean uploadFile(@RequestBody ProjectSkyDriveDTO dto) throws Exception {
        return this.projectSkyDriverService.uploadFile(dto.getMultipartFile(), dto.getPid(), dto.getProjectId(), dto.getCompanyId(), dto.getAccountId());
    }

}



