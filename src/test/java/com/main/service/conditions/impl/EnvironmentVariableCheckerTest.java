package com.main.service.conditions.impl;

import com.main.JwsConfigRewriterApplication;
import com.main.domain.enums.EnvironmentVariable;
import com.main.service.conditions.EnvironmentCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JwsConfigRewriterApplication.class)
class EnvironmentVariableCheckerTest {

    @Autowired
    private EnvironmentCheck<Boolean, EnvironmentVariable> checker;

    @Test
    void checkEnvironmentVariables() {
        Boolean result = checker.checkEnvironmentVariables(Arrays.asList(
                EnvironmentVariable.JBOSS_HOME, EnvironmentVariable.RCLOGS_HOME));
        assertThat(result).isTrue();
    }
}