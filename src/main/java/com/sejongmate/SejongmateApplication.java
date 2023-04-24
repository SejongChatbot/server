package com.sejongmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SejongmateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SejongmateApplication.class, args);
    }

}
