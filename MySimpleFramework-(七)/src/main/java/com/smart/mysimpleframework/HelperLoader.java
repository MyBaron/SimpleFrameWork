package com.smart.mysimpleframework;


import com.smart.mysimpleframework.Helper.*;
import com.smart.mysimpleframework.Util.ClassUtil;

/*
*  加载相应的Helper类
* */
public final class HelperLoader {

    public  static void init() {
       Class<?>[] classList={
               ClassHelper.class,
               BeanHelper.class,
               ConfigHelper.class,
               ControllerHelper.class,
               IOCHelper.class
       };
        for (Class c : classList) {
            ClassUtil.loadClass(c.getName(), true);
        }
    }
}
