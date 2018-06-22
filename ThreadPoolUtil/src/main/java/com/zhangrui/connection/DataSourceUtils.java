package com.zhangrui.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Desp:自定义数据库连接池，用于获取数据库连接对象
 * 2018-05-19 11:04
 * Created by zhangrui.
 */
public class DataSourceUtils {

    static {
        try {
            Class.forName(ConfigManager.getInstance().getProperty("jdbc.driver_Class"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(ConfigManager.getInstance().getProperty("jdbc.url"),
                    ConfigManager.getInstance().getProperty("jdbc.userName"),
                    ConfigManager.getInstance().getProperty("jdbc.password"));

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(String username, String password) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(ConfigManager.getInstance().getProperty("jdbc.url"),
                    username, password);

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
