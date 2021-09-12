package com.main.service.conditions.impl;

import com.main.domain.enums.EnvironmentVariable;
import com.main.jwsconfigrewriter.JwsConfigRewriterApplication;
import com.main.service.conditions.EnvironmentCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JwsConfigRewriterApplication.class)
class EnvironmentVariableCheckerTest {

    @Autowired
    private EnvironmentCheck<Boolean, EnvironmentVariable> checker;

    @Test
    void checkEnvironmentVariables() {
    }
}