package com.zhangrui.apollo;

import com.google.common.base.Charsets;
import com.zhangrui.apollo.entity.AnnotationBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * desc
 *
 * @author nsc
 * @date 2018/6/8
 */
public class ApolloConfigMain {

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.zhangrui.apollo");
        AnnotationBean annotationBean = context.getBean(AnnotationBean.class);

        while (true) {
            System.out.print("> ");
            String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
            if (input == null || input.length() == 0) {
                continue;
            }
            input = input.trim();
            if (input.equalsIgnoreCase("quit")) {
                System.exit(0);
            }

            System.out.println(annotationBean);
        }

    }
}
