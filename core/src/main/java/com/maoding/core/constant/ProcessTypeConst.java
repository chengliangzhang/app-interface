package com.maoding.core.constant;

/**
 * 流程类型定义
 */
public interface ProcessTypeConst {


    /*****流程类型中的key的定义方式：companyId_流程类型_type(companyId:组织id，流程类型：为以下定义的类型，type：1=自由流程，2=固定流程，3=分条件流程）)******/

    /**
     * 报销类型
     */
    String  PROCESS_TYPE_EXPENSE = "expense";

    /**
     * 费用申请类型
     */
    String  PROCESS_TYPE_COST_APPLY = "costApply";

    /**
     * 请假类型
     */
    String  PROCESS_TYPE_LEAVE = "leave";

    /**
     * 出差类型
     */
    String  PROCESS_TYPE_ON_BUSINESS = "onBusiness";

    String PROCESS_TYPE_FREE="free_process";

    /**
     * 项目费用申请
     */
    String PROCESS_TYPE_PROJECT_PAY_APPLY = "projectPayApply";

    /**
     * 无流程
     */
    Integer TYPE_NOT_PROCESS = 0;

    /**
     * 自由流程
     */
    Integer TYPE_FREE = 1;

    /**
     * 固定流程
     */
    Integer TYPE_FIXED = 2;

    /**
     * 分条件流程
     */
    Integer PROCESS_TYPE_CONDITION = 3;

    /**
     * 通过
     */
    String PASS = "1";

    /**
     * 不通过
     */
    String NOT_PASS = "0";
}
