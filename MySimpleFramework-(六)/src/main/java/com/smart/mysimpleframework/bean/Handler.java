package com.smart.mysimpleframework.bean;


import java.lang.reflect.Method;

/*
*  封装Action信息
*  封装类 和方法
* */
public class Handler {

    /*
    *  Controller注解 类
    * */
    private Class<?> controllerClass;

    /*
     *  Action注解的 方法
     * */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
