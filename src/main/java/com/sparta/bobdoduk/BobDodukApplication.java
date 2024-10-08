package com.sparta.bobdoduk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaAuditing
public class BobDodukApplication {

    public static void main(String[] args) {
        SpringApplication.run(BobDodukApplication.class, args);
    }

}
