package com.zhangrui.apollo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.zhangrui.apollo.entity.AnnotationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc 配置Apollo环境，启用Apollo
 *
 * @author nsc
 * @date 2018/6/8
 */


@Configuration
@EnableApolloConfig(value = {"application", "FX1.apollo"})
public class ApolloConfigBean {

    @Bean
    AnnotationBean annotationBean(){
        return new AnnotationBean();
    }
}
