package com.sparta.vikingband;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VikingBandApplication {

    public static void main(String[] args) {
        SpringApplication.run(VikingBandApplication.class, args);
    }

}
