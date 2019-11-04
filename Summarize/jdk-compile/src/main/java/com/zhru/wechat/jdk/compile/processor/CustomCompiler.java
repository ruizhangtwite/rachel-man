package com.zhru.wechat.jdk.compile.processor;


import javax.tools.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * @Author zr
 * @Date 2019-11-04
 **/
public class CustomCompiler {

    private final String sourceDirectory;
    private final String outDirectory;

    private JavaCompiler compiler;
    private StandardJavaFileManager standardFileManager;

    public CustomCompiler(String sourceDirectory, String outDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.outDirectory = outDirectory;
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.standardFileManager = this.compiler
                .getStandardFileManager(null, null, null);
    }

    public boolean compile(String className, String extendName) throws Exception {
        String path = sourceDirectory
                + File.separator
                + className.replaceAll("\\.", File.separator)
                + "." + extendName;
        Iterable<? extends JavaFileObject> files = this.standardFileManager
                .getJavaFileObjectsFromFiles(Collections.singletonList(new File(path)));
        this.standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT,
                Arrays.asList(new File(outDirectory)));
        JavaCompiler.CompilationTask compilationTask = this.compiler.getTask(new PrintWriter(System.out), this.standardFileManager,
                null, null, null, files);

        compilationTask.setProcessors(Arrays.asList(new CustomProcessor()));
        return compilationTask.call();
    }
}
