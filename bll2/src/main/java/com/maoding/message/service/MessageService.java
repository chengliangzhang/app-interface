package com.maoding.message.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.message.dto.QueryMessageDTO;
import com.maoding.message.dto.SendMessageDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.mytask.entity.MyTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/20.
 */
public interface MessageService extends BaseService<MessageEntity> {

    ResponseBean sendMessage(MessageEntity messageEntity) throws Exception;

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    ResponseBean sendMessage(String projectId, String companyId, String targetId, int messageType, String paramId,
                             String userId, String accountId, String content) throws Exception;

    /**
     * 通用的发送消息方法
     * @return: 发送失败原因，如果成功，返回null
     */
    <T> String sendMessage(T origin, T target, List<String> toUserIdList, String projectId, String companyId, String userId) throws Exception;

    ResponseBean sendMessage(List<MessageEntity> messageList) throws Exception;

    /**
     * 设校审完成消息
     * @param messageDTO
     */
    void sendMessageForProcess(SendMessageDTO messageDTO) throws Exception;
    /**
     * 任务负责人，设计负责人
     */
    void sendMessageForDesigner(SendMessageDTO messageDTO) throws Exception;

    /**
     * 经营负责人+经营助理
     */
    void sendMessageForProjectManager(SendMessageDTO messageDTO);

    void sendMessageForCopy(SendMessageDTO messageDTO);
    /**
     * 方法描述：获取消息
     * 作者：MaoSF
     * 日期：2017/3/17
     */
    ResponseBean getMessage(Map<String, Object> map) throws Exception;

    /**
     * 获取最新消息，公告，讨论组的信息
     */
    ResponseBean getLastMessage(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：根据关键字删除
     * 作者：MaoSF
     * 日期：2017/3/25
     */
    int deleteMessage(String field) throws Exception;

    /**
     * 根据参数查找（目前只用于判断某类消息是否已经发送给某人过）
     */
    List<MessageEntity> getMessageByParam(QueryMessageDTO dto);

    /**
     * 处理已读
     */
    ResponseBean readMessage(QueryMessageDTO dto);

    /**
     * 处理已读
     */
    ResponseBean clearMessage(QueryMessageDTO dto);
}
