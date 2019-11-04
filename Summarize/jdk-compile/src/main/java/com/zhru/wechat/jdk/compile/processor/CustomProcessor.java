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
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author zhru
 * @Date 2019-11-04
 **/
@SupportedAnnotationTypes({"com.zhru.processor.annotation.Respository"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomProcessor extends AbstractProcessor {
    private final Properties properties = new Properties();;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator iterator = roundEnv.getRootElements().iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            List<Type> interfaces = ((Symbol.ClassSymbol) element).getInterfaces();
            interfaces.stream()
                    .filter(this::generateType)
                    .collect(Collectors.toList())
                    .forEach(type -> properties.put(type.toString(), element.toString()));
        }

        if (roundEnv.processingOver()) {
            String path = System.getProperty("user.dir") + File.separator +
                    "src/main/resources/META-INF/meta-data.properties";
            try {
                FileWriter writer = new FileWriter(new File(path));
                properties.store(writer, "Generate by Generater");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private boolean generateType(Type type) {
        return !type.asElement().toString().equals(Serializable.class.getName());
    }


}
