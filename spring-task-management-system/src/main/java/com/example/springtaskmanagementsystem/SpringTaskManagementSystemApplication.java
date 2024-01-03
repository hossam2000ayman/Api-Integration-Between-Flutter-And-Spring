package com.example.springtaskmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringTaskManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTaskManagementSystemApplication.class, args);
    }

}
