package com.weilaios.iqxceqhnhubt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication()
public class NovotsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovotsApplication.class, args);
    }

}