package com.zhru.wechat.jdk.compile.processor;


import com.sun.tools.javac.processing.PrintingProcessor;

import javax.tools.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * 自定义编译器{@link JavaCompiler}
 * @Author zr
 * @Date 2019-11-04
 **/
public class CustomCompiler {

    // 源文件基准目录
    private final String sourceDirectory;
    // 编译文件输出目录
    private final String outDirectory;

    //　编译器
    private JavaCompiler compiler;
    // 文件管理器
    private StandardJavaFileManager standardFileManager;

    public CustomCompiler(String sourceDirectory, String outDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.outDirectory = outDirectory;
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.standardFileManager = this.compiler
                .getStandardFileManager(null, null, null);
    }

    /**
     * 编译
     * @param name　名称，如果是类包含包名
     * @param extendName　扩展名
     * @return
     * @throws Exception
     */
    public boolean compile(String name, String extendName) throws Exception {
        String path = sourceDirectory
                + File.separator
                + name.replaceAll("\\.", File.separator)
                + "." + extendName;
        // 封装成文件集合
        Iterable<? extends JavaFileObject> files = this.standardFileManager
                .getJavaFileObjectsFromFiles(Collections.singletonList(new File(path)));

        //　设置文件编译时的options
        this.standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT,
                Arrays.asList(new File(outDirectory)));

        // 初始化编译
        JavaCompiler.CompilationTask compilationTask = this.compiler.getTask(new PrintWriter(System.out), this.standardFileManager,
                null, null, null, files);
        // 设置编译的过程
        compilationTask.setProcessors(Arrays.asList(new CustomProcessor(), new PrintingProcessor()));
        // 执行编译
        return compilationTask.call();
    }
}
