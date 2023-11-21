package com.adobe.springdemo;

import com.adobe.springdemo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx  =
                SpringApplication.run(SpringDemoApplication.class, args);
        String[] beans = ctx.getBeanDefinitionNames();
        for(String bean: beans) {
            System.out.println(bean);
        }

        AppService service = ctx.getBean("appService", AppService.class);
        service.insert();
    }

}
