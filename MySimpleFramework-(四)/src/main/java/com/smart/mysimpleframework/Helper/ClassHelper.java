package com.smart.mysimpleframework.Helper;


import com.smart.mysimpleframework.Util.ClassUtil;
import com.smart.mysimpleframework.annotation.Controller;
import com.smart.mysimpleframework.annotation.Service;

import java.util.HashSet;
import java.util.Set;

/*
 *  类操作 助手类
 * */
public class ClassHelper {

    /*
     *  定义类集合(用于存放所加载的类）
     * */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    /*
     *  获取应用包下的所有类
     * */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /*
     *
     *  获取应用包名下所有Service类
     * */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }


    /*
     *  获取应用包下所有Controller类
     * */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /*
     *  获取应用包下所有Bean类 （包括： Service,Controller等）
     *
     * */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

}
