package com.example.schedulerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulerAppDevelopProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerAppDevelopProjectApplication.class, args);
    }

}
