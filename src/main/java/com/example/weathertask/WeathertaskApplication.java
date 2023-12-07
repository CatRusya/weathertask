package com.example.weathertask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WeathertaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeathertaskApplication.class, args);
    }

}
