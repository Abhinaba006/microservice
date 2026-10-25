package com.ecom.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {

        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner printDb(MongoTemplate mongoTemplate) {
        return args -> System.out.println(">>> MONGO DB IN USE: " + mongoTemplate.getDb().getName());
    }

}
