package com.adobe.orderapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class OrderappApplication {
    @Autowired
    CacheManager cacheManager;
    public static void main(String[] args) {
        SpringApplication.run(OrderappApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return  builder.build();
    }
//    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "0 0/30 * * * *")
    public void clearCache() {
        System.out.println("scheduled to clear Cache...");
        cacheManager.getCacheNames().forEach(name -> {
            cacheManager.getCache(name).clear();
        });
    }
}
