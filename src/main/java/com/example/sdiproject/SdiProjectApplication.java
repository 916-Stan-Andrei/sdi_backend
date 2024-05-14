package com.example.sdiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SdiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdiProjectApplication.class, args);
    }

}
