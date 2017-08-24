package com.maoding.financial.dto;


import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpFixedInterfaceIdsDTO
 * 描    述 : ids
 * 作    者 : LY
 * 日    期 : 2016/8/4 10:59
 */
public class ExpFixedInterfaceDTO extends BaseDTO {

    private String appOrgId;

    private String accountId;

    private String expDate;

    private String expAmount1;

    private String expAmount2;

    private String expAmount3;

    private String expAmount4;

    private String expAmount5;

    private String expAmount6;

    private String expAmount7;

    private String expAmount8;

    private String expAmount9;

    private String expAmount10;

    private String expAmount11;

    private String expAmount12;

    private String expAmount13;

    private String expAmount14;

    private String remark1;

    private String remark14;


    private List<ExpFixedInterfaceIdsDTO> idsLists = new ArrayList<ExpFixedInterfaceIdsDTO>();

    private List<ExpFixedInterfaceIdsExpTypesDTO> expLists = new ArrayList<ExpFixedInterfaceIdsExpTypesDTO>();

    @Override
    public String getAppOrgId() {
        return appOrgId;
    }

    @Override
    public void setAppOrgId(String appOrgId) {
        this.appOrgId = appOrgId;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpAmount1() {
        return expAmount1;
    }

    public void setExpAmount1(String expAmount1) {
        this.expAmount1 = expAmount1;
    }

    public String getExpAmount2() {
        return expAmount2;
    }

    public void setExpAmount2(String expAmount2) {
        this.expAmount2 = expAmount2;
    }

    public String getExpAmount3() {
        return expAmount3;
    }

    public void setExpAmount3(String expAmount3) {
        this.expAmount3 = expAmount3;
    }

    public String getExpAmount4() {
        return expAmount4;
    }

    public void setExpAmount4(String expAmount4) {
        this.expAmount4 = expAmount4;
    }

    public String getExpAmount5() {
        return expAmount5;
    }

    public void setExpAmount5(String expAmount5) {
        this.expAmount5 = expAmount5;
    }

    public String getExpAmount6() {
        return expAmount6;
    }

    public void setExpAmount6(String expAmount6) {
        this.expAmount6 = expAmount6;
    }

    public String getExpAmount7() {
        return expAmount7;
    }

    public void setExpAmount7(String expAmount7) {
        this.expAmount7 = expAmount7;
    }

    public String getExpAmount8() {
        return expAmount8;
    }

    public void setExpAmount8(String expAmount8) {
        this.expAmount8 = expAmount8;
    }

    public String getExpAmount9() {
        return expAmount9;
    }

    public void setExpAmount9(String expAmount9) {
        this.expAmount9 = expAmount9;
    }

    public String getExpAmount10() {
        return expAmount10;
    }

    public void setExpAmount10(String expAmount10) {
        this.expAmount10 = expAmount10;
    }

    public String getExpAmount11() {
        return expAmount11;
    }

    public void setExpAmount11(String expAmount11) {
        this.expAmount11 = expAmount11;
    }

    public String getExpAmount12() {
        return expAmount12;
    }

    public void setExpAmount12(String expAmount12) {
        this.expAmount12 = expAmount12;
    }

    public String getExpAmount13() {
        return expAmount13;
    }

    public void setExpAmount13(String expAmount13) {
        this.expAmount13 = expAmount13;
    }

    public String getExpAmount14() {
        return expAmount14;
    }

    public void setExpAmount14(String expAmount14) {
        this.expAmount14 = expAmount14;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark14() {
        return remark14;
    }

    public void setRemark14(String remark14) {
        this.remark14 = remark14;
    }

    public List<ExpFixedInterfaceIdsDTO> getIdsLists() {
        return idsLists;
    }

    public void setIdsLists(List<ExpFixedInterfaceIdsDTO> idsLists) {
        this.idsLists = idsLists;
    }

    public List<ExpFixedInterfaceIdsExpTypesDTO> getExpLists() {
        return expLists;
    }

    public void setExpLists(List<ExpFixedInterfaceIdsExpTypesDTO> expLists) {
        this.expLists = expLists;
    }
}