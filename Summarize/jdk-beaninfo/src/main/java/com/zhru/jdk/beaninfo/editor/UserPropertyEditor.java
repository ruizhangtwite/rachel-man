package com.zhru.jdk.beaninfo.editor;

import java.beans.PropertyEditorSupport;

/**
 * 模拟转换{@link String} 到　{@link Integer}
 * @Author zhru
 * @Date 2019-10-24
 **/
public class UserPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Integer.valueOf(text));
    }
}
