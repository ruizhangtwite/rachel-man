package com.zhangrui.connection;

import org.apache.ibatis.reflection.ExceptionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Desp:自定义数据库连接对象，实现InvocationHandler,以重写Connection的close()方法；
 * close()方法执行时并不是关闭Connection连接对象，而是放回到连接池中
 * 2018-05-19 11:39
 * Created by zhangrui.
 */
public class MyPooledConnHandler implements InvocationHandler {

    private static final String CLOSE = "close";
    private static final Class<?>[] IFACES = new Class<?>[] { Connection.class };

    private ConnectionPool pool;
    private Connection realConnection;
    private Connection proxyConnection;

    public MyPooledConnHandler(ConnectionPool pool, Connection connnection){
        this.pool = pool;
        this.realConnection = connnection;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACES, this);
    }

    /**
     * 获取代理的数据库连接对象
     * @return
     */
    public Connection getProxyConnection(){
        return proxyConnection;
    }

    /**
     * 获取真实的Connecttion，便于直接关闭
     * @return
     */
    public Connection getRealConnection(){
        return realConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
            pool.releaseConnecttion(this);
            return null;
        } else {
            try {
                return method.invoke(realConnection, args);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        }
    }
}
