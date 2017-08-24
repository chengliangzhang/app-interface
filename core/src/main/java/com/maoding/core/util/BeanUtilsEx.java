package com.maoding.core.util;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import org.springframework.beans.BeanUtils;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/27.
 */
public class BeanUtilsEx extends BeanUtils {
    public static void copyProperties(final Map<String, Object> input, final Object output) {
        if (input == null || output == null)
            return;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(output.getClass(), Object.class);
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

            Iterator<String> it = input.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                Object value = input.get(key);
                for (PropertyDescriptor pty : properties) {
                    if (pty.getName().equals(key) && pty.getPropertyType().equals(value.getClass())){
                        pty.getWriteMethod().invoke(output, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void copyProperties(final Object input, final Map<String, Object> output) {
        if (input == null || output == null)
            return;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(input.getClass(), Object.class);
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pty : properties) {
                Object val = pty.getReadMethod().invoke(input);
                if (val != null) {
                    output.put(pty.getName(),val);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void copyProperties(final Object input, final Object output) {
        if (input == null || output == null)
            return;

        try {
            BeanInfo sourceBeanInfo = Introspector.getBeanInfo(input.getClass(), Object.class);
            PropertyDescriptor[] sourceProperties = sourceBeanInfo.getPropertyDescriptors();
            BeanInfo destBeanInfo = Introspector.getBeanInfo(output.getClass(), Object.class);
            PropertyDescriptor[] destProperties = destBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor sourceProperty : sourceProperties) {
                for (PropertyDescriptor destProperty : destProperties) {
                    if (sourceProperty.getName().equals(destProperty.getName()) &&
                            sourceProperty.getPropertyType().equals(destProperty.getPropertyType())) {
                        destProperty.getWriteMethod().invoke(output, sourceProperty.getReadMethod().invoke(input));
                        break;
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static ResponseBean convertToResponseBean(AjaxMessage msg) {
        String code = msg.getCode();
        String info = msg.getInfo().toString();
        Map<String, Object> data = new HashMap<>();
        BeanUtilsEx.copyProperties(msg.getData(), data);
        return (("0".equals(code)) ? ResponseBean.responseSuccess(info).setData(data) :
                ResponseBean.responseError(info));
    }

    /**
     * 读取Bean属性
     */
    public static Object getProperty(final Object obj, final String ptyName) {
        if ((obj == null) || (ptyName == null)) return null;

        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();

            for (PropertyDescriptor pty : properties) {
                if (pty.getName().equals(ptyName)){
                    return pty.getReadMethod().invoke(obj);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
