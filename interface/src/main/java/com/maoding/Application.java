package com.maoding;


import com.maoding.version.entity.InterfaceVersionEntity;
import com.maoding.version.service.impl.InterfaceVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Wuwq on 2016/10/10.
 */
@Controller
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableJms
public class Application {

    @Autowired
    private InterfaceVersionService interfaceVersionService;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    @ResponseBody
    String home1() throws Exception{
        annotationScan();
        return "正在运行";
    }

    @RequestMapping("/version")
    @ResponseBody
    String home() throws Exception{
        annotationScan();
        return "执行成功";
    }

    @PostConstruct
    public  void annotationScan() throws IllegalAccessException {
        Field field = null;
        InterfaceVersionEntity entity = null;
        try {
            field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            Vector<Class> classes=(Vector<Class>) field.get(this.getClass().getClassLoader());
            List<Class> cl=new ArrayList<>(classes);
            Iterator<Class> itor=cl.iterator();
            while(itor.hasNext()){
                Class c=itor.next();
                if(c.getAnnotation(RequestMapping.class)!=null){
                    RequestMapping req= (RequestMapping)c.getAnnotation(RequestMapping.class);
                    String[] bath=req.path().length>0?req.path():req.value();
                    if(bath.length==0){
                        continue ;
                    }
                    Method[] ms=c.getDeclaredMethods();
                    for(Method m : ms){
                        RequestMapping rm=m.getAnnotation(RequestMapping.class);
                        if(rm==null){
                            continue ;
                        }
                        String[] bath2=rm.path().length>0?rm.path():rm.value();
                        if(bath2.length==0){
                            continue ;
                        }
                        //插入数据表
                        entity = new InterfaceVersionEntity();
                        entity.setClassName(c.getName());
                        entity.setInterfaceName(bath[0]+bath2[0]);
                        if(!bath[0].substring(bath[0].length()-1).equals("/") && !bath2[0].substring(0,1).equals("/")){
                            entity.setInterfaceName(bath[0] +"/"+ bath2[0]);
                        }
                        entity.setLastUseDate(new Date());
                        entity.setDeleted(0);
                        entity.setVersion(1);
                        entity.setUseCount(0);
                        if(entity.getInterfaceName().equals("/v2/task/transferTaskDesigner")){
                            System.out.println("22222222222");
                        }
                        try {
                            interfaceVersionService.saveInterface(entity);
                        }catch (Exception e){
                            logger.info("发生异常接口："+c.getName()+"   "+bath[0]+bath2[0]);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.print(entity.getClassName());
            System.out.print(entity.getInterfaceName());
            e.printStackTrace();
        }
    }
}
