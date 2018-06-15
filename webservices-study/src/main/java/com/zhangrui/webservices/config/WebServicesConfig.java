package com.zhangrui.webservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Desp: WebServices配置，暴露WebService
 * 2018-06-15 19:13
 * Created by zhangrui.
 */
@Configuration
//@EnableWs  不可以加，WebServiceAutoConfiguration中有
public class WebServicesConfig extends WsConfigurerAdapter{

    @Bean("human")
    public DefaultWsdl11Definition humanWsd11Definition() {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("HumanServicePort");
        definition.setLocationUri("/humanService");
        definition.setTargetNamespace("http://com.zhangrui/com.zhangrui.webservices");
        definition.setSchema(xsdSchema());

        return definition;
    }
    
    @Bean
    public XsdSchema xsdSchema(){
        return new SimpleXsdSchema(new ClassPathResource("META-INF/human.xsd"));
    }
    
}
