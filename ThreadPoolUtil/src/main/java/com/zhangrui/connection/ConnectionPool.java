package com.zhangrui.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desp: 数据库连接池
 * 2018-05-19 11:48
 * Created by zhangrui.
 */
public class ConnectionPool{

    protected int maximumActive = 20;  //默认最大连接数
    protected int maximumIdle = 10;     //默认最大空闲数
    protected int maxWaitTime = 8;      //最大等待时间

    private AtomicInteger realActiveNum = new AtomicInteger(0);

    LinkedBlockingQueue<MyPooledConnHandler> active;
    LinkedBlockingQueue<MyPooledConnHandler> idle;

    public ConnectionPool(){
        active = new LinkedBlockingQueue<>(this.maximumActive);
        idle = new LinkedBlockingQueue<>(this.maximumIdle);
    }

    public ConnectionPool(int maxActive, int maxIdle){
        active = new LinkedBlockingQueue<>(maxActive);
        idle = new LinkedBlockingQueue<>(maxIdle);
    }

    /**
     * 获取数据库连接对象
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        /**
         *  1、从空闲队列中获取
         *  2、poll()获取第一个元素，没有的话返回null
         */
        MyPooledConnHandler handler = idle.poll();
        //如果空闲队列中有，则直接获取，并添加到Active队列
        if (handler != null){
            Connection connection = handler.getProxyConnection();
            boolean status = active.offer(handler);
            if (status){
                realActiveNum.incrementAndGet();
                return connection;
            }
        } else {
            /**
             * 如果空闲队列中没有
             * 第一种情况：Active队列未满
             * 1、新建一个Connection对象
             * 2、将该对象添加到Active队列
             * 第二种情况：Active队列已满
             * 等待
             */
            if (realActiveNum.incrementAndGet() <= maximumActive){ //线程安全
                Connection connection = DataSourceUtils.getConnection();
                MyPooledConnHandler tempHandler = new MyPooledConnHandler(this, connection);
                boolean status = active.offer(tempHandler);
                if (status){
                    return  tempHandler.getProxyConnection();
                } else {
                    realActiveNum.decrementAndGet();
                }
            } else {
                // Must wait
                try {
                    realActiveNum.decrementAndGet();
                    handler = idle.poll(maxWaitTime, TimeUnit.SECONDS);
                    if (handler != null){
                        Connection connection = handler.getProxyConnection();
                        boolean status = active.offer(handler);
                        if (status){
                            realActiveNum.incrementAndGet();
                            return connection;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
        throw new RuntimeException("没有足够的连接了");
    }


    /**
     * 执行close()方法时，并不是真的执行，而是借助代理对象，将Connection对象
     * 放到了空闲队列中
     * @param handler
     */
    public void releaseConnecttion(MyPooledConnHandler handler){
        if (handler == null){
            return;
        }

        boolean removeStatus = active.remove(handler);
        if (removeStatus){
            realActiveNum.decrementAndGet();

            //如果空闲队列还可以收集Connection
            if (idle.size() < maximumIdle){
                boolean status = idle.offer(handler);
                if (!status){
                    try {
                        handler.getRealConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    handler = null;
                    System.gc();
                }

            } else {
                try {
                    handler.getRealConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                handler = null;
                System.gc();
                realActiveNum.decrementAndGet();
            }
        } else {

        }
    }
}
