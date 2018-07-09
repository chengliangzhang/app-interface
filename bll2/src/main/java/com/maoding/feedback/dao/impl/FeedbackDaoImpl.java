package com.maoding.feedback.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.feedback.dao.FeedbackDao;
import com.maoding.feedback.entity.FeedbackEntity;
import org.springframework.stereotype.Service;

/**
 * Created by sandy on 2017/9/26.
 */
@Service("feedbackDao")
public class FeedbackDaoImpl extends GenericDao<FeedbackEntity> implements FeedbackDao {
}
