package com.zhru.wechat.jdk.compile;

import com.zhru.wechat.jdk.compile.processor.CustomCompiler;
import com.zhru.wechat.jdk.compile.processor.DefaultCrudRespository;
import com.zhru.wechat.jdk.compile.processor.bean.Person;

import java.io.File;
import java.net.URI;

/**
 * 测试用例
 *
 * @Author zhru
 * @Date 2019-11-04
 **/
public class CustomCompilerTest {

    public static void main(String[] args) throws Exception {
        String outDirectory = Thread.currentThread().getContextClassLoader()
                .getResource(".").toURI().getPath();
        String userDir = System.getProperty("user.dir");
        String s = outDirectory.substring(userDir.length());
        String sourceDirectory = userDir
                + s.substring(0, s.indexOf("/", 1))
                + "/src/main/java";

        CustomCompiler customCompiler = new CustomCompiler(sourceDirectory, outDirectory);
        boolean result = customCompiler.compile(Person.class.getName(), "java");
        if (result) {
            System.out.println("编译ok");
        }

    }
}
