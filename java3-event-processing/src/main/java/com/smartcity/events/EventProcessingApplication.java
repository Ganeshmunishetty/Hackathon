package com.smartcity.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class EventProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventProcessingApplication.class, args);
    }
}

