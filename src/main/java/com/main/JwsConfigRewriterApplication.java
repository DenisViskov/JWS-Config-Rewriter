package com.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class JwsConfigRewriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwsConfigRewriterApplication.class, args);
        log.info(" --------------------------------------------------------------------------------------");
        log.info("|                                JWS-CONFIG-REWRITER V1.0                              |");
        log.info(" --------------------------------------------------------------------------------------");
    }

}
