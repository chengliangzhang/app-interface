package com.maoding.feedback.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.feedback.dao.FeedbackDao;
import com.maoding.feedback.dto.FeedbackDTO;
import com.maoding.feedback.entity.FeedbackEntity;
import com.maoding.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sandy on 2017/9/26.
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public ResponseBean saveFeedback(FeedbackDTO dto) throws Exception {
        FeedbackEntity entity = new FeedbackEntity();
        BaseDTO.copyFields(dto,entity);
        entity.setCreateBy(dto.getAccountId());
        entity.setCreateDate(DateUtils.getDate());
        entity.setId(StringUtil.buildUUID());
        entity.setHandle(false);
        this.feedbackDao.insert(entity);
        return ResponseBean.responseSuccess("保存成功");
    }
}
