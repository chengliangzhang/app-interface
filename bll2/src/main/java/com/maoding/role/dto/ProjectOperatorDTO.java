package com.maoding.role.dto;

public class ProjectOperatorDTO {

    //项目基本信息模块
    private int allProjectView;//项目总览
    private int projectEdit;   //项目编辑
    private int projectDelete; //项目删除
    private int projectMemberModule =1;//项目参与人员模块
    private int outerCooperationModule =1;//外部合作模块
    private int projectDocModule =1;//项目文档

    //签发模块
    private int issueModule = 1;
    private int issueView;//查看签发
    private int allIssueView;//查看签发总览
//    private int issueCreate;//创建签发任务
//    private int issueUpdate;//更新签发任务
//    private int issueDelete;//删除签发任务
//    private int issuePlanTimeEdit;//任务时间编辑
//    private int issueTaskPublish;//任务发布
//    private int operatorSet;//经营负责人设置
//    private int operatorAssistantSet;//经营助理设置

    //生产模块
    private int productModule = 1;
    private int productView;//查看生产
    private int allProductView;//查看生产总览
//    private int productCreate;//创建生产任务
//    private int productUpdate;//更新生产任务
//    private int productDelete;//删除生产任务
//    private int productPlanTimeEdit;//任务时间编辑
//    private int designerEdit;//设计人设置
//    private int taskResponsible;//任务负责人设置
//    //private int productTaskPublish;//任务发布
//    private int designDirectorSet;//设计负责人设置
//    private int designerAssistantSet;//经营助理设置

    //收支管理模块
    private int paymentModule;
    private int contractModule;//合同模块
//    private int contractAmountSet;//合同总金额设置
//    private int contractClauseSet;//合同条款设置（回款节点设置）
//    private int contractBackFeeSet;//回款金额设置
//    //private String contractPaidDayConfirm;//到款日期确认，此处带了任务，其值为任务的id


    //技术审查费管理模块
    private int technologyModule;//技术审查费
    private int technologyPayModule;//技术审查费模块（付款）
    private int technologyPaidModule;//技术审查费收款模块（收款）
//    private int technologyAmountSet;//合同总金额设置
//    private int technologyClauseSet;//合同条款设置（回款节点设置）
//    private int technologyBackFeeSet;//回款金额设置
    //private String contractPaidDayConfirm;//到款日期确认，此处带了任务，其值为任务的id

    //合作设计费管理模块
    private int cooperationModule;//合作设计模块
    private int cooperationPayModule;//合作设计模块（付款）
    private int cooperationPaidModule;//合作设计模块（收款）
//    private int cooperationAmountSet;//合作设计总金额设置
//    private int cooperationClauseSet;//合作设计条款设置（回款节点设置）
//    private int cooperationBackFeeSet;//合作设计回款金额设置

    private int otherModule;//其他费用模块
    //其他付款管理模块
    private int otherPayModule;//其他付款模块
//    private int otherPayAmountSet;//其他付款总金额设置
//    private int otherPayClauseSet;//其他付款条款设置（回款节点设置）
//    private int otherPayBackFeeSet;//其他付款回款金额设置

    //其他收款管理模块
    private int otherPaidModule;//合作设计费模块
//    private int otherPaidAmountSet;//合作设计费总金额设置
//    private int otherPaidClauseSet;//合作设计费条款设置（回款节点设置）
//    private int otherPaidBackFeeSet;//合作设计费回款金额设置


    public int getAllProjectView() {
        return allProjectView;
    }

    public void setAllProjectView(int allProjectView) {
        this.allProjectView = allProjectView;
    }

    public int getProjectEdit() {
        return projectEdit;
    }

    public void setProjectEdit(int projectEdit) {
        this.projectEdit = projectEdit;
    }

    public int getProjectDelete() {
        return projectDelete;
    }

    public void setProjectDelete(int projectDelete) {
        this.projectDelete = projectDelete;
    }

    public int getProjectMemberModule() {
        return projectMemberModule;
    }

    public void setProjectMemberModule(int projectMemberModule) {
        this.projectMemberModule = projectMemberModule;
    }

    public int getProjectDocModule() {
        return projectDocModule;
    }

    public void setProjectDocModule(int projectDocModule) {
        this.projectDocModule = projectDocModule;
    }

    public int getOuterCooperationModule() {
        return outerCooperationModule;
    }

    public void setOuterCooperationModule(int outerCooperationModule) {
        this.outerCooperationModule = outerCooperationModule;
    }

    public int getIssueModule() {
        return issueModule;
    }

    public void setIssueModule(int issueModule) {
        this.issueModule = issueModule;
    }

    public int getIssueView() {
        return issueView;
    }

    public void setIssueView(int issueView) {
        this.issueView = issueView;
    }

    public int getAllIssueView() {
        return allIssueView;
    }

    public void setAllIssueView(int allIssueView) {
        this.allIssueView = allIssueView;
    }

    public int getProductModule() {
        return productModule;
    }

    public void setProductModule(int productModule) {
        this.productModule = productModule;
    }

    public int getProductView() {
        return productView;
    }

    public void setProductView(int productView) {
        this.productView = productView;
    }

    public int getAllProductView() {
        return allProductView;
    }

    public void setAllProductView(int allProductView) {
        this.allProductView = allProductView;
    }

    public int getPaymentModule() {
        if(this.contractModule==1 || this.technologyModule==1 || this.cooperationModule==1 || this.otherModule==1){
            paymentModule = 1;
        }
        return paymentModule;
    }

    public void setPaymentModule(int paymentModule) {
        this.paymentModule = paymentModule;
    }

    public int getContractModule() {
        return contractModule;
    }

    public void setContractModule(int contractModule) {
        this.contractModule = contractModule;
    }

    public int getTechnologyModule() {
        return technologyModule;
    }

    public void setTechnologyModule(int technologyModule) {
        this.technologyModule = technologyModule;
    }

    public int getCooperationModule() {
        return cooperationModule;
    }

    public void setCooperationModule(int cooperationModule) {
        this.cooperationModule = cooperationModule;
    }

    public int getOtherModule() {
        return otherModule;
    }

    public void setOtherModule(int otherModule) {
        this.otherModule = otherModule;
    }

    public int getOtherPayModule() {
        return otherPayModule;
    }

    public void setOtherPayModule(int otherPayModule) {
        this.otherPayModule = otherPayModule;
    }

    public int getOtherPaidModule() {
        return otherPaidModule;
    }

    public void setOtherPaidModule(int otherPaidModule) {
        this.otherPaidModule = otherPaidModule;
    }

    public int getTechnologyPayModule() {
        return technologyPayModule;
    }

    public void setTechnologyPayModule(int technologyPayModule) {
        this.technologyPayModule = technologyPayModule;
    }

    public int getTechnologyPaidModule() {
        return technologyPaidModule;
    }

    public void setTechnologyPaidModule(int technologyPaidModule) {
        this.technologyPaidModule = technologyPaidModule;
    }

    public int getCooperationPayModule() {
        return cooperationPayModule;
    }

    public void setCooperationPayModule(int cooperationPayModule) {
        this.cooperationPayModule = cooperationPayModule;
    }

    public int getCooperationPaidModule() {
        return cooperationPaidModule;
    }

    public void setCooperationPaidModule(int cooperationPaidModule) {
        this.cooperationPaidModule = cooperationPaidModule;
    }
}
