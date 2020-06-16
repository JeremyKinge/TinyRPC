package com.kingge.tinyrpc.entity;

import java.io.Serializable;

/**
 * 这个就是相当于rpc通信协议
 */
public class ClassInfo implements Serializable{
    private String ClassName;//调用的接口全类名
    private String methodName;//调用的方法名
    private Object[] args;//方法参数
    private Class[] clazzType;//接口类型

    public ClassInfo() {
    }

    public ClassInfo(String className, String methodName, Object[] args, Class[] clazzType) {
        ClassName = className;
        this.methodName = methodName;
        this.args = args;
        this.clazzType = clazzType;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setClazzType(Class[] clazzType) {
        this.clazzType = clazzType;
    }

    public String getClassName() {
        return ClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class[] getClazzType() {
        return clazzType;
    }
}
