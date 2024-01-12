package com.hungnln.vleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
//@EnableSwagger2
@SpringBootApplication

public class VleagueApplication {
    public static void main(String[] args) {
        SpringApplication.run(VleagueApplication.class, args);
    }

}
