package com.smart.mysimpleframework;

import com.smart.mysimpleframework.Helper.BeanHelper;
import com.smart.mysimpleframework.Helper.ConfigHelper;
import com.smart.mysimpleframework.Helper.ControllerHelper;
import com.smart.mysimpleframework.Util.JsonUtil;
import com.smart.mysimpleframework.Util.ReflectionUtil;
import com.smart.mysimpleframework.bean.Data;
import com.smart.mysimpleframework.bean.Handler;
import com.smart.mysimpleframework.bean.Param;
import com.smart.mysimpleframework.bean.View;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

        /*
*  请求转发器
* */

@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    private static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    /*
    *  思路：
    *  1. 在初始化Servlet容器的时候，加载相关的Helper类，Jsp路径，静态资源路径.
    *  2. 从request获取到请求方法和路径
    *     -》根据请求方法和路径去Controller容器获取封装了类和方法的Handler对象
    *     -》从Handler对象里拿到类名，然后从Bean容器里获取到该类的实例
    *     -》从request获取参数，将所有参数存到Param对象里面
    *     -》从Handler对象拿到方法名，通过ReflectionUtil工具类调用方法（传入对象，方法名，参数）
    *     -》从调用方法返回的结果判断是View类型的就进行View处理，返回JSP页面，如果是Data类型的就返回JSON数据。
    *
    * */

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关的Helper
        HelperLoader.init();
        //获取ServletContext 对象
        ServletContext servletContext = config.getServletContext();
        //注册JSP路径
        ServletRegistration jsp = servletContext.getServletRegistration("jsp");
        jsp.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration def = servletContext.getServletRegistration("default");
        def.addMapping(ConfigHelper.getAppAssetPath() + "*");

        log.info("Init() success");

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取到请求方法和路径
        String method = req.getMethod().toLowerCase();
        //getPathInfo() 和getRequestURI() 去区别就是
        // getPathInfo 是从Servlet配置路径开始获取额外路径的。
        // getRequestURI 则是从端口号开始获取路径的
        String pathInfo = req.getPathInfo();
        //获取Handler对象
        Handler handler = ControllerHelper.getHandler(method, pathInfo);
        if (handler != null) {
            //获取类
            Class<?> controllerClass = handler.getControllerClass();
            //获取实例对象
            Object bean = BeanHelper.getBean(controllerClass);
            //从request获取参数
            Enumeration<String> parameterNames = req.getParameterNames();
            Map<String, Object> paramMap = new HashMap<>();
            while (parameterNames.hasMoreElements()) {
                String s = parameterNames.nextElement();
                String parameter = req.getParameter(s);
                paramMap.put(s, parameter);
            }
            //获取方法名
            Method actionMethod = handler.getActionMethod();
            Param param = new Param(paramMap);
            log.info("调用方法名： "+ actionMethod.getName());
            //调用方法
            Object result = ReflectionUtil.invokeMethod(bean, actionMethod, param);
            //判断是否是View
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    //如果是/开头，则是重定向
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        //转发
                        Map<String, Object> map = view.getModel();
                        for (Map.Entry<String, Object> m : map.entrySet()) {
                            req.setAttribute(m.getKey(),m.getValue());
                            log.info("key "+ m.getKey());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                    }
                } else if (result instanceof Data) {
                    //返回JSON数据
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if (model != null) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("utf-8");
                        PrintWriter writer = resp.getWriter();
                        String s = JsonUtil.toJson(model);
                        writer.write(s);
                        writer.flush();
                        writer.close();

                    }
                }
            }
        }
    }


}
