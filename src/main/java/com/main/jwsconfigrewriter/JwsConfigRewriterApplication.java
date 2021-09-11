package com.main.jwsconfigrewriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class JwsConfigRewriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwsConfigRewriterApplication.class, args);
        log.error("warn");
    }

}
