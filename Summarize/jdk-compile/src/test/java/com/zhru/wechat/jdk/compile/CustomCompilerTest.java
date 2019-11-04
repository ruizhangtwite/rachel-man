package com.zhru.wechat.jdk.compile;

import com.zhru.wechat.jdk.compile.processor.CustomCompiler;
import com.zhru.wechat.jdk.compile.processor.DefaultCrudRespository;

import java.io.File;
import java.net.URI;

/**
 * @Author zhru
 * @Date 2019-11-04
 **/
public class CustomCompilerTest {

    public static void main(String[] args) throws Exception{
        String sourceDirectory = System.getProperty("user.dir") + File.separator + "src/main/java";
        URI uri = Thread.currentThread().getContextClassLoader().getResource(".").toURI();

        CustomCompiler customCompiler = new CustomCompiler(sourceDirectory, uri.getPath());
        boolean result = customCompiler.compile(DefaultCrudRespository.class.getName(), "java");
        if (result){
            System.out.println("编译ok");
        }

    }
}
