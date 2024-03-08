package ru.hubabank.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HubabankCoreApplication {

    public static void main(String[] args) {
        // HIaa
        SpringApplication.run(HubabankCoreApplication.class, args);
    }

}
