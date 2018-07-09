package com.maoding.project.constDefine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class EnterpriseServer {

    @Value("${yongyoucloud.enterpriseUrl}")
    public  String yongyouCloudServerUrl;

    public static String URL_ENTERPRISE_QUERY_FULL = "/enterpriseSearch/queryFull";
    public static String URL_ENTERPRISE_QUERY_DETAIL = "/enterpriseSearch/queryDetail";
    public static String URL_ENTERPRISE_AUTO_COMPLETE = "/enterpriseSearch/queryAutoComplete";
    public static String URL_ENTERPRISE_GET_ENTERPRISE = "/enterpriseSearch/getEnterprise";
    public static String URL_ENTERPRISE_DETAIL = "/enterpriseSearch/queryEnterpriseDetail";
    public static String URL_ENTERPRISE_SAVE_LINKMAN = "/enterpriseLinkman/saveLinkman";
    public static String URL_ENTERPRISE_DELETE_LINKMAN  = "/enterpriseLinkman/deleteLinkman";

    @Bean
    public EnterpriseServer getImServer(@Value("${yongyoucloud.enterpriseUrl}") String yongyouCloudServerUrl) {
//        URL_ENTERPRISE_QUERY_DETAIL = yongyouCloudServerUrl +  "/enterpriseSearch/queryDetail";
//        URL_ENTERPRISE_QUERY_FULL =  yongyouCloudServerUrl +  "/enterpriseSearch/queryFull";
        return new EnterpriseServer();
    }

    public String getQueryFull(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_QUERY_FULL;
    }

    public String getQueryDetail(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_QUERY_DETAIL;
    }

    public String getAutoComplete(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_AUTO_COMPLETE;
    }

    public String getEnterprise(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_GET_ENTERPRISE;
    }

    public String getEnterpriseDetail(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_DETAIL;
    }

    public String getSaveLinkman(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_SAVE_LINKMAN;
    }

    public String getDeleteLinkman(){
        return yongyouCloudServerUrl +URL_ENTERPRISE_DELETE_LINKMAN;
    }
}
