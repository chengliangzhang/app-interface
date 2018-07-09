package com.maoding.projectcost.dto;

import java.math.BigDecimal;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：NoticeService
 * 类描述：发起金额的DTO（用于我的任务）
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
public class PaymentDataDTO {

    private String id;

    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 到款/付款日期
     */
    private String payDate;

    /**
     * 高亮标示（后台传递参数中与当前数据匹配的时候，高亮标示：1）
     */
    private int highLightFlag;

    private String handlerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public int getHighLightFlag() {
        return highLightFlag;
    }

    public void setHighLightFlag(int highLightFlag) {
        this.highLightFlag = highLightFlag;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
}
