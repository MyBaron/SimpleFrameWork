package com.smart.mysimpleframework.Helper;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ClassHelperTest {

    @Test
    public void getClassSet() {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        classSet.forEach(s-> System.out.println(s.getSimpleName()));

    }

    @Test
    public void getServiceClassSet() {
    }

    @Test
    public void getControllerClassSet() {
    }

    @Test
    public void getBeanClassSet() {
    }
}