package com.smart.mysimpleframework.Helper;


import com.smart.mysimpleframework.annotation.Action;
import com.smart.mysimpleframework.bean.Handler;
import com.smart.mysimpleframework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 *  控制类助手类
 * */
public class ControllerHelper {
    /*
     *  思路：
     *  1、遍历所有Controller 注解的类
     *  2、遍历每个类中的方法，找出有Action注解的方法
     *  3、从Action 注解中获取URL映射
     *  4、将URL映射存到Request对象，将该类和方法存到Handler对象
     *  5、存到Map集合中。 key---Request  value---Handler
     * */

    /*
     *  定义一个Map容器，存放Request和Handler的映射关系
     * */
    private final static Map<Request, Handler> ACTION_MAP =
            new HashMap<>();

    static{
        //获取所有具有Controoler注解的类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            //遍历类
            for (Class<?> c : controllerClassSet) {
                //获取该类下的所有方法
                Method[] declaredMethods = c.getDeclaredMethods();
                if (declaredMethods != null) {
                    //遍历方法
                    for (Method m : declaredMethods) {
                        if (m.isAnnotationPresent(Action.class)) {
                            Action action = m.getAnnotation(Action.class);
                            //获取Action注解中的URL值
                            String mapping = action.value();
                            //验证URL映射值
                            if (mapping.matches("\\w+:/\\w*")) {
                                //将URL分割成Method 和path
                                String[] split = mapping.split(":");
                                if (split != null && split.length == 2) {
                                    //获取请求方法与请求路径
                                    String method=split[0];
                                    String path = split[1];
                                    Request request = new Request(method, path);
                                    Handler handler = new Handler(c, m);
                                    //存进 Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }


            }
        }
    }


    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
