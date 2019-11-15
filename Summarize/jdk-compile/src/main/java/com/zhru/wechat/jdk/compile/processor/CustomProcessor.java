package com.zhru.wechat.jdk.compile.processor;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.util.List;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义编译过程器
 *
 * @Author zhru
 * @Date 2019-11-14
 **/
// 支持的注解
@SupportedAnnotationTypes(
        "com.zhru.wechat.jdk.compile.processor.annotation.Respository")
// 支持的jdk版本号（最低版本）
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomProcessor extends AbstractProcessor {
    //
    private final Properties properties = new Properties();

    private File outputFile;

    /**
     * 处理过程
     *
     * @param annotations 注解类型
     * @param roundEnv    文件集合
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator iterator = roundEnv.getRootElements().iterator();
        //　第一阶段：处理数据
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // 获取接口集合
            List<Type> interfaces = ((Symbol.ClassSymbol) element).getInterfaces();
            interfaces.stream()
                    .filter(this::filterType)       // 过滤java.io.Serializable类型的接口
                    .collect(Collectors.toList())   // 转为集合
                    .forEach(type ->
                            properties.setProperty(type.toString(),
                            element.toString()));   // 保存于java.util.Properties对象中
        }

        // 第二阶段：持久化数据
        if (roundEnv.processingOver()) {
            try {
                if (getOutputFile() == null) {
                    setDefaultOutputFile();
                }
                FileWriter writer = new FileWriter(getOutputFile());
                properties.store(writer, "Generate by CustomProcessor");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    private void setDefaultOutputFile() throws Exception {
        String outDirectory = Thread.currentThread().getContextClassLoader()
                .getResource(".").toURI().getPath();
        String userDir = System.getProperty("user.dir");
        String s = outDirectory.substring(userDir.length());
        String out = userDir + s.substring(0, s.indexOf("/", 1));
        String outPath = out + "/src/main/resources/META-INF/meta-data.properties";
        outputFile = new File(outPath);
        if (!outputFile.getParentFile().exists()) outputFile.getParentFile().mkdir();
    }

    /**
     * 过滤java.io.Serializable类型的接口
     *
     * @param type
     * @return
     */
    private boolean filterType(Type type) {
        return !type.asElement().toString().equals(Serializable.class.getName());
    }


}
