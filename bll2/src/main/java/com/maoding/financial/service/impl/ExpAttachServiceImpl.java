package com.maoding.financial.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.HttpUtils;
import com.maoding.core.util.JsonUtils;
import com.maoding.financial.entity.ExpAttachEntity;
import com.maoding.financial.service.ExpAttachService;
import com.maoding.user.dto.UploadAttachDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpAttachServiceImpl
 * 描    述 : 报销附件Service
 * 作    者 : MaoSF
 * 日    期 : 2016/12/27-15:56
 */
@Service("expAttachService")
public class ExpAttachServiceImpl extends GenericService<ExpAttachEntity> implements ExpAttachService {

    @Value("${fastdfs.url}")
    protected String fastdfsUrl;




}
