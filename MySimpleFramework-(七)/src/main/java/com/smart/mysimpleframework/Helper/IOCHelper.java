package com.smart.mysimpleframework.Helper;


import com.smart.mysimpleframework.Util.ReflectionUtil;
import com.smart.mysimpleframework.annotation.Inject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/*
*  依赖注入助手类
* */
public final class IOCHelper {

    static final Logger log = LoggerFactory.getLogger(IOCHelper.class);
    /*
    * 思路：
    * 从BeanHelper Bean容器里获取到所有Bean的映射关系。
    * 然后遍历这个容器。获取 类--key，实例--value
    * 检查类的成员变量是否有Inject注解，如果有，那么就对该变量注入实例
    *
    * */

    static {
        //获取到容器
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>,Object> beanEntity:beanMap.entrySet()) {
                Class<?> key = beanEntity.getKey();
                Object value = beanEntity.getValue();
                log.info("bean Map : class is  "+key.getSimpleName());
                //获取Bean 类定义的所有成员变量（简称Bean Field）
                Field[] declaredFields = key.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    for (Field field : declaredFields) {
                        if (field.isAnnotationPresent(Inject.class)) {

                            Class<?> type = field.getType();
                            log.info("class is  "+type.getSimpleName());
                            //在容器中获取该实例
                            Object o = beanMap.get(type);
                            if (o != null) {
                                ReflectionUtil.setField(value,field,o);
                            }
                        }

                    }
                }

            }
        }

    }

}
