package com.zhru.wechat.jdk.extend.spi;

import com.zhru.wechat.jdk.extend.control.CustomControl;

import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

/**
 * Desp: 自定义的{@link java.util.spi.ResourceBundleControlProvider}
 * 2019-10-27 16:44
 * Created by zhru.
 */
public class CustomResourceBundleControlProvider implements ResourceBundleControlProvider {

    @Override
    public ResourceBundle.Control getControl(String baseName) {
        return new CustomControl();
    }
}
