package com.main.service.conditions.impl;

import com.main.domain.enums.EnvironmentVariable;
import com.main.service.conditions.EnvironmentCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Service
public class EnvironmentVariableVerifier implements EnvironmentCheck<Boolean, EnvironmentVariable> {

    @Override
    public Boolean checkEnvironmentVariables(Collection<EnvironmentVariable> variables) {
        log.info("Checking variables : {}", variables);
        return variables.stream()
                .peek(var -> log.info(var.name() + ": {}", System.getenv(var.name())))
                .allMatch(var -> Objects.nonNull(System.getenv(var.name())));
    }
}
