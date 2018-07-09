package com.maoding.feedback.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.feedback.dto.FeedbackDTO;

/**
 * Created by sandy on 2017/9/26.
 */
public interface FeedbackService{

    ResponseBean saveFeedback(FeedbackDTO dto) throws Exception;
}
