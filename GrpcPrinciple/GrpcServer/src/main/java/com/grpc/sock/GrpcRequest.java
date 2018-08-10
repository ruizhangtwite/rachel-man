package com.grpc.sock;

import java.io.Serializable;

/**
 * Desp:
 * 2018-08-10 0:48
 * Created by zhangrui.
 */
public class GrpcRequest implements Serializable{
    private String className;
    private String methodName;
    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
