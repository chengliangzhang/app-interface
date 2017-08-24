package com.maoding.config;

import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.SmsSenderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/11/29.
 */
@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class SmsSendConfig {

    @Value("${SMS.type}")
    private String smsType;

    @Value("${SMS.url_cl}")
    private String smsUrlCl;

    @Value("${SMS.account}")
    private String smsAccount;

    @Value("${SMS.pswd}")
    private String smsPswd;

    @Value("${SMS.product}")
    private String smsProduct;

    @Value("${SMS.url_yp}")
    private String smsUrlyp;

    @Value("${SMS.extno}")
    private String smsExtno;

    @Value("${SMS.apikey}")
    private String smsApikey;

    @Value("${SMS.extend}")
    private String smsExtend;

    @Value("${SMS.uid}")
    private String smsUid;

    @Value("${SMS.callback_url}")
    private String smsCallbackUrl;

    @Bean
    public SmsSenderFactory getSmsSenderFactory(){
        Map<String,String>  initProperties=new HashMap<>();
        initProperties.put("url_cl",smsUrlCl);
        initProperties.put("account",smsAccount);
        initProperties.put("pswd",smsPswd);
        initProperties.put("product",smsProduct);
        initProperties.put("extno",smsExtno);
        initProperties.put("url_yp",smsUrlyp);
        initProperties.put("apikey",smsApikey);
        initProperties.put("extend",smsExtno);
        initProperties.put("uid",smsUid);
        initProperties.put("callback_url",smsCallbackUrl);
        SmsSenderFactory smsSenderFactory=SmsSenderFactory.getInstance(smsType);
        smsSenderFactory.setInitProperties(initProperties);
        return smsSenderFactory;
    }

    @Bean(name="smsSender")
    public SmsSender getSmsSender(SmsSenderFactory smsSenderFactory){
        SmsSender smsSender=   smsSenderFactory.createSmsSender();
        return smsSender;
    }

}
