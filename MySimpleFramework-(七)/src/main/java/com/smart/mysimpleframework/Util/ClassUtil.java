package com.smart.mysimpleframework.Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassUtil {

    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);


    /*
    *  获取类加载器
    * */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /*
    *  加载类
    * */

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls=null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    /*
    *  获取指定包名下的所有类
    * */

    public static Set<Class<?>> getClassSet(String packageName) {

        Set<Class<?>> classSet = new HashSet<>();

        try {
            // 获取到包名下所有类的URL
            Enumeration<URL> urls =
                    getClassLoader().getResources(packageName.replace(".", "/"));

            // 开始遍历
            while (urls.hasMoreElements()) {
                URL url=urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();

                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String name = jarEntry.getName();
                                    if (name.endsWith(".class")) {
                                        String className = name.substring(0, name.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet,className);
                                    }

                                }


                            }
                        }
                    }

                }

            }
        } catch (IOException e) {
            log.error("获取类失败",e);
            e.printStackTrace();
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        //在该路径下获取所有文件
        //FileFilter过滤器，只要class文件和文档。
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        //遍历每个文件
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                //去掉.class 后缀
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                //如果包名不是空的 则加上包名
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }

                //加载类
                doAddClass(classSet, className);
            } else {
                //这里是对file 是文件夹 进行的操作
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }

    }

    //真正的加载类
    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }



}
