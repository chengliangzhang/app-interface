package com.maoding.hxIm.constDefine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class ImServer {

    public static String URL_IM_SERVER;
    public static String URL_ACCOUNT_HANDLE = "/account/handle";
    public static String URL_GROUP_HANDLE = "/group/handle";

    @Bean
    public ImServer getImServer(@Value("${imServerUrl}") String imServerUrl) {
        URL_IM_SERVER = imServerUrl;
        URL_ACCOUNT_HANDLE = imServerUrl + URL_ACCOUNT_HANDLE;
        URL_GROUP_HANDLE = imServerUrl + URL_GROUP_HANDLE;
        return new ImServer();
    }
}
