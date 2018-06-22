package com.zhangrui.connection;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 * Desp:读取数据库配置文件，设计成单利模式
 * 2018-05-19 11:11
 * Created by zhangrui.
 */
public class ConfigManager {

    private InputStream inputStream = null;
    private PropertyResourceBundle resourceBundle = null;

    private static ConfigManager configManager = new ConfigManager("jdbc.properties");

    private ConfigManager(String configPath){
        try {
            inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(configPath);
            resourceBundle = new PropertyResourceBundle(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){

                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public String getProperty(String key){
        if (resourceBundle == null){
            throw new RuntimeException("配置文件加载异常");
        }
        return resourceBundle.getString(key);
    }


    public static ConfigManager getInstance(){
        return configManager;
    }

}
