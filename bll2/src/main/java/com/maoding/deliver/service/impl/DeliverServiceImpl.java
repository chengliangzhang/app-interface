package com.maoding.deliver.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.util.StringUtils;
import com.maoding.deliver.dao.DeliverDao;
import com.maoding.deliver.dto.DeliverDTO;
import com.maoding.deliver.entity.DeliverEntity;
import com.maoding.deliver.service.DeliverService;
import com.maoding.mytask.dto.MyTaskQueryDTO;
import com.maoding.task.dao.ProjectTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/18
 * @description :
 */
@Service("deliverService")
public class DeliverServiceImpl extends GenericService<DeliverEntity> implements DeliverService {

    @Autowired
    private DeliverDao deliverDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Value("${fastdfs.url}")
    protected String fastdfsUrl;

    /**
     * @param query 交付任务查询条件
     * @return 交付任务列表
     * @author 张成亮
     * @date 2018/7/18
     * @description 查询交付任务
     **/
    @Override
    public List<DeliverDTO> listDeliver(MyTaskQueryDTO query) {
        List<DeliverDTO> list = deliverDao.listDeliver(query);
        if (!ObjectUtils.isEmpty(list)) {
            list.forEach(deliver -> {
                if (!ObjectUtils.isEmpty(deliver.getResponseList())){
                    deliver.getResponseList().forEach(response->{
                        if (!StringUtils.isEmpty(response.getFileFullPath())){
                            response.setFileFullPath(fastdfsUrl + response.getFileFullPath());
                        }
                    });
                }
                if (!StringUtils.isEmpty(deliver.getIssueId())){
                    String issuePath = projectTaskDao.getTaskParentName(deliver.getIssueId());
                    if (!StringUtils.isEmpty(issuePath)) {
                        deliver.setIssuePath(issuePath.replaceAll(" — ","/"));
                    }
                }
            });
        }
        return list;
    }
}
