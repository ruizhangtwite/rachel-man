package com.zhru.wechat.jdk.compile.processor;


import com.sun.tools.javac.processing.PrintingProcessor;

import javax.annotation.processing.Processor;
import javax.tools.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

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
    // 编译Processor
    private final List<Processor> processors;
    // 编译监听器
    private DiagnosticListener<? super JavaFileObject> diagnosticListener;
    // 编译Options
    private final Iterable<String> options;

    //　编译器
    private JavaCompiler compiler;
    // 文件管理器
    private StandardJavaFileManager standardFileManager;

    public CustomCompiler(String sourceDirectory, String outDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.outDirectory = outDirectory;
        this.processors = new LinkedList<>();
        this.options = new LinkedList<>();
        // 1.创建编译器
        this.compiler = ToolProvider.getSystemJavaCompiler();
        // 2.创建FileManager
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

        // 3.设置文件编译时的options,指定输出目录，类似javac -d
        this.standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT,
                Arrays.asList(new File(outDirectory)));

        // 4.封装成文件集合
        Iterable<? extends JavaFileObject> files = this.standardFileManager
                .getJavaFileObjectsFromFiles(Collections.singletonList(new File(path)));


        // 5.初始化JavaCompiler.CompilationTask
        JavaCompiler.CompilationTask compilationTask = this.compiler.getTask(
                new PrintWriter(System.out),
                this.standardFileManager,
                this.diagnosticListener,
                this.options,
                null,
                files);
        // 6.设置编译的过程
        compilationTask.setProcessors(this.processors);
        // 7.执行编译
        return compilationTask.call();
    }

    /**
     * 设置{@link Processor}
     * @param processor
     */
    public void addProcessor(Processor processor) {

        if (processor != null) {
            this.processors.add(processor);
        }

    }

    /**
     * 设置编译诊断监听器
     * @param diagnosticListener
     */
    public void setDiagnosticListener(DiagnosticListener<? super JavaFileObject> diagnosticListener) {
        this.diagnosticListener = diagnosticListener;
    }
}
