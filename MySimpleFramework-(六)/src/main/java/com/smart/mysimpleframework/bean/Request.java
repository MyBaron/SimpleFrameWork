package com.smart.mysimpleframework.bean;


import java.util.Objects;

/*
*
*   封装请求信息
*
*   思路：
*   1. 封装请求的信息， 正常的请求信息是: get:/customer_show
*      需要拆分成 requestMethod = get
*               requestPath  = /customer_show
*
*   2. 因为需要将请求信息放在Map中，以Request的对象为key，因为是对象作为key，
*       所以重写hashcode()和equals()方法，主要改变Map判断key的时候发挥作用。
* */
public class Request {

    /*
     *  请求方法
     * */
    private String requestMethod;

    /*
     *  请求路径
     * */
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }



    @Override
    public boolean equals(Object o) {
        if (o instanceof Request) {
            Request r = (Request) o;
            //进行判断
            if (r.getRequestMethod().equals(this.requestMethod) &&
                    r.getRequestPath().equals(this.requestPath)) {
                return true;
            }
        }

        return false;

    }


    // 主要思路就是利用两个变量的hashCode，而不是对象的hashCode。

    @Override
    public int hashCode() {

//        if (requestPath != null && requestMethod != null) {
//            String s = new String(requestMethod+requestPath);
//            return s.hashCode();
//        } else if (requestPath == null) {
//            return requestMethod.hashCode();
//        } else if (requestMethod == null) {
//            return requestPath.hashCode();
//        }
//        return 0;
        return Objects.hash(this.requestPath, this.requestMethod);
    }
}
